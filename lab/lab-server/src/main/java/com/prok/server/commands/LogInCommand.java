package com.prok.server.commands;

import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Collection;

public class LogInCommand implements Command{
    private final Collection collection;
    public LogInCommand(Collection collection) {
        this.collection = collection;
    }
    @Override
    public Response execute(Request request) {
        boolean success;
        try {
            success = collection.getDbManager().checkUser(request.username, request.password);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, false, e.getMessage());
        }
        if (!success) {
            return new Response(false,false,"Не удалось авторизироваться.");
        }

        return new Response(true, "Авторизация успешна.");
    }
}
