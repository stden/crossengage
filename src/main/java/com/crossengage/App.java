package com.crossengage;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        UserRepository repository = new UserRepository(new File(args[0]));
        for (String email : repository.getAllEmails()) {
            sendEmail(email, "Welcome to our system");
        }
    }

    private static void sendEmail(String to, String text) {
        System.out.println("Email send to " + to + " with text: " + text);
    }
}
