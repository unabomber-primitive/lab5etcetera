package com.prok.server;

import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Invoker;
import com.prok.server.socketwork.ReceiveRequest;
import com.prok.server.socketwork.SendResponse;
import com.prok.server.socketwork.SocketManager;

import java.util.concurrent.ThreadPoolExecutor;

public class RequestProcess implements Runnable{
    SocketManager socketManager;
    Request request;
//    ThreadPoolExecutor fixedTP;
//    private Invoker invoker;
//    private SocketManager socketManager;
//    private Request request;
//    Collection collection;

    public RequestProcess(SocketManager socketManager, Request request) {
        this.socketManager = socketManager;
        this.request = request;
    }


    @Override
    public void run() {
        Response response;
        Invoker invoker = socketManager.getInvoker();

        String[] args = request.args;
        String commandName = request.commandName;
//            try {
//                input = (" " + collection.getIn().nextLine()).split("\\s+");
//            } catch (NoSuchElementException e) {
//                System.out.println("Программа завершена");
//                break;
//            }

        try {
            if (args!=null && args.length > 1) {
                response = new Response(true, "Введены лишние аргументы");
            } else if (args!=null && args.length == 1) {
                //System.out.println(input[1] + input[2]);
                response = invoker.invoke(request);
            } else if (commandName != null && !"".equals(commandName)) {
                response = invoker.invoke(request);
            } else {
                response = new Response(true,"Вы не ввели команду");
            }
        } catch (Exception e) {
            response = new Response(true, e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        socketManager.getFixedTP().submit(new SendResponse(socketManager, request, response));
    }
}
