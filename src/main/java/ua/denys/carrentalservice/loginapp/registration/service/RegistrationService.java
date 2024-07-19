package ua.denys.carrentalservice.loginapp.registration.service;

import ua.denys.carrentalservice.loginapp.common.db.user.repository.UserRepository;
import ua.denys.carrentalservice.loginapp.exception.OccupiedNameException;
import ua.denys.carrentalservice.loginapp.utils.Md5Encoder;

import java.sql.SQLException;

public class RegistrationService {
    private static RegistrationService INSTANCE = null;
    private static UserRepository userDbHelper;

    private RegistrationService(UserRepository userDbHelper) {
        this.userDbHelper = userDbHelper;
    }

    public static RegistrationService getInstance(UserRepository userDbHelper) {
        if (INSTANCE == null) INSTANCE = new RegistrationService(userDbHelper);
        return INSTANCE;
    }

    public void perform(String username, String password) throws SQLException, OccupiedNameException {
        var isUsernameExists = checkUsernameForExisting(username);
        if (isUsernameExists) throw new OccupiedNameException("The name of user is exist.");
        registerUser(username, password);
    }

    private void registerUser(String username, String password) throws SQLException {
        var dbHelper = userDbHelper;
        var encoder = Md5Encoder.getINSTANCE();
        var cipheredPass = encoder.encodeToMD5(password);

        dbHelper.saveUser(username, cipheredPass);
    }

    private boolean checkUsernameForExisting(String username) throws SQLException {
        return userDbHelper.isUsernameExists(username);
    }
}
