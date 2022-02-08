package main;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import DAO.JDBC;
import model.Contact;
import model.ContactDAOImpl;

import java.sql.SQLException;
import java.time.*;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {

        JDBC.makeConnection();
        //CustomerDAOImpl.addCustomer(10,"test", 10, "test", "test", "test", Date.valueOf("2019-01-05"), "test", Date.valueOf("2019-01-05"), "test");
        //CustomerDAOImpl.deleteCustomer(4);
        //CustomerDAOImpl.updateCustomer("testing1212", "testing", "testing", "testing", Date.valueOf("2020-01-01"), "testing", Date.valueOf("2020-01-01"), "testing", 1);
        launch(args);
    }
}
