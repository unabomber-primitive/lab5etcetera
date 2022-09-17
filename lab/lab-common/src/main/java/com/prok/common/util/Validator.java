package com.prok.common.util;

import com.prok.common.entities.Coordinates;
import com.prok.common.entities.RouteFieldType;

public final class Validator {
    //private static boolean ;
    private Validator() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static <T> void validateField(T value, RouteFieldType fieldName) {
        if (value == null) {
            throw new NullPointerException();
        }
        if (fieldName == RouteFieldType.NAME) {
            if ("".equals((String) value)) {
                throw new IllegalArgumentException("Значение имени не может являться пустой строкой.");
            }
        }
        if (fieldName == RouteFieldType.DISTANCE) {
            if ((Float) value <= 1) {
                throw new IllegalArgumentException("Значение дистанции должно быть больше 1.");
            }
        }
        if (fieldName == RouteFieldType.COORDINATES_X) {
            if ((Double) value > Coordinates.getXMaxValue()) {
                throw new IllegalArgumentException("Максимальное значение поля: 970.");
            }
        }
        if (fieldName == RouteFieldType.COORDINATES_Y) {
            if ((Integer) value > Coordinates.getYMaxValue()) {
                throw new IllegalArgumentException("Максимальное значение поля: 361.");
            }
        }
    }


}
