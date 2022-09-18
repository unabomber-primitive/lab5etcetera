package com.prok.client.commands;

import com.prok.common.entities.Collection;
import com.prok.common.entities.RouteFactory;

public class UpdateCommand implements Command {
    private final Collection collection;

    public UpdateCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String arg) {
        try {
            Integer id = Integer.parseInt(arg);
            RouteFactory factory = new RouteFactory(collection.getIn());
            collection.replaceById(id, factory.getRoute());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
