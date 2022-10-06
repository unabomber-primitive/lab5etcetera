package com.prok.server.database;

import com.prok.common.entities.Coordinates;
import com.prok.common.entities.Location;
import com.prok.common.entities.Route;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Class to work with database
 */
public class DBManager {

    private final Connection db;
    private final MessageDigest digest;

    public DBManager(Connection connection) throws NoSuchAlgorithmException {
        try {
            new DBInitializer(connection).initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db = connection;
        digest = MessageDigest.getInstance("MD2");
    }

    public ResultSet getCollection() {
        try {
            PreparedStatement preparedStatement = db.prepareStatement(Statements.takeAll.getStatement());
            return preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            //logger.warn("SQL problem with taking all collection!");
            return null;
        }
    }

    public Integer addRoute(Route route) {
        try {
            PreparedStatement preparedStatement = db.prepareStatement(Statements.addRoute.getStatement());
            Integer newId = setRouteToStatement(preparedStatement, route);
            preparedStatement.executeUpdate();
            return (newId == null) ? 0 : newId;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }

    public boolean updateById(Route route, int anId) {
        try {
            boolean status = getById(anId, route.getAuthor());
            if (!status) return false;

            PreparedStatement preparedStatement = db.prepareStatement(Statements.updateStudyGroup.getStatement());
            setUpdatedRouteToStatement(preparedStatement, route);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            //logger.warn("SQL problem with updating element !");
            return false;
        }
    }

    public boolean removeById(int anId, String anUsername) {
        try {
            boolean success = getById(anId, anUsername);
            if (!success) return false;

            PreparedStatement preparedStatement = db.prepareStatement(Statements.deleteById.getStatement());
            preparedStatement.setInt(1, anId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            //logger.warn("SQL problem with removing element!");
            return false;
        }
    }

    public boolean getById(int anId, String anUsername) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = db.prepareStatement(Statements.getById.getStatement());
            preparedStatement.setInt(1, anId);
            ResultSet deletingStudyGroup = preparedStatement.executeQuery();

            if (!deletingStudyGroup.next())
                return false;

            if (!deletingStudyGroup.getString("author").equals(anUsername))
                return false;

            return true;
        } catch (SQLException throwables) {
            //logger.warn("SQL problem with getting element!");
            return false;
        }
    }

    public boolean clear(String username) {
        try {
            PreparedStatement preparedStatement = db.prepareStatement(Statements.clearAllByUser.getStatement());
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            //logger.warn("SQL problem with removing elements!");
            return false;
        }
    }

    public boolean addUser(String anUsername, String aPassword) {
        try {
            PreparedStatement insertStatement = db.prepareStatement(Statements.addUserWithPassword.getStatement());
            insertStatement.setString(1, anUsername);
            insertStatement.setBytes(2, getHash(aPassword));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            //logger.warn("SQL problem with adding user!");
            return false;
        }
    }

    public boolean checkUser(String anUsername, String aPassword) {
        try {
            PreparedStatement checkStatement = db.prepareStatement(Statements.checkUser.getStatement());
            checkStatement.setString(1, anUsername);
            checkStatement.setBytes(2, getHash(aPassword));
            ResultSet user = checkStatement.executeQuery();
            return user.next();
        } catch (SQLException e) {
            //logger.warn("SQL problem with logging user!");
            return false;
        }
    }

    private Integer generateId() {
        try {
            Statement statement = db.createStatement();
            ResultSet resultSet = statement.executeQuery(Statements.generateId.getStatement());
            if (resultSet.next()) {
                return resultSet.getInt("nextval");
            }
            return 0;
        } catch (SQLException throwables) {
            //logger.warn("SQL problem with generating id!");
            return 0;
        }
    }

    private Integer setRouteToStatement(PreparedStatement stmt, Route route) throws SQLException {
        Integer newId = generateId();
        if (newId == null) return null;

        route.setId(newId);
        stmt.setInt(1, route.getId());
        stmt.setString(2, route.getName());
        stmt.setDouble(3, route.getCoordinates().getX());
        stmt.setInt(4, route.getCoordinates().getY());
        stmt.setString(5, route.getFrom().getName());
        stmt.setDouble(6, route.getFrom().getX());
        stmt.setInt(7, route.getFrom().getY());
        stmt.setString(8, route.getTo().getName());
        stmt.setDouble(9, route.getTo().getX());
        stmt.setInt(10, route.getTo().getY());
        stmt.setFloat(11, route.getDistance());
        stmt.setString(12, route.getAuthor());

        return newId;
    }

    private void setUpdatedRouteToStatement(PreparedStatement stmt, Route route) throws SQLException {
        route.setId(generateId());
        stmt.setString(1, route.getName());
        stmt.setDouble(2, route.getCoordinates().getX());
        stmt.setInt(3, route.getCoordinates().getY());
        stmt.setString(4, route.getFrom().getName());
        stmt.setDouble(5, route.getFrom().getX());
        stmt.setInt(6, route.getFrom().getY());
        stmt.setString(7, route.getTo().getName());
        stmt.setDouble(8, route.getTo().getX());
        stmt.setInt(9, route.getTo().getY());
        stmt.setFloat(10, route.getDistance());
        stmt.setInt(11, route.getId());
    }

    private byte[] getHash(String str) {
        return (str == null)
                ? digest.digest("null".getBytes(StandardCharsets.UTF_8))
                : digest.digest(str.getBytes(StandardCharsets.UTF_8));
    }

    public ArrayList<Route> getCollectionFromDb() {
        ArrayList<Route> collection = new ArrayList<>();
        try {
            ResultSet data = getCollection();
            while (data.next()) {
                collection.add(new Route(
                        data.getInt(1),
                        data.getString(2),
                        new Coordinates(data.getDouble(3), data.getInt(4)),
                        data.getDate(5).toLocalDate(),
                        new Location(data.getDouble(7), data.getInt(8), data.getString(6)),
                        new Location(data.getDouble(10), data.getInt(11), data.getString(9)),
                        data.getFloat(12),
                        data.getString(13)
                ));
            }
            return collection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}