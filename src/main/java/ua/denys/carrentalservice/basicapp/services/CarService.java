package ua.denys.carrentalservice.basicapp.services;

import ua.denys.carrentalservice.basicapp.model.Car;
import ua.denys.carrentalservice.loginapp.common.db.DbHelper;
import ua.denys.carrentalservice.loginapp.session.service.SessionService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ua.denys.carrentalservice.loginapp.utils.DatabaseConstUtils.CarsColumns.*;

public class CarService {
  private static CarService INSTANCE = null;

  private CarService() {}

  public static CarService getInstance() {
    if (INSTANCE == null) INSTANCE = new CarService();
    return INSTANCE;
  }

  public void createNewCar(
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
    DbHelper.getInstance()
        .addCar(
            brand,
            carSeats,
            bodyType,
            engineVolume,
            fuelType,
            massBaggageToPossible,
            model,
            power,
            tranmission);
  }

  public List<Car> getByUserId() {
    ResultSet rs = null;
    List<Car> cars = null;
    try {
      rs =
          DbHelper.getInstance()
              .getCarsByUserId(
                  DbHelper.getInstance()
                      .getIdBySessionKey(SessionService.getInstance().getSkByFile()));
      while (rs.next()) {
        if (cars == null) cars = new ArrayList<>();
        cars.add(
            new Car(
                rs.getString(BRAND),
                rs.getString(MODEL),
                rs.getInt(CAR_SEATS),
                rs.getInt(MASS_BAGGAGE_TO_POSSIBLE),
                rs.getString(BODY_TYPE)));
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return cars;
  }
}
