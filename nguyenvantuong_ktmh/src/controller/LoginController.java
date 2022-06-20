package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class LoginController {

    public final static String HOST_NAME = "localhost";
    public final static int PORT = 8089;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lbMessageError;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnLogin(ActionEvent event) throws IOException {
        Socket socket = new Socket(HOST_NAME, PORT);
        try(InputStream inStream = socket.getInputStream();
            OutputStream outStream = socket.getOutputStream();
            var in = new Scanner(inStream, StandardCharsets.UTF_8);
            var out = new PrintWriter(
                    new OutputStreamWriter(outStream, StandardCharsets.UTF_8),
                    true /* autoFlush */)) {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
//            Account account = new Account(username,password);
            out.println(username+" "+password);
            int rs = Integer.parseInt(in.nextLine());
//            int rs = 1;
            if(rs==0){
                App.screenController.activate("welcome");
            }else if (rs==1){
                lbMessageError.setText("Wrong username or password!");
            }else{
                App.screenController.activate("register");
            }
        } catch (IOException ie) {
            System.out.println("Can't connect to server");
        }
    }
}