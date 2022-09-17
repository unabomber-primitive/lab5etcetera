package com.prok.common.entities;

import com.prok.common.util.Validator;

import java.util.Scanner;

public class RouteFactory {

    private Scanner in;

    public RouteFactory(Scanner in) {
        this.in = in;
    }

    public String getName() {
        String name;

        while (true) {
            System.out.print("Введите название дороги:  ");
            name = in.nextLine();
            try {
                Validator.<String>validateField(name, RouteFieldType.NAME);
                return name;
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Coordinates getCoordinates() {
        Double coordinatesX;
        Integer coordinatesY;
        Coordinates coordinates;

        while (true) {
            System.out.print("Введите координаты х, y через пробел:  ");
            String input = " " + in.nextLine();
            String[] inputXY = input.split("\\s+");
            try {
                coordinatesX = Double.valueOf(inputXY[1]);
                coordinatesY = Integer.valueOf(inputXY[2]);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Вы ввели не все требуемые аргументы.");
                continue;
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат данных (требуется: х - число с плавающей точкой, у - целое)");
                continue;
            }
            try {
                Validator.<Double>validateField(coordinatesX, RouteFieldType.COORDINATES_X);
                Validator.<Integer>validateField(coordinatesY, RouteFieldType.COORDINATES_Y);
                coordinates = new Coordinates(coordinatesX, coordinatesY);
                return coordinates;
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Location getLocationFrom() {
        String name;
        Double x;
        int y;
        Location locationFrom;
        while (true) {
            System.out.print("Введите название пункта отправления:  ");
            name = in.nextLine();
            try {
                Validator.<String>validateField(name, RouteFieldType.NAME);
                break;
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            System.out.print("Введите координаты пункта отправления х, y через пробел:  ");
            String input = " " + in.nextLine();
            String[] inputXY = input.split("\\s+");
            try {
                x = Double.valueOf(inputXY[1]);
                y = Integer.parseInt(inputXY[2]);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Вы ввели не все требуемые аргументы.");
                continue;
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат данных (требуется: х - число с плавающей точкой, у - целое)");
                continue;
            }
            try {
                Validator.<Double>validateField(x, RouteFieldType.LOCATION_X);
                Validator.<Integer>validateField(y, RouteFieldType.LOCATION_Y);
                locationFrom = new Location(x, y, name);
                return locationFrom;
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Location getLocationTo() {
        String name;
        Double x;
        int y;
        Location locationTo;
        while (true) {
            System.out.print("Введите название пункта прибытия:  ");
            name = in.nextLine();
            try {
                Validator.<String>validateField(name, RouteFieldType.CREATION_DATE);
                break;
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            System.out.print("Введите координаты пункта прибытия х, y через пробел:  ");
            String input = " " + in.nextLine();
            String[] inputXY = input.split("\\s+");
            try {
                x = Double.valueOf(inputXY[1]);
                y = Integer.parseInt(inputXY[2]);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Вы ввели не все требуемые аргументы.");
                continue;
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат данных (требуется: х - число с плавающей точкой, у - целое)");
                continue;
            }
            try {
                Validator.<Double>validateField(x, RouteFieldType.LOCATION_X);
                Validator.<Integer>validateField(y, RouteFieldType.LOCATION_Y);
                locationTo = new Location(x, y, name);
                return locationTo;
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Float getDistance() {
        Float distance;
        while (true) {
            System.out.print("Введите дистанцию пути:  ");
            String input = in.nextLine();
            try {
                distance = Float.valueOf(input);
                Validator.<Float>validateField(distance, RouteFieldType.DISTANCE);
                return distance;
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Route getRoute() {
        String name = getName();
        Coordinates coordinates = getCoordinates();
        Location locationFrom = getLocationFrom();
        Location locationTo = getLocationTo();
        Float distance = getDistance();
        return new Route(name, coordinates, locationFrom, locationTo, distance);
    }
}
