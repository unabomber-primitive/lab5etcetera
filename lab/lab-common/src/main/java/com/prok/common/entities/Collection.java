package com.prok.common.entities;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

public class Collection {
    private ArrayList<Route> collection;
    private LocalDate initialisationDate;

    private Scanner in = new Scanner(System.in);
    private Comparator<Route> comparatorById = Comparator.comparing(Route::getId);

    public Collection() {
        collection = new ArrayList<Route>();
        initialisationDate = LocalDate.now();
    }

    public void add(Route route) {
        collection.add(route);
    }

    public Route getRouteById(Integer id) {
        for (Route route : collection) {
            if (route.getId() == id) {
                return route;
            }
        }
        throw new IllegalArgumentException("Нет маршрута с таким id.");
    }

    public void sortByDistance() {
        Collections.sort(collection);
    }

    public void sortById() {
        collection.sort(comparatorById);
    }

    public void shuffle() {
        Collections.shuffle(collection);
    }

    public void removeLast() {
        collection.remove(collection.size() - 1);
    }

    public void clear() {
        collection.clear();
    }

    public void removeById(Integer id) {
        //collection.removeIf(route -> route.getId() == id);
        int foundAnyFlag = 0;
        for (Route r : collection) {
            if (r.getId() == id) {
                foundAnyFlag = 1;
                collection.remove(r);
            }
        }
        if (foundAnyFlag == 0) {
            throw new IllegalArgumentException("Нет маршрута с таким id");
        }
    }

    public void replaceById(Integer id, Route route) {
        int foundAnyFlag = 0;
        for (Route r : collection) {
            if (r.getId() == id) {
                foundAnyFlag = 1;
                collection.set(collection.indexOf(r), route);
            }
        }
        if (foundAnyFlag == 0) {
            throw new IllegalArgumentException("Нет маршрута с таким id");
        }
    }

    public void removeGreater(Route route) {
        collection.removeIf(x -> x.compareTo(route) > 0);
    }

    public String lessThanDistanceToString(Float distance) {
        StringBuilder str = new StringBuilder();
        for (Route route : collection) {
            if (route.getDistance() < distance) {
                //str.append(route.toString()).append("\n");
                str.append(route == collection.get(collection.size() - 1) ? route.toString() : route.toString() + "\n");
            }
        }
        //str.delete(str.length() - 2, str.length());
        if (str.length() == 0) {
            return "Не нашлось подходящих маршрутов";
        } else {
            return str.toString();
        }
    }

    public String greaterThanDistanceToString(Float distance) {
        StringBuilder str = new StringBuilder();
        for (Route route : collection) {
            if (route.getDistance() > distance) {
                //str.append(route.toString()).append("\n");
                str.append(route == collection.get(collection.size() - 1) ? route.toString() : route.toString() + "\n");
            }
        }
        //str.delete(str.length() - 2, str.length());
        if (str.length() == 0) {
            return "Не нашлось подходящих маршрутов";
        } else {
            return str.toString();
        }
    }

    public ArrayList<Route> getArrayList() {
        return collection;
    }

    public LocalDate getInitialisationDate() {
        return initialisationDate;
    }

    public int getSize() {
        return collection.size();
    }

    public int getMaxId() {
        collection.sort(comparatorById);
        return collection.get(collection.size() - 1).getId();
    }

    public void setCollection(ArrayList<Route> collection) {
        this.collection = collection;
        Route.setIdCounter(getMaxId() + 1);
    }

    public Scanner getIn() {
        return in;
    }

    public void setIn(Scanner in) {
        this.in = in;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (collection.isEmpty()) {
            return "Коллекция пуста.";
        } else {
            for (Route route : collection) {
                str.append(route == collection.get(collection.size() - 1) ? route.toString() : route.toString() + "\n");
                //str.append(route.toString()).append("\n");
            }
            //str.delete(str.length() - 2, str.length());
            return str.toString();
        }
    }
}
