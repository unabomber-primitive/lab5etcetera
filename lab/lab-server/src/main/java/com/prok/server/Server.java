package com.prok.server;

import java.util.NoSuchElementException;

public final class Server {
    private Server(){
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        while (true) {
            try {
                ListenProcess server = new ListenProcess(null);
                server.startProcess();

            } catch (NoSuchElementException e) {
                System.out.println("Сервер закрыт");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
