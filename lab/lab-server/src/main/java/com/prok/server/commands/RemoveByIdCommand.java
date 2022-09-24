package com.prok.server.commands;

import com.prok.common.Command;
import com.prok.common.entities.Collection;

import java.util.ConcurrentModificationException;

public class RemoveByIdCommand implements Command {
    private final Collection collection;

    public RemoveByIdCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String arg) {
        try {
            Integer id = Integer.parseInt(arg);
            collection.removeById(id);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (ConcurrentModificationException e) {}
    }
}
