package com.prok.server.database;

/**
 * Enum of database universal statements
 */
public enum Statements {

    addRoute("INSERT INTO s335104Routes " +
            "(id, name, xCoordinate, yCoordinate, nameLocationFrom, xLocationFrom, yLocationFrom, " +
            "nameLocationTo, xLocationTo, yLocationTo, distance, author) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),

    generateId("SELECT nextval('ids')"),

    addUserWithPassword("INSERT INTO s335104Users (username, hashPassword) VALUES(?, ?)"),

    checkUser("SELECT * FROM s335104Users WHERE username=? AND hashPassword=?"),

    updateStudyGroup("UPDATE s335104Routes SET " +
            "name=?, xCoordinate=?, yCoordinate=?, nameLocationFrom=?, xLocationFrom=?, yLocationFrom=?, " +
            "nameLocationTo=?, xLocationTo=?, yLocationTo=?, distance=?" +
            "WHERE id = ?"),

    getById("SELECT * FROM s335104Routes WHERE id = ?"),

    deleteById("DELETE FROM s335104Routes WHERE id = ?"),

    clearAllByUser("DELETE FROM s335104Routes WHERE author = ?"),

    takeAll("SELECT * FROM s335104Routes");

    private final String statement;

    Statements(String aStatement) {
        statement = aStatement;
    }

    public String getStatement() {
        return statement;
    }
}