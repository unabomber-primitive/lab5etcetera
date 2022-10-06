package com.prok.client;

import com.prok.common.network.DefaultSettings;
import com.prok.common.network.ObjSerializer;
import com.prok.common.network.Request;
import com.prok.common.network.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class SocketManager{
    SocketAddress address;
    DatagramSocket socket;


    public SocketManager() throws IOException {
        socket = new DatagramSocket();

        address = new InetSocketAddress("localhost", 13333);
    }

    public void connectToServer() {
        try {
            socket.connect(address);

            System.out.println("connected");
        } catch (Exception e) {
            System.out.println("Проблемы с сетью, не удалось подключиться к серверу.");
        }
    }

    public void closeConnection() {
        if (socket.isConnected()) {
            try {
                socket.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Response makeRequest(Request request) {
        byte[] dataToSend = ObjSerializer.toByteArray(request);

//        if (!socket.isConnected()) {
//            connectToServer();
//        }

        try {
//            System.out.println(dataToSend);
//            System.out.println(dataToSend.length);
            DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, address);
            socket.send(packet);

            Response response = receiveAnswer();

            if (response == null) {
                System.out.println("Сервер не отвечает. Попробуйте позже.");
            }
            return response;
        } catch (IOException | NullPointerException e) {
            System.out.println("Команда не отправлена, попробуйте еще раз.");
            e.printStackTrace();
            return null;
        }
    }

    public Response receiveAnswer() {
        long timeStart = System.currentTimeMillis();
        while (System.currentTimeMillis() - timeStart < DefaultSettings.SERVER_TIMEOUT_SEC * 1000) {
            try {
                socket.setSoTimeout(DefaultSettings.SERVER_TIMEOUT_SEC * 1000);
                byte[] buf = new byte[DefaultSettings.DEFAULT_PACKAGE_SIZE];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                return ObjSerializer.fromByteArray(packet.getData());
            } catch (SocketTimeoutException e) {
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
