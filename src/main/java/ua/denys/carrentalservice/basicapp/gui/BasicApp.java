package ua.denys.carrentalservice.basicapp.gui;

import java.sql.SQLException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import ua.denys.carrentalservice.basicapp.services.CarService;
import ua.denys.carrentalservice.loginapp.common.LabelHandler;

import static ua.denys.carrentalservice.basicapp.consts.BasicAppConst.BASIC_APP_HEIGHT;
import static ua.denys.carrentalservice.basicapp.consts.BasicAppConst.BASIC_APP_TITLE;
import static ua.denys.carrentalservice.basicapp.consts.BasicAppConst.BASIC_APP_WIDTH;
import static ua.denys.carrentalservice.basicapp.consts.FileFormatConst.IMAGE_FILE_FORMATS;

public class BasicApp extends JFrame {
  private int backPageIndex = 0;

  private static BasicApp INSTANCE = null;
  private JPanel mainPanel;
  private JTabbedPane pages;
  private JButton newOfferButton;
  private JPanel mainPage;
  private JPanel createPage;
  private JPanel profilePage;
  private JTextField textField1;
  private JButton myProfileButton;
  private JButton newOfferBackButton;
  private JButton profileBackButton;
  private JButton letSSeeButton;
  private JButton uploadPhotoButton;
  private JList carList;
  private JTextField textField4;
  private JButton createButton;
  private JButton createANewCarButton;
  private JTextField powerField;
  private JTextField brandField;
  private JTextField modelField;
  private JTextField seatsField;
  private JList fuelList;
  private JTextField volumeField;
  private JList transmissionList;
  private JTextField baggageMassField;
  private JPanel creatingNewCar;
  private JList bodyList;
  private JButton createCarButton;
  private JLabel errorLabel;
  private JButton backCarButton;
  private JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView());
  private FileNameExtensionFilter imageFilter =
      new FileNameExtensionFilter("Image files", IMAGE_FILE_FORMATS);

  private BasicApp() {
    setSize(BASIC_APP_WIDTH, BASIC_APP_HEIGHT);
    setTitle(BASIC_APP_TITLE);
    setContentPane(mainPanel);
    newOfferButton.addActionListener(e -> toPage(1));
    myProfileButton.addActionListener(e -> toPage(2));
    createANewCarButton.addActionListener(e -> toPage(3));
    newOfferBackButton.addActionListener(e -> back(0));
    profileBackButton.addActionListener(e -> back());
    backCarButton.addActionListener(e -> back());
    uploadPhotoButton.addActionListener(e -> choosePhoto());
    createCarButton.addActionListener(
        e -> {
          try {
            CarService carServiceInstance = CarService.getInstance();

            String brandField = this.brandField.getText();
            String numberOfSeats = this.seatsField.getText();
            String body = bodyList.getSelectedValue().toString();
            String volume = volumeField.getText();
            String fuelType = fuelList.getSelectedValue().toString();
            String baggageMass = baggageMassField.getText();
            String model = modelField.getText();
            String power = powerField.getText();
            String transmission = transmissionList.getSelectedValue().toString();

            carServiceInstance.createNewCar(
                brandField,
                Integer.parseInt(numberOfSeats),
                body,
                Integer.parseInt(volume),
                fuelType,
                Integer.parseInt(baggageMass),
                model,
                Integer.parseInt(power),
                transmission);
          } catch (SQLException exception) {
            LabelHandler.setError(errorLabel, "Some fields is blank");
            exception.printStackTrace();
          }
        });
    setDefaultCloseOperation(3);
    setVisible(true);
  }

  public static BasicApp getInstance() {
    if (INSTANCE == null) INSTANCE = new BasicApp();
    return INSTANCE;
  }

  private void toPage(int indexPage) {
    backPageIndex = pages.getSelectedIndex();
    pages.setSelectedIndex(indexPage);
    if (indexPage == 1) getCarsList();
  }

  private void back() {
    pages.setSelectedIndex(backPageIndex);
    if (pages.getSelectedIndex() == 1) getCarsList();
  }

  private void back(int index) {
    pages.setSelectedIndex(index);
    if (pages.getSelectedIndex() == 1) getCarsList();
  }

  private void choosePhoto() {
    jFileChooser.showSaveDialog(null);
    jFileChooser.addChoosableFileFilter(imageFilter);
  }

  private void getCarsList() {
    var carServiceInstance = CarService.getInstance();
    var cars = carServiceInstance.getCarListByUserId();
    var dfl = new DefaultListModel<>();
    for (var car : cars) {
      var selectedCar = car.getCar();
      dfl.addElement(selectedCar);
    }
    carList.setModel(dfl);
  }
}
