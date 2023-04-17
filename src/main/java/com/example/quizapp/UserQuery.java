package com.example.quizapp;

public class UserQuery{
    private String name, email, password;

    private UserQuery(UserQueryBuilder builder){
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
    }

    public static class UserQueryBuilder{
        private String name, email, password;

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
