package Java;

import com.example.quizapp.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUser {

    private User user;
    @BeforeEach
    public void setup(){
        user = new User("123","user1", "user1@gmail.com", "user123");
    }

    @Test
    public void testGetId(){
        Assertions.assertEquals("123", user.getId());
    }

    @Test
    public void testGetName(){
        Assertions.assertEquals("user1", user.getName());
    }

    @Test
    public void testGetEmail(){
        Assertions.assertEquals("user1@gmail.com", user.getEmail());
    }

    @Test
    public void testGetPassword(){
        Assertions.assertEquals("user123", user.getPassword());
    }

}
