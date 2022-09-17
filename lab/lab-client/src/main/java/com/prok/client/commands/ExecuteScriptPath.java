package com.prok.client.commands;

import java.util.ArrayList;

public class ExecuteScriptPath {
    private static ArrayList<String> paths;
    public static void addPath(String path) {
        paths.add(path);
    }
    public static void removePath(String path) {
        paths.remove(path);
    }
    public static Boolean isInList(String path) {
        return paths.contains(path);
    }
}
