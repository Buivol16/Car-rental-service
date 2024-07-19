package ua.denys.carrentalservice.utils;

import java.time.format.DateTimeFormatter;

public final class DatabaseConstUtils {
  public static final String JDBC_DRIVER_NAME = "org.h2.Driver";
  public static final String USER_NAME = "root";
  public static final String PASSWORD = "admin";
  public static final String DB_URL = "jdbc:h2:mem:carrental;INIT=runscript from 'src/create.sql';DB_CLOSE_DELAY=-1";

  public static final String USERS_TABLE = "users";
  public static final String SESSION_TABLE = "session";
  public static final String CARS_TABLE = "cars";

  public static class UsersColumns {
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
  }

  public static class SessionColumns {
    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String SESSION_KEY = "session_key";
    public static final String EXPIRED_AT = "expired_at";
  }

  public static class SessionColumnConsts {
    public static final int MINUTES_PLUS = 30;
    public static final DateTimeFormatter dtf =
        DateTimeFormatter.ofPattern(ConstUtils.TIME_PATTERN);
  }

  public static class CarsColumns {
    public static final String BRAND = "brand";
    public static final String MODEL = "model";
    public static final String CAR_SEATS = "car_seats";
    public static final String MASS_BAGGAGE_TO_POSSIBLE = "mass_baggage_to_possible";
    public static final String TRANSMISSION = "transmission";
    public static final String ENGINE_VOLUME = "engine_volume";
    public static final String BODY_TYPE = "body_type";
    public static final String FUEL_TYPE = "fuel_type";
    public static final String POWER = "power";
    public static final String STATUS = "status";
    public static final String USER_ID = "user_id";
  }
}
