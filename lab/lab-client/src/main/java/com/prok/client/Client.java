package com.prok.client;

import com.prok.client.commands.*;

import com.prok.common.entities.Collection;
//import com.prok.common.entities.Coordinates;
import com.prok.common.util.FileManager;

import java.util.NoSuchElementException;

public final class Client {
    private Collection collection;
    private Invoker invoker;

    public Client(Collection collection) {
        if (collection == null) {
            collection = new Collection();
            FileManager.loadTo(collection);
        }
        this.collection = collection;
        this.invoker = invokerInit();
    }

    private Invoker invokerInit() {
        Command addCommand = new AddCommand(collection);
        Command saveCommand = new SaveCommand(collection);
        Command showCommand = new ShowCommand(collection);
        Command exitCommand = new ExitCommand();
        Command clearCommand = new ClearCommand(collection);
        Command filterGreaterThanDistanceCommand = new FilterGreaterThanDistanceCommand(collection);
        Command filterLessThanDistanceCommand = new FilterLessThanDistanceCommand(collection);
        Command helpCommand = new HelpCommand();
        Command infoCommand = new InfoCommand(collection);
        Command printAscendingCommand = new PrintAscendingCommand(collection);
        Command removeByIdCommand = new RemoveByIdCommand(collection);
        Command removeGreaterCommand = new RemoveGreaterCommand(collection);
        Command removeLastCommand = new RemoveLastCommand(collection);
        Command shuffleCommand = new ShuffleCommand(collection);
        Command updateCommand = new UpdateCommand(collection);
        Command executeScriptCommand = new ExecuteScriptCommand(collection);
        Invoker invoker = new Invoker(collection);
        invoker.register("add", addCommand);
        invoker.register("save", saveCommand);
        invoker.register("show", showCommand);
        invoker.register("exit", exitCommand);
        invoker.register("help", helpCommand);
        invoker.register("execute_script", executeScriptCommand);
        invoker.register("clear", clearCommand);
        invoker.register("filter_greater_than_distance", filterGreaterThanDistanceCommand);
        invoker.register("filter_less_than_distance", filterLessThanDistanceCommand);
        invoker.register("info", infoCommand);
        invoker.register("print_ascending", printAscendingCommand);
        invoker.register("remove_by_id", removeByIdCommand);
        invoker.register("remove_greater", removeGreaterCommand);
        invoker.register("remove_last", removeLastCommand);
        invoker.register("shuffle", shuffleCommand);
        invoker.register("update", updateCommand);
        return invoker;
    }

    public void startProcess() {
        final int maxOfArgs = 2;
        System.out.println("Программа запущена");

        while (true) {
            String[] input;
            try {
                input = (" " + collection.getIn().nextLine()).split("\\s+");
            } catch (NoSuchElementException e) {
                System.out.println("Программа завершена");
                break;
            }

            try {
                if (input.length > maxOfArgs + 1) {
                    System.out.println("Введены лишние аргументы");
                } else if (input.length == maxOfArgs + 1) {
                    invoker.invoke(input[1], input[2]);
                } else if (input.length == 2) {
                    invoker.invoke(input[1], null);
                } else {
                    System.out.println("Вы не ввели команду");
                }
            } catch (NoSuchElementException e) {
                System.out.println("\nПрограмма завершена");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
