package com.prok.server.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class to initialize database tables
 */
public class DBInitializer {

    private final Connection dbConnection;

    public DBInitializer(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void initialize() throws SQLException {

        Statement stmt = dbConnection.createStatement();

        stmt.executeUpdate("CREATE SEQUENCE IF NOT EXISTS ids START 1");

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS s335104Routes (" +
                "id int PRIMARY KEY," +
                "name varchar(255) NOT NULL CHECK(name<>'')," +
                "xCoordinate double precision NOT NULL CHECK(xCoordinate < 971)," +
                "yCoordinate int NOT NULL CHECK(yCoordinate < 362)," +
                "creationDate date DEFAULT (current_date) NOT NULL," +
                "nameLocationFrom varchar(255) NOT NULL," +
                "xLocationFrom double precision NOT NULL," +
                "yLocationFrom int NOT NULL," +
                "nameLocationTo varchar(255) NOT NULL," +
                "xLocationTo double precision NOT NULL," +
                "yLocationTo int NOT NULL," +
                "distance real NOT NULL CHECK(distance > 1)," +
                "author varchar(255)" +
                ")");

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS s335104Users (" +
                "username varchar(255) PRIMARY KEY," +
                "hashPassword BYTEA DEFAULT (null)" +
                ")");
    }
}
