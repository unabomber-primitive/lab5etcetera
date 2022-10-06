package com.prok.server.socketwork;

import com.prok.common.network.DefaultSettings;
import com.prok.common.network.ObjSerializer;
import com.prok.common.network.Request;
import com.prok.server.Collection;
import com.prok.server.Invoker;
import com.prok.server.RequestProcess;
import com.prok.server.Server;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ReceiveRequest implements Runnable{
    SocketManager socketManager;
//    ThreadPoolExecutor fixedTP;
//    ThreadPoolExecutor cachedTP;
//    DatagramChannel channel;
//    HashMap<Request, SocketAddress> requestSocketAddresses;
//    Collection collection;
//    Invoker invoker;

    public ReceiveRequest(SocketManager socketManager) {
        this.socketManager = socketManager;
    }


    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(DefaultSettings.DEFAULT_PACKAGE_SIZE);
        byte[] byteArray;
        SocketAddress address;
//      int bytesReceived = 0;
        while (true) {
            try{
                SocketAddress clientAddress = socketManager.getChannel().receive(buffer);
                System.out.println("upd");

                byteArray = buffer.array();
                buffer.clear();

                Request request = ObjSerializer.fromByteArray(byteArray);

                if (request == null) {
                    continue;
                }

                address = clientAddress;
                System.out.println(address);

                System.out.println(request.commandName);

                socketManager.getRequestSocketAddresses().put(request, clientAddress);

                socketManager.getCachedTP().submit(new RequestProcess(socketManager, request));
                //return request;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
