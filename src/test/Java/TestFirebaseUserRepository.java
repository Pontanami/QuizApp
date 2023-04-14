package Java;

import com.example.quizapp.FirebaseUserRepository;
import com.example.quizapp.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

public class TestFirebaseUserRepository {

    private static FirebaseUserRepository repo;
    @BeforeAll
    public static void setup(){
        repo = FirebaseUserRepository.getAuth();
    }

    @Test
    public void testCreateUser_UserIsAdded() {
        User user;
        try {
            repo.createUser("user1", "user1@gmail.com", "user321");
            Thread.sleep(1000);
            user = repo.getUser("user1");
            Assertions.assertEquals("user1", user.getName());
            repo.removeUser("user1");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRemoveUser_UserIsRemoved() {
        try {
            repo.createUser("user1", "user1@gmail.com", "user321");
            Thread.sleep(1000);
            repo.removeUser("user1");
            Thread.sleep(1000);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        User user = repo.getUser("user1");

        Assertions.assertNull(user);
    }

    @Test
    public void testGetUsers(){
        repo.createUser("user1", "user1@gmail.com", "user321");
        repo.createUser("user2", "user2@gmail.com", "user321");

        var users = repo.getUsers();

        Assertions.assertTrue(users.size() > 1);

        try {
            repo.removeUser("user1");
            repo.removeUser("user2");
        } catch (ExecutionException  | InterruptedException e) {
            e.printStackTrace();
        }
    }



}
