package edu.neu.recipehub.objects;

import java.io.Serializable;

public class User implements Serializable {
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
