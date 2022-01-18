package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import DAO.JDBC;
import model.*;

import java.sql.Date;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        JDBC.makeConnection();
        CustomerDAOImpl.addCustomer(10,"test", 10, "test", "test", "test", Date.valueOf("2019-01-05"), "test", Date.valueOf("2019-01-05"), "test");
        launch(args);
    }
}
