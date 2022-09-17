package com.prok.client.commands;

import com.prok.common.entities.Collection;

public class RemoveByIdCommand implements Command {
    private final Collection collection;

    public RemoveByIdCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String arg) {
        try {
            Integer id = Integer.getInteger(arg);
            collection.removeById(id);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
