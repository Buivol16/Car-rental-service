package ua.denys.carrentalservice.loginapp.common.db.user.consts;

import static ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils.USERS_TABLE;

import ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils.UsersColumns;

public class UserSQLConst {
  public static final String selectByUsername(String username) {
    String queryFormat = "SELECT * FROM %s WHERE %s = '%s'";
    return String.format(queryFormat, USERS_TABLE, UsersColumns.USERNAME, username);
  }

  public static final String selectById(Long id) {
    String queryFormat = "SELECT * FROM %s WHERE %s = '%s'";
    return String.format(queryFormat, USERS_TABLE, UsersColumns.ID, id);
  }

  public static final String insertUser(String username, String password) {
    String queryFormat = "INSERT INTO %s(%s, %s) values('%s', '%s')";
    return String.format(
        queryFormat, USERS_TABLE, UsersColumns.USERNAME, UsersColumns.PASSWORD, username, password);
  }
}
