package Java;

import com.example.quizapp.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUser {

    private User user;
    @BeforeEach
    public void setup(){
        user = new User("user1", "user1@gmail.com", "user123");
    }

    @Test
    public void testGetName(){
        user.getName();

        Assertions.assertEquals("user1", user.getName());
    }

    @Test
    public void testGetEmail(){
        user.getEmail();

        Assertions.assertEquals("user1@gmail.com", user.getEmail());
    }

    @Test
    public void testGetPassword(){
        user.getPassword();

        Assertions.assertEquals("user123", user.getPassword());
    }

}
