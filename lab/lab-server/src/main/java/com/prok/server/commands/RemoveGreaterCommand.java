package com.prok.server.commands;

import com.prok.common.entities.RouteFactory;
import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Collection;

public class RemoveGreaterCommand implements Command {
    private final Collection collection;

    public  RemoveGreaterCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public Response execute(Request request) {
        if (request.args != null) {
            return new Response(false, "Эта команда не поддерживает аргументы");
        }
        try {
            collection.removeGreater(request.route);
            return new Response(true, "Все элементы, большие введенного, удалены");
        } catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}
