package ua.denys.carrentalservice.loginapp.login.service;

import ua.denys.carrentalservice.loginapp.common.db.DbHelper;
import ua.denys.carrentalservice.loginapp.utils.Md5Encoder;

import java.sql.SQLException;

public class LoginService {
    private static LoginService INSTANCE = null;

    private LoginService() {
    }

    public static LoginService getInstance() {
        if (INSTANCE == null) INSTANCE = new LoginService();
        return INSTANCE;
    }

    public boolean login(String username, String password) throws SQLException {
        var dbHelper = DbHelper.getInstance();
        var md5 = Md5Encoder.getINSTANCE();
        var cipheredPassword = md5.encodeToMD5(password);
        return dbHelper.login(username, cipheredPassword);
    }
}
