package com.prok.server.socketwork;

import com.prok.common.network.DefaultSettings;
import com.prok.common.network.ObjSerializer;
import com.prok.common.network.Request;
import com.prok.common.network.Response;

import java.io.IOException;
import java.net.DatagramPacket;

public class SendResponse implements Runnable{
    SocketManager socketManager;
    Request request;
    Response response;

    public SendResponse(SocketManager socketManager, Request request, Response response) {
        this.socketManager = socketManager;
        this.request = request;
        this.response = response;
    }

    @Override
    public void run() {
        if(!socketManager.getRequestSocketAddresses().containsKey(request)) {
            return;
        }
        int packetSize = DefaultSettings.DEFAULT_PACKAGE_SIZE;
        byte[] dataToSend = ObjSerializer.toByteArray(response);

        if (dataToSend == null) {
            return;
        }

        if (dataToSend.length == 0) {
            return;
        }

        try {
            for (int i=0; i < (dataToSend.length-1) / packetSize + 1; i++) {
                int length = Math.min(dataToSend.length - i * packetSize, packetSize);
                DatagramPacket packet = new DatagramPacket(dataToSend, i * packetSize, length, socketManager.getRequestSocketAddresses().get(request));
                socketManager.getChannel().socket().send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
