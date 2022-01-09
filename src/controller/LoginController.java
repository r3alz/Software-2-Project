package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;
import model.UserDAOImpl;

import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public TextField UserID;
    public TextField Password;
    public Button SignIn;
    public Label TimeZone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zone = ZoneId.systemDefault();
        TimeZone.setText("Time Zone: " + String.valueOf(zone));
        String lang = "en";
        String country = "US";
        Locale l = new Locale(lang, country);
        ResourceBundle r = ResourceBundle.getBundle("bundle",l);
        String str = r.getString("test");
        System.out.println(str);
    }

    public void onSignIn(ActionEvent actionEvent) {
        ObservableList<User> userList = UserDAOImpl.getAllUsers();
        int userID = Integer.parseInt(UserID.getText());
        String password = Password.getText().trim();
        for(User u: userList) {
            if(u.getId().equals(userID) && u.getPassword().equals(password)) {
                System.out.println("login successful");
            }
            else {
                System.out.println("not successful");
            }
        }
    }
}
