package com.prok.client;

import com.prok.common.entities.Route;
import com.prok.common.entities.RouteFactory;
import com.prok.common.network.Request;
import com.prok.common.network.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private String username;
    private String passwd;
    private Scanner in;
    private SocketManager socketManager;
    ClientCommand execute_script;
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
        //sendUsernameAndPassword();
        processAuthorization();
        while (true) {
            String[] input;
            Response checkResponse;
            try {
                input = (" " + in.nextLine()).split("\\s+");

                checkResponse = checkCommandRequest(input);
                if (checkResponse == null) {
                    continue;
                }
//                if (!checkResponse.loggedInSuccess) {
//                    System.out.println("Не удалось авторизироваться.\n" +
//                            "Проверьте логин и пароль." +
//                            checkResponse.message);
//                    processAuthorization();
//                    continue;
//                }
                System.out.println(checkResponse.message);

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
                e.printStackTrace();
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
            } else if ("add".equals(input[1])) {
                System.out.println("Эта команда не поддерживает аргументы.");
                return null;
            } else {
                request = new Request(username, passwd, input[1], Arrays.copyOfRange(input, 2, input.length + 1));
            }
        } else if (input.length == 2) {
            if ("exit".equals(input[1])) {
                socketManager.closeConnection();
                System.out.println("Программа завершена");
                System.exit(0);
                return null;
            } else if ("add".equals(input[1])) {
                request = new Request(username, passwd, input[1], true, makeRoute());
            } else request = new Request(username, passwd, input[1], null);
        } else {
            System.out.println("Вы не ввели команду");
            return null;
        }
        //System.out.println(request);
        return socketManager.makeRequest(request);
    }

    public Boolean processResponse(Response response) {
        if (!response.success) {
            System.out.println("Неудачный запрос к серверу.");
            return false;
        }

        if (!response.message.isEmpty()) {
            //System.out.println(response.message);
        }
        return true;
    }

    public Route makeRoute() {
        RouteFactory routeFactory = new RouteFactory(in);
        return routeFactory.getRoute(username);
    }

    public Response sendUsernameAndPassword() {
        Boolean needCreateNewAccount = true;
        System.out.println("Вы не авторизировались.\n" +
                "Вы хотите войти в свою учетную запись (1), или создать новую (2)?\n" +
                "Введите 1 или 2:   ");
        while (true) {
            String[] input;
            try {
                input = (" " + in.nextLine()).split("\\s");
            } catch (NoSuchElementException e) {
                socketManager.closeConnection();
                System.out.println("Программа завершена");
                System.exit(0);
                return null;
            }
            if (input == null || input.length != 2 || (!"1".equals(input[1]) && !"2".equals(input[1]))){
                System.out.println("Введите 1 или 2:    ");
                continue;
            }

            needCreateNewAccount = "2".equals(input[1]);
            break;
        }
        while (true) {
            if (needCreateNewAccount) {
                try {
                    System.out.println("Введите логин: ");
                    String username = in.nextLine();
                    String passwd;
                    while (true) {
                        System.out.println("Введите пароль: ");
                        String passwd1 = in.nextLine();
                        if (!((" " + passwd1).split("\\s").length == 2)) {
                            System.out.println("Пароль не должен содержать пробельных символов.");
                            continue;
                        }
                        System.out.println("Повторите пароль: ");
                        String passwd2 = in.nextLine();
                        if (!passwd2.equals(passwd1)) {
                            System.out.println("Введенные пароли не совпадают.");
                            continue;
                        } else {
                            passwd = passwd2;
                            break;
                        }
                    }
                    this.username = username;
                    this.passwd = passwd;
                    Request request = new Request(this.username, this.passwd, "addUser", null);
                    return socketManager.makeRequest(request);
                } catch (NoSuchElementException e) {
                    socketManager.closeConnection();
                    System.out.println("Программа завершена");
                    System.exit(0);
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    System.out.println("Введите логин: ");
                    String username = in.nextLine();
                    System.out.println("Введите пароль: ");
                    String passwd = in.nextLine();
                    this.username = username;
                    this.passwd = passwd;
                    Request request = new Request(this.username, this.passwd, "logIn", null);
                    return socketManager.makeRequest(request);
                } catch (NoSuchElementException e) {
                    socketManager.closeConnection();
                    System.out.println("Программа завершена");
                    System.exit(0);
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean processAuthorization() {
        while (true) {
            Response response = sendUsernameAndPassword();
            if (!response.loggedInSuccess) {
                System.out.println("Не удалось авторизироваться.\n" +
                        "Проверьте логин и пароль." +
                        "\nСообщение от сервера: " + response.message);
                continue;
            }
            System.out.println(response.message);
            return true;
        }
    }
}
