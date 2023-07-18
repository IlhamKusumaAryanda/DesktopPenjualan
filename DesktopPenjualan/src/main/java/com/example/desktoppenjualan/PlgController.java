package com.example.desktoppenjualan;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PlgController implements Initializable {
    Stage stage;
    ObservableList<Pelanggan> listPelanggan = FXCollections.observableArrayList();
    boolean flagAdd = true;

    @FXML
    private Button bAdd;
    @FXML
    private Button bCancel;
    @FXML
    private Button bDel;
    @FXML
    private Button bEdit;
    @FXML
    private Button bUpdate;
    @FXML
    private TextField tfidpelanggan;
    @FXML
    private TextField tfnama;
    @FXML
    private TextField tfalamat;

    @FXML
    private TableColumn<Pelanggan, String> idpelanggan;
    @FXML
    private TableColumn<Pelanggan, String> nama;
    @FXML
    private TableColumn<Pelanggan, String> alamat;

    @FXML
    private TableView<Pelanggan> tbpelanggan;
    @FXML
    private Button btnPilih;
    @FXML
    private TextField tfKeyword;

    @FXML
    void add(ActionEvent event) {
        setButton(false, false, false, true, true);
        clearTeks();
        setTeks(true);
        tfidpelanggan.requestFocus();
    }

    @FXML
    void cancel(ActionEvent event) {
        setButton(true, true, true, false, false);
        clearTeks();
    }

    @FXML
    void del(ActionEvent event) {
        Connection conn = DBConnection.getConn();
        String sql = "DELETE FROM pelanggan WHERE idpelanggan=?";
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, tfidpelanggan.getText());
            st.executeUpdate();
            loadData();
            clearTeks();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void edit(ActionEvent event) {
        flagAdd = false;
        setButton(false, false, false, true, true);
        setTeks(true);
        tfidpelanggan.setEditable(false);
        tfnama.requestFocus();
    }

    @FXML
    void update(ActionEvent event) {
        Connection conn = DBConnection.getConn();
        if (flagAdd) {
            String sql = "INSERT INTO pelanggan (idpelanggan, nama, alamat) VALUES (?, ?, ?)";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, tfidpelanggan.getText());
                st.setString(2, tfnama.getText());
                st.setString(3, tfalamat.getText());
                st.executeUpdate();
                loadData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            String sql = "UPDATE pelanggan SET nama=?, alamat=? WHERE idpelanggan=?";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1, tfnama.getText());
                st.setString(2, tfalamat.getText());
                st.setString(3, tfidpelanggan.getText());
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        loadData();
        setButton(true, true, true, false, false);
        clearTeks();
    }

    @FXML
    void pilih(ActionEvent event) {
        stage = (Stage) btnPilih.getScene().getWindow();
        Pelanggan b = tbpelanggan.getSelectionModel().getSelectedItem();
        stage.setUserData(b);
        stage.close();
    }

    @FXML
        void pilihPelanggan(MouseEvent event) {
        Pelanggan p = tbpelanggan.getSelectionModel().getSelectedItem();
        if (p != null) {
            tfidpelanggan.setText(Integer.toString(p.getIdpelanggan()));
            tfnama.setText(p.getNama());
            tfalamat.setText(p.getAlamat());
        }
    }

    void initTabel() {
        idpelanggan.setCellValueFactory(new PropertyValueFactory<Pelanggan, String>("idpelanggan"));
        nama.setCellValueFactory(new PropertyValueFactory<Pelanggan, String>("nama"));
        alamat.setCellValueFactory(new PropertyValueFactory<Pelanggan, String>("alamat"));
    }

    void loadData() {
        listPelanggan = DBUtil.getDataPelanggan();
        tbpelanggan.setItems(listPelanggan);
    }

    void setFilter() {
        FilteredList<Pelanggan> filterData = new FilteredList<>(listPelanggan, b -> true);
        tfKeyword.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(pelanggan -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (pelanggan.getNama().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Pelanggan> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tbpelanggan.comparatorProperty());
        tbpelanggan.setItems(sortedData);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTabel();
        loadData();
        setFilter();
        setButton(true, true, true, false, false);
        setTeks(false);
    }

    protected void clearTeks() {
        tfidpelanggan.setText(null);
        tfnama.setText(null);
        tfalamat.setText(null);
    }

    protected void setButton(Boolean b1, Boolean b2, Boolean b3, Boolean b4, Boolean b5) {
        bAdd.setDisable(!b1);
        bEdit.setDisable(!b2);
        bDel.setDisable(!b3);
        bUpdate.setDisable(!b4);
        bCancel.setDisable(!b5);
    }

    protected void setTeks(Boolean b) {
        tfidpelanggan.setEditable(b);
        tfnama.setEditable(b);
        tfalamat.setEditable(b);
    }
}
