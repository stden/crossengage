package com.crossengage.sender;

import com.crossengage.model.DispatchingOptions;
import com.crossengage.model.User;

import java.util.HashMap;
import java.util.Map;

import static com.crossengage.model.DispatchingOptions.BOTH;
import static com.crossengage.model.DispatchingOptions.MAIL;

/**
 * Connector to send e-mail
 */
public class EmailsSender implements Sender {
    private Map<String, Integer> counter = new HashMap<>();

    public void send(DispatchingOptions options, User u, String text) {
        if ((options == BOTH || options == MAIL) && u.canReceiveEmails()) {
            System.out.println("Email send to " + u.email + " with text: " + text);
            counter.put(u.email, counter.getOrDefault(u.email, 0) + 1);
        }
    }

    public int count(String to) {
        return counter.getOrDefault(to, 0);
    }
}