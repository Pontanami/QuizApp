package user;

import com.example.quizapp.UserQuery;
import com.example.quizapp.user.FirebaseUserRepository;
import com.example.quizapp.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestFirebaseUserRepository {

    private static FirebaseUserRepository repo;
    UserQuery.UserQueryBuilder userQ = new UserQuery.UserQueryBuilder().setName("user1");
    @BeforeAll
    public static void setup(){
        repo = FirebaseUserRepository.getAuth();
    }

    @Test
    public void testCreateUser_UserIsAdded() {
        User user;
        repo.createUser("user1", "user1@gmail.com", "user321");
        String id = repo.getCurrentUser().getId();

        user = repo.getUsers(userQ).get(0);
        Assertions.assertEquals("user1", user.getName());
        repo.removeUser(id);
    }

    @Test
    public void testRemoveUser_UserIsRemoved() {
        repo.createUser("user1", "user1@gmail.com", "user321");
        String id = repo.getCurrentUser().getId();
        repo.removeUser(id);
        UserQuery.UserQueryBuilder userQ = new UserQuery.UserQueryBuilder().setId(id);
        List<User> users = repo.getUsers(userQ);
        Assertions.assertTrue(users.isEmpty());
    }

    @Test
    public void testGetUsers(){
        repo.createUser("user1", "user1@gmail.com", "user321");
        String id = repo.getCurrentUser().getId();
        repo.createUser("user2", "user2@gmail.com", "user321");
        String id2 = repo.getCurrentUser().getId();
        List<User> users = repo.getUsers();
        Assertions.assertTrue(users.size() > 1);
        repo.removeUser(id);
        repo.removeUser(id2);
    }

    @Test
    public void testLogin(){
        User user;
        repo.createUser("user1", "user1@gmail.com", "user321");
        String id = repo.getCurrentUser().getId();
        user = repo.getCurrentUser();
        repo.createUser("user2", "user2@gmail.com", "user321");
        String id2 = repo.getCurrentUser().getId();
        repo.loginUser("user1@gmail.com", "user321");
        Assertions.assertEquals(repo.getCurrentUser().getId(), user.getId());
        repo.removeUser(id);
        repo.removeUser(id2);
    }
}
