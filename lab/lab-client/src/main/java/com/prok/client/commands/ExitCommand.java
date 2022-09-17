package com.prok.client.commands;

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
