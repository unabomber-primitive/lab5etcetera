package com.prok.server.commands;

import com.prok.common.network.Request;
import com.prok.common.network.Response;

import java.util.NoSuchElementException;

public class ExitCommand implements Command {
    @Override
    public Response execute(Request request) {
//        if (arg != null) {
//            throw new IllegalArgumentException("Эта команда не поддерживает аргументы.");
//        }
//        throw new NoSuchElementException();
        return null;
    }
}
