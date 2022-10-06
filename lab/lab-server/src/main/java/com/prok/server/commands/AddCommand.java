package com.prok.server.commands;

import com.prok.common.entities.Route;
import com.prok.common.entities.RouteFactory;
import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Collection;

public class AddCommand implements Command {
    private final Collection collection;

    public AddCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public Response execute(Request request) {
//        if (arg != null) {
//            throw new IllegalArgumentException("Эта команда не поддерживает аргументы.");
//        }
        Route route = request.route;
        try {
            collection.add(route);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new Response(false, "Не удалось добавить элемент в коллекцию");
        }
        return new Response(true, "Элемент добавлен в коллекцию");
        //System.out.println("\nМаршрут добавлен в коллекцию.");
    }
}
