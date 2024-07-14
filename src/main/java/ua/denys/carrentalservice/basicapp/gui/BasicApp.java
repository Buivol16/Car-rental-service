package ua.denys.carrentalservice.basicapp.gui;

import ua.denys.carrentalservice.basicapp.model.Car;
import ua.denys.carrentalservice.basicapp.services.CarService;
import ua.denys.carrentalservice.loginapp.common.LabelHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
      new FileNameExtensionFilter(
          "Image files", "tif, tiff, bmp, jpg, jpeg, png, raw, cr2, nef, orf, sr2");

  private BasicApp() {
    setSize(1400, 1000);
    setTitle("Car rent app");
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
            CarService.getInstance()
                .createNewCar(
                    brandField.getText(),
                    Integer.parseInt(seatsField.getText()),
                    bodyList.getSelectedValue().toString(),
                    Integer.parseInt(volumeField.getText()),
                    fuelList.getSelectedValue().toString(),
                    Integer.parseInt(baggageMassField.getText()),
                    modelField.getText(),
                    Integer.parseInt(powerField.getText()),
                    transmissionList.getSelectedValue().toString());
          } catch (SQLException exception) {
            LabelHandler.setError(errorLabel, "Not all is blank");
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
    List<Car> cars = new ArrayList<>();
    cars = CarService.getInstance().getByUserId();
    DefaultListModel<String> dfl = new DefaultListModel<>();
    for (Car car : cars) {
      dfl.addElement(car.getCar());
    }
    carList.setModel(dfl);
  }
}
