package com.prok.server.commands;

import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Collection;

public class AddUserCommand  implements Command{
    Collection collection;
    public AddUserCommand(Collection collection) {
        this.collection = collection;
    }
    @Override
    public Response execute(Request request) {
        boolean success;
        try {
            success = collection.getDbManager().addUser(request.username, request.password);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, false, e.getMessage());
        }
        if (!success) {
            return new Response(false,false,"Не удалось добавить пользователя");
        }

        return new Response(true, "Пользователь успешно добавлен");
    }
}
