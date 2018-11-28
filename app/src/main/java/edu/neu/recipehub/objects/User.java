package edu.neu.recipehub.objects;

public class User {
    public String mUserName;

    public User(String mUserName) {
        this.mUserName = mUserName;
    }

    /**
     * For tests ONLY.
     * @return a dummy user.
     */
    public static User getDummyUser(){
        return new User("JundaYang");
    }
}
