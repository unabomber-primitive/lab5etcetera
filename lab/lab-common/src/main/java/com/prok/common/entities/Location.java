package com.prok.common.entities;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Location {
    @XStreamAlias("name")
    private String name; //Поле может быть null
    @XStreamAlias("x")
    private Double x; //Поле не может быть null
    @XStreamAlias("y")
    private int y; //Поле не может быть null

    public Location(Double x, int y, String name) {
        setX(x);
        setY(y);
        setName(name);
    }

    public Double getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setX(Double x) throws NullPointerException {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setName(String name) throws NullPointerException {
        this.name = name;
    }

    @Override
    public String toString() {
        return "- - Location name: " + name + "\n"
                + "- - Location X: " + x + "\n"
                + "- - Location Y: " + y;
    }
}
