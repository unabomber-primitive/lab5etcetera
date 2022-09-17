package com.prok.client.commands;

import com.prok.common.entities.Collection;
import com.prok.common.entities.RouteFactory;

public class AddCommand implements Command {
    private final Collection collection;

    public AddCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String arg) {
        if (arg != null) {
            throw new IllegalArgumentException("Эта команда не поддерживает аргументы.");
        }
        RouteFactory factory = new RouteFactory(collection.getIn());
        collection.add(factory.getRoute());
        System.out.println("\nМаршрут добавлен в коллекцию.");
    }
}
