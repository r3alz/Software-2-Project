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
import model.AppointmentDAOImpl;
import model.User;
import model.UserDAOImpl;
import util.LoginTracker;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created By Chris Ortiz
 * Used to control the Login view
 */
public class LoginController implements Initializable {

    public TextField UserID;
    public TextField Password;
    public Button SignIn;
    public Label TimeZone;
    public Label UserIDLabel;
    public Label PasswordLabel;
    public Label AppointmentScheduler;

    private int count = 0;
    private static User loggedInUser = new User(0, null, null);

    /**
     * This will initialize the scene
     * @param url
     * @param resourceBundle
     */
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

    /**
     * This will be used when a user signs in
     * @param actionEvent on click of the sign in button
     */
    public void onSignIn(ActionEvent actionEvent) throws IOException {
        ObservableList<User> userList = UserDAOImpl.getAllUsers();
        String userID = UserID.getText();
        String password = Password.getText().trim();
        for (User u : userList) {
            count += 1;
            if (u.getUsername().equals(userID) && u.getPassword().equals(password)) {
                loggedInUser.setId(u.getId());
                loggedInUser.setUsername(u.getUsername());

                LoginTracker.loginSuccess(u.getUsername());

                //load widget hierarchy of next screen
                Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));

                //get the stage from an event's source widget
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

                //Create the New Scene
                Scene scene = new Scene(root, 850, 550);
                stage.setTitle("Appointment View");

                //Set the scene on the stage
                stage.setScene(scene);

                //raise the curtain
                stage.show();

                AppointmentDAOImpl.getAppointmentAlert();

                return;
            } else if (count == userList.size()) {
                count = 0;

                LoginTracker.loginFailed(UserID.getText());

                try {
                    ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
                    if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")) {
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

    /**
     * this will return the logged in user
     * @return loggedInUser
     */
    public static User getLoggedInUser() {
        return loggedInUser;
    }
}
