package sample.demo3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private HelloController controller;

    @Override
    public void start(Stage primaryStage) {
        controller = new HelloController(primaryStage);
        controller.showLoginScreen();
        Homepage homepage = new Homepage();
        HelloController controller = new HelloController(primaryStage);
        controller.showLoginScreen();
        //AdminHomepage adminHomepage = new AdminHomepage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}