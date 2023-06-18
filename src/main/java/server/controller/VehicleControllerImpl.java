package server.controller;

import server.dao.VehicleDAO;
import server.dao.VehicleDAOImpl;
import server.model.Vehicle;
import server.services.CurrentUserManager;

import java.util.List;
import java.util.Map;

/**
 * Класс для валидации данных, введенных пользовалем
 */
public class VehicleControllerImpl implements VehicleController {
    private VehicleDAO vehicleDAO;

    /**
     * Instantiates a new Vehicle controller.
     *
     * @param fileName the file name
     */
    public VehicleControllerImpl(String fileName) {
        this.vehicleDAO = new VehicleDAOImpl(fileName);
    }

    public VehicleControllerImpl(CurrentUserManager userManager) {
        this.vehicleDAO = new VehicleDAOImpl(userManager);
    }

    @Override
    public String info() {
        return vehicleDAO.info();
    }

    @Override
    public Vehicle getVehicleById(int id) {
        return vehicleDAO.getVehicleById(id);
    }

    @Override
    public List<Vehicle> getAllVehicle() {
        return vehicleDAO.getAllVehicle();
    }

    @Override
    public Integer addVehicle(Vehicle vehicle) {
        return vehicleDAO.addVehicle(vehicle);
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle, int id) {
        return vehicleDAO.updateVehicle(vehicle, id);
    }

    @Override
    public void removeVehicleById(int id) {
        vehicleDAO.removeVehicleById(id);
    }

    @Override
    public void clear() {
        vehicleDAO.clear();
    }

    @Override
    public void save() {
        vehicleDAO.save();
    }

    @Override
    public void removeAt(int index) {
        vehicleDAO.removeAt(index);
    }

    @Override
    public void removeLower(int enginePower) {
        vehicleDAO.removeLower(enginePower);
    }

    @Override
    public List<Vehicle> reorder() {
        return vehicleDAO.reorder();
    }

    @Override
    public Vehicle minByNumberOfWheels() {
        return vehicleDAO.minByNumberOfWheels();
    }

    @Override
    public Map<String, Integer> groupCountingByName() {
        return vehicleDAO.groupCountingByName();
    }

    @Override
    public List<Vehicle> printAscending() {
        return vehicleDAO.printAscending();
    }

    @Override
    public boolean checkUserPassword(String username, String password) {
        return vehicleDAO.checkUserPassword(username, password);
    }

    @Override
    public void userRegister(String username, String password) {
        vehicleDAO.userRegister(username, password);
    }

    @Override
    public List<String> getUserNameList() {
        return vehicleDAO.getUserNameList();
    }

    /**
     * Gets vehicle dao.
     *
     * @return the vehicle dao
     */
    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    /**
     * Sets vehicle dao.
     *
     * @param vehicleDAO the vehicle dao
     */
    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }
}
