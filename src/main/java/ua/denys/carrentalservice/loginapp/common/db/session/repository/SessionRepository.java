package ua.denys.carrentalservice.loginapp.common.db.session.repository;

import static ua.denys.carrentalservice.loginapp.common.db.session.consts.SessionSQLConst.selectBySessionKey;
import static ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils.SESSION_TABLE;

import java.sql.ResultSet;
import java.sql.SQLException;
import ua.denys.carrentalservice.loginapp.common.db.DbHelper;
import ua.denys.carrentalservice.loginapp.session.model.Session;
import ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils;

public class SessionRepository extends DbHelper {
  private static SessionRepository INSTANCE;

  private SessionRepository(String url) {
    super(url);
  }

  public static SessionRepository getInstance(String url) {
    if (INSTANCE == null) INSTANCE = new SessionRepository(url);
    return INSTANCE;
  }

  public Long getUserIdBySessionKey(String sessionKey) throws SQLException {
    try (var con = openConnection()) {
      var query = selectBySessionKey(sessionKey);
      var resultSet = con.executeQuery(query);
      if (resultSet.next()) {
        return resultSet.getLong(DatabaseConstUtils.SessionColumns.USER_ID);
      }
      return null;
    }
  }

  public String getSessionExpired_at(String session_key) throws SQLException {
    String result = "";
    try (var con = openConnection()) {
      StringBuilder stringBuilder = new StringBuilder();
      ResultSet rs = null;
      rs =
          con.executeQuery(
              stringBuilder
                  .append("SELECT ")
                  .append(DatabaseConstUtils.SessionColumns.EXPIRED_AT)
                  .append(" FROM ")
                  .append(SESSION_TABLE)
                  .append(" WHERE ")
                  .append(DatabaseConstUtils.SessionColumns.SESSION_KEY)
                  .append(" = ")
                  .append("'")
                  .append(session_key)
                  .append("'")
                  .toString());
      while (rs.next()) result = rs.getString(1);
    }
    return result;
  }

  public void registerSession(Session session) throws SQLException {
    try (var con = openConnection()) {

      con.execute(
          "INSERT INTO session("
              + DatabaseConstUtils.SessionColumns.USER_ID
              + ", "
              + DatabaseConstUtils.SessionColumns.SESSION_KEY
              + ", "
              + DatabaseConstUtils.SessionColumns.EXPIRED_AT
              + ") values("
              + session.getUserId()
              + ", '"
              + session.getSessionKey()
              + "', '"
              + session.getExpiredAt()
              + "')");
    }
  }

  public void deleteSession(String session_key) throws SQLException {
    StringBuilder stringBuilder = new StringBuilder();
    try (var con = openConnection()) {

      con.execute(
          stringBuilder
              .append("DELETE FROM ")
              .append(SESSION_TABLE)
              .append(" WHERE ")
              .append(DatabaseConstUtils.SessionColumns.SESSION_KEY)
              .append(" = ")
              .append("'")
              .append(session_key)
              .append("'")
              .toString());
    }
  }
}
