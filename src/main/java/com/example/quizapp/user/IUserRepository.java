package com.example.quizapp.user;

import java.util.List;

public interface IUserRepository {

    User getCurrentUser();
    void createUser(String name, String email, String password);
    void loginUser(String name, String password);
    List<User> getUsers(String name);
    List<User> getUsers();
    void removeUser(String name);

}
