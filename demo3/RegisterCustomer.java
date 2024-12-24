
package sample.demo3;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegisterCustomer {
    private Stage stage;
    private Scene scene;
    private AnchorPane root;
    private Runnable registrationSuccessCallback;
    private Runnable backButtonCallback;

    public RegisterCustomer() {
        root = new AnchorPane();
        root.setPrefSize(600, 400);
        initializeUI();
        scene = new Scene(root, 600, 400);
    }

    public void setRegistrationSuccessCallback(Runnable callback) {
        this.registrationSuccessCallback = callback;
    }

    public void setBackButtonCallback(Runnable callback) {
        this.backButtonCallback = callback;
    }

    public void show(Stage stage) {
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle("E-Commerce System - Register");
        stage.show();
    }

    private void initializeUI() {
        // Background Rectangle
        Rectangle backgroundRect = new Rectangle(300, 0, 300, 600);
        backgroundRect.setArcHeight(5);
        backgroundRect.setArcWidth(5);
        backgroundRect.setStroke(Color.BLACK);
        backgroundRect.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        backgroundRect.setFill(new LinearGradient(
                0, 0, 1, 1, true,
                javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(45, 143, 146)),
                new Stop(1, Color.WHITE)
        ));
        root.getChildren().add(backgroundRect);

        // Left side content
        try {
            ImageView arrowImage = new ImageView(new Image(getClass().getResource("/sample/demo3/arrow.png").toExternalForm()));
            arrowImage.setFitWidth(81);
            arrowImage.setFitHeight(80);
            arrowImage.setLayoutX(112);
            arrowImage.setLayoutY(65);
            arrowImage.setPickOnBounds(true);
            arrowImage.setPreserveRatio(true);
            arrowImage.setEffect(new MotionBlur());
            root.getChildren().add(arrowImage);
        } catch (Exception e) {
            System.err.println("Error loading arrow image: " + e.getMessage());
        }

        Label joinUsLabel = new Label("Join Us!");
        joinUsLabel.setLayoutX(104);
        joinUsLabel.setLayoutY(158);
        joinUsLabel.setFont(Font.font("Montserrat Medium", 24));
        joinUsLabel.setTextFill(Color.rgb(45, 143, 146));
        root.getChildren().add(joinUsLabel);

        Label descriptionLabel = new Label("Subscribe Easy Tutorials YouTube Channel to watch more videos");
        descriptionLabel.setLayoutX(80);
        descriptionLabel.setLayoutY(200);
        descriptionLabel.setPrefHeight(80);
        descriptionLabel.setPrefWidth(144);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setFont(Font.font("Montserrat Regular", 14));
        descriptionLabel.setTextFill(Color.rgb(45, 143, 146));
        root.getChildren().add(descriptionLabel);

        // Right side content
        VBox rightContent = new VBox(20);
        rightContent.setLayoutX(320);
        rightContent.setLayoutY(20);
        rightContent.setPrefWidth(260);
        rightContent.setAlignment(Pos.TOP_CENTER);

        Label registerLabel = new Label("Register Here");
        registerLabel.setFont(Font.font("Montserrat Medium", 24));
        registerLabel.setTextFill(Color.WHITE);

        // Form fields
        VBox formVBox = new VBox(15);
        formVBox.setAlignment(Pos.TOP_CENTER);
        formVBox.setPrefWidth(240);

        String[] prompts = {"Username", "Password", "Date of Birth (DD/MM/YYYY)", "Balance", "Address", "Gender", "Interests"};
        TextField[] fields = new TextField[prompts.length];
        Label[] errorLabels = new Label[prompts.length];

        for (int i = 0; i < prompts.length; i++) {
            VBox fieldBox = new VBox(5);
            TextField field = new TextField();
            field.setPromptText(prompts[i]);
            field.setPrefWidth(240);
            Label errorLabel = new Label();
            errorLabel.setTextFill(Color.RED);
            errorLabel.setWrapText(true);
            fieldBox.getChildren().addAll(field, errorLabel);
            formVBox.getChildren().add(fieldBox);
            fields[i] = field;
            errorLabels[i] = errorLabel;
        }

        // Buttons VBox
        VBox buttonsBox = new VBox(10);
        buttonsBox.setAlignment(Pos.CENTER);

        // Register Button
        Button registerButton = new Button("REGISTER");
        registerButton.setStyle("-fx-background-color: transparent; -fx-border-color: white; -fx-border-radius: 30; -fx-min-width: 120;");
        registerButton.setTextFill(Color.WHITE);
        registerButton.setFont(Font.font("Montserrat Regular", 12));
        registerButton.setOnAction(e -> handleRegistration(fields, errorLabels));

        // Back Button
        Button backButton = new Button("BACK TO LOGIN");
        backButton.setStyle("-fx-background-color: transparent; -fx-border-color: white; -fx-border-radius: 30; -fx-min-width: 120;");
        backButton.setTextFill(Color.WHITE);
        backButton.setFont(Font.font("Montserrat Regular", 12));
        backButton.setOnAction(e -> {
            if (backButtonCallback != null) {
                backButtonCallback.run();
            }
        });

        buttonsBox.getChildren().addAll(registerButton, backButton);

        // Add all components to the right content VBox
        rightContent.getChildren().addAll(registerLabel, formVBox, buttonsBox);
        root.getChildren().add(rightContent);
    }

    private void handleRegistration(TextField[] fields, Label[] errorLabels) {
        boolean allFieldsValid = true;

        // Reset all error messages
        for (Label errorLabel : errorLabels) {
            errorLabel.setText("");
        }

        // Extract field values
        String username = fields[0].getText().trim();
        String password = fields[1].getText().trim();
        String dob = fields[2].getText().trim();
        String balanceStr = fields[3].getText().trim();
        String address = fields[4].getText().trim();
        String genderStr = fields[5].getText().trim();

        // Validate Username (using existing logic)
        if (username.isEmpty() || !Character.isLetter(username.charAt(0)) || username.equals(username.toUpperCase())) {
            fields[0].setStyle("-fx-border-color: red;");
            errorLabels[0].setText("Username must start with a letter and include lowercase letters.");
            allFieldsValid = false;
        } else {
            fields[0].setStyle("-fx-border-color: #2d8f92;");
        }

        // Validate Password
        if (password.length() < 5) {
            fields[1].setStyle("-fx-border-color: red;");
            errorLabels[1].setText("Password must be at least 5 characters long.");
            allFieldsValid = false;
        } else {
            fields[1].setStyle("-fx-border-color: #2d8f92;");
        }

        // Validate Date of Birth
        LocalDate parsedDob = null;
        if (!dob.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            fields[2].setStyle("-fx-border-color: red;");
            errorLabels[2].setText("Date must be in the format DD/MM/YYYY.");
            allFieldsValid = false;
        } else {
            try {
                parsedDob = LocalDate.parse(dob, DateTimeFormatter.ofPattern("d/M/yyyy"));
                if (parsedDob.isAfter(LocalDate.now())) {
                    fields[2].setStyle("-fx-border-color: red;");
                    errorLabels[2].setText("Date of birth cannot be in the future.");
                    allFieldsValid = false;
                } else {
                    fields[2].setStyle("-fx-border-color: #2d8f92;");
                }
            } catch (Exception ex) {
                fields[2].setStyle("-fx-border-color: red;");
                errorLabels[2].setText("Invalid date format.");
                allFieldsValid = false;
            }
        }

        // Validate Balance
        double balance = 0;
        try {
            balance = Double.parseDouble(balanceStr);
            fields[3].setStyle("-fx-border-color: #2d8f92;");
        } catch (NumberFormatException ex) {
            fields[3].setStyle("-fx-border-color: red;");
            errorLabels[3].setText("Balance must be a valid number.");
            allFieldsValid = false;
        }

        // Validate Address
        if (address.isEmpty()) {
            fields[4].setStyle("-fx-border-color: red;");
            errorLabels[4].setText("Address cannot be empty.");
            allFieldsValid = false;
        } else {
            fields[4].setStyle("-fx-border-color: #2d8f92;");
        }

        // Validate Gender
        Customer.Gender gender = null;
        try {
            gender = Customer.Gender.valueOf(genderStr.trim());
            fields[5].setStyle("-fx-border-color: #2d8f92;");
        } catch (IllegalArgumentException ex) {
            fields[5].setStyle("-fx-border-color: red;");
            errorLabels[5].setText("Gender must be 'Male' or 'Female'.");
            allFieldsValid = false;
        }

        // If all fields are valid, create customer and add to database
        if (allFieldsValid) {
            try {
                Customer newCustomer = new Customer(username, password, dob, balance, address, gender);
                Database.addCustomer(newCustomer);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Registration Successful");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Registration completed successfully! Customer count: " + Database.customerCount);
                successAlert.showAndWait();

                // Clear fields and execute callback
                for (TextField field : fields) {
                    field.clear();
                }

                if (registrationSuccessCallback != null) {
                    registrationSuccessCallback.run();
                }
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Registration Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Error creating customer: " + e.getMessage());
                errorAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Registration Failed");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please fix the highlighted errors and try again.");
            errorAlert.showAndWait();
        }
    }
}