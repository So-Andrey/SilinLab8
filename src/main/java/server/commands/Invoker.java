package server.commands;

import server.commands.list.*;
import server.controller.VehicleController;
import server.controller.VehicleControllerImpl;
import server.exceptions.ArgumentException;
import server.exceptions.FileException;
import server.exceptions.ValidationException;
import server.services.CurrentUserManager;
import server.services.ScriptManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static server.utils.Parser.tildaResolver;


/**
 * Класс инвокер для паттерна команды. Вызывает метод execute от введеной строки, валидирует её, отлавливает ошибки возникающие при выполнении команд.
 * Содержит Map, где ключ -- название, а значение -- класс команды.
 *
 */
public class Invoker {
    private static Map<String, Command> commandMap = new HashMap<>();
    private final VehicleController controller;
    private final ScriptManager scriptManager;
    private final String fileName;
    private final CurrentUserManager userManager;
    private BufferedReader reader;

    /**
     * При создании сущности, создается контроллер, создается скрипт менеджер и обрабатывается путь до файла (filename)
     * Происходит инициализация Map'ы команд.
     *
     * @param filename the filename
     */
    public Invoker(String filename) {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.fileName = tildaResolver(filename);
        controller = new VehicleControllerImpl(fileName);
        scriptManager = new ScriptManager(null);
        userManager = null;
        init();
    }

    public Invoker(CurrentUserManager userManager) {
        this.controller = new VehicleControllerImpl(userManager);
        this.userManager = userManager;
        this.scriptManager = new ScriptManager(null);
        this.fileName = null;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        init();
    }

    /**
     * Метод инициализации команд, добавленные здесь, будут видны пользователю.
     */
    public void init() {
        addCommandToMap("help", new HelpCommand());
        addCommandToMap("exit", new ExitCommand());
        addCommandToMap("info", new InfoCommand(controller));
        addCommandToMap("clear", new ClearCommand(controller));
        addCommandToMap("show", new ShowCommand(controller));
        addCommandToMap("add", new AddCommand(controller));
        addCommandToMap("update", new UpdateCommand(controller, userManager));
        addCommandToMap("remove_by_id", new RemoveByIdCommand(controller, userManager));
        addCommandToMap("remove_at", new RemoveAtCommand(controller));
        addCommandToMap("remove_lower", new RemoveLowerCommand(controller));
        addCommandToMap("reorder", new ReorderCommand(controller));
        addCommandToMap("min_by_number_of_wheels", new MinByNumOfWheelsCommand(controller));
        addCommandToMap("print_ascending", new PrintAscendingCommand(controller));
        addCommandToMap("group_counting_by_name", new GroupCountingByNameCommand(controller));
        addCommandToMap("save", new SaveCommand(controller));
        addCommandToMap("execute_script", new ExecuteScriptCommand(this, scriptManager));
    }

    /**
     * Метод для обработки введеной строки:
     *  если первый элемент (по разбиению на пробелы) совпадает с названим существующей команды, то вызывается .execute у соответствующей команды
     *  если нет, то "Команда не найдена."
     *
     * Обработка ошибок: при вызове .execute у команды, могут вылетать ошибки ArgumentException etc.
     * Тут они обрабатываются и выводятся пользователю.
     *
     * @param input the input
     */
    public void execute(String input) {
        try {
            input = input.trim();
            String[] commandArray = input.split(" ");
            String command = commandArray[0];
            for (Map.Entry<String, Command> pair : Invoker.getCommandMap().entrySet()) {
                if (pair.getKey().equals(command)) {
                    pair.getValue().execute(commandArray);
                }
            }
            if (!Invoker.getCommandMap().containsKey(command)) {
                System.out.println("Команда не найдена.");
            }
        } catch (ArgumentException | ValidationException | FileException e) {
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Нет аргумента.");
        } catch (NoSuchElementException e) {
            System.out.println("Завешение программы...");
            System.exit(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets reader.
     *
     * @return the reader
     */
    public BufferedReader getReader() {
        return reader;
    }

    /**
     * Sets reader.
     *
     * @param reader the reader
     */
    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * Gets command map.
     *
     * @return the command map
     */
    public static Map<String, Command> getCommandMap() {
        return commandMap;
    }

    /**
     * Sets command map.
     *
     * @param commandMap the command map
     */
    public static void setCommandMap(Map<String, Command> commandMap) {
        Invoker.commandMap = commandMap;
    }

    /**
     * Add command to map.
     *
     * @param commandName the command name
     * @param command     the command
     */
    public void addCommandToMap(String commandName, Command command) {
        commandMap.put(commandName, command);
    }
}