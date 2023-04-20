package com.example.quizapp.user;

/**
 * Class for holding information about a user
 */
public class User {
    private String id,name,email,password;


    public User(String id,String name, String email, String password){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }



    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public  String getId(){return id;}

}
