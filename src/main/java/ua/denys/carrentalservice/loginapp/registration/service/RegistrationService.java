package ua.denys.carrentalservice.loginapp.registration.service;

import ua.denys.carrentalservice.loginapp.common.db.DbHelper;
import ua.denys.carrentalservice.loginapp.exception.OccupiedNameException;

import java.sql.SQLException;

public class RegistrationService {
    private static RegistrationService INSTANCE = null;

    private RegistrationService(){}
    public static RegistrationService getInstance(){
        if (INSTANCE == null) INSTANCE = new RegistrationService();
        return INSTANCE;
    }

    public void perform(String username, String password) throws SQLException, OccupiedNameException {
        if (DbHelper.getInstance().checkUser(username)) throw new OccupiedNameException("The name of user is exist.");
        DbHelper.getInstance().registerUser(username, password);
    }
}
