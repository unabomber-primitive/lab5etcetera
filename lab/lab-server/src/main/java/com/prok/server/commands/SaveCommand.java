package com.prok.server.commands;

import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Collection;
import com.prok.server.FileManager;

public class SaveCommand implements Command {
    private Collection collection;

    public SaveCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public Response execute(Request request) {
        if (request.args != null) {
            return new Response(false, "Эта команда не поддерживает аргументы");
        }
        try {
            FileManager.saveFile(collection);
            return new Response(true, "Файл сохранен");
        } catch (Exception e) {
            e.getMessage();
            return new Response(false, e.getMessage());
        }
    }
}
