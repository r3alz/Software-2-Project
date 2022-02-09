package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LoginTracker {
    private static final String FILENAME = "LoginTracker.txt";

    public static void loginSuccess(String username) throws IOException {
        FileWriter fw = new FileWriter(FILENAME, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.println("Successful login by " + username + " on " + LocalDateTime.now());
    }

    public static void loginFailed(String userID) throws IOException {
        FileWriter fw = new FileWriter(FILENAME, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.println("Failed login with the userID of " + userID + " on " + LocalDateTime.now());
    }
}
