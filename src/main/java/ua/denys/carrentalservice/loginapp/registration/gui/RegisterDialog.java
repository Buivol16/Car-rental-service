package ua.denys.carrentalservice.loginapp.registration.gui;

import ua.denys.carrentalservice.loginapp.exception.OccupiedNameException;
import ua.denys.carrentalservice.loginapp.registration.service.RegistrationService;

import javax.swing.*;
import java.sql.SQLException;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static ua.denys.carrentalservice.loginapp.common.LabelHandler.resetError;
import static ua.denys.carrentalservice.loginapp.common.LabelHandler.setError;
import static ua.denys.carrentalservice.loginapp.utils.FieldValidation.isNotEmpty;

public class RegisterDialog extends JDialog {
  private JPanel contentPane;
  private JTextField usernameField;
  private JPasswordField passwordField;
  private JButton registerMeButton;
  private JButton cancelButton;
  private JLabel usernameErrorLabel;
  private JLabel passwordErrorLabel;
  private JLabel errorLabel;

  public RegisterDialog() {
    setContentPane(contentPane);
    setModal(true);
    registerMeButton.addActionListener(
        e -> {
          resetError(usernameErrorLabel, passwordErrorLabel);

          if (!isNotEmpty(usernameField)) {
            setError(usernameErrorLabel, "Username field is empty.");
          }
          if (!isNotEmpty(passwordField)) {
            setError(passwordErrorLabel, "Password field is empty.");
          }

          if (!usernameField.getText().isBlank() && !passwordField.getText().isBlank()) {
            register();
          }
        });
    cancelButton.addActionListener(e -> dispose());
  }

  public void showDialog() {
    RegisterDialog dialog = new RegisterDialog();
    dialog.setSize(400, 400);
    dialog.setVisible(true);
  }

  private void register() {
    try {
      RegistrationService.getInstance().perform(usernameField.getText(), passwordField.getText());
    } catch (OccupiedNameException exception) {
      setError(errorLabel, exception.getMessage());
      return;
    } catch (SQLException exception) {
      exception.printStackTrace();
      setError(errorLabel, "Unexpected error");
      return;
    }
    showMessageDialog(null, "You are successfully registered!", "Success", INFORMATION_MESSAGE);
    dispose();
  }
}
