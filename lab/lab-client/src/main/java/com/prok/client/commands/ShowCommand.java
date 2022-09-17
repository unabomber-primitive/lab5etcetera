package com.prok.client.commands;

import com.prok.common.entities.Collection;

public class ShowCommand implements Command {
    private final Collection collection;

    public ShowCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String arg) {
        if (arg != null) {
            throw new IllegalArgumentException("Эта команда не поддерживает аргументы.");
        }
        System.out.println(collection);
    }
}
