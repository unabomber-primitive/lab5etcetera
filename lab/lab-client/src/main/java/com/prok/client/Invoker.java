package com.prok.client;


import com.prok.client.commands.Command;
import com.prok.common.entities.Collection;

import java.io.FileNotFoundException;
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


    public void invoke(String commandName, String arg) {
        Command command;
        try {
            command = getCommand(commandName);
            command.execute(arg);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }
}
