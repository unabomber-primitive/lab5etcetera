package com.prok.server.commands;

import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Collection;

public class PrintAscendingCommand implements Command {
    private final Collection collection;

    public PrintAscendingCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public Response execute(Request request) {
        if (request.args != null) {
            return new Response(false, "Эта команда не поддерживает аргументы");
        }
        collection.sortByDistance();
        String out = collection.toString();
        return new Response(true, out);
    }
}
