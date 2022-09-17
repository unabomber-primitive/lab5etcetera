package com.prok.client;

import com.prok.common.entities.Collection;

public final class Main {
    private Main(){
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        Client consoleClient = new Client(null);
        consoleClient.startProcess();
    }
}
