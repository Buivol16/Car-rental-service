package ua.denys.carrentalservice.loginapp.common.db.car.repository;

import static ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils.CARS_TABLE;

import java.sql.ResultSet;
import java.sql.SQLException;
import ua.denys.carrentalservice.loginapp.common.db.DbHelper;
import ua.denys.carrentalservice.loginapp.session.service.SessionService;
import ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils;

public class CarRepository extends DbHelper {

  private static CarRepository INSTANCE;

  private CarRepository(String url) {
    super(url);
  }

  public static CarRepository getInstance(String url) {
    if (INSTANCE == null) INSTANCE = new CarRepository(url);
    return INSTANCE;
  }

  public ResultSet getCarsByUserId(Long userId) throws SQLException {
    return openConnection()
        .executeQuery(
            "SELECT * FROM "
                + CARS_TABLE
                + " WHERE "
                + DatabaseConstUtils.CarsColumns.USER_ID
                + " = "
                + userId);
  }

  public void addCar(
      String brand,
      int carSeats,
      String bodyType,
      int engineVolume,
      String fuelType,
      int massBaggageToPossible,
      String model,
      int power,
      String tranmission)
      throws SQLException {
    try (var con = openConnection()) {

      con.execute(
          new StringBuilder()
              .append("INSERT INTO ")
              .append(CARS_TABLE + "(")
              .append(DatabaseConstUtils.CarsColumns.BRAND)
              .append(", ")
              .append(DatabaseConstUtils.CarsColumns.MODEL)
              .append(", ")
              .append(DatabaseConstUtils.CarsColumns.CAR_SEATS)
              .append(", ")
              .append(DatabaseConstUtils.CarsColumns.MASS_BAGGAGE_TO_POSSIBLE)
              .append(", ")
              .append(DatabaseConstUtils.CarsColumns.TRANSMISSION)
              .append(", ")
              .append(DatabaseConstUtils.CarsColumns.ENGINE_VOLUME)
              .append(", ")
              .append(DatabaseConstUtils.CarsColumns.BODY_TYPE)
              .append(", ")
              .append(DatabaseConstUtils.CarsColumns.FUEL_TYPE)
              .append(", ")
              .append(DatabaseConstUtils.CarsColumns.POWER)
              .append(", ")
              .append(DatabaseConstUtils.CarsColumns.STATUS)
              .append(", ")
              .append(DatabaseConstUtils.CarsColumns.USER_ID)
              .append(") ")
              .append("VALUES")
              .append("(")
              .append("'")
              .append(brand)
              .append("'")
              .append(", ")
              .append("'")
              .append(model)
              .append("'")
              .append(", ")
              .append(carSeats)
              .append(", ")
              .append(massBaggageToPossible)
              .append(", ")
              .append("'")
              .append(tranmission)
              .append("'")
              .append(", ")
              .append(engineVolume)
              .append(", ")
              .append("'")
              .append(bodyType)
              .append("'")
              .append(", ")
              .append("'")
              .append(fuelType)
              .append("'")
              .append(", ")
              .append(power)
              .append(", ")
              .append("'")
              .append("Free")
              .append("'")
              .append(", ")
              .append("'")
              .append((SessionService.getInstance().getSkByFile()))
              .append("'")
              .append(")")
              .toString());
    }
  }
}
