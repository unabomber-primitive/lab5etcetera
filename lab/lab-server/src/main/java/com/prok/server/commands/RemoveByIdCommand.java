package com.prok.server.commands;

import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Collection;

import java.util.ConcurrentModificationException;

public class RemoveByIdCommand implements Command {
    private final Collection collection;

    public RemoveByIdCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public Response execute(Request request) {
        try {
            Integer id = Integer.parseInt(request.args[0]);
            collection.removeById(id, request.username);
            return new Response(true, "Элемент с указанным ID удален");
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return new Response(true, e.getMessage());
        } catch (Exception e) {
            return new Response(false,e.getMessage());
        }
    }
}
