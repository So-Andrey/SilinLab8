package server.utils;

import server.model.FuelType;
import server.model.VehicleType;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Класс преобразователь.
 */
public class Parser {
    /**
     * Tilda resolver string.
     *
     * @param file the file
     * @return the string
     */
    public static String tildaResolver(String file) {
        if (file.startsWith("~")) {
            file = file.replaceFirst("^~", System.getProperty("user.home"));
        }
        return file;
    }

    /**
     * From string to fuel type fuel type.
     *
     * @param line the line
     * @return the fuel type
     */
    public static FuelType fromStringToFuelType(String line) {
        switch (line.toLowerCase()) {
            case "1", "gasoline" -> {
                return FuelType.GASOLINE;
            }
            case "2", "alcohol" -> {
                return FuelType.ALCOHOL;
            }
            case "3", "antimatter" -> {
                return FuelType.ANTIMATTER;
            }
            case "4", "plasma" -> {
                return FuelType.PLASMA;
            }
            default -> {
                return FuelType.GASOLINE;
            }
        }
    }

    /**
     * From string to vehicle type vehicle type.
     *
     * @param line the line
     * @return the vehicle type
     */
    public static VehicleType fromStringToVehicleType(String line) {
        switch (line.toUpperCase()) {
            case "1", "DRONE" -> {
                return VehicleType.DRONE;
            }
            case "2", "SUBMARINE" -> {
                return VehicleType.SUBMARINE;
            }
            case "3", "MOTORCYCLE" -> {
                return VehicleType.MOTORCYCLE;
            }
            case "4", "HOVERBOARD" -> {
                return VehicleType.HOVERBOARD;
            }
            case "5", "SPACESHIP" -> {
                return VehicleType.SPACESHIP;
            }
            default -> {
                return VehicleType.DRONE;
            }
        }
    }
    public static long parseLocalDateTimeToMillis(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    public static LocalDateTime parseMillisToLocalDateTime(long millis){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }
}
