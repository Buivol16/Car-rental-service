package ua.denys.carrentalservice.basicapp.model;

public class Car {
  private String brand;
  private String model;
  private int seatsCount;
  private int baggageMass;
  private String bodyType;

  public Car(String brand, String model, int seatsCount, int baggageMass, String bodyType) {
    this.brand = brand;
    this.model = model;
    this.seatsCount = seatsCount;
    this.baggageMass = baggageMass;
    this.bodyType = bodyType;
  }

  public String getCar() {
    return new StringBuilder()
        .append(brand + " | ")
        .append(model + " | ")
        .append(seatsCount + " seats | ")
        .append(baggageMass + " kg | ")
        .append(bodyType)
        .toString();
  }
}
