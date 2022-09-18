package com.prok.client;

import com.prok.common.entities.Collection;

import java.util.NoSuchElementException;

public final class Main {
    private Main(){
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        try {
            Client consoleClient = new Client(null);
            consoleClient.startProcess();
        } catch (NoSuchElementException e) {
            System.out.println("Программа завершена");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
