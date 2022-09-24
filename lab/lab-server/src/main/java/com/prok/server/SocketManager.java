package com.prok.server;

import com.prok.common.network.DefaultSettings;
import com.prok.common.network.ObjSerializer;
import com.prok.common.network.Request;
import com.prok.common.network.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class SocketManager {
    DatagramSocket socket;
    SocketAddress address;


    public SocketManager() throws IOException {
        socket = new DatagramSocket(1488);
        address = new InetSocketAddress("localhost", 1488);
    }

    public Request getUpdates() {
        while (true) {
            try{
                byte[] buf = new byte[DefaultSettings.DEFAULT_PACKAGE_SIZE];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);

                socket.receive(packet);

                SocketAddress clientAddress = packet.getSocketAddress();

                Request request = (Request) ObjSerializer.<Request>fromByteArray(packet.getData());

                if (request == null) {
                    continue;
                }

                address = clientAddress;

                return request;
            } catch (IOException e) { }
        }
    }

    public Boolean sendResponse(Response response) {
        int packetSize = DefaultSettings.DEFAULT_PACKAGE_SIZE;
        byte[] dataToSend = ObjSerializer.toByteArray(response);

        if (dataToSend == null) {
            return false;
        }

        if (dataToSend.length == 0) {
            return false;
        }

        try {
            for (int i=0; i < (dataToSend.length-1) / (packetSize + 1); i++) {
                int length = Math.min(dataToSend.length - i * packetSize, packetSize);
                DatagramPacket packet = new DatagramPacket(dataToSend, i * packetSize, length, address);
                socket.send(packet);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }


}
