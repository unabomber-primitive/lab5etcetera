package com.prok.server;

import com.prok.common.network.Response;
import com.prok.server.commands.*;

import com.prok.server.commands.Command;
import com.prok.server.database.DBManager;
import com.prok.server.database.DataBaseConnection;
import com.prok.server.socketwork.SocketManager;
//import com.prok.common.entities.Coordinates;

import java.io.IOException;
import java.sql.SQLException;

public final class ListenProcess {
    private Collection collection;
    private Invoker invoker;
    private SocketManager socketManager;
    public ListenProcess(Collection collection) {
        if (collection == null) {
            try {
                collection = new Collection(new DBManager(new DataBaseConnection().connect()));
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
            //FileManager.loadTo(collection);

        }
        this.collection = collection;
        this.invoker = invokerInit();
        try{
            this.socketManager = new SocketManager(this.collection, this.invoker);
        } catch (IOException e) {
            System.out.println("Проблема с сетью");
            e.printStackTrace();
            System.exit(0);
        }
    }

    private Invoker invokerInit() {
        Command logInCommand = new LogInCommand(collection);
        Command addUserCommand = new AddUserCommand(collection);
        Command addCommand = new AddCommand(collection);
        Command saveCommand = new SaveCommand(collection);
        Command showCommand = new ShowCommand(collection);
        //Command exitCommand = new ExitCommand();
        Command clearCommand = new ClearCommand(collection);
        Command filterGreaterThanDistanceCommand = new FilterGreaterThanDistanceCommand(collection);
        Command filterLessThanDistanceCommand = new FilterLessThanDistanceCommand(collection);
        Command helpCommand = new HelpCommand(collection);
        Command infoCommand = new InfoCommand(collection);
        Command printAscendingCommand = new PrintAscendingCommand(collection);
        Command removeByIdCommand = new RemoveByIdCommand(collection);
        Command removeGreaterCommand = new RemoveGreaterCommand(collection);
        Command removeLastCommand = new RemoveLastCommand(collection);
        Command shuffleCommand = new ShuffleCommand(collection);
        Command updateCommand = new UpdateCommand(collection);
        //Command executeScriptCommand = new ExecuteScriptCommand(collection);
        Invoker invoker = new Invoker(collection);
        invoker.register("logIn", logInCommand);
        invoker.register("addUser", addUserCommand);
        invoker.register("add", addCommand);
        invoker.register("save", saveCommand);
        invoker.register("show", showCommand);
        //invoker.register("exit", exitCommand);
        invoker.register("help", helpCommand);
        //invoker.register("execute_script", executeScriptCommand);
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

        try {
            socketManager.process();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        final int maxOfArgs = 2;
//        System.out.println("Программа запущена");
//
//        while (true) {
//            socketManager.getUpdates();
//
//
//            String[] args = request.args;
//            String commandName = request.commandName;
////            try {
////                input = (" " + collection.getIn().nextLine()).split("\\s+");
////            } catch (NoSuchElementException e) {
////                System.out.println("Программа завершена");
////                break;
////            }
//
//            try {
//                if (args!=null && args.length > 1) {
//                    socketManager.sendResponse(new Response(true, "Введены лишние аргументы"));
//                } else if (args!=null && args.length == 1) {
//                    //System.out.println(input[1] + input[2]);
//                    invoker.invoke(commandName, args[0]);
//                } else if (commandName != null && !"".equals(commandName)) {
//                    invoker.invoke(commandName, null);
//                } else {
//                    socketManager.sendResponse(new Response(true,"Вы не ввели команду"));
//                }
//            } catch (Exception e) {
//                socketManager.sendResponse(new Response(true, e.getMessage()));
//                System.out.println(e.getMessage());
//                e.printStackTrace();
//            }
//
//
//        }
    }
}
