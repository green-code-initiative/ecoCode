package fr.greencodeinitiative.java.checks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.PreparedStatement;


class AvoidStatementForDMLQueries {
    AvoidStatementForDMLQueries(AvoidStatementForDMLQueries mc) {
    }

    public void insert() {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO persons(id, name) VALUES(2, 'Toto')");  // Noncompliant {{You must not use Statement for a DML query}}
    }
}