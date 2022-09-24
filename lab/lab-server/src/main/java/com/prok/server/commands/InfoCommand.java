package com.prok.server.commands;

import com.prok.common.Command;
import com.prok.common.entities.Collection;
import com.prok.common.entities.Route;

import java.time.format.DateTimeFormatter;

public class InfoCommand implements Command {
    private final Collection collection;

    public InfoCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String arg) {
        if (arg != null) {
            throw new IllegalArgumentException("Эта команда не поддерживает аргументы.");
        }
        System.out.println("Collection of entities info:\n"
                + "- Collection type: " + collection.getArrayList().getClass().getSimpleName() + "\n"
                + "- Initialisation date: " + collection.getInitialisationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")) + "\n"
                + "- Elements type: " + Route.class.getSimpleName() + "\n"
                + "- Elements count: " + collection.getSize()
        );
    }
}
