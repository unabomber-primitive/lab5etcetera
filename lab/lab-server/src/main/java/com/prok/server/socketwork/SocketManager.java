package com.prok.server.socketwork;

import com.prok.common.network.DefaultSettings;
import com.prok.common.network.ObjSerializer;
import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Collection;
import com.prok.server.Invoker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SocketManager {
    private Invoker invoker;
    private Collection collection;
    private DatagramChannel channel;
    private HashMap<Request, SocketAddress> requestSocketAddresses;
    private  ThreadPoolExecutor fixedTP;
    private  ThreadPoolExecutor cachedTP;


    public SocketManager(Collection collection, Invoker invoker) throws IOException {
        channel = DatagramChannel.open();
        //channel.configureBlocking(false);
        channel.configureBlocking(true);
        channel.socket().bind(new InetSocketAddress("localhost", 13333));
        this.collection = collection;
        this.invoker = invoker;
        this.requestSocketAddresses = new HashMap<>();
        this.fixedTP = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        this.cachedTP = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }


    public void process() {
        ReceiveRequest target = new ReceiveRequest(this);
        Thread thread = new Thread(target);
        thread.start();
    }

    public Collection getCollection() {
        return collection;
    }

    public DatagramChannel getChannel() {
        return channel;
    }

    public HashMap<Request, SocketAddress> getRequestSocketAddresses() {
        return requestSocketAddresses;
    }

    public Invoker getInvoker() {
        return invoker;
    }

    public ThreadPoolExecutor getCachedTP() {
        return cachedTP;
    }

    public ThreadPoolExecutor getFixedTP() {
        return fixedTP;
    }

//    public void getUpdates() {
//
//
//
//        ByteBuffer buffer = ByteBuffer.allocate(DefaultSettings.DEFAULT_PACKAGE_SIZE);
//        byte[] byteArray;
////      int bytesReceived = 0;
//        while (true) {
//            try{
//                SocketAddress clientAddress = channel.receive(buffer);
//                System.out.println("upd");
//
//                byteArray = buffer.array();
//                buffer.clear();
//
//                Request request = ObjSerializer.fromByteArray(byteArray);
//
//                if (request == null) {
//                    continue;
//                }
//
//                address = clientAddress;
//                System.out.println(address);
//
//                System.out.println(request.commandName);
//                return request;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    public Boolean sendResponse(Response response) {
//        int packetSize = DefaultSettings.DEFAULT_PACKAGE_SIZE;
//        byte[] dataToSend = ObjSerializer.toByteArray(response);
//
//        if (dataToSend == null) {
//            return false;
//        }
//
//        if (dataToSend.length == 0) {
//            return false;
//        }
//
//        try {
//            for (int i=0; i < (dataToSend.length-1) / packetSize + 1; i++) {
//                int length = Math.min(dataToSend.length - i * packetSize, packetSize);
//                DatagramPacket packet = new DatagramPacket(dataToSend, i * packetSize, length, address);
//                channel.socket().send(packet);
//            }
//            return true;
//        } catch (IOException e) {
//            return false;
//        }
//    }

}
