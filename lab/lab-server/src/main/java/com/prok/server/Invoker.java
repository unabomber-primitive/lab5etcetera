package com.prok.server;


import com.prok.common.network.Request;
import com.prok.server.commands.Command;
import com.prok.common.network.Response;

import java.util.HashMap;

public class Invoker {
    private Collection collection;
    private HashMap<String, Command> commandMap;

    public Invoker(Collection collection) {
        this.collection = collection;
        this.commandMap = new HashMap<String, Command>();
    }

    private Command getCommand(String commandName) {
        Command a = commandMap.get(commandName);
        if (a == null) {
            throw new IllegalArgumentException("Не найдено такой команды");
        } else {
            return a;
        }
    }


    public Response invoke(Request request) {
        if (!request.commandName.equals("logIn") && !request.commandName.equals("addUser")){
            try {
                boolean logInSuccess = collection.getDbManager().checkUser(request.username, request.password);
                if (!logInSuccess) {
                    return new Response(false,false, "Вы не авторизировались");
                }
            } catch (Exception e) {
                return new Response(false, false, e.getMessage());
            }
        }
        Command command;
        try {
            command = getCommand(request.commandName);
            return command.execute(request);
        } catch (IllegalArgumentException e) {
            return new Response(true, e.getMessage());
        }
    }

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }
}
