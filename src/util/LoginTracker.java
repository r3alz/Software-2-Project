package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * Created by Chris Ortiz
 * This is used to keep track of login attemps
 */
public class LoginTracker {
    private static final String FILENAME = "login_activity.txt";

    /**
     * will track login attempts that are successful
     * @param username username of user that is attempting to login
     * @throws IOException
     */
    public static void loginSuccess(String username) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true));
        bw.append("Successful login by " + username + " on " + LocalDateTime.now() + "\n");
        bw.flush();
        bw.close();
    }

    /**
     * will track login attemps that are unsuccessful
     * @param userID username of user that is attempting to login
     * @throws IOException
     */
    public static void loginFailed(String userID) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true));
        bw.append("Failed login with the userID of " + userID + " on " + LocalDateTime.now() + "\n");
        bw.flush();
        bw.close();
    }
}
