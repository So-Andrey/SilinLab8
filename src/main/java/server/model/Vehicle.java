package server.model;

import com.google.gson.annotations.JsonAdapter;
import server.services.GsonLocalDateTime;

import java.time.LocalDateTime;

/**
 * The type Vehicle.
 */
public class Vehicle implements Comparable<Vehicle> {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    @JsonAdapter(GsonLocalDateTime.class)
    private LocalDateTime creationDate;
    private int enginePower;
    private int numberOfWheels;
    private VehicleType type;
    private FuelType fuelType;
    private String creatorName;

    /**
     * Instantiates a new Vehicle.
     *
     * @param name           the name
     * @param coordinates    the coordinates
     * @param creationDate   the creation date
     * @param enginePower    the engine power
     * @param numberOfWheels the number of wheels
     * @param type           the type
     * @param fuelType       the fuel type
     */
    public Vehicle(String name, Coordinates coordinates, LocalDateTime creationDate, int enginePower, int numberOfWheels, VehicleType type, FuelType fuelType) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.type = type;
        this.fuelType = fuelType;
    }

    /**
     * Instantiates a new Vehicle.
     *
     * @param id             the id
     * @param name           the name
     * @param coordinates    the coordinates
     * @param creationDate   the creation date
     * @param enginePower    the engine power
     * @param numberOfWheels the number of wheels
     * @param type           the type
     * @param fuelType       the fuel type
     */
    public Vehicle(Integer id, String name, Coordinates coordinates, LocalDateTime creationDate, int enginePower, int numberOfWheels, VehicleType type, FuelType fuelType) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.type = type;
        this.fuelType = fuelType;
    }

    /**
     * Instantiates a new Vehicle.
     */
    public Vehicle() {
        creationDate = LocalDateTime.now();
    }

    /**
     * Update vehicle.
     *
     * @param vehicle the vehicle
     * @return the vehicle
     */
    public Vehicle update(Vehicle vehicle) {
        name = vehicle.getName();
        coordinates = vehicle.getCoordinates();
        enginePower = vehicle.getEnginePower();
        numberOfWheels = vehicle.getNumberOfWheels();
        type = vehicle.getType();
        fuelType = vehicle.getFuelType();
        return this;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets coordinates.
     *
     * @param coordinates the coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Sets creation date.
     *
     * @param creationDate the creation date
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets engine power.
     *
     * @return the engine power
     */
    public int getEnginePower() {
        return enginePower;
    }

    /**
     * Sets engine power.
     *
     * @param enginePower the engine power
     */
    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    /**
     * Gets number of wheels.
     *
     * @return the number of wheels
     */
    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    /**
     * Sets number of wheels.
     *
     * @param numberOfWheels the number of wheels
     */
    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public VehicleType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(VehicleType type) {
        this.type = type;
    }

    /**
     * Gets fuel type.
     *
     * @return the fuel type
     */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * Sets fuel type.
     *
     * @param fuelType the fuel type
     */
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", enginePower=" + enginePower +
                ", numberOfWheels=" + numberOfWheels +
                ", type=" + type +
                ", fuelType=" + fuelType +
                ", Owner=" +creatorName +
                '}';
    }

    @Override
    public int compareTo(Vehicle o) {
        return enginePower - o.getEnginePower();
    }
}