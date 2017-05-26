package com.crossengage;

import com.crossengage.model.ContactBy;
import com.crossengage.model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository {

    private File data;

    public UserRepository(File data) {
        this.data = data;
    }

    /**
     * Parse one line with user data:
     * id,active,contactBy,email,phone
     * 1,true,email,test1@mail.com,+999999999999
     * 2,true,none,test2@mail.com,+999999999998
     * 3,false,phone,test3mail.com,+999999999997
     * 4,true,email,test4@mail.com,+999999999996
     * 5,true,all,test4@mail.com,+999999999996
     *
     * @param line one line in format
     * @return User object
     */
    static User parse(String line) {
        String[] params = line.split(",");
        if (params.length != 5) {
            throw new IllegalArgumentException("Expected 5 params, but found " + params.length);
        }
        return new User(Integer.parseInt(params[0].trim()), // id
                Boolean.parseBoolean(params[1].trim()), // active
                ContactBy.valueOf(params[2].trim().toUpperCase()), // contactBy
                params[3].trim(), // email
                params[4].trim()); // phone
    }

    public List<String> getAllEmails() throws IOException {
        return Files.lines(data.toPath())
                .skip(1)
                .map(line -> line.substring(line.indexOf(',', 8) + 1, line.lastIndexOf(',')))
                .collect(Collectors.toList());
    }

    public List<User> getAllUsers() throws IOException {
        return Files.lines(data.toPath())
                .skip(1)
                .map(UserRepository::parse)
                .collect(Collectors.toList());
    }

    public List<User> getEnabledUsers() throws IOException {
        return Files.lines(data.toPath())
                .skip(1)
                .map(UserRepository::parse)
                .filter(user -> user.active)
                .collect(Collectors.toList());
    }
}
