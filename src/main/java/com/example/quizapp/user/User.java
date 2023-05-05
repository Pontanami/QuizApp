package com.example.quizapp.user;

/**
 * Class for holding information about a user
 */
public class User {
    private String id,name,email,password;

    /**
     * Initiates a {@link User} instance
     * @param id The user's unique id.
     * @param name The user's name.
     * @param email The user's email.
     * @param password The user's password
     */
    public User(String id,String name, String email, String password){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }


    /**
     * @return The user's name.
     */
    public String getName() {
        return name;
    }
    /**
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }
    /**
     * @return The user's id.
     */
    public  String getId(){return id;}

}
