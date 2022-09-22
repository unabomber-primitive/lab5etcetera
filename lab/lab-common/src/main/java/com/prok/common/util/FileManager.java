package com.prok.common.util;

import com.prok.common.entities.Collection;
//import com.prok.common.entities.Route;
import com.prok.common.entities.Route;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.security.AnyTypePermission;
//import com.thoughtworks.xstream.annotations.XStreamAlias;
//import com.thoughtworks.xstream.converters.collections.CollectionConverter;
//import com.thoughtworks.xstream.mapper.Mapper;
//import com.thoughtworks.xstream.security.NoTypePermission;
//import com.thoughtworks.xstream.security.NullPermission;
//import com.thoughtworks.xstream.security.PrimitiveTypePermission;

//import javax.print.DocFlavor;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.ArrayList;
import java.util.ArrayList;
//import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
//import java.io.Writer;
//import java.util.Scanner;

public final class FileManager {
    private static final String ENV_FILENAME = "LAB5_FILENAME";
    private static String fileName;
    private static XStream xStream = new XStream();

    private FileManager() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    static {
        xStream.addPermission(AnyTypePermission.ANY);
        xStream.alias("list", FileManager.Result.class);
        //xStream.aliasField("routes", FileManager.Result.class, "routes");
        xStream.alias("route", Route.class);
        xStream.addImplicitCollection(FileManager.Result.class, "routes");
//        fileName = System.getenv(ENV_FILENAME);
//        if (fileName == null || "".equals(fileName)) {
//            throw new NullPointerException("Переменная окружения LAB5_FILENAME пустая.");
//        }
//        System.out.println("env checked");
    }

//    public FileManager() {
//        fileName = System.getenv(ENV_FILENAME);
//        if (fileName == null || "".equals(fileName)) {
//            throw new NullPointerException("Переменная окружения LAB5_FILENAME пустая.");
//        }
//    }

    public static void loadTo(Collection collection) {
        try {
            fileName = System.getenv(ENV_FILENAME);
            if (fileName == null || "".equals(fileName)) {
                System.out.println("Переменная окружения LAB5_FILENAME пустая.");
                throw new NoSuchElementException();
            }
            Scanner scanner = new Scanner(new File(fileName));
            System.out.println("env checked");
            System.out.println(fileName);
            StringBuilder xml = new StringBuilder();
            while (scanner.hasNext()) {
                xml.append(scanner.nextLine()).append("\n");
            }
            if (xml.length() != 0) {
                Result res = (Result) xStream.fromXML(xml.toString());
                collection.setCollection(res.getRoutes());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден. \nАдрес, переданный в переменной окружения LAB5_FILENAME: " + fileName);
            throw new NoSuchElementException();
        }
    }

    public static void saveFile(Collection collection) {
        fileName = System.getenv(ENV_FILENAME);
        if (fileName == null || "".equals(fileName)) {
            throw new NullPointerException("Переменная окружения LAB5_FILENAME пустая.");
        }
        System.out.println("Saved to " + fileName);
        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter(fileName));
            Result res = new Result(collection.getArrayList());
            ArrayList<Route> routes = res.getRoutes();
            //System.out.println(xStream.toXML(routes));
            out.write(xStream.toXML(routes));
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    //@XStreamAlias("routes")
    @XStreamAlias("list")
    public static class Result {
        @XStreamImplicit(itemFieldName = "route")
        private ArrayList<Route> routes;
        public Result(ArrayList<Route> routes) {
            this.routes = routes;
        }

        public ArrayList<Route> getRoutes() {
            return routes;
        }
    }

//    public void parseFile() {
//        XStream xStream = new XStream();
//        Scanner in = new Scanner(fileName);
//        StringBuilder xmlSB = new StringBuilder();
//        while (in.hasNext()) {
//            xmlSB.append(in.nextLine());
//        }
//        in.close();
//        String xml = xmlSB.toString();
//        ArrayList<Route> collection = (ArrayList<Route>) xStream.fromXML(xml);
//    }
}
