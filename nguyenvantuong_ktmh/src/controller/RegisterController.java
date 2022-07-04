package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static controller.LoginController.HOST_NAME;
import static controller.LoginController.PORT;

public class RegisterController {

    @FXML
    private Button btnRegister;
    @FXML
    private Label lbMess;
    @FXML
    private TextField txtConfirmPw;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnRegister(ActionEvent event) throws IOException {
        Socket socket = new Socket(HOST_NAME, PORT);
        try (InputStream inStream = socket.getInputStream();
             OutputStream outStream = socket.getOutputStream();
             var in = new Scanner(inStream, StandardCharsets.UTF_8);
             var out = new PrintWriter(
                     new OutputStreamWriter(outStream, StandardCharsets.UTF_8),
                     true /* autoFlush */)) {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String confPassword = txtConfirmPw.getText();
            if (username.equals("")) {
                lbMess.setText("Please enter user name!");
            } else if (password.equals("")) {
                lbMess.setText("Please enter password!");
            } else if (!password.equals(confPassword)) {
                lbMess.setText("Password does not match confirm passsword!");
            } else {
                out.println(username + " " + password + " register");
                int rs = Integer.parseInt(in.nextLine());
                if (rs == 0){
//                    lbMess.setText("Account is created!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText("Account is created!");
                    alert.showAndWait();
                    App.screenController.activate("login");
                }else {
                    lbMess.setText("Username is invalid!");
                }
            }
            out.flush();
        } catch (IOException ie) {
            System.out.println("Can't connect to server");
        }
    }
}

