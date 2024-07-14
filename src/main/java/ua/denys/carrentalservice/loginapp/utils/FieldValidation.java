package ua.denys.carrentalservice.loginapp.utils;

import javax.swing.*;

public class FieldValidation{
    public static boolean isNotEmpty(JTextField field){
        return !field.getText().isBlank();
    }
    public static boolean isNotEmpty(JTextField... fields){
        boolean areNotEmpty = true;
        for (JTextField field:
             fields) {
            if (!isNotEmpty(field)){
                areNotEmpty = false;
                break;
            }
        }
        return areNotEmpty;
    }
}
