package Spec;

import java.util.List;

/**
 * Private class for handling config users
 */
public class User {
    /**
     * Private variable
     */
    private String username;
    /**
     * Private variable
     */
    private String password;
    /**
     * Private variable
     */
    private int permissions;

    /**
     * Package wide constructor for user
     * @param username user username
     * @param password user password
     * @param permissions user permissions
     */
    User(String username, String password, int permissions){
        this.username = username;
        this.password = password;
        this.permissions = permissions;
    }

    /**
     * Retrieve username
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Retrieve password
     * @return password
     */
    public String getPassword() {
        return password;
    }


    /**
     * Retrieve permissions
     * @return  permissions
     */
    public int getPermissions() {
        return permissions;
    }

    /**
     * Checks if user existis in a collection of users
     * @param user user to log
     * @param Users collection of all users
     * @return whether it was successful
     */
    public static boolean existingUser(User user, List<User> Users){
        boolean existing = false;
        for( User oldUser : Users){
            existing = oldUser.username.equals(user.username);
        }
        return existing;
    }

}
