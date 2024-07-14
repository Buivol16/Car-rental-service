import org.junit.jupiter.api.*;
import ua.denys.carrentalservice.loginapp.common.db.DbHelper;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    @Test
    void shouldFindIdNumber4(){
        try {
            assertEquals(4l, DbHelper.getInstance().getIdByUsername("vladosik"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
