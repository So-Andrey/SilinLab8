package server.controller;

import server.model.Vehicle;

import java.util.List;
import java.util.Map;

/**
 * The interface Vehicle controller.
 */
public interface VehicleController {
    /**
     * Info string.
     *
     * @return the string
     */
    String info();

    /**
     * Gets vehicle by id.
     *
     * @param id the id
     * @return the vehicle by id
     */
    Vehicle getVehicleById(int id);

    /**
     * Gets all vehicle.
     *
     * @return the all vehicle
     */
    List<Vehicle> getAllVehicle();

    /**
     * Add vehicle integer.
     *
     * @param vehicle the vehicle
     * @return the integer
     */
    Integer addVehicle(Vehicle vehicle);

    /**
     * Update vehicle vehicle.
     *
     * @param vehicle the vehicle
     * @param id      the id
     * @return the vehicle
     */
    boolean updateVehicle(Vehicle vehicle, int id);

    /**
     * Remove vehicle by id.
     *
     * @param id the id
     */
    void removeVehicleById(int id);

    /**
     * Clear.
     */
    void clear();

    /**
     * Save.
     */
    void save();

    /**
     * Remove at.
     *
     * @param index the index
     */
    void removeAt(int index);

    /**
     * Remove lower.
     *
     * @param enginePower the engine power
     */
    void removeLower(int enginePower);

    /**
     * Reorder list.
     *
     * @return the list
     */
    List<Vehicle> reorder();

    /**
     * Min by number of wheels vehicle.
     *
     * @return the vehicle
     */
    Vehicle minByNumberOfWheels();

    /**
     * Group counting by name map.
     *
     * @return the map
     */
    Map<String, Integer> groupCountingByName();

    /**
     * Print ascending list.
     *
     * @return the list
     */
    List<Vehicle> printAscending();
    boolean checkUserPassword(String username, String password);
    void userRegister(String username, String password);

    List<String> getUserNameList();
}
