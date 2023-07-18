package com.example.desktoppenjualan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;

public class DBUtil {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String userName) {
        Parent root = null;
        if (userName != null) {
            try {
                FXMLLoader loader = new
                        FXMLLoader(DBUtil.class.getResource(fxmlFile));
                root = loader.load();
                MenuController menu = loader.getController();
                menu.setUserInfo(userName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtil.class.getResource(fxmlFile));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node)
                event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1100, 800));
        stage.show();
    }


    public static void signUpUser(ActionEvent event, String
            userName, String password) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        conn = DBConnection.getConn();
        String sql = "select * from user where username=?";
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, userName);
            rs = st.executeQuery();
            if (rs.isBeforeFirst()) {
                System.out.println("User sdh ada..");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Anda tdk bisa pake user ini");
                alert.show();
            } else {
                sql = "insert into user (username,password) values (?,?)";
                st = conn.prepareStatement(sql);
                st.setString(1, userName);
                st.setString(2, password);
                st.executeUpdate();
                changeScene(event, "Menu.fxml", "Welcome", userName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void loginUser(ActionEvent event, String
            userName, String password) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql;
        try {
            conn = DBConnection.getConn();
            sql = "select * from user where username=?";
            st = conn.prepareStatement(sql);
            st.setString(1, userName);
            rs = st.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("User not found..");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User Tdk ditemukan");
                alert.show();
            } else {
                while (rs.next()) {
                    String rpassword = rs.getString("password");
                    if (rpassword.equals(password)) {
                        changeScene(event, "Menu.fxml", "Welcome", userName);
                    } else {
                        System.out.println("Password not valid..");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Password Salah");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ObservableList<Produk> getDataProduk() {
        ObservableList<Produk> listBrg =
                FXCollections.observableArrayList();
        try {
            Connection c = DBConnection.getConn();
            String sql = "select * from produk";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Produk m = new Produk(rs.getString("kode"), rs.getString("nama"), rs.getInt("hrg"), rs.
                        getInt("jml"), rs.getString("satuan"));
                listBrg.add(m);

            }
            return listBrg;
        } catch (SQLException ex) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static ObservableList<Pelanggan> getDataPelanggan() {
        ObservableList<Pelanggan> listpelanggan = FXCollections.observableArrayList();

        try {
            Connection conn = DBConnection.getConn();
            String sql = "SELECT * FROM pelanggan";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pelanggan m = new Pelanggan(rs.getInt("idpelanggan"), rs.getString("nama"), rs.getString("alamat"));
                listpelanggan.add(m);
            }

            return listpelanggan;
        } catch (SQLException ex) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
