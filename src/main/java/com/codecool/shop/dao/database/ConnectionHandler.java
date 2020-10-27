package com.codecool.shop.dao.database;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ConnectionHandler {

    public static DataSource getDataSource() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName(System.getenv("PSQL_DB"));
        dataSource.setUser(System.getenv("PSQL_USER"));
        dataSource.setPassword(System.getenv("PSQL_PASSWORD"));

        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connection OK");

        return dataSource;
    }
}
