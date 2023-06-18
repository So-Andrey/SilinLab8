package server.builders;

import server.model.VehicleType;

import java.util.Scanner;

import static server.utils.Parser.fromStringToVehicleType;

/**
 * Класс строитель для  Vehicle type.
 */
public class VehicleTypeBuilder {
    /**
     * Строит объект класса VehicleType, используя консольный ввод.
     *
     * @return the vehicle type
     */
    public static VehicleType build() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите тип транспорта: (1 - DRONE, 2 - SUBMARINE, 3 - MOTORCYCLE, 4 - HOVERBOARD, 5 - SPACESHIP, DEFAULT - SPACESHIP)");
        return fromStringToVehicleType(sc.nextLine());
    }
}
