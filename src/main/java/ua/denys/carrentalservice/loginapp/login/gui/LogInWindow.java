package ua.denys.carrentalservice.loginapp.login.gui;

import static ua.denys.carrentalservice.loginapp.utils.ConsoleMessageConst.SESSION_CREATE_SUCCESS;
import static ua.denys.carrentalservice.loginapp.utils.ConstUtils.PATH_TO_KEY;

import java.io.File;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import javax.swing.*;
import ua.denys.carrentalservice.basicapp.gui.BasicApp;
import ua.denys.carrentalservice.loginapp.common.LabelHandler;
import ua.denys.carrentalservice.loginapp.login.service.LoginService;
import ua.denys.carrentalservice.loginapp.registration.gui.RegisterDialog;
import ua.denys.carrentalservice.loginapp.session.service.SessionService;
import ua.denys.carrentalservice.loginapp.utils.FieldValidation;

public class LogInWindow extends JFrame {
  private static LogInWindow INSTANCE = null;

  private JPanel mainPanel;
  private JTextField usernameField;
  private JPasswordField passwordField;
  private JButton loginButton;
  private JCheckBox rememberMeCheckBox;
  private JButton registerButton;
  private JLabel passwordLabel;
  private JLabel usernameLabel;
  private JLabel errorLabel;

  private LogInWindow() {
    boolean fileNotExists = !new File(PATH_TO_KEY).exists();
    var sessionService = SessionService.getInstance();

    if (fileNotExists) {
      LabelHandler.resetError(errorLabel);
    } else if (sessionService.isExpired()) {
      resetSession();
      LabelHandler.setError(errorLabel, "Your session is already expired.");
    } else {
      changeWindow();
      return;
    }
    setTitle("Please, login into your account.");
    setContentPane(mainPanel);
    setDefaultCloseOperation(3);
    setVisible(true);
    setSize(400, 600);
    loginButton.addActionListener(e -> login());
    registerButton.addActionListener(e -> new RegisterDialog().showDialog());
  }

  public static LogInWindow getInstance() {
    if (INSTANCE == null) INSTANCE = new LogInWindow();
    return INSTANCE;
  }

  private void login() {
    checkFields();

    var loginService = LoginService.getInstance();
    String username = usernameField.getText();
    String password = Arrays.toString(passwordField.getPassword());

    try {
      if (loginService.login(username, password)) {
        createSessionIfRememberMe();
        changeWindow();
      } else {
        LabelHandler.setError(errorLabel, "Username or password is incorrect");
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  private boolean createSessionIfRememberMe() throws SQLException {

    if (rememberMeCheckBox.isSelected()) {
      SessionService sessionService = SessionService.getInstance();
      String username = usernameField.getText();

      try {
        sessionService.create(username);
        System.out.println(SESSION_CREATE_SUCCESS);
      } catch (SQLIntegrityConstraintViolationException e) {
        LabelHandler.setError(errorLabel, "User with that name is already logged");
        return true;
      }
    }
    return false;
  }

  private void checkFields() {
    if (!FieldValidation.isNotEmpty(usernameField)) {
      LabelHandler.setError(usernameLabel, "Field is empty.");
    } else LabelHandler.resetError(usernameLabel);

    if (!FieldValidation.isNotEmpty(passwordField)) {
      LabelHandler.setError(passwordLabel, "Field is empty.");
    } else LabelHandler.resetError(passwordLabel);
  }

  private void resetSession() {
    SessionService.getInstance().delete();
  }

  private void changeWindow() {
    BasicApp.getInstance();
    dispose();
  }
}
