package com.yanicksenn.experimental.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import com.yanicksenn.libraries.flags.Flags;

public final class SqliteWriter {
  public static void main(String[] args) {
    Flags.init(args);
    
    String dbPath = Flags.get("db_path").orElseThrow(() -> new IllegalArgumentException("db_path is not specified"));
    var url = "jdbc:sqlite:" + dbPath;
    
    try (Connection connection = DriverManager.getConnection(url)) {
        if (connection != null) {
          String createTableSql = """
            CREATE TABLE IF NOT EXISTS entries (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              timestamp DATETIME NOT NULL
            );
          """;
          try (PreparedStatement ps = connection.prepareStatement(createTableSql)) {
            ps.execute();
          }

          String insertEntrySql = "INSERT INTO entries(timestamp) VALUES (?);";
          try (PreparedStatement ps = connection.prepareStatement(insertEntrySql)) {
            Instant instant = Instant.now();
            Timestamp timestamp = Timestamp.from(instant);
            ps.setTimestamp(1, timestamp);
            
            int rows = ps.executeUpdate();
            System.out.println("Total rows inserted: " + rows);
          }
        }
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }
  }
}
