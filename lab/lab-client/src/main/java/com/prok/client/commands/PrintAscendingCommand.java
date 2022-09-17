package com.prok.client.commands;

import com.prok.common.entities.Collection;

public class PrintAscendingCommand implements Command {
    private final Collection collection;

    public PrintAscendingCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String arg) {
        if (arg != null) {
            throw new IllegalArgumentException("Эта команда не поддерживает аргументы.");
        }
        collection.sortByDistance();
        System.out.println(collection.toString());
    }
}
