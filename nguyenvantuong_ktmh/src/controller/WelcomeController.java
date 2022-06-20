package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WelcomeController {
    @FXML
    private Button btnShopping;
    @FXML
    void btnShopping(ActionEvent event) {
        App.screenController.activate("shopping");

    }

}
