package com.prok.client;

import com.prok.common.network.DefaultSettings;
import com.prok.common.network.ObjSerializer;
import com.prok.common.network.Request;
import com.prok.common.network.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class SocketManager{
    SocketAddress address;
    DatagramChannel channel;


    public SocketManager() throws IOException {
        channel = DatagramChannel.open();
        address = new InetSocketAddress("localhost", 1488);
    }

    public void connectToServer() {
        try {
            channel.configureBlocking(false);
            channel.connect(address);
        } catch (Exception e) {
            System.out.println("Проблемы с сетью, не удалось подключиться к серверу.");
        }
    }

    public void closeConnection() {
        if (channel.isConnected()) {
            try {
                channel.disconnect();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Response makeRequest(Request request) {
        byte[] dataToSend = ObjSerializer.toByteArray(request);

        if (!channel.isConnected()) {
            connectToServer();
        }

        try {
            ByteBuffer buf = ByteBuffer.wrap(dataToSend);
            do {
                channel.write(buf);
            } while (buf.hasRemaining());

            Response response = receiveAnswer();

            if (response == null) {
                System.out.println("Сервер не отвечает. Попробуйте позже.");
            }
            return response;
        } catch (IOException | NullPointerException e) {
            System.out.println("Команда не отправлена, попробуйте еще раз.");
            return null;
        }
    }

    public Response receiveAnswer() {
        long timeStart = System.currentTimeMillis();
        ByteBuffer buffer = ByteBuffer.allocate(DefaultSettings.DEFAULT_PACKAGE_SIZE);
        byte[] byteArray = new byte[0];
        int bytesReceived = 0;

        while (System.currentTimeMillis() - timeStart < DefaultSettings.SERVER_TIMEOUT_SEC * 1000) {
            try {
                channel.receive(buffer);

                if (buffer.position() != 0) {
                    ByteArrayOutputStream str = new ByteArrayOutputStream();
                    str.write(byteArray);
                    str.write(buffer.array());
                    byteArray = str.toByteArray();
                    buffer.clear();
                } else if (byteArray.length != 0) {
                    return ObjSerializer.fromByteArray(byteArray);
                }
            } catch (IOException ignored) { }
        }
        return null;
    }
}
