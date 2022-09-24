package com.prok.server.commands;

import com.prok.common.Command;

import java.util.NoSuchElementException;

public class ExitCommand implements Command {
    @Override
    public void execute(String arg) {
        if (arg != null) {
            throw new IllegalArgumentException("Эта команда не поддерживает аргументы.");
        }
        throw new NoSuchElementException();
    }
}
