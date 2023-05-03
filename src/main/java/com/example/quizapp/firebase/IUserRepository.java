package com.example.quizapp.firebase;

import com.example.quizapp.user.User;
import com.example.quizapp.user.UserQuery;

import java.util.List;

public interface IUserRepository {

    User getCurrentUser();
    void createUser(String name, String email, String password);
    void loginUser(String name, String password);
    List<User> getUsers(UserQuery.UserQueryBuilder query);
    List<User> getUsers();
    void removeUser(String name);

}
