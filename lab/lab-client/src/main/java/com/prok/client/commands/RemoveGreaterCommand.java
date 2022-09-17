package com.prok.client.commands;

import com.prok.common.entities.Collection;
import com.prok.common.entities.RouteFactory;

public class RemoveGreaterCommand implements Command {
    private final Collection collection;

    public  RemoveGreaterCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String arg) {
        if (arg != null) {
            throw new IllegalArgumentException("Эта команда не поддерживает аргументы.");
        }
        RouteFactory factory = new RouteFactory(collection.getIn());
        collection.removeGreater(factory.getRoute());
    }
}
