package ua.denys.carrentalservice.loginapp.common.db;

import ua.denys.carrentalservice.loginapp.session.model.Session;
import ua.denys.carrentalservice.loginapp.session.service.SessionService;

import java.sql.*;

import static ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils.*;

public class DbHelper {
  private Connection con;

  private static DbHelper INSTANCE = null;

  private DbHelper() {}

  public static DbHelper getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new DbHelper();
    }
    return INSTANCE;
  }

  public boolean checkUser(String username) throws SQLException {
    var result = false;
    try {
      if (openConnection()
          .executeQuery(
              " SELECT "
                  + UsersColumns.USERNAME
                  + " FROM "
                  + USERS_TABLE
                  + " WHERE "
                  + UsersColumns.USERNAME
                  + " = '"
                  + username
                  + "'")
          .next()) result = true;
    } finally {
      closeConnection();
      return result;
    }
  }

  public boolean login(String username, String password) throws SQLException {
    var result = false;
    try {
      result =
          openConnection()
              .executeQuery(
                  " SELECT "
                      + UsersColumns.USERNAME
                      + " FROM "
                      + USERS_TABLE
                      + " WHERE "
                      + UsersColumns.USERNAME
                      + " = '"
                      + username
                      + "' AND password = '"
                      + password
                      + "'")
              .next();
    } finally {
      closeConnection();
    }
    return result;
  }

  public void registerUser(String username, String password) throws SQLException {
    try {
      openConnection()
          .execute(
              "INSERT INTO "
                  + USERS_TABLE
                  + "("
                  + UsersColumns.USERNAME
                  + ","
                  + UsersColumns.PASSWORD
                  + ") values('"
                  + username
                  + "', '"
                  + password
                  + "')");
    } finally {
      closeConnection();
    }
  }

  public void registerSession(Session session) throws SQLException {
    try {
      openConnection()
          .execute(
              "INSERT INTO session("
                  + SessionColumns.USER_ID
                  + ", "
                  + SessionColumns.SESSION_KEY
                  + ", "
                  + SessionColumns.EXPIRED_AT
                  + ") values("
                  + session.getUserId()
                  + ", '"
                  + session.getSessionKey()
                  + "', '"
                  + session.getExpiredAt()
                  + "')");
    } finally {
      closeConnection();
    }
  }

  public void deleteSession(String session_key) throws SQLException {
    StringBuilder stringBuilder = new StringBuilder();
    try {
      openConnection()
          .execute(
              stringBuilder
                  .append("DELETE FROM ")
                  .append(SESSION_TABLE)
                  .append(" WHERE ")
                  .append(SessionColumns.SESSION_KEY)
                  .append(" = ")
                  .append("'")
                  .append(session_key)
                  .append("'")
                  .toString());
    } finally {
      closeConnection();
    }
  }

  public void addCar(
      String brand,
      int carSeats,
      String bodyType,
      int engineVolume,
      String fuelType,
      int massBaggageToPossible,
      String model,
      int power,
      String tranmission)
      throws SQLException {
    try {
      openConnection()
          .execute(
              new StringBuilder()
                  .append("INSERT INTO ")
                  .append(CARS_TABLE + "(")
                  .append(CarsColumns.BRAND)
                  .append(", ")
                  .append(CarsColumns.MODEL)
                  .append(", ")
                  .append(CarsColumns.CAR_SEATS)
                  .append(", ")
                  .append(CarsColumns.MASS_BAGGAGE_TO_POSSIBLE)
                  .append(", ")
                  .append(CarsColumns.TRANSMISSION)
                  .append(", ")
                  .append(CarsColumns.ENGINE_VOLUME)
                  .append(", ")
                  .append(CarsColumns.BODY_TYPE)
                  .append(", ")
                  .append(CarsColumns.FUEL_TYPE)
                  .append(", ")
                  .append(CarsColumns.POWER)
                  .append(", ")
                  .append(CarsColumns.STATUS)
                  .append(", ")
                  .append(CarsColumns.USER_ID)
                  .append(") ")
                  .append("VALUES")
                  .append("(")
                  .append("'")
                  .append(brand)
                  .append("'")
                  .append(", ")
                  .append("'")
                  .append(model)
                  .append("'")
                  .append(", ")
                  .append(carSeats)
                  .append(", ")
                  .append(massBaggageToPossible)
                  .append(", ")
                  .append("'")
                  .append(tranmission)
                  .append("'")
                  .append(", ")
                  .append(engineVolume)
                  .append(", ")
                  .append("'")
                  .append(bodyType)
                  .append("'")
                  .append(", ")
                  .append("'")
                  .append(fuelType)
                  .append("'")
                  .append(", ")
                  .append(power)
                  .append(", ")
                  .append("'")
                  .append("Free")
                  .append("'")
                  .append(", ")
                  .append("'")
                  .append(
                      DbHelper.getInstance()
                          .getIdBySessionKey(SessionService.getInstance().getSkByFile()))
                  .append("'")
                  .append(")")
                  .toString());
    } finally {
      closeConnection();
    }
  }

  public ResultSet getCarsByUserId(int userId) throws SQLException {
    return openConnection()
        .executeQuery(
            "SELECT * FROM " + CARS_TABLE + " WHERE " + CarsColumns.USER_ID + " = " + userId);
  }

  public String getSessionExpired_at(String session_key) throws SQLException {
    String result = "";
    try {
      StringBuilder stringBuilder = new StringBuilder();
      ResultSet rs = null;
      rs =
          openConnection()
              .executeQuery(
                  stringBuilder
                      .append("SELECT ")
                      .append(SessionColumns.EXPIRED_AT)
                      .append(" FROM ")
                      .append(SESSION_TABLE)
                      .append(" WHERE ")
                      .append(SessionColumns.SESSION_KEY)
                      .append(" = ")
                      .append("'")
                      .append(session_key)
                      .append("'")
                      .toString());
      while (rs.next()) result = rs.getString(1);
    } finally {
      closeConnection();
      return result;
    }
  }

  public long getIdByUsername(String username) throws SQLException {
    long id = 0;
    try {
      ResultSet rs =
          openConnection()
              .executeQuery(
                  "SELECT "
                      + UsersColumns.ID
                      + " FROM "
                      + USERS_TABLE
                      + " WHERE "
                      + UsersColumns.USERNAME
                      + " = '"
                      + username
                      + "'");
      while (rs.next()) {
        id = rs.getLong("id");
      }
    } finally {
      closeConnection();
      return id;
    }
  }

  public String getUsernameById(int id) throws SQLException {
    String username = "";
    try {
      ResultSet rsUsername =
          openConnection()
              .executeQuery(
                  "SELECT "
                      + UsersColumns.USERNAME
                      + " FROM "
                      + USERS_TABLE
                      + " WHERE "
                      + UsersColumns.ID
                      + " = "
                      + id);
      while (rsUsername.next()) {
        username = rsUsername.getString(1);
      }
    } finally {
      closeConnection();
      return username;
    }
  }

  public int getIdBySessionKey(String sessionKey) throws SQLException {
    int id = 0;
    ResultSet rsId = null;
    try {
      rsId =
          openConnection()
              .executeQuery(
                  "SELECT "
                      + SessionColumns.USER_ID
                      + " FROM "
                      + SESSION_TABLE
                      + " WHERE "
                      + SessionColumns.SESSION_KEY
                      + " = "
                      + "'"
                      + sessionKey
                      + "'");
      while (rsId.next()) {
        id = rsId.getInt(SessionColumns.USER_ID);
      }
    } finally {
      closeConnection();
      return id;
    }
  }

  private Statement openConnection() {
    Statement statement = null;
    try {
      con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
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
