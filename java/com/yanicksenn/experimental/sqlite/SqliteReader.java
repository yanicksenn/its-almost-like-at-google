package com.yanicksenn.experimental.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.yanicksenn.libraries.flags.Flags;

public final class SqliteReader {
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

          String selectEntriesSql = "SELECT * FROM entries;";
          try (PreparedStatement ps = connection.prepareStatement(selectEntriesSql)) {
            try (ResultSet rs = ps.executeQuery()) {
              int rows = 0;
              while (rs.next()) {
                int id = rs.getInt(1);
                Timestamp timestamp = rs.getTimestamp(2);
                System.out.println(String.format("%d: %s", id, timestamp.toString()));
                rows ++;
              }
              System.out.println("Total rows read: " + rows);
            }
          }
        }
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }
  }
}
