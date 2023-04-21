package com.example.quizapp;

import com.example.quizapp.user.FetchQuery;

import java.lang.reflect.Field;


/**
 * Class for holding the values of each field the user has specified for the query
 * Extends FetchQuery as it specifies a common method for getting all non-Null fields
 * @author Felix Erngård
 */
public class UserQuery extends FetchQuery {


    private String name, email, password, id;

    private UserQuery(UserQueryBuilder builder){
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.id = builder.id;
    }

    /**
     * Itterates through all fields in the class to get both their specified name and value
     * @throws IllegalAccessException If this class is not allowed to access the private fields, this exception is thrown
     */
    @Override
    public void loopFields() throws IllegalAccessException {
        for (Field field : UserQuery.class.getDeclaredFields()){
            addFields(field.getName(), field.get(this));
        }
    }

    /**
     * Class for building queries in different steps, where the user can "build" their own query
     * @author Felix Erngård
     */
    public static class UserQueryBuilder implements IBuilder<UserQuery> {
        private String name, email, password, id;

        /**
         * Method for adding an id we want the query to search for2
         * @param id the id we want to search in the database for
         * @return a UserQueryBuilder object containing information used to query the id of the user
         */
        public UserQueryBuilder setId(String id){
            this.id = id;
            return this;
        }

        /**
         * Method for adding a name we want the query to search for
         * @param name the name we want to search in the database for
         * @return a UserQueryBuilder object containing information used to query the name of the user
         */
        public UserQueryBuilder setName(String name){
            this.name = name;
            return this;
        }

        /**
         * Method for adding an email we want the query to search for
         * @param email the email we want to search in the database for
         * @return a UserQueryBuilder object containing information used to query the email of the user
         */
        public UserQueryBuilder setEmail(String email){
            this.email = email;
            return this;
        }

        /**
         * Method for adding a password we want the query to search for
         * @param password the password we want to search in the database for
         * @return a UserQueryBuilder object containing information used to query the password of the user
         */
        public UserQueryBuilder setPassword(String password){
            this.password = password;
            return this;
        }

        /**
         * Method for creating the UserQuery, makes a class userQuery containing the information from
         * this class, as this class contains the information about the search
         * @return a UserQuery containing all the query information
         */
        public UserQuery build(){
            return new UserQuery(this);
        }
    }
}
