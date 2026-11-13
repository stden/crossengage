package com.crossengage.sender;

import com.crossengage.model.DispatchingOptions;
import com.crossengage.model.User;

public interface Sender {
    void send(DispatchingOptions options, User u, String text);
}