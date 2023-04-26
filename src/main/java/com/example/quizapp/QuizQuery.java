package com.example.quizapp;

import com.example.quizapp.user.FetchQuery;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class QuizQuery extends FetchQuery {
    private String name, createdBy, id;
    private List<Quiz.Subjects> tags;


    private QuizQuery(QuizQueryBuilder builder){
        this.name = builder.name;
        this.createdBy = builder.createdBy;
        this.id = builder.id;
        this.tags = builder.tags;
    }

    /**
     * Itterates through all fields in the class to get both their specified name and value
     * @throws IllegalAccessException If this class is not allowed to access the private fields, this exception is thrown
     */
    @Override
    public void loopFields() throws IllegalAccessException {
        for (Field field : QuizQuery.class.getDeclaredFields()){
            addFields(field.getName(), field.get(this));
        }
    }

    /**
     * Class for building queries in different steps, where the user can "build" their own query
     * @author Felix Erngård
     */
    public static class QuizQueryBuilder implements IBuilder<QuizQuery> {
        private String name, createdBy, id;
        private List<Quiz.Subjects> tags = new ArrayList<>();

        /**
         * Method for adding an id we want the query to search for2
         *
         * @param id the id we want to search in the database for
         * @return a UserQueryBuilder object containing information used to query the id of the user
         */
        public QuizQueryBuilder setId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Method for adding a name we want the query to search for
         *
         * @param name the name we want to search in the database for
         * @return a UserQueryBuilder object containing information used to query the name of the user
         */
        public QuizQueryBuilder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Method for adding an email we want the query to search for
         *
         * @param email the email we want to search in the database for
         * @return a UserQueryBuilder object containing information used to query the email of the user
         */
        public QuizQueryBuilder setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        /**
         * Method for adding a password we want the query to search for
         *
         * @param password the password we want to search in the database for
         * @return a UserQueryBuilder object containing information used to query the password of the user
         */
        public QuizQueryBuilder setTags(List<Quiz.Subjects> tags) {
            this.tags.addAll(tags);
            return this;
        }

        /**
         * Method for creating the UserQuery, makes a class userQuery containing the information from
         * this class, as this class contains the information about the search
         *
         * @return a UserQuery containing all the query information
         */
        public QuizQuery build() {
            return new QuizQuery(this);
        }
    }
    }
