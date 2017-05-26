package com.crossengage;

import com.crossengage.model.DispatchingOptions;
import com.crossengage.model.User;
import com.crossengage.sender.EmailsSender;
import com.crossengage.sender.SMSSender;
import com.crossengage.sender.Sender;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class App {
    private List<Sender> senders = new ArrayList<>();
    private DispatchingOptions options = DispatchingOptions.BOTH;

    public App(Sender... senders) {
        this.senders.addAll(asList(senders));
    }

    public static void main(String... args) throws IOException {
        UserRepository repository = new UserRepository(new File(args[0]));
        App app = new App(new SMSSender(), new EmailsSender());
        app.sendWelcomeToUsers(repository);
    }

    public void sendWelcomeToUsers(UserRepository repository) throws IOException {
        for (User user : repository.getEnabledUsers()) {
            send(user, "Welcome to our system");
        }
    }

    /**
     * For this challenge do not implement the necessary code to actually send any message,
     * just write messages to the console.
     *
     * @param to   e-mail address
     * @param text e-mail text
     */
    public void send(User to, String text) {
        for (Sender sender : senders) {
            sender.send(options, to, text);
        }
    }

    public void setDispatchingOptions(DispatchingOptions dispatching) {
        this.options = dispatching;
    }
}
