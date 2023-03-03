package fr.greencodeinitiative.java.checks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.PreparedStatement;

import javax.sql.DataSource;


class AvoidStatementForDMLQueries {
    AvoidStatementForDMLQueries(AvoidStatementForDMLQueries mc) {
    }

    public void insert() throws SQLException {
        Connection connection = DriverManager.getConnection("URL");
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO persons(id, name) VALUES(2, 'Toto')");  // Noncompliant
    }
}