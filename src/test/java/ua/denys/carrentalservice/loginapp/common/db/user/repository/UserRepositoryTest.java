package ua.denys.carrentalservice.loginapp.common.db.user.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.denys.carrentalservice.utils.DatabaseConstUtils.DB_URL;

import java.sql.SQLException;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import ua.denys.carrentalservice.utils.displaynamegenerator.CamelCaseToSpace;

@DisplayNameGeneration(CamelCaseToSpace.class)
public class UserRepositoryTest {
  private static final UserRepository repository = UserRepository.getInstance(DB_URL);

  @Test
  public void shouldFind2Id() throws SQLException {
    // given
    var expectedId = 2;
    var expectedUsername = "testUser2";
    var expectedPassword = "test";
    repository.saveUser("testUser1", "test");
    repository.saveUser(expectedUsername, expectedPassword);
    repository.saveUser("testUser3", "test");
    repository.saveUser("testUser4", "test");
    // when
    var user = repository.findByUsername("testUser2");
    // then
    assertEquals(expectedId, user.getId());
    assertEquals(expectedUsername, user.getUsername());
    assertEquals(expectedPassword, user.getPassword());
  }
}
