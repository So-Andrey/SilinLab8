package server.builders;

import java.util.Scanner;

/**
 * Класс строитель для Integer
 */
public class IntegerBuilder {
    /**
     * Выводит сообщений и обращает полученную строку в тип Integer. Иначе ловит NumberFormatException и вызывает метод снова.
     * Валидирует число, проверяя, чтобы оно было больше нуля.
     *
     * @param message the message
     * @return the int
     */
    public static int build(String message) {
        try {
            System.out.println(message);
            Scanner sc = new Scanner(System.in);
            int value = Integer.parseInt(sc.nextLine());
            if (value <= 0) {
                System.out.println("Значение должно быть больше нуля.");
                return build(message);
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Это поле принимает числовое значение.");
            return build(message);
        }
    }
}
