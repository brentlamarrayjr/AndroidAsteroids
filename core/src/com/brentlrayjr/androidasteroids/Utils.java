package com.brentlrayjr.androidasteroids;

import com.badlogic.gdx.math.Vector2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class Utils {

    public enum GameType {

        CLASSIC,
        ARENA, NONE


    }

    public static String generateId() {

        StringBuilder builder = new StringBuilder();



        for (int i = 0; i < 9; i++) {

            builder.append(new Random().nextInt());

        }

        return builder.toString();
    }

 
    public static byte[] toByteArray(Object obj) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return bytes;
    }

    public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
        return obj;
    }

    public static String toString(byte[] bytes) {
        return new String(bytes);
    }

    public static Vector2 difference(Vector2 first, Vector2 second) {
        return new Vector2(first.x-second.x, first.y-second.y);
    }

    public static Vector2 multiply(Vector2 first, float second) {
        return new Vector2(first.x*second, first.y*second);
    }

    public static Vector2 multiply(Vector2 first, Vector2 second) {
        return new Vector2(first.x*second.x, first.y*second.y);
    }

    public static Vector2 inverse(Vector2 first) {
        return new Vector2(1/first.x, 1/first.y);
    }

}
