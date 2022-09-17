package com.prok.client.commands;

import com.prok.common.entities.Collection;
import com.prok.common.util.FileManager;

public class SaveCommand implements Command {
    private Collection collection;

    public SaveCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String arg) {
        if (arg != null) {
            throw new IllegalArgumentException("Эта команда не поддерживает аргументы.");
        }
        try {
            FileManager.saveFile(collection);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
