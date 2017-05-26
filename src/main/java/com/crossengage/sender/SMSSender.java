package com.crossengage.sender;

import com.crossengage.model.DispatchingOptions;
import com.crossengage.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.crossengage.model.DispatchingOptions.BOTH;
import static com.crossengage.model.DispatchingOptions.SMS;

/**
 * Connector to send SMS
 */
public class SMSSender implements Sender {
    private Map<String, List<String>> sms = new HashMap<>();

    @Override
    public void send(DispatchingOptions options, User u, String text) {
        if ((options == SMS || options == BOTH) && u.canReceiveSms()) {
            System.out.println("SMS send to " + u.phone + " with text: " + text);
            sms.putIfAbsent(u.phone, new ArrayList<>());
            sms.get(u.phone).add(text);
        }
    }

    public String[] getSMSByPhone(String phone) {
        List<String> list = sms.get(phone);
        if (list == null) list = new ArrayList<>();
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }
}
