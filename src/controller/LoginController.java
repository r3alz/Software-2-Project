package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.UserDAOImpl;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public TextField UserID;
    public TextField Password;
    public Button SignIn;
    public Label TimeZone;
    public Label UserIDLabel;
    public Label PasswordLabel;
    public Label AppointmentScheduler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zone = ZoneId.systemDefault();
        try {
            ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
            if(Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")) {
                AppointmentScheduler.setText(rb.getString("appointmentscheduler"));
                UserIDLabel.setText(rb.getString("userid"));
                PasswordLabel.setText(rb.getString("password"));
                SignIn.setText(rb.getString("signin"));
                TimeZone.setText(rb.getString("timezone") + ":" + " " + zone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSignIn(ActionEvent actionEvent) throws IOException {
        ObservableList<User> userList = UserDAOImpl.getAllUsers();
        int userID = Integer.parseInt(UserID.getText());
        String password = Password.getText().trim();
        for(User u: userList) {
            if(u.getId().equals(userID) && u.getPassword().equals(password)) {
                System.out.println("login successful");
                //load widget hierarchy of next screen
                Parent root = FXMLLoader.load(getClass().getResource("/view/Initial.fxml"));

                //get the stage from an event's source widget
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

                //Create the New Scene
                Scene scene = new Scene(root, 600, 500);
                stage.setTitle("Modify Part");

                //Set the scene on the stage
                stage.setScene(scene);

                //raise the curtain
                stage.show();
                return;
            }
            else {
                try {
                    ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
                    if(Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("alert"));
                        alert.showAndWait();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
