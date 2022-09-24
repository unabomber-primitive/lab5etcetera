package com.prok.client;

import com.prok.common.Command;
import com.prok.common.network.Request;
import com.prok.common.network.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private Scanner in;
    private SocketManager socketManager;
    Command execute_script;
    public Client() {
        in = new Scanner(System.in);
        try {
            socketManager = new SocketManager();
        } catch (IOException e) {
            System.out.println("Ошибка при создании канала.");
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public Scanner getIn() {
        return in;
    }

    public void setIn(Scanner in) {
        this.in = in;
    }

    public Boolean processCommands() {
        System.out.println("Программа запущена");
        while (true) {
            String[] input;
            Response checkResponse;
            try {
                input = (" " + in.nextLine()).split("\\s+");

                checkResponse = checkCommandRequest(input);
                if (checkResponse == null) {
                    continue;
                }

                Boolean success = processResponse(checkResponse);
                if (!success) {
                    return false;
                }
            } catch (NoSuchElementException e) {
                socketManager.closeConnection();
                System.out.println("Программа завершена");
                System.exit(0);
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Response checkCommandRequest(String[] input) {
        Request request;
        if (input.length >= 3) {
            if ("execute_script".equals(input[1])) {
                if (input.length == 3) {
                    execute_script.execute(input[2]);
                    return null;
                } else {
                    System.out.println("Вы неверно ввели аргументы для команды execute_script.");
                    return null;
                }
            } else {
                request = new Request(input[1], Arrays.copyOfRange(input, 2, input.length + 1));
            }
        } else if (input.length == 2) {
            if ("exit".equals(input[1])) {
                socketManager.closeConnection();
                System.out.println("Программа завершена");
                System.exit(0);
                return null;
            } else request = new Request(input[1], null);
        } else {
            System.out.println("Вы не ввели команду");
            return null;
        }
        return socketManager.makeRequest(request);
    }

    public Boolean processResponse(Response response) {
        if (!response.success) {
            System.out.println("Неудачный запрос к серверу.");
            return false;
        }

        if (!response.message.isEmpty()) {
            System.out.println(response.message);
        }
        return true;
    }
}
