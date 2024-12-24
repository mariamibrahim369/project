// AdminLogin.java
package sample.demo3;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class AdminLogin {
    private Stage stage;
    private Scene scene;
    private AnchorPane root;
    private TextField loginUsername;
    private PasswordField loginPassword;
    private Button loginButton;
    private Button registerButton;
    private Button backButton;
    private VBox registrationForm;
    private TextField[] registrationFields;

    // Callbacks for navigation
    private Callback<Admin> loginSuccessCallback;
    private Callback<Admin> registrationSuccessCallback;
    private Runnable backButtonCallback;

    public interface Callback<T> {
        void call(T param);
    }

    public AdminLogin() {
        root = new AnchorPane();
        root.setPrefSize(600, 400);
        initializeUI();
        scene = new Scene(root, 600, 400);
    }

    public void show(Stage stage) {
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle("Admin Login/Register Screen");
        stage.show();
    }

    private void initializeUI() {
        Rectangle backgroundRect = createBackgroundRectangle();
        setupArrowImage();
        createLoginComponents();
        createRegistrationComponents();
        createBackButton();

        root.getChildren().addAll(
                backgroundRect,
                createLoginLabel(),
                createRegisterLabel(),
                loginUsername,
                loginPassword,
                loginButton,
                registrationForm,
                registerButton,
                backButton
        );

        setupEventHandlers();
    }

    private Rectangle createBackgroundRectangle() {
        Rectangle backgroundRect = new Rectangle();
        backgroundRect.setArcHeight(5.0);
        backgroundRect.setArcWidth(5.0);
        backgroundRect.setHeight(400.0);
        backgroundRect.setLayoutX(300.0);
        backgroundRect.setWidth(300.0);
        backgroundRect.setStroke(Color.BLACK);
        backgroundRect.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        Stop[] stops = new Stop[] {
                new Stop(0, Color.rgb(45, 143, 146)),
                new Stop(1, Color.rgb(255, 255, 255))
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true,
                javafx.scene.paint.CycleMethod.NO_CYCLE, stops);
        backgroundRect.setFill(gradient);
        return backgroundRect;
    }

    private void setupArrowImage() {
        try {
            ImageView arrowImage = new ImageView(new Image(getClass().getResource("/sample/demo3/arrow.png").toExternalForm()));
            arrowImage.setFitHeight(80.0);
            arrowImage.setFitWidth(81.0);
            arrowImage.setLayoutX(112.0);
            arrowImage.setLayoutY(65.0);
            arrowImage.setPickOnBounds(true);
            arrowImage.setPreserveRatio(true);
            arrowImage.setEffect(new MotionBlur());
            root.getChildren().add(arrowImage);
        } catch (Exception e) {
            System.err.println("Error loading arrow image: " + e.getMessage());
        }
    }

    private void createLoginComponents() {
        loginUsername = new TextField();
        loginUsername.setLayoutX(85.0);
        loginUsername.setLayoutY(219.0);
        loginUsername.setPromptText("Username");

        loginPassword = new PasswordField();
        loginPassword.setLayoutX(86.0);
        loginPassword.setLayoutY(256.0);
        loginPassword.setPromptText("Password");

        loginButton = new Button("LOGIN");
        loginButton.setLayoutX(174.0);
        loginButton.setLayoutY(296.0);
        loginButton.setStyle("-fx-background-color: transparent; -fx-border-color: #2d8f92; -fx-border-radius: 30;");
        loginButton.setTextFill(Color.rgb(45, 143, 146));
        loginButton.setFont(Font.font("Montserrat Regular", 12.0));
    }

    private void createRegistrationComponents() {
        registrationForm = new VBox(4.0);
        registrationForm.setAlignment(Pos.CENTER);
        registrationForm.setLayoutX(351.0);
        registrationForm.setLayoutY(105.0);
        registrationForm.setPrefHeight(228.0);
        registrationForm.setPrefWidth(198.0);

        String[] prompts = {"Username", "Password", "Date of Birth (dd/MM/yyyy)", "Role", "Working Hours"};
        registrationFields = new TextField[prompts.length];

        for (int i = 0; i < prompts.length; i++) {
            TextField field = i == 1 ? new PasswordField() : new TextField();
            field.setPromptText(prompts[i]);
            registrationFields[i] = field;
            registrationForm.getChildren().add(field);
        }

        registerButton = new Button("REGISTER");
        registerButton.setLayoutX(470.0);
        registerButton.setLayoutY(321.0);
        registerButton.setStyle("-fx-background-color: transparent; -fx-border-color: #2d8f92; -fx-border-radius: 30;");
        registerButton.setTextFill(Color.WHITE);
        registerButton.setFont(Font.font("Montserrat Regular", 12.0));
    }

    private void createBackButton() {
        backButton = new Button("Back");
        backButton.setLayoutX(20.0);
        backButton.setLayoutY(20.0);
        backButton.setStyle("-fx-background-color: transparent; -fx-border-color: #2d8f92; -fx-border-radius: 30;");
        backButton.setTextFill(Color.rgb(45, 143, 146));
    }

    private Label createLoginLabel() {
        Label loginLabel = new Label("Admin Login");
        loginLabel.setAlignment(Pos.CENTER);
        loginLabel.setLayoutX(85.0);
        loginLabel.setLayoutY(170.0);
        loginLabel.setTextFill(Color.rgb(45, 143, 146));
        loginLabel.setFont(Font.font("Montserrat Medium", 24.0));
        return loginLabel;
    }

    private Label createRegisterLabel() {
        Label registerLabel = new Label("Register New Admin");
        registerLabel.setLayoutX(360.0);
        registerLabel.setLayoutY(74.0);
        registerLabel.setPrefHeight(62.0);
        registerLabel.setPrefWidth(180.0);
        registerLabel.setTextFill(Color.WHITE);
        registerLabel.setWrapText(true);
        registerLabel.setFont(Font.font("Montserrat Medium", 24.0));
        return registerLabel;
    }

    private void handleLogin() {
        String username = loginUsername.getText().trim();
        String password = loginPassword.getText().trim();

        if (!validateLoginInput(username, password)) {
            return;
        }

        Admin admin = Database.validateAdminLogin(username, password);
        if (admin != null) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful",
                    "Welcome back, Administrator " + username + "!");
            if (loginSuccessCallback != null) {
                loginSuccessCallback.call(admin);
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed",
                    "Invalid administrator credentials. Please try again.");
            loginPassword.clear();
            loginUsername.requestFocus();
        }
    }

    private void handleRegistration() {
        try {
            String username = registrationFields[0].getText().trim();
            String password = registrationFields[1].getText().trim();
            String dateOfBirth = registrationFields[2].getText().trim();
            String role = registrationFields[3].getText().trim();
            String workingHoursStr = registrationFields[4].getText().trim();

            if (!validateRegistrationInput(username, password, dateOfBirth, role, workingHoursStr)) {
                return;
            }

            double workingHours = Double.parseDouble(workingHoursStr);
            Admin newAdmin = new Admin(username, password, dateOfBirth, role, workingHours);
            Database.addAdmin(newAdmin);

            showAlert(Alert.AlertType.INFORMATION, "Registration Successful",
                    "Administrator account created successfully!");

            if (registrationSuccessCallback != null) {
                registrationSuccessCallback.call(newAdmin);
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input",
                    "Please enter a valid number for working hours.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Registration Error",
                    "An error occurred during registration: " + e.getMessage());
        }
    }

    private void setupEventHandlers() {
        loginButton.setOnAction(e -> handleLogin());
        registerButton.setOnAction(e -> handleRegistration());
        backButton.setOnAction(e -> {
            if (backButtonCallback != null) {
                backButtonCallback.run();
            }
        });
    }

    private boolean validateLoginInput(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input",
                    "Username and password cannot be empty.");
            return false;
        }
        return true;
    }

    private boolean validateRegistrationInput(String username, String password,
                                              String dateOfBirth, String role, String workingHoursStr) {
        if (username.isEmpty() || !Character.isLetter(username.charAt(0)) ||
                username.equals(username.toUpperCase())) {
            showAlert(Alert.AlertType.ERROR, "Invalid Username",
                    "Username must:\n- Not be empty\n- Start with a letter\n- Not be all uppercase");
            return false;
        }

        if (password.length() < 5) {
            showAlert(Alert.AlertType.ERROR, "Invalid Password",
                    "Password must be at least 5 characters long.");
            return false;
        }

        if (!validateDate(dateOfBirth)) {
            return false;
        }

        if (role.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Role",
                    "Please enter an administrative role.");
            return false;
        }

        try {
            double hours = Double.parseDouble(workingHoursStr);
            if (hours <= 0 || hours > 24) {
                showAlert(Alert.AlertType.ERROR, "Invalid Working Hours",
                        "Working hours must be between 0 and 24.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Working Hours",
                    "Please enter a valid number for working hours.");
            return false;
        }

        return true;
    }

    private boolean validateDate(String dateStr) {
        if (!dateStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            showAlert(Alert.AlertType.ERROR, "Invalid Date Format",
                    "Please enter date in format: dd/MM/yyyy");
            return false;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate parsedDate = LocalDate.parse(dateStr, formatter);

            if (parsedDate.isAfter(LocalDate.now())) {
                showAlert(Alert.AlertType.ERROR, "Invalid Date",
                        "Date of birth cannot be in the future.");
                return false;
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Date",
                    "Please enter a valid date in the format dd/MM/yyyy");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Setters for callbacks
    public void setLoginSuccessCallback(Callback<Admin> callback) {
        this.loginSuccessCallback = callback;
    }

    public void setRegistrationSuccessCallback(Callback<Admin> callback) {
        this.registrationSuccessCallback = callback;
    }

    public void setBackButtonCallback(Runnable callback) {
        this.backButtonCallback = callback;
    }
}