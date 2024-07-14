package ua.denys.carrentalservice.loginapp.common;

import ua.denys.carrentalservice.loginapp.utils.ConstUtils;

import javax.swing.*;

public class LabelHandler {
    public static void setError(JLabel labelOfOutput, String textOfError) {
        labelOfOutput.setForeground(ConstUtils.ERROR_COLOR);
        labelOfOutput.setText(textOfError);
    }

    public static void resetError(JLabel... labelOfOutput) {
        for (JLabel oneLabel : labelOfOutput) {
            oneLabel.setText("");
        }
    }
    public static void setSuccess(JLabel labelOfOutput, String textOfSuccess){
        labelOfOutput.setForeground(ConstUtils.SUCCESS_COLOR);
        labelOfOutput.setText(textOfSuccess);
    }

}
