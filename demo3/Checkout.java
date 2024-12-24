package sample.demo3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Checkout {

    private VBox root;
    private RadioButton creditButton;
    private RadioButton debitButton;
    private RadioButton paypalButton;
    private TextField cardNumber;
    private TextField cvvNumber;
    private TextField promoCode;
    private TextField emailField;
    private PasswordField passwordField;
    private Button processButton;

    public Checkout() {
        // Initialize UI elements
        root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-alignment: TOP_CENTER");

        Label titleLabel = new Label("Payment Method");
        titleLabel.setFont(Font.font("Montserrat Medium", 24));
        titleLabel.setStyle("-fx-text-fill: #2d8f92;");

        ToggleGroup paymentGroup = new ToggleGroup();

        creditButton = new RadioButton("Credit");
        creditButton.setToggleGroup(paymentGroup);
        creditButton.setSelected(true);
        creditButton.setFont(Font.font("Montserrat Regular", 12));
        creditButton.setStyle("-fx-text-fill: #2d8f92;");

        debitButton = new RadioButton("Debit");
        debitButton.setToggleGroup(paymentGroup);
        debitButton.setFont(Font.font("Montserrat Regular", 12));
        debitButton.setStyle("-fx-text-fill: #2d8f92;");

        paypalButton = new RadioButton("PayPal");
        paypalButton.setToggleGroup(paymentGroup);
        paypalButton.setFont(Font.font("Montserrat Regular", 12));
        paypalButton.setStyle("-fx-text-fill: #2d8f92;");

        HBox paymentBox = new HBox(10, creditButton, debitButton, paypalButton);

        cardNumber = new TextField();
        cardNumber.setPromptText("Card number");

        cvvNumber = new TextField();
        cvvNumber.setPromptText("CVV");

        promoCode = new TextField();
        promoCode.setPromptText("Promo Code (Optional)");

        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setVisible(false); // Initially hidden

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setVisible(false); // Initially hidden

        processButton = new Button("Process Payment");
        processButton.setStyle("-fx-background-color: transparent; -fx-border-color: #2d8f92; -fx-border-radius: 30; -fx-text-fill: #2d8f92;");
        processButton.setOnAction(e -> processPayment());

        VBox inputBox = new VBox(10, cardNumber, cvvNumber, emailField, passwordField, promoCode, processButton);

        // Add change listener to toggle visibility of input fields
        paymentGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == paypalButton) {
                cardNumber.setVisible(false);
                cvvNumber.setVisible(false);
                emailField.setVisible(true);
                passwordField.setVisible(true);
            } else {
                cardNumber.setVisible(true);
                cvvNumber.setVisible(true);
                emailField.setVisible(false);
                passwordField.setVisible(false);
            }
        });

        root.getChildren().addAll(titleLabel, paymentBox, inputBox);
    }

    public VBox getRoot() {
        return root;
    }

    private void processPayment() {
        String selectedMethod = creditButton.isSelected() ? "Credit" :
                debitButton.isSelected() ? "Debit" : "PayPal";

        String promo = promoCode.getText();

        if (paypalButton.isSelected()) {
            String email = emailField.getText().trim();
            String password = passwordField.getText();

            // Validate email
            if (!validateEmail(email)) {
                showAlert(Alert.AlertType.INFORMATION, "Validation Error", "Invalid email address. Please enter a valid email.");
                return;
            }

            // Validate password
            if (password.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Validation Error", "Password cannot be empty.");
                return;
            }

            // Simulate successful payment via PayPal
            integrateWithBackend(selectedMethod, email, password, promo);
            showAlert(Alert.AlertType.INFORMATION, "Payment Successful", "Your payment has been processed successfully!");
        } else {
            String cardNum = cardNumber.getText().trim();
            String cvv = cvvNumber.getText().trim();

            // Validate card details
            if (!validateCardDetails(cardNum, cvv)) {
                showAlert(Alert.AlertType.INFORMATION, "Validation Error", "Invalid card details. Please check the card number and CVV.");
                return;
            }

            // Apply promo code
            if (!promo.isEmpty() && !applyPromoCode(promo)) {
                showAlert(Alert.AlertType.INFORMATION, "Validation Error", "Invalid promo code. Please try again.");
                return;
            }

            // Simulate successful payment
            integrateWithBackend(selectedMethod, cardNum, cvv, promo);
            showAlert(Alert.AlertType.INFORMATION, "Payment Successful", "Your payment has been processed successfully!");
        }

        // Close the application
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    private boolean validateCardDetails(String cardNumber, String cvv) {
        if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            return false;
        }
        if (cvv.length() != 3 || !cvv.matches("\\d+")) {
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }

    public boolean applyPromoCode(String promoCode) {
        int discountPercentage = 0;

        if (promoCode.equalsIgnoreCase("SAVE10")) {
            discountPercentage = 10;
        } else if (promoCode.equalsIgnoreCase("SAVE20")) {
            discountPercentage = 20;
        } else if (promoCode.equalsIgnoreCase("FREESHIP")) {
            discountPercentage = 15;
        } else {
            return false;
        }

        System.out.println("Promo code applied! Discount: " + discountPercentage + "%");
        return true;
    }

    private void integrateWithBackend(String method, String input1, String input2, String promo) {
        System.out.println("Integrating with backend using method: " + method);
        System.out.println("Input 1: " + input1);
        System.out.println("Input 2: " + input2);
        System.out.println("Promo code: " + promo);
        System.out.println("Backend integration complete.");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
