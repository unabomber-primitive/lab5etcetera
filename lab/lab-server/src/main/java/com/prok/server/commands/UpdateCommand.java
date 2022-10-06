package com.prok.server.commands;

import com.prok.common.entities.RouteFactory;
import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Collection;

public class UpdateCommand implements Command {
    private final Collection collection;

    public UpdateCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public Response execute(Request request) {
        try {
            Integer id = Integer.parseInt(request.args[0]);
            collection.replaceById(id, request.route);
            return new Response(true,"Объект коллекции обновлен");
        } catch (Exception e) {
            return new Response(false,e.getMessage());
        }
    }
}
