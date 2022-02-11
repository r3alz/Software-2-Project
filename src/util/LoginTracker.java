package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LoginTracker {
    private static final String FILENAME = "/Users/LabUser/IdeaProjects/HelloWorldJFX/LoginTracker.txt";

    public static void loginSuccess(String username) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true));
        bw.append("Successful login by " + username + " on " + LocalDateTime.now() + "\n");
        bw.flush();
        bw.close();
    }

    public static void loginFailed(String userID) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true));
        bw.append("Failed login with the userID of " + userID + " on " + LocalDateTime.now() + "\n");
        bw.flush();
        bw.close();
    }
}
