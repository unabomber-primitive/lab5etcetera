package com.prok.server;

import com.prok.common.entities.Route;
import com.prok.server.database.DBManager;
import com.prok.server.socketwork.SocketManager;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Collection {
    private DBManager dbManager;
    private CopyOnWriteArrayList<Route> collection;
    private LocalDate initialisationDate;
    private Comparator<Route> comparatorById = Comparator.comparing(Route::getId);

    public Collection(DBManager dbManager) {
        this.dbManager = dbManager;
        collection = new CopyOnWriteArrayList<>(dbManager.getCollectionFromDb());
        initialisationDate = LocalDate.now();
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    public void setDbManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void add(Route route) {
        route.setId(this.getMaxId()+1);
        dbManager.addRoute(route);
        collection.add(route);
    }

    public Route getRouteById(Integer id) {
        Route[] r = (Route[]) collection.stream().filter((x)->x.getId()==id).toArray();
        if(r.length != 0) {
            return r[0];
        }
//        for (Route route : collection) {
//            if (route.getId() == id) {
//                return route;
//            }
//        }
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

    public void removeLast(String username) throws Exception{
        if (collection.size() == 0) {
            return;
        }
        int id = collection.get(collection.size() -1).getId();
        if (dbManager.removeById(id, username))  {
            throw new Exception("unsuccess");
        }
        collection.remove(collection.size() - 1);
    }

    public void clear(String username) throws Exception{
        dbManager.clear(username);
        collection.clear();
    }

    public void removeById(Integer id, String username) throws Exception{
        //collection.removeIf(route -> route.getId() == id);
        if (!dbManager.removeById(id, username)) {
            throw new Exception("unsuccess");
        }
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

    public void replaceById(Integer id, Route route) throws Exception{
        if (dbManager.updateById(route, id)) {
            throw new Exception("unsuccess");
        }
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
        //dbManager.
        collection = new CopyOnWriteArrayList<>((Route[]) collection.stream().filter(route1 -> route1.compareTo(route) > 0).toArray());
        //collection.removeIf(x -> x.compareTo(route) > 0);
    }

    public String lessThanDistanceToString(Float distance) {
        StringBuilder str = new StringBuilder();
//        for (Route route : collection) {
//            if (route.getDistance() < distance) {
//                //str.append(route.toString()).append("\n");
//                str.append(route == collection.get(collection.size() - 1) ? route.toString() : route.toString() + "\n");
//            }
//        }
        collection.stream().filter(route -> route.getDistance() < distance).forEach(route -> str.append(route.toString() + "\n"));
        //str.delete(str.length() - 2, str.length());
        if (str.length() == 0) {
            return "Не нашлось подходящих маршрутов";
        } else {
            return str.toString();
        }
    }

    public String greaterThanDistanceToString(Float distance) {
        StringBuilder str = new StringBuilder();
//        for (Route route : collection) {
//            if (route.getDistance() > distance) {
//                //str.append(route.toString()).append("\n");
//                str.append(route == collection.get(collection.size() - 1) ? route.toString() : route.toString() + "\n");
//            }
//        }
        collection.stream().filter(route -> route.getDistance() < distance).forEach(route -> str.append(route.toString() + "\n"));
        //str.delete(str.length() - 2, str.length());
        if (str.length() == 0) {
            return "Не нашлось подходящих маршрутов";
        } else {
            return str.toString();
        }
    }

    public CopyOnWriteArrayList<Route> getArrayList() {
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
        return collection.size() == 0 ? 0 : collection.get(collection.size() - 1).getId();
    }

    public void setCollection(CopyOnWriteArrayList<Route> collection) {
        this.collection = collection;
        Route.setIdCounter(getMaxId());
    }

//    public SocketManager getSocketManager() {
//        return socketManager;
//    }

//    public void setSocketManager(SocketManager socketManager) {
//        this.socketManager = socketManager;
//    }

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
