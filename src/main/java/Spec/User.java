package Spec;

import java.util.List;

public class User {
    private String username;
    private String password;
    private int permissions;

    User(String username, String password, int permissions){
        this.username = username;
        this.password = password;
        this.permissions = permissions;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public int getPermissions() {
        return permissions;
    }

    public static boolean existingUser(User user, List<User> Users){
        boolean existing = false;
        for( User oldUser : Users){
            existing = oldUser.username.equals(user.username);
        }
        return existing;
    }

}
