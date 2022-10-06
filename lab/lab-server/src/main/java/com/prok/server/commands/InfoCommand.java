package com.prok.server.commands;

import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Collection;
import com.prok.common.entities.Route;

public class InfoCommand implements Command {
    private final Collection collection;

    public InfoCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public Response execute(Request request) {
        if (request.args != null) {
            return new Response(false, "Эта команда не поддерживает аргументы");
        }
        String out = "Collection of entities info:\n"
                + "- Collection type: " + collection.getArrayList().getClass().getSimpleName() + "\n"
                + "- Initialisation date: " + collection.getInitialisationDate() + "\n"
                + "- Elements type: " + Route.class.getSimpleName() + "\n"
                + "- Elements count: " + collection.getSize();
        return new Response(true, out);
    }
}
