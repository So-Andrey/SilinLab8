package server.builders;

import server.exceptions.ValidationException;
import server.model.Coordinates;

import java.util.Scanner;

/**
 * Класс строитель координат. Заполняет соответствующие поля класса Coordinates
 */
public class CoordinatesBuilder {
    /**
     * Build coordinates. Выводит сообщение для ввода определенной координаты и преобразует её в числовое значение методом Integer.parseInt
     * Строит объект класса координаты, используя консольный ввод.
     *
     * @return the coordinates
     */
    public static Coordinates build() {
        int x;
        int y;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите координату x");
            x = Integer.parseInt(sc.nextLine());
            System.out.println("Введите координату y");
            y = Integer.parseInt(sc.nextLine());
            if (y < -775) {
                throw new ValidationException("Значение y должно быть больше -775");
            }
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            return build();
        } catch (NumberFormatException e) {
            System.out.println(("Координаты принимают числовые значения"));
            return build();
        }
        return new Coordinates(x, y);
    }
}
