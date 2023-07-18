package com.example.desktoppenjualan;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MenuController implements Initializable {
    @FXML
    private Button btnHome;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnBrg;
    @FXML
    private Button btnPlg;
    @FXML
    private Button btnJual;
    @FXML
    void home(ActionEvent event) {
    }
    @FXML
    void p1(ActionEvent event) {
    }
    @FXML
    void p2(ActionEvent event) {
    }
    @FXML
    void p3(ActionEvent event) {
    }
    @FXML
    private StackPane contentArea;
    @FXML
    private Label lbTeks;
    public void logout(ActionEvent event){
        DBUtil.changeScene(event, "Login.fxml", "Login", null);
    }

    public void initialize(URL location,ResourceBundle resources) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("fhome.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void homee() throws IOException {
        Parent fxml =FXMLLoader.load(getClass().getResource("fhome.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    @FXML
    public void brg() throws IOException {
        Parent fxml =
                FXMLLoader.load(getClass().getResource("productSearch.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    @FXML
    public void plg() throws IOException {
        Parent fxml =
                FXMLLoader.load(getClass().getResource("fplg.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    @FXML
    public void jual() throws IOException {
        Parent fxml =
                FXMLLoader.load(getClass().getResource("fjual.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    public void setUserInfo(String userName) {
        lbTeks.setText("Selamat Datang ....." + userName);
    }
}
