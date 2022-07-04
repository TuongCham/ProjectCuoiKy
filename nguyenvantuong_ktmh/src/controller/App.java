package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    static ScreenController screenController;
    Scene scene;

    public static ScreenController getScreenController() {
        return screenController;
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("../fxmls/login.fxml"));
        LoginController controller = new LoginController();
        Pane pane = loader.load();
        loader.setController(controller);
        scene = new Scene(pane);
        screenController = new ScreenController(scene);
        screenController.addScreen("login", new FXMLLoader(getClass().getResource("../fxmls/login.fxml")).load());
        screenController.addScreen("register", new FXMLLoader(getClass().getResource("../fxmls/register.fxml")).load());
        screenController.addScreen("welcome", new FXMLLoader(getClass().getResource("../fxmls/welcome.fxml")).load());
        screenController.addScreen("shopping", new FXMLLoader(getClass().getResource("../fxmls/shopping.fxml")).load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
