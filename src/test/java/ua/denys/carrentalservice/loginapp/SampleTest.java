package ua.denys.carrentalservice.loginapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.denys.carrentalservice.utils.DatabaseConstUtils.URL;

import java.sql.SQLException;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import ua.denys.carrentalservice.loginapp.common.db.DbHelper;
import ua.denys.carrentalservice.utils.displaynamegenerator.CamelCaseToSpace;

@DisplayNameGeneration(CamelCaseToSpace.class)
public class SampleTest {
  private static final DbHelper dbHelperInstance = DbHelper.getInstance(URL);

  @Test
  public void shouldFind2Id() throws SQLException {
    // given
    dbHelperInstance.registerUser("testUser1", "test");
    dbHelperInstance.registerUser("testUser2", "test");
    dbHelperInstance.registerUser("testUser3", "test");
    dbHelperInstance.registerUser("testUser4", "test");
    // when
    var expectedId = dbHelperInstance.getIdByUsername("testUser2");
    // then
    assertEquals(2, expectedId);
  }
}
