package server.dao;

import server.database.DataBaseProvider;
import server.model.Vehicle;
import server.services.CurrentUserManager;

import java.util.*;

/**
 * Класс для взаимодействия с базой данных.
 */
public class VehicleDAOImpl implements VehicleDAO {
    private final DataBaseProvider source;

    /**
     * Instantiates a new Vehicle dao.
     *
     * @param fileName the file name
     */
    public VehicleDAOImpl(String fileName) {
        this.source = new DataBaseProvider(fileName);
    }
    public VehicleDAOImpl(CurrentUserManager userManager) {
        this.source = new DataBaseProvider(userManager);
    }

    @Override
    public String info() {
        String answer = ("Данные о базе данных: \n");
        answer += "Тип: " + source.getDataList().getClass().getTypeName().split("\\.")[2] + "\n";
        answer += "Время создания: " + source.getCreationDate().toString() + "\n";
        answer += "Элементов внутри: " + (source.getDataList().size()) + "\n";
        return answer;
    }

    @Override
    public Vehicle getVehicleById(int id) {
        return null;
    }

    @Override
    public List<Vehicle> getAllVehicle() {
        return new ArrayList<>(source.getDataList());
    }

    @Override
    public Integer addVehicle(Vehicle vehicle) {
        return source.addVehicleToDataBase(vehicle);
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle, int id) {
        return source.updateVehicleInDataBase(vehicle, id);
    }

    @Override
    public void removeVehicleById(int id) {
        source.removeVehicleById(id);
    }

    @Override
    public void clear() {
        source.clearVehicles();
    }

    @Override
    public void save() {
        source.save();
    }

    @Override
    public void removeAt(int index) {
        source.getDataList().remove(index);
    }

    @Override
    public void removeLower(int enginePower) {
        source.getDataList().removeIf(v -> v.getEnginePower() < enginePower);
    }

    @Override
    public List<Vehicle> reorder() {
        Collections.reverse(source.getDataList());
        return source.getDataList();
    }

    @Override
    public Vehicle minByNumberOfWheels() {
        return source.getDataList().stream().min(Comparator.comparing(Vehicle::getNumberOfWheels)).orElse(null);
    }

    @Override
    public Map<String, Integer> groupCountingByName() {
        Map<String, Integer> response = new HashMap<>();
        for (Vehicle vehicle : source.getDataList()) {
            response.merge(vehicle.getName(), 1, Integer::sum);
        }
        return response;
    }

    @Override
    public List<Vehicle> printAscending() {
        return source.getDataList().stream().sorted().toList();
    }

    @Override
    public boolean checkUserPassword(String username, String password) {
        return source.checkUserPassword(username, password);
    }

    @Override
    public void userRegister(String username, String password) {
        source.userRegister(username, password);
    }

    @Override
    public List<String> getUserNameList() {
        return source.getUserNameList();
    }
}
