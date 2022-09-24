package com.prok.common.network;

import java.io.*;

public class ObjSerializer {
    public static byte[] toByteArray(Serializable obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();

            byte[] result = byteArrayOutputStream.toByteArray();

            byteArrayOutputStream.close();
            objectOutputStream.close();

            return result;
        } catch (IOException e) {
            return null;
        }
    }

    public static <T extends Serializable> T fromByteArray(byte[] byteArray) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
            ObjectInputStream objectInput = new ObjectInputStream(byteArrayInputStream);

            T result = (T) objectInput.readObject();

            objectInput.close();
            byteArrayInputStream.close();

            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
