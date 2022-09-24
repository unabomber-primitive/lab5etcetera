package com.prok.server.commands;

import com.prok.common.Command;
import com.prok.common.entities.Collection;

public class ClearCommand implements Command {
    private final Collection collection;

    public ClearCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String arg) {
        if (arg != null) {
            throw new IllegalArgumentException("Эта команда не поддерживает аргументы.");
        }
        collection.clear();
    }
}
