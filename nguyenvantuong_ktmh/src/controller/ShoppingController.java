package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import object.Product;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.Scanner;


import static controller.LoginController.HOST_NAME;
import static controller.LoginController.PORT;

public class ShoppingController implements Initializable{
    @FXML
    private TableView<Product> table;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Product, String> colDes;

    @FXML
    private TableColumn<Product, Double> colPrice;

    @FXML
    private TableColumn<Product, TextField> colQuality;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnSearch(ActionEvent event) {
        table.setPlaceholder(
                new Label("No matching results were found"));
        try(Socket incoming = new Socket(HOST_NAME, PORT);
            InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();
            var in = new Scanner(inStream, StandardCharsets.UTF_8);
            var out = new PrintWriter(
                    new OutputStreamWriter(outStream, StandardCharsets.UTF_8),
                    true /* autoFlush */)) {
            String key = txtSearch.getText();
//            if (key.equals("")){
//                key = "get";
//            }
            out.println("product list "+key);
            ObservableList<Product> listProduct = FXCollections.observableArrayList();
            while (in.hasNext()){
                String line = in.nextLine();
                String[] pro = line.split(",");
                Product product = new Product(pro[0], Double.parseDouble(pro[1]), new TextField());
                listProduct.add(product);
            }
            table.setEditable(true);
                colDes.setCellValueFactory(new PropertyValueFactory<>("Descriptions"));
                colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
                colQuality.setCellValueFactory(new PropertyValueFactory< >("Quality"));
            table.setItems(listProduct);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Product> listProduct = FXCollections.observableArrayList();
        try(Socket incoming = new Socket(HOST_NAME, PORT);
            InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();
            var in = new Scanner(inStream, StandardCharsets.UTF_8);
            var out = new PrintWriter(
                    new OutputStreamWriter(outStream, StandardCharsets.UTF_8),
                    true /* autoFlush */)) {
                out.println("product list ");
                while (in.hasNext()){
                    String line = in.nextLine();
                    String[] pro = line.split(",");
                    Product product = new Product(pro[0], Double.parseDouble(pro[1]), new TextField());
                    listProduct.add(product);
                }
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
        table.setEditable(true);
        colDes.setCellValueFactory(new PropertyValueFactory<>("Descriptions"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colQuality.setCellValueFactory(new PropertyValueFactory< >("Quality"));
        table.setItems(listProduct);
    }
}