package ua.denys.carrentalservice.loginapp.common.db.user.mapper;

import ua.denys.carrentalservice.loginapp.common.db.user.model.User;
import ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils.UsersColumns;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
  public static final User resultSetToUser(ResultSet resultSet) throws SQLException {
    var userBuilder = User.builder();
    var id = resultSet.getLong(UsersColumns.ID);
    var username = resultSet.getString(UsersColumns.USERNAME);
    var password = resultSet.getString(UsersColumns.PASSWORD);
    userBuilder.id(id).username(username).password(password);

    return userBuilder.build();
  }
}
