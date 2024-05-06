package lk.ijse.tailorshop.util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Paint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFiledValid(TextField textField, String text){
        //validation
        String field = "";

        switch (textField){
            case CUSTOMERID:
                field = "^([A-Z][0-9]{3})$";
                break;
            case ADDRESS:
                field = "^([A-z0-9]|[-/,.@+]|\\s){4,}$";
                break;
            case EMAIL:
                field =  "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;
            case MATERIALID:
                field = "^([A-Z][0-9]{3})$";
                break;
            case QTY:
                field =  "^([0-9]){1,}[.]([0-9]){1,}$";
                break;
            case EMPLOYEEID:
                field = "^([A-Z][0-9]{3})$";
                break;
            case MEASUREMENTID:
                field = "^([A-Z][0-9]{3})$";
                break;
            case DUEDATE:
                field = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";
                break;
            case USERID:
                field = "^([A-Z][0-9]{3})$";
                break;

        }

        Pattern pattern = Pattern.compile(field);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean setTextColor(TextField location, javafx.scene.control.TextField field) {
        boolean isValid = isTextFiledValid(location, field.getText());

        if (isValid) {
            field.setStyle("-fx-border-color: green;");
        } else {
            field.setStyle("-fx-border-color: red;");
        }

        return isValid;
    }

}
