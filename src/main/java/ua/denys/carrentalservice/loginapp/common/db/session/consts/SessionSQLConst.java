package ua.denys.carrentalservice.loginapp.common.db.session.consts;

import ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils;
import ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils.SessionColumns;

import static ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils.SESSION_TABLE;

public class SessionSQLConst {
  public static final String selectBySessionKey(String sessionKey) {
    var sqlFormat = "SELECT %s FROM %s WHERE %s = '%s'";

    return String.format(
        sqlFormat, SessionColumns.USER_ID, SESSION_TABLE, SessionColumns.SESSION_KEY, sessionKey);
  }
}
