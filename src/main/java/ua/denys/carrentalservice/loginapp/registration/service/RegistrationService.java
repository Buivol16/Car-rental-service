package ua.denys.carrentalservice.loginapp.registration.service;

import ua.denys.carrentalservice.loginapp.common.db.DbHelper;
import ua.denys.carrentalservice.loginapp.exception.OccupiedNameException;
import ua.denys.carrentalservice.loginapp.utils.Md5Encoder;

import java.sql.SQLException;

public class RegistrationService {
    private static RegistrationService INSTANCE = null;

    private RegistrationService() {
    }

    public static RegistrationService getInstance() {
        if (INSTANCE == null) INSTANCE = new RegistrationService();
        return INSTANCE;
    }

    public void perform(String username, String password) throws SQLException, OccupiedNameException {
        var isUsernameExists = checkUsernameForExisting(username);
        if (isUsernameExists) throw new OccupiedNameException("The name of user is exist.");
        registerUser(username, password);
    }

    private void registerUser(String username, String password) throws SQLException {
        var dbHelper = DbHelper.getInstance();
        var encoder = Md5Encoder.getINSTANCE();
        var cipheredPass = encoder.encodeToMD5(password);

        dbHelper.registerUser(username, cipheredPass);
    }

    private boolean checkUsernameForExisting(String username) throws SQLException {
        return DbHelper.getInstance().checkUser(username);
    }
}
