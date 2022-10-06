package com.prok.server.database;

import com.prok.common.network.DefaultSettings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    public Connection connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            return null;
        }

        return DriverManager.getConnection(DefaultSettings.DATABASE_URL, DefaultSettings.DATABASE_LOGIN, DefaultSettings.DATABASE_PASSWORD);
    }
}
