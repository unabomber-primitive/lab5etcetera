package com.prok.common.entities;

import com.thoughtworks.xstream.annotations.XStreamAlias;
//import com.thoughtworks.xstream.annotations.XStreamAliasType;

import java.io.Serializable;
import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
@XStreamAlias("route")
public class Route implements Comparable<Route>, Serializable{
    private static int idCounter = 0;
    @XStreamAlias("id")
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @XStreamAlias("name")
    private String name; //Поле не может быть null, Строка не может быть пустой
    @XStreamAlias("coordinates")
    private Coordinates coordinates; //Поле не может быть null
    @XStreamAlias("creationDate")
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @XStreamAlias("from")
    private Location from; //Поле может быть null
    @XStreamAlias("to")
    private Location to; //Поле не может быть null
    @XStreamAlias("distance")
    private Float distance; //Поле не может быть null, Значение поля должно быть больше 1
    private String author;

    public Route(String name, Coordinates coordinates, Location from, Location to, Float distance) throws IllegalArgumentException, NullPointerException {
//        if (name == null || coordinates == null || from == null || to == null || distance == null ) {
//            throw new NullPointerException();
//        } else {
//            if (name.equals("")) {
//                throw new IllegalArgumentException("Имя не может являться пустой строкой");
//            }
//            if (distance<=1) {
//                throw new IllegalArgumentException("Дистанция должна быть больше 1");
//            }
//        }
        setCoordinates(coordinates);
        setDistance(distance);
        setFrom(from);
        setTo(to);
        setName(name);
        this.id = ++idCounter;
        this.creationDate = LocalDate.now();
    }

    public Route(int id, String name, Coordinates coordinates, LocalDate creationDate,
                 Location from, Location to, Float distance, String author) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.author = author;
    }

    public Route setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public Float getDistance() {
        return distance;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public void setName(String name) throws NullPointerException, IllegalArgumentException {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) throws NullPointerException {
        this.coordinates = coordinates;
    }

//    public void setCreationDate(LocalDate creationDate) throws NullPointerException{
//        this.creationDate = creationDate;
//    }

    public void setFrom(Location from) throws NullPointerException {
        this.from = from;
    }

    public void setTo(Location to) throws NullPointerException {
        this.to = to;
    }

    public void setDistance(Float distance) throws NullPointerException, IllegalArgumentException {
        this.distance = distance;
    }

    public static void setIdCounter(int idCounter) {
        Route.idCounter = idCounter;
    }

    public int compareTo(Route r) {
        return distance.compareTo(r.getDistance());
    }

    @Override
    public String toString() {
        return "Route ID #" + id + ":\n"
                //+ "- Creation Date:" + creationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")) + "\n"
                + "- Author:    " + "\"" + author + "\"" + "\n"
                + "- Creation Date: " + creationDate.toString() + "\n"
                + "- Name: " + "\"" + name + "\"" + "\n"
                + "- Coordinates:\n" + coordinates + "\n"
                + "- From Location:\n" + from + "\n"
                + "- To Location:\n" + to + "\n"
                + "- Distance: " + distance;
    }
}
