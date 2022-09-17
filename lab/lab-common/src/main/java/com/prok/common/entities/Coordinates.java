package com.prok.common.entities;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Coordinates {
    private static final Double X_MAXVALUE = 970.0;
    private static final Integer Y_MAXVALUE = 361;
    @XStreamAlias("x")
    private Double x; //Максимальное значение поля: 970, Поле не может быть null
    @XStreamAlias("y")
    private Integer y; //Максимальное значение поля: 361, Поле не может быть null

    public Coordinates(Double x, Integer y) {
        setX(x);
        setY(y);
    }

    public static Double getXMaxValue() {
        return X_MAXVALUE;
    }

    public static Integer getYMaxValue() {
        return Y_MAXVALUE;
    }

    public void setX(Double x) throws IllegalArgumentException, NullPointerException {
        this.x = x;
    }

    public void setY(Integer y) throws IllegalArgumentException, NullPointerException {
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "- - X coordinate: " + x + "\n"
                + "- - Y coordinate: " + y;
    }
}
