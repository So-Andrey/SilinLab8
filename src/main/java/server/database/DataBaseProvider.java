package server.database;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.model.Coordinates;
import server.model.FuelType;
import server.model.Vehicle;
import server.model.VehicleType;
import server.services.CurrentUserManager;
import server.sql.SQLConnection;
import server.utils.Parser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static server.utils.Parser.parseLocalDateTimeToMillis;

public class DataBaseProvider {
    private final SQLConnection sqlConnection;
    private final LocalDateTime creationDate;
    private final String pepper = "*61&^dQLC(#";
    private CurrentUserManager userManager;
    private List<Vehicle> dataList;

    public DataBaseProvider(CurrentUserManager userManager) {
        this.sqlConnection = new SQLConnection();
        this.creationDate = LocalDateTime.now();
        this.userManager = userManager;
        this.dataList = loadDataBase();
    }
    public DataBaseProvider(String fileName) {
        this.sqlConnection = new SQLConnection();
        this.creationDate = LocalDateTime.now();
        this.dataList = loadDataBase();
    }
    public void save() {
        try (Writer writer = new FileWriter("database.csv")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(dataList, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String saltBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            stringBuilder.append((char) new Random().nextInt(33, 126));
        }
        return stringBuilder.toString();
    }

    public static String sha384encoding(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int addVehicleToDataBase(Vehicle vehicle){
        int id = -1;
        try {
            String query = "INSERT INTO vehicles VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING vehicle_id";
            PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
            statement.setString(1, vehicle.getName());
            statement.setInt(2, addCoordinatesToDB(vehicle.getCoordinates()));
            statement.setLong(3, parseLocalDateTimeToMillis(vehicle.getCreationDate()));
            statement.setInt(4, vehicle.getEnginePower());
            statement.setInt(5, vehicle.getNumberOfWheels());
            statement.setString(6, vehicle.getType().toString());
            statement.setString(7, vehicle.getFuelType().toString());
            statement.setString(8, userManager.getUserName());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt("vehicle_id");
            }

            statement.close();

            vehicle.setCreatorName(userManager.getUserName());
            vehicle.setId(id);

            dataList.add(vehicle);

            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateVehicleInDataBase(Vehicle vehicle, int id){
        try {
            String query = "UPDATE vehicles SET name = ?, coordinates_id = ?, " +
                    "engine_power = ?, number_of_wheels = ?, vehicle_type = ?, fuel_type = ?" +
                    "WHERE vehicle_id = ? AND  creator_name = ?";
            PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
            statement.setString(1, vehicle.getName());
            statement.setInt(2, addCoordinatesToDB(vehicle.getCoordinates()));
            statement.setInt(3, vehicle.getEnginePower());
            statement.setInt(4, vehicle.getNumberOfWheels());
            statement.setString(5, vehicle.getType().toString());
            statement.setString(6, vehicle.getFuelType().toString());
            statement.setInt(7, id);
            statement.setString(8, userManager.getUserName());

            int affectedRows = statement.executeUpdate();
            if (affectedRows <= 0){
                return false;
            }

            statement.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Vehicle getVehicleById(int id){
        Vehicle response = new Vehicle();
        if (findVehicleById(id)) {
            try {
                String query = "SELECT * FROM vehicles WHERE vehicle_id = ?";
                PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
                statement.setInt(1, id);

                ResultSet rs = statement.executeQuery();
                while (rs.next()){
                    response.setId(id);
                    response.setName(rs.getString("name"));
                    response.setCoordinates(getCoordinates(rs.getInt("coordinates_id")));
                    response.setCreationDate(Parser.parseMillisToLocalDateTime(rs.getLong("creation_time")));
                    response.setEnginePower(rs.getInt("engine_power"));
                    response.setNumberOfWheels(rs.getInt("number_of_wheels"));
                    response.setType(VehicleType.valueOf(rs.getString("vehicle_type")));
                    response.setFuelType(FuelType.valueOf(rs.getString("fuel_type")));
                    response.setCreatorName(rs.getString("creator_name"));
                }
                statement.close();

                return response;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public boolean findVehicleById(int id) {
        Set<Integer> idSet = new HashSet<>();
        try {
            String query = "SELECT vehicle_id FROM vehicles";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idSet.add(resultSet.getInt("vehicle_id"));
            }
            if (idSet.contains(id)) {
                return true;
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public boolean removeVehicleById(int id){
        if (!findVehicleById(id)){
            return false;
        } else {
            try {
                String query = "DELETE FROM vehicles WHERE creator_name = ? AND vehicle_id = ?";
                PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
                preparedStatement.setString(1, userManager.getUserName());
                preparedStatement.setInt(2, id);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows != 0) {
                    return true;
                }
                preparedStatement.close();
                return false;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public boolean clearVehicles(){
        int before = dataList.size();
        dataList.stream().filter(p -> p.getCreatorName().equals(userManager.getUserName()))
                .map(Vehicle::getId)
                .forEach(this::removeVehicleById);
        dataList.removeIf(p -> p.getCreatorName().equals(userManager.getUserName()));
        return (before - dataList.size()) > 0;
    }
    public Coordinates getCoordinates(int id) {
        Coordinates coordinates = new Coordinates();
        try {
            String query = "SELECT coordinate_x, coordinate_y FROM coordinates WHERE coordinates_id = ?";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                coordinates.setX(resultSet.getInt("coordinate_x"));
                coordinates.setY(resultSet.getInt("coordinate_y"));
            }
            preparedStatement.close();
            return coordinates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int addCoordinatesToDB(Coordinates coordinates) {
        int coordinatesId = -1;
        try {
            String queryId = "SELECT coalesce(MAX(coordinates_id) +1, 1) FROM coordinates";
            PreparedStatement statement = sqlConnection.getConnection().prepareStatement(queryId);
            ResultSet rs = statement.executeQuery();
            int id = 0;
            while (rs.next()){
                id = rs.getInt("coalesce");
                System.out.println(id);
            }
            statement.close();

            String query = "INSERT INTO coordinates VALUES (?, ?, ?) RETURNING coordinates_id";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setDouble(2, coordinates.getX());
            preparedStatement.setDouble(3, coordinates.getY());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                coordinatesId = resultSet.getInt("coordinates_id");
            }

            preparedStatement.close();
            return coordinatesId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vehicle> updateDataSet() {
        dataList = loadDataBase();
        return dataList;
    }

    private List<Vehicle> loadDataBase(){
        List<Vehicle> dbList = new ArrayList<>();
        try {
            String query = "SELECT * FROM vehicles";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Vehicle response = new Vehicle();
                response.setId(rs.getInt("vehicle_id"));
                response.setName(rs.getString("name"));
                response.setCoordinates(getCoordinates(rs.getInt("coordinates_id")));
                response.setCreationDate(Parser.parseMillisToLocalDateTime(rs.getLong("creation_time")));
                response.setEnginePower(rs.getInt("engine_power"));
                response.setNumberOfWheels(rs.getInt("number_of_wheels"));
                response.setType(VehicleType.valueOf(rs.getString("vehicle_type")));
                response.setFuelType(FuelType.valueOf(rs.getString("fuel_type")));
                response.setCreatorName(rs.getString("creator_name"));
                dbList.add(response);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dbList;
    }

    private String getPassword(String userName) {
        String pass = "";
        try {
            String query = "SELECT password FROM users WHERE user_name = ?";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userName);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pass = (resultSet.getString("password"));
            }
            preparedStatement.close();
            return pass;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void userRegister(String username, String password) {
        try {
            String salt = saltBuilder();
            String query = "INSERT INTO users VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, sha384encoding(pepper + password + salt));
            preparedStatement.setString(3, salt);


            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Ошибка! Ничего не изменилось.");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private String getSalt(String userName) {
        String salt = "";
        try {
            String query = "SELECT salt FROM users WHERE user_name = ?";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                salt = (resultSet.getString("salt"));
            }
            preparedStatement.close();
            return salt;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean checkUserPassword(String username, String password) {
        String passwordHash = sha384encoding(pepper + password + getSalt(username));
        assert passwordHash != null;
        return passwordHash.equals(getPassword(username));
    }
    public List<String> getUserNameList() {
        List<String> userList = new ArrayList<>();
        try {
            String query = "SELECT user_name FROM users";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(resultSet.getString("user_name"));
            }
            preparedStatement.close();
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void InfoOfCollection() {
        System.out.println("Тип коллекции: " + dataList.getClass()
                + " дата инициализации: " + creationDate
                + " количество элементов: " + dataList.size());
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public CurrentUserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(CurrentUserManager userManager) {
        this.userManager = userManager;
    }

    public List<Vehicle> getDataList() {
        return dataList;
    }
}
