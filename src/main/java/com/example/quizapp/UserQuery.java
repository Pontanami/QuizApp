package com.example.quizapp;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UserQuery{


    private String name, email, password, id;

    private UserQuery(UserQueryBuilder builder){
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.id = builder.id;
    }

    public Map<String, String> getNonNullFields() throws IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        Object value;
        for (Field field : UserQuery.class.getDeclaredFields()){
            value = field.get(this);
            if (value != null){
                map.put(field.getName(), value.toString());
            }
        }
        return map;
    }

    public static class UserQueryBuilder implements IBuilder<UserQuery> {
        private String name, email, password, id;


        public UserQueryBuilder id(String id){
            this.id = id;
            return this;
        }

        /**
         * Method for adding a name to the search
         * @param name the name we want to search in the database for
         * @return a UserQueryBuilder object containing information used to query the name of the user
         */
        public UserQueryBuilder name(String name){
            this.name = name;
            return this;
        }

        public UserQueryBuilder email(String email){
            this.email = email;
            return this;
        }

        public UserQueryBuilder password(String password){
            this.password = password;
            return this;
        }

        public UserQuery build(){
            return new UserQuery(this);
        }
    }
}
