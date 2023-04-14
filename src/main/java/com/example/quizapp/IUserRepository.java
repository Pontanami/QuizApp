package com.example.quizapp;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IUserRepository {

    void createUser(String name, String email, String password);

    void loginUser(String name, String password);
    List<User> getUsers();
    User getUser(String name);
    void removeUser(String name);

}
