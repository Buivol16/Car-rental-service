package ua.denys.carrentalservice.loginapp.registration.gui;

import ua.denys.carrentalservice.loginapp.exception.OccupiedNameException;
import ua.denys.carrentalservice.loginapp.registration.service.RegistrationService;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Arrays;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static ua.denys.carrentalservice.loginapp.common.LabelHandler.resetError;
import static ua.denys.carrentalservice.loginapp.common.LabelHandler.setError;
import static ua.denys.carrentalservice.loginapp.registration.utils.consts.RegisterMessageConst.SUCCESS_REGISTER_MESSAGE;
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
                    String username = usernameField.getText();
                    String password = passwordField.getText();

                    resetError(usernameErrorLabel, passwordErrorLabel);

                    checkFields();

                    if (!username.isBlank() && !password.isBlank()) register();
                });

        cancelButton.addActionListener(e -> dispose());
    }


    public void showDialog() {
        RegisterDialog dialog = new RegisterDialog();
        dialog.setSize(400, 400);
        dialog.setVisible(true);
    }

    private void register() {
        var username = usernameField.getText();
        var password = Arrays.toString(passwordField.getPassword());

        try {
            RegistrationService.getInstance().perform(username, password);
            showMessageDialog(null, SUCCESS_REGISTER_MESSAGE, "Success", INFORMATION_MESSAGE);
            dispose();
        } catch (OccupiedNameException exception) {
            var exceptionMessage = exception.getMessage();
            setError(errorLabel, exceptionMessage);
        } catch (SQLException exception) {
            exception.printStackTrace();
            setError(errorLabel, "Unexpected error");
        }
    }

    private void checkFields() {
        if (!isNotEmpty(usernameField)) {
            setError(usernameErrorLabel, "Username field is empty.");
        }
        if (!isNotEmpty(passwordField)) {
            setError(passwordErrorLabel, "Password field is empty.");
        }
    }
}
