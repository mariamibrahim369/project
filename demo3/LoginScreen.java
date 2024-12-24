package sample.demo3;

import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Scanner;

public class LoginScreen {
    private Button adminButton;
    private Button signUpButton;
    private Button signInButton;
    private Scene scene;
    private Stage primaryStage;

    private TextField usernameField;
    private PasswordField passwordField;
    private Label statusLabel;

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Create main SplitPane
        SplitPane splitPane = new SplitPane();
        splitPane.setPrefSize(600, 400);

        // Left Pane (UI Elements)
        AnchorPane leftPane = createLeftPane();

        // Right Pane (UI Elements)
        AnchorPane rightPane = createRightPane();

        // Add panes to SplitPane
        splitPane.getItems().addAll(leftPane, rightPane);
        adminButton.setOnAction(e -> {
            AdminLogin adminLogin = new AdminLogin();
            adminLogin.show(primaryStage);
        });

        signUpButton.setOnAction(e -> {
            RegisterCustomer registerCustomer = new RegisterCustomer();
            registerCustomer.show(primaryStage );
        });


        // Scene and Stage setup
        scene = new Scene(splitPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("E-Commerce System");
        primaryStage.show();

    }

    private AnchorPane createLeftPane() {
        AnchorPane leftPane = new AnchorPane();
        leftPane.setPrefSize(191, 398);
        leftPane.setStyle("-fx-background-color: #2d8f92;");

        // New Here? Label
        Label leftLabel1 = new Label("New Here?");
        leftLabel1.setLayoutX(28);
        leftLabel1.setLayoutY(101);
        leftLabel1.setTextFill(javafx.scene.paint.Color.WHITE);
        leftLabel1.setFont(new Font("Montserrat Medium", 24));

        // Sign up description Label
        Label leftLabel2 = new Label("Sign up and discover a great amount of new opportunities!");
        leftLabel2.setLayoutX(38);
        leftLabel2.setLayoutY(131);
        leftLabel2.setPrefSize(116, 78);
        leftLabel2.setWrapText(true);
        leftLabel2.setTextFill(javafx.scene.paint.Color.WHITE);
        leftLabel2.setFont(new Font("Montserrat Regular", 14));

        // Sign Up Button
        signUpButton = new Button("SIGN UP");
        signUpButton.setLayoutX(46);
        signUpButton.setLayoutY(230);
        signUpButton.setPrefSize(100, 50);
        signUpButton.setStyle("-fx-background-color: transparent; -fx-border-color: white; -fx-border-radius: 30;");
        signUpButton.setTextFill(javafx.scene.paint.Color.WHITE);

        // Logo Image
        ImageView logoImageView = createLogoImageView();

        // Logo Label
        Label logoLabel = new Label("E-Commerce System");
        logoLabel.setLayoutX(46);
        logoLabel.setLayoutY(14);
        logoLabel.setPrefSize(100, 30);
        logoLabel.setWrapText(true);
        logoLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        leftPane.getChildren().addAll(leftLabel1, leftLabel2, signUpButton, logoImageView, logoLabel);
        return leftPane;
    }

    private AnchorPane createRightPane() {
        AnchorPane rightPane = new AnchorPane();
        rightPane.setPrefSize(323, 398);
        rightPane.setStyle("-fx-background-color: white;");

        // Login Label
        Label loginLabel = new Label("Login to Your Account");
        loginLabel.setLayoutX(263);
        loginLabel.setLayoutY(69);
        loginLabel.setTextFill(javafx.scene.paint.Color.web("#2d8f92"));
        loginLabel.setFont(new Font("Montserrat Medium", 24));

        // Username Field
        usernameField = new TextField();
        usernameField.setLayoutX(326);
        usernameField.setLayoutY(123);
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-border-color: #2d8f92;");

        // Password Field (use PasswordField instead of TextField)
        passwordField = new PasswordField();
        passwordField.setLayoutX(326);
        passwordField.setLayoutY(186);
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-border-color: #2d8f92;");

        // Sign In Button
        signInButton = new Button("SIGN IN");
        signInButton.setLayoutX(352);
        signInButton.setLayoutY(232);
        signInButton.setPrefSize(100, 50);
        signInButton.setStyle("-fx-background-color: transparent; -fx-border-color: #2d8f92; -fx-border-radius: 30;");
        signInButton.setTextFill(javafx.scene.paint.Color.web("#2d8f92"));

        // Admin Button
        adminButton = new Button("Click here if you're an admin");
        adminButton.setLayoutX(311);
        adminButton.setLayoutY(334);
        adminButton.setStyle("-fx-background-color: transparent; -fx-border-color: #2d8f92; -fx-border-radius: 30;");
        adminButton.setTextFill(javafx.scene.paint.Color.web("#2d8f92"));

        // Status label to show login status
        statusLabel = new Label();
        statusLabel.setLayoutX(326);
        statusLabel.setLayoutY(270);
        statusLabel.setTextFill(javafx.scene.paint.Color.web("#FF0000"));

        rightPane.getChildren().addAll(loginLabel, usernameField, passwordField, signInButton, adminButton, statusLabel);
        return rightPane;
    }

    private ImageView createLogoImageView() {
        try {
            ImageView logoImageView = new ImageView(new Image(getClass().getResourceAsStream("logo.jpg")));
            logoImageView.setFitWidth(34);
            logoImageView.setFitHeight(30);
            logoImageView.setLayoutX(11);
            logoImageView.setLayoutY(14);
            logoImageView.setOpacity(0.52);
            return logoImageView;
        } catch (Exception e) {
            System.err.println("Error loading logo image: " + e.getMessage());
            return new ImageView(); // Return empty ImageView if image loading fails
        }
    }

    // Getter methods for buttons
    public Button getAdminButton() {
        return adminButton;
    }

    public Button getSignUpButton() {
        return signUpButton;
    }

    public Button getSignInButton() {
        return signInButton;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }
}
