package ua.denys.carrentalservice.loginapp.common.db.user.repository;

import static ua.denys.carrentalservice.loginapp.common.db.user.consts.UserSQLConst.insertUser;
import static ua.denys.carrentalservice.loginapp.common.db.user.consts.UserSQLConst.selectById;
import static ua.denys.carrentalservice.loginapp.common.db.user.consts.UserSQLConst.selectByUsername;
import static ua.denys.carrentalservice.loginapp.common.db.user.mapper.UserMapper.resultSetToUser;

import java.sql.SQLException;
import ua.denys.carrentalservice.loginapp.common.db.DbHelper;
import ua.denys.carrentalservice.loginapp.common.db.user.model.User;

public class UserRepository extends DbHelper {
  private static UserRepository INSTANCE;

  private UserRepository(String url) {
    super(url);
  }

  public static UserRepository getInstance(String url) {
    if (INSTANCE == null) INSTANCE = new UserRepository(url);
    return INSTANCE;
  }

  public boolean isUsernameExists(String username) throws SQLException {
    return findByUsername(username) != null;
  }

  public User findById(Long id) throws SQLException {
    try (var con = openConnection()) {
      var query = selectById(id);
      var resultSet = con.executeQuery(query);
      if (resultSet.next() && resultSet.isFirst()) {
        return resultSetToUser(resultSet);
      }
    }
    return null;
  }
  public User findByUsername(String username) throws SQLException {
    try (var connection = openConnection()) {
      String query = selectByUsername(username);
      var resultSet = connection.executeQuery(query);
      if (resultSet.next() && resultSet.isFirst()) {
        return resultSetToUser(resultSet);
      }
    }
    return null;
  }

  public void saveUser(String username, String password) throws SQLException {
    try (var con = openConnection()) {
      var sql = insertUser(username, password);
      con.execute(sql);
    }
  }

}
