package sample.demo3;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Validation{
public static boolean validateUsername(String username) {
    if (username.isEmpty()) {
        showError("Username cannot be empty.");
        return false;
    }
    if (!Character.isLetter(username.charAt(0))) {
        showError("Username must start with a letter.");
        return false;
    }
    if (username.equals(username.toUpperCase())) {
        showError("Username cannot be all uppercase letters.");
        return false;
    }
    return true;
}

public static boolean validatePassword(String password) {
    if (password.length() < 5) {
        showError("Password must be at least 5 characters long.");
        return false;
    }
    return true;
}

public static boolean validateDateOfBirth(String dateOfBirth) {
    if (!dateOfBirth.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
        showError("Invalid date format. Please use dd/MM/yyyy");
        return false;
    }

    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate parsedDate = LocalDate.parse(dateOfBirth, formatter);

        if (parsedDate.isAfter(LocalDate.now())) {
            showError("Date of birth cannot be in the future.");
            return false;
        }
    } catch (Exception e) {
        showError("Invalid date. Please enter a valid date in dd/MM/yyyy format.");
        return false;
    }
    return true;
}

public static boolean validateGender(String gender) {
    try {
        Customer.Gender.valueOf(gender);
        return true;
    } catch (IllegalArgumentException e) {
        showError("Invalid gender. Please enter 'Male' or 'Female'");
        return false;
    }
}

public static boolean validateNumericField(String value, String fieldName) {
    try {
        double numericValue = Double.parseDouble(value);
        if (numericValue < 0) {
            showError(fieldName + " cannot be negative.");
            return false;
        }
        return true;
    } catch (NumberFormatException e) {
        showError("Please enter a valid number for " + fieldName);
        return false;
    }
}

public static Customer validateCustomerLogin(String username, String password) {
    for (Customer customer : Database.customers) {
        if (customer != null && customer.getUsername().equals(username)
                && customer.getPassword().equals(password)) {
            return customer;
        }
    }
    return null;
}

public static Admin validateAdminLogin(String username, String password) {
    for (Admin admin : Database.admins) {
        if (admin != null && admin.getUsername().equals(username)
                && admin.getPassword().equals(password)) {
            return admin;
        }
    }
    return null;
}

public static void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Validation Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

public static void showSuccess(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
}