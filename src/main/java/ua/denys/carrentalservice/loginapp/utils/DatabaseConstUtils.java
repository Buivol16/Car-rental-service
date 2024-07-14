package ua.denys.carrentalservice.loginapp.utils;

import java.time.format.DateTimeFormatter;

public final class DatabaseConstUtils {
    public final static String JDBC_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    public final static String USER_NAME = "root";
    public final static String PASSWORD = "admin";
    public final static String URL = "jdbc:mysql://localhost:3306/carrental";

    public final static String USERS_TABLE = "users";
    public final static String SESSION_TABLE = "session";
    public final static String CARS_TABLE = "cars";

    public static class UsersColumns {
        public final static String ID = "id";
        public final static String USERNAME = "username";
        public final static String PASSWORD = "password";
    }
    public static class SessionColumns {
        public final static String ID = "id";
        public final static String USER_ID = "user_id";
        public final static String SESSION_KEY = "session_key";
        public final static String EXPIRED_AT = "expired_at";
    }
    public static class SessionColumnConsts {
        public final static int MINUTES_PLUS = 30;
        public final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ConstUtils.TIME_PATTERN);
    }
    public static class CarsColumns{
        public final static String BRAND = "brand";
        public final static String MODEL = "model";
        public final static String CAR_SEATS = "car_seats";
        public final static String MASS_BAGGAGE_TO_POSSIBLE = "mass_baggage_to_possible";
        public final static String TRANSMISSION = "transmission";
        public final static String ENGINE_VOLUME = "engine_volume";
        public final static String BODY_TYPE = "body_type";
        public final static String FUEL_TYPE = "fuel_type";
        public final static String POWER = "power";
        public final static String STATUS  = "status";
        public final static String USER_ID  = "user_id";
    }
}
