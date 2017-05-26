package com.crossengage.functional;

import com.crossengage.App;
import com.crossengage.UserRepository;
import com.crossengage.model.ContactBy;
import com.crossengage.model.User;
import com.crossengage.sender.EmailsSender;
import com.crossengage.sender.SMSSender;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static com.crossengage.model.DispatchingOptions.*;
import static org.junit.Assert.*;

public class AppTest {
    private static final String TEST_USER_DATA = "/test_user_data.txt";

    @Test
    public void onlyEnabledUsersReceiveEmails() throws Exception {
        User user1 = new User(1, true, ContactBy.EMAIL, "test1@mail.com", "+999999999999");
        assertTrue(user1.canReceiveEmails());

        UserRepository repository = new UserRepository(new File(this.getClass().getResource(TEST_USER_DATA).getFile()));

        List<User> users = repository.getEnabledUsers();
        assertEquals(4, users.size());
        assertEquals(1, users.get(0).id);
        assertEquals(2, users.get(1).id);
        // Skip user with id = 3
        assertEquals(4, users.get(2).id);
        assertEquals(5, users.get(3).id);

        EmailsSender counter = new EmailsSender();
        App app = new App(counter);
        app.sendWelcomeToUsers(repository);

        for (User user : repository.getAllUsers()) {
            int emailsCount = counter.count(user.email);
            if (user.canReceiveEmails())
                assertTrue(emailsCount > 0);
            else
                assertEquals(0, emailsCount);
        }
    }

    /**
     * Add the capability to send sms messages
     */
    @Test
    public void testCapabilityToSendSmsMessages() throws Exception {
        SMSSender sender = new SMSSender();
        App app = new App(sender);
        String phone = "+1234567";
        User user = new User(1, true, ContactBy.PHONE, "a@test.com", phone);
        app.send(user, "Test SMS");
        assertArrayEquals(new String[]{"Test SMS"}, sender.getSMSByPhone(phone));
    }

    /**
     * Make sure possible dispatching options are mail|sms|both
     */
    @Test
    public void testPossibleDispatchingOptions() throws Exception {
        SMSSender smsSender = new SMSSender();
        EmailsSender emailsSender = new EmailsSender();
        App app = new App(smsSender, emailsSender);

        String email = "mail@test.com";
        String phone = "+123";
        User user = new User(1, true, ContactBy.ALL, email, phone);

        app.setDispatchingOptions(MAIL);
        app.send(user, "This is test e-mail");
        assertEquals(1, emailsSender.count(email));
        assertArrayEquals(new String[]{}, smsSender.getSMSByPhone(phone));

        app.setDispatchingOptions(SMS);
        String smsText = "SMS only :)";
        app.send(user, smsText);
        assertEquals(1, emailsSender.count(email));
        assertArrayEquals(new String[]{smsText}, smsSender.getSMSByPhone(phone));

        app.setDispatchingOptions(BOTH);
        String both = "E-mail & SMS";
        app.send(user, both);
        assertEquals(2, emailsSender.count(email));
        assertArrayEquals(new String[]{smsText, both}, smsSender.getSMSByPhone(phone));
    }
}
