package ua.denys.carrentalservice.loginapp.common.db;

import static ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils.*;

import java.sql.*;

public abstract class DbHelper {
  public Connection con;
  public String url;

  public DbHelper(String url) {
    this.url = url;
  }

  protected Statement openConnection() {
    Statement statement = null;
    try {
      con = DriverManager.getConnection(url, USER_NAME, PASSWORD);
      statement = con.createStatement();
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return statement;
  }

  private void closeConnection() {
    try {
      con.close();
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }
}
