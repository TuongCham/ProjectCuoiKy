package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConnectController {

    @FXML
    private Button btnConnect;

    @FXML
    private Label lbRs;

    @FXML
    private TextField txtHost;

    @FXML
    private TextField txtPort;

    @FXML
    void btnConnect(ActionEvent event) throws IOException {
        try (var s = new Socket(txtHost.getText(), Integer.parseInt(txtPort.getText()));
             var in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8))
        {
            while (in.hasNextLine())
            {
                String line = in.nextLine();
//                System.out.println(line);
                lbRs.setText("connect success");
                System.out.println("connect success");
            }
        }
    }

}
