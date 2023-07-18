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

public class ProductSearchController implements Initializable {
    Stage stage;
    ObservableList<Produk> listBrg =
            FXCollections.observableArrayList() ;
    //ObservableList<Produk> listBrg ;
    boolean flagAdd = true;
    @FXML
    private TableColumn<Produk, Integer> cJml;
    @FXML
    private TableColumn<Produk, String> cSatuan;
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
    private TextField tfHrg;
    @FXML
    private TextField tfJml;
    @FXML
    private TextField tfNama;
    @FXML
    private TextField tfSatuan;
    @FXML
    private TextField tfkode;
    @FXML
    void add(ActionEvent event) {
        setButton(false,false,false,true,true);
        clearTeks();
        setTeks(true);
        tfkode.requestFocus();
    }
    @FXML
    void cancel(ActionEvent event) {
        setButton(true,true,true,false,false);
        clearTeks();
    }
    @FXML
    void del(ActionEvent event) {
        Connection conn = DBConnection.getConn();
        String sql="delete from produk where kode=?";
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(sql);
            st.setString(1,tfkode.getText());
            st.executeUpdate();
            loadData();
            clearTeks();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void edit(ActionEvent event) {
        flagAdd=false;
        setButton(false,false,false,true,true);
        setTeks(true);
        tfkode.setEditable(false);
        tfNama.requestFocus();
    }
    @FXML
    void update(ActionEvent event) {
        Connection conn = DBConnection.getConn();
        if (flagAdd){
            String sql="insert into produk (kode,nama,hrg,jml,satuan) values (?,?,?,?,?)";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1,tfkode.getText());
                st.setString(2,tfNama.getText());
                st.setInt(3,Integer.parseInt(tfHrg.getText()));
                st.setInt(4,Integer.parseInt(tfJml.getText()));
                st.setString(5,tfSatuan.getText());
                st.executeUpdate();
                loadData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//do something..
        } else {
            String sql="update produk set nama=?,hrg=?,jml=?,satuan=? where kode=?";
            try {
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1,tfNama.getText());
                st.setInt(2,Integer.valueOf(tfHrg.getText()) );
                st.setInt(3,Integer.valueOf(tfJml.getText()));
                st.setString(4,tfSatuan.getText());
                st.setString(5,tfkode.getText());
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        loadData();
        setButton(true,true,true,false,false);
        clearTeks();
    }
    @FXML
    private TableColumn<Produk, Integer> hrg;
    @FXML
    private TableColumn<Produk, String> kode;
    @FXML
    private TableColumn<Produk, String> nama;

    @FXML
    private TableView<Produk> tbProduk;
    @FXML
    private Button btnPilih;
    @FXML
    private TextField tfKeyword;
    @FXML
    void pilih(ActionEvent event) {
        stage = (Stage) btnPilih.getScene().getWindow();
        Produk b= tbProduk.getSelectionModel().getSelectedItem();
//b.setKode(tfkode.getText());
        stage.setUserData(b);
        stage.close();
    }
    @FXML
    void pilihProduk(MouseEvent event) {
        Produk p= tbProduk.getSelectionModel().getSelectedItem();
        tfkode.setText(p.getKode());
        tfNama.setText(p.getNama());
        tfHrg.setText(Integer.toString(p.getHrg()));
        tfJml.setText(Integer.toString(p.getStok()));
        tfSatuan.setText(p.getSatuan());

    }
    void initTabel(){
        kode.setCellValueFactory(new PropertyValueFactory<Produk,String>("kode"));
        nama.setCellValueFactory(new PropertyValueFactory<Produk,String>("nama"));
        hrg.setCellValueFactory(new PropertyValueFactory<Produk,Integer>("hrg"));
        cJml.setCellValueFactory(new PropertyValueFactory<Produk,Integer>("stok"));
        cSatuan.setCellValueFactory(new PropertyValueFactory<Produk,String>("satuan"));
    }
    void loadData(){
        listBrg= DBUtil.getDataProduk();
        tbProduk.setItems(listBrg);
    }
    void setFilter(){
        FilteredList<Produk> filterData;
        filterData = new FilteredList<>(listBrg, b->true);
        tfKeyword.textProperty().addListener((observable,oldValue,newValue)-> {
                filterData.setPredicate(Produk->  {
                    if (newValue.isEmpty()){
                        return true;
                    }
                    String searchKeyword=newValue.toLowerCase();
                    if (Produk.getNama().toLowerCase().indexOf(searchKeyword)>-1){
                        return true;
                    }else if
                    (Produk.getKode().toLowerCase().indexOf(searchKeyword)>-1){
                        return true;
                    }else
                        return false;
                });
        });
        SortedList<Produk> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tbProduk.comparatorProperty());
        tbProduk.setItems(sortedData);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTabel();
        loadData();
        setFilter();
        setButton(true, true, true, false, false);
        setTeks(false);
    }
    protected void clearTeks(){
        tfkode.setText(null);
        tfNama.setText(null);
        tfHrg.setText(null);
        tfJml.setText(null);
        tfSatuan.setText(null);
    }
    protected void setButton(Boolean b1,Boolean b2,Boolean b3,Boolean b4,Boolean b5){
        bAdd.setDisable(!b1);
        bEdit.setDisable(!b2);
        bDel.setDisable(!b3);
        bUpdate.setDisable(!b4);
        bCancel.setDisable(!b5);
    }
    protected void setTeks(Boolean b){
        tfkode.setEditable(b);
        tfNama.setEditable(b);
        tfHrg.setEditable(b);
        tfJml.setEditable(b);
        tfSatuan.setEditable(b);
    }
}
