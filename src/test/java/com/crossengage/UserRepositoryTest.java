package com.crossengage;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserRepositoryTest {

    @Test
    public void test() throws IOException {
        UserRepository repository = new UserRepository(new File(this.getClass().getResource("/test_user_data.txt").getFile()));
        List<String> emails = repository.getAllEmails();

        assertEquals(5, emails.size());
        System.out.println(emails);
    }
}
