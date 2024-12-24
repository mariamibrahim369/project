// HelloController.java
package sample.demo3;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class HelloController {
    private Stage primaryStage;
    Database database;
    Main main;

    public HelloController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showLoginScreen() {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.start(primaryStage);

        loginScreen.getAdminButton().setOnAction(e -> showAdminLogin());
        loginScreen.getSignUpButton().setOnAction(e -> showRegisterCustomer());

        loginScreen.getSignInButton().setOnAction(e -> {
            String username = loginScreen.getUsernameField().getText().trim();
            String password = loginScreen.getPasswordField().getText().trim();

            Customer loggedInCustomer = validateCustomerLogin(username, password);

            if (loggedInCustomer != null) {
                showSuccessAlert("Login Successful", "Welcome back, " + username + "!");
                showHomepage();
            } else {
                showErrorAlert("Login Failed", "Invalid username or password. Please try again.");
                loginScreen.getUsernameField().clear();
                loginScreen.getPasswordField().clear();
                loginScreen.getUsernameField().requestFocus();
            }
        });
    }

    private Customer validateCustomerLogin(String username, String password) {
        for (Customer customer : Database.customers) {
            if (customer != null &&
                    customer.getUsername().equals(username) &&
                    customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }

    public void showAdminLogin() {
        AdminLogin adminLogin = new AdminLogin();

        adminLogin.setLoginSuccessCallback(admin -> {
            Admin validAdmin = Database.validateAdminLogin(admin.getUsername(), admin.getPassword());
            if (validAdmin != null) {
                showSuccessAlert("Login Successful", "Welcome Admin " + validAdmin.getUsername());
                showAdminHomepage();
            } else {
                showErrorAlert("Login Failed", "Invalid credentials");
            }
        });

        adminLogin.setRegistrationSuccessCallback(admin -> {
            Database.addAdmin(admin);
            showSuccessAlert("Registration Successful", "Please login with your credentials");
            showAdminLogin();
        });

        adminLogin.setBackButtonCallback(() -> showLoginScreen());
        adminLogin.show(primaryStage);
    }

    public void showAdminHomepage(){
        AdminHomepage adminHomepage=new AdminHomepage();
        adminHomepage.show(primaryStage);
    }

    public void showRegisterCustomer() {
        RegisterCustomer registerCustomer = new RegisterCustomer();
        registerCustomer.setRegistrationSuccessCallback(() -> showLoginScreen());
        registerCustomer.setBackButtonCallback(() -> showLoginScreen());
        registerCustomer.show(primaryStage);
    }

    private void showHomepage() {
        Homepage homepage = new Homepage();
        homepage.displayHomepage(primaryStage);
    }

    private void showSuccessAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}