package com.crossengage;

import com.crossengage.model.User;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.crossengage.UserRepository.parse;
import static com.crossengage.model.ContactBy.*;
import static org.junit.Assert.*;

public class UserRepositoryTest {

    private static final String TEST_USER_DATA = "/test_user_data.txt";

    @Test
    public void testLoadEmails() throws IOException {
        UserRepository repository = new UserRepository(new File(this.getClass().getResource(TEST_USER_DATA).getFile()));
        List<String> emails = repository.getAllEmails();

        assertEquals(5, emails.size());
        System.out.println(emails);
    }

    @Test
    public void testLoadUsers() throws Exception {
        // Format: id,active,contactBy,email,phone
        User user = new User(1, true, EMAIL, "test1@mail.com", "+999999999999");
        assertEquals(1, user.id);
        assertTrue(user.active);
        assertEquals(EMAIL, user.contactBy);
        assertEquals("test1@mail.com", user.email);
        assertEquals("+999999999999", user.phone);

        UserRepository repository = new UserRepository(new File(this.getClass().getResource(TEST_USER_DATA).getFile()));
        List<User> users = repository.getAllUsers();

        assertEquals(5, users.size());
        assertEquals(1, users.get(0).id);

        System.out.println(users);
    }


    @Test
    public void testParseUserData() throws Exception {
        User u1 = parse("1,true,email,test1@mail.com,+999999999999");
        assertEquals(1, u1.id);
        assertTrue(u1.active);
        assertEquals(EMAIL, u1.contactBy);
        assertEquals("test1@mail.com", u1.email);
        assertEquals("+999999999999", u1.phone);
        assertTrue(u1.canReceiveEmails());
        assertFalse(u1.canReceiveSms());

        User u2 = parse("2,true,none,test2@mail.com,+999999999998");
        assertEquals(NONE, u2.contactBy);
        assertFalse(u2.canReceiveEmails());
        assertFalse(u2.canReceiveSms());

        User u3 = parse("3,false,phone,test3mail.com,+999999999997");
        assertFalse(u3.active);
        assertEquals(PHONE, u3.contactBy);
        assertFalse(u3.canReceiveEmails());
        assertFalse(u3.canReceiveSms());

        User u4 = parse("4,true,email,test4@mail.com,+999999999996");
        assertEquals(EMAIL, u4.contactBy);
        assertTrue(u4.canReceiveEmails());
        assertFalse(u4.canReceiveSms());

        User u5 = parse("5,true,all,test4@mail.com,+999999999996");
        assertEquals(ALL, u5.contactBy);
        assertTrue(u5.canReceiveEmails());
        assertTrue(u5.canReceiveSms());
    }
}
