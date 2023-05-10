package com.example.quizapp.quiz.takeQuiz;

import com.example.quizapp.FetchQuery;
import com.example.quizapp.interfaces.IBuilder;

import java.lang.reflect.Field;
import java.util.Date;

public class TakenQuery extends FetchQuery {
    private String quizId;
    private String userId;
    private Date date;
    private String orderBy;
    private int limit;

    private TakenQuery(TakenQuery.TakenQueryBuilder builder){
        this.quizId = builder.quizId;
        this.userId = builder.userId;
        this.date = builder.date;
        this.orderBy = builder.orderBy;
        this.limit = builder.limit;
    }

    /**
     * Itterates through all fields in the class to get both their specified name and value
     * @throws IllegalAccessException If this class is not allowed to access the private fields, this exception is thrown
     */
    @Override
    public void loopFields() throws IllegalAccessException {
        for (Field field : TakenQuery.class.getDeclaredFields()){
            addFields(field.getName(), field.get(this));
        }
    }

    /**
     * Class for building queries in different steps, where the user can "build" their own query
     * @author Felix Erngård
     */
    public static class TakenQueryBuilder implements IBuilder<TakenQuery> {
        private String quizId;
        private String userId;
        private Date date;
        private String orderBy;
        private int limit;

        /**
         * Method for adding an id we want the query to search for2
         * @param id the id we want to search in the database for
         * @return a UserQueryBuilder object containing information used to query the id of the user
         */
        public TakenQuery.TakenQueryBuilder setquizId(String quizId){
            this.quizId = quizId;
            return this;
        }

        /**
         * Method for adding a name we want the query to search for
         * @param name the name we want to search in the database for
         * @return a UserQueryBuilder object containing information used to query the name of the user
         */
        public TakenQuery.TakenQueryBuilder setuserId(String userId){
            this.userId = userId;
            return this;
        }

        /**
         * Method for adding a password we want the query to search for
         * @param password the password we want to search in the database for
         * @return a UserQueryBuilder object containing information used to query the password of the user
         */
        public TakenQuery.TakenQueryBuilder setDate(Date date){
            this.date = date;
            return this;
        }

        public TakenQuery.TakenQueryBuilder setOrder(String orderBy){
            this.orderBy = orderBy;
            return this;
        }

        public TakenQuery.TakenQueryBuilder setLimit(int limit){
            this.limit = limit;
            return this;
        }

        /**
         * Method for creating the UserQuery, makes a class userQuery containing the information from
         * this class, as this class contains the information about the search
         * @return a UserQuery containing all the query information
         */
        public TakenQuery build(){
            return new TakenQuery(this);
        }
    }
}
