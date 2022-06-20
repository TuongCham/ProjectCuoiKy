package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    void btnRegister(ActionEvent event) {
        final String DB_URL = "jdbc:derby:AccountDB";
        Map<String, String> accounts = new HashMap<String, String>();
        try
        {
            // Create a connection to the database.
            Connection conn = DriverManager.getConnection(DB_URL);

            // Create a Statement object.
            Statement stmt = conn.createStatement();

            // Create a string with a SELECT statement.
            String sqlStatement =
                    "SELECT * FROM Account";

            // Send the statement to the DBMS.
            ResultSet result = stmt.executeQuery(sqlStatement);

            // Display the contents of the result set.
            // The result set will have three columns.
            while (result.next())
            {
                accounts.put(result.getString("Username").replaceAll(" ", ""),
                        result.getString("Password").replaceAll(" ", ""));
//                System.out.println(result.getString("Username") +
//                        result.getString("Password"));
            }
            String sql = "INSERT INTO Account " +
                    "VALUES('"+txtUsername.getText()+"','"+txtPassword.getText()+"')";
            if (checkUsername(accounts)!=0){
                lbMess.setText("Username isvalid!");
            }else{
                String pw = txtPassword.getText();
                String conPw = txtConfirmPw.getText();
                if (checkPassword(pw, conPw)!=0){
                    lbMess.setText("Wrong confirm password!");
                }else {
                    System.out.println("Account is created!");
                    stmt.executeUpdate(sql);
                    App.screenController.activate("login");
                }
            }
            // Close the connection.
            conn.close();
        }
        catch(Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }

    }
    int checkUsername(Map<String, String> accounts){
        Set<String> set = accounts.keySet();
        for (Object key : set) {
            if (key.equals(txtUsername.getText())){
                return -1;
            }
        }
        return 0;
    }
    int checkPassword(String pw, String conPw){
        if (pw.equals(conPw)){
            return 0;
        }
        return -1;
    }

}

