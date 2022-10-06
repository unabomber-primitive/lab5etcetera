package com.prok.server.commands;

import com.prok.common.entities.Route;
import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Collection;

public class ClearCommand implements Command {
    private final Collection collection;

    public ClearCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public Response execute(Request request) {
        if (request.args != null) {
            return new Response(false, "Эта команда не поддерживает аргументы");
            //throw new IllegalArgumentException("Эта команда не поддерживает аргументы.");
        }
        try {
            collection.clear(request.username);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage());
        }
        return new Response(true,"Коллекция очищена");
    }
}
