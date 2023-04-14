package com.example.quizapp.user;

import java.util.List;

public interface IUserRepository {

    void createUser(String name, String email, String password);

    void loginUser(String name, String password);
    List<User> getUsers();
    User getUser(String name);
    void removeUser(String name);

}
