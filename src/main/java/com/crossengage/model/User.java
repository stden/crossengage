package com.crossengage.model;

import static com.crossengage.model.ContactBy.*;

public class User {
    public final int id;
    public final boolean active;
    public final ContactBy contactBy;
    public final String email;
    public final String phone;

    public User(int id, boolean active, ContactBy contactBy, String email, String phone) {
        this.id = id;
        this.active = active;
        this.contactBy = contactBy;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", active=" + active +
                ", contactBy='" + contactBy + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public boolean canReceiveEmails() {
        return active && (contactBy == EMAIL || contactBy == ALL);
    }

    public boolean canReceiveSms() {
        return active && (contactBy == PHONE || contactBy == ALL);
    }
}
