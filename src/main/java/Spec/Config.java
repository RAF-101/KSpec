package Spec;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Private class for handling storage config
 */
public class Config {
    /**
     * Private variable
     */
    private boolean occupied = false;
    /**
     * Private variable
     */
    private int permissions = 0;
    /**
     * Private variable
     */
    private int filesSize = 0;
    /**
     * Private variable
     */
    private int filesCount = 0;
    /**
     * Private variable
     */
    private int fileSizeLimit = 0;
    /**
     * Private variable
     */
    private int filesSizeLimit = 0;
    /**
     * Private variable
     */
    private int filesCountLimit = 0;

    /**
     * Private variable
     */
    private List<User> users = new ArrayList<User>();

    /**
     * Private variable
     */
    private List<String> bans = new ArrayList<String>();

    // json string

    /**
     * Generates a config based off of the config.json
     * @param config File typed config
     */
    public Config(File config){
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(config)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            occupied = (boolean) jsonObject.get("occupied");
            permissions = ((Long) jsonObject.get("permissions")).intValue();
            filesSize = ((Long) jsonObject.get("filesSize")).intValue();
            filesCount = ((Long) jsonObject.get("filesCount")).intValue();
            filesSizeLimit = ((Long) jsonObject.get("filesSizeLimit")).intValue();
            filesCountLimit = ((Long) jsonObject.get("filesCountLimit")).intValue();

            JSONArray jsBans = (JSONArray) jsonObject.get("bans");
            Iterator<JSONObject> iterator1 = jsBans.iterator();
            while (iterator1.hasNext()) {
                JSONObject temp = iterator1.next();
                bans.add( temp.get("ext").toString() );
            }

            JSONArray jsUsers = (JSONArray) jsonObject.get("users");
            Iterator<JSONObject> iterator2 = jsUsers.iterator();
            while (iterator2.hasNext()) {
                JSONObject temp = iterator2.next();
                User jsUser = new User(
                        temp.get("username").toString(),
                        temp.get("password").toString(),
                        ((Long) temp.get("permissions")).intValue()
                );
                if (!User.existingUser(jsUser, users)) {
                    users.add(jsUser);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /**
     * @return JSON parsable String to store.
     */
    public String getJSONForm(){
        JSONObject konfig = new JSONObject();

        konfig.put("occupied", occupied);
        konfig.put("permissions", permissions);
        konfig.put("filesSize", filesSize);
        konfig.put("filesCount", filesCount);
        konfig.put("filesSizeLimit", filesSizeLimit);
        konfig.put("filesCountLimit", filesCountLimit);

        JSONArray jsUsers = new JSONArray();
        for(User user : users) {
            JSONObject jsUser = new JSONObject();
            jsUser.put("username", user.getUsername());
            jsUser.put("password", user.getPassword());
            jsUser.put("permissions", user.getPermissions());
            jsUsers.add(jsUser);
        }
        konfig.put("users", jsUsers);

        JSONArray jsBans = new JSONArray();
        for(String ext : bans){
            JSONObject jsBan = new JSONObject();
            jsBan.put("ext", ext);
            jsBans.add(jsBan);
        }
        konfig.put("bans", jsBans);

        return konfig.toJSONString();
    }

    /**
     * @return JSON parsable String to init storage.
     */
    public static String initConfig(){
        JSONObject konfig = new JSONObject();
        List<User> users = new ArrayList<User>();
        List<String> bans = new ArrayList<String>();

        konfig.put("occupied", false);
        konfig.put("permissions", 0);
        konfig.put("filesSize", 0);
        konfig.put("filesCount", 0);
        konfig.put("filesSizeLimit", 0);
        konfig.put("filesCountLimit", 0);



        JSONArray jsUsers = new JSONArray();
        for(User user : users) {
            JSONObject jsUser = new JSONObject();
            jsUser.put("username", user.getUsername());
            jsUser.put("password", user.getPassword());
            jsUser.put("permissions", user.getPermissions());
            jsUsers.add(jsUser);
        }
        konfig.put("users", jsUsers);

        JSONArray jsBans = new JSONArray();
        for(String ext : bans){
            JSONObject jsBan = new JSONObject();
            jsBan.put("ext", ext);
            jsBans.add(jsBan);
        }
        konfig.put("bans", jsBans);

        return konfig.toJSONString();
    }


    /**
     * Adds file count
     * @param count Number of files to add
     */
    public void addFileCount(int count){
        filesCount += count;
    }

    /**
     * Adds file size
     * @param size Size of files to add
     */
    public void addFileSize(int size){
        filesSize += size;
    }

    /**
     * Limit storage total size
     * @param bytes Amount of bytes
     * @return whether it was successful
     */
    public boolean setStorageSize(int bytes){
        if(filesSizeLimit == 0) {
            filesSizeLimit = bytes;
            return true;
        } return false;
    }

    /**
     * Check if file breaks the limit
     * @param fileSize File Size in bytes
     * @return whether it was successful
     */
    public boolean checkStorageSizeLimit(int fileSize){
        return ( filesSizeLimit == 0 || (filesSize + fileSize < filesSizeLimit) );
    } // added size

    /**
     * Limit storage total file count, folders excluded
     * @param limit Amount of files to limit to
     * @return whether it was successful
     */
    public boolean setFileCountLimit(int limit){
        if(filesCountLimit == 0){
            filesCountLimit = limit;
            return true;
        } return false;
    }

    /**
     * Check if adding fileCount files would pass the limit
     * @param fileCount amount of files
     * @return whether it was successful
     */
    public boolean checkFileCountLimit(int fileCount){
        return ( filesCountLimit == 0 || (filesCount + fileCount < filesCountLimit) );
    }

    /**
     * Set single file size limit
     * @param limit Size limit in bytes
     * @return whether it was successful
     */
    public boolean setFileSizeLimit(int limit){
        if(fileSizeLimit == 0){
            fileSizeLimit = limit;
            return true;
        } return false;
    }

    /**
     * Check if file is withing the limits
     * @param size File size in bytes
     * @return whether it was successful
     */
    public boolean checkFileSizeLimit(int size){
        return ( fileSizeLimit == 0 || (size < fileSizeLimit) );
    }


    /**
     * Add extension ban
     * @param ext extension to ban
     * @return whether it was successful
     */
    public boolean addExtensionBan(String ext){
        if(bans.contains(ext)) return false;
        bans.add(ext);
        return true;
    }

    /**
     * Unban extension
     * @param ext extension to unban
     * @return whether it was successful
     */
    public boolean removeExtensionBan(String ext){
        if(bans.contains(ext)) {
            bans.remove(ext);
            return true;
        }
        return false;
    }

    /**
     * Check whether extension is allowed
     * @param ext Extension to check
     * @return whether it was successful
     */
    public boolean extensionAllowed(String ext){
        if(bans.contains(ext)) {
            return false;
        }
        return true;
    }

    /**
     * Craete a new user in the storage
     * @param Username   new user username
     * @param Password   new user password
     * @param Permission integer permission 1 read, 10 write, 100 move, 1000 delete, 10000 owner
     * @return whether it was successful
     */
    public int createUser(String Username, String Password, int Permission){
        User newUser = new User(Username, Password, Permission);
        if(!User.existingUser(newUser, users)) {
            users.add(newUser);
            return 0;
        }
        else return 1; // user already exists
    }


    /**
     * Check if user exists
     * @param Username user username
     * @param Password user password
     * @return whether it was successful
     */
    public int loginUser(String Username, String Password){
        for(User user : users){
            if(user.getUsername().equals(Username) && user.getPassword().equals(Password)){
                permissions = user.getPermissions();
                return 0;
            }
        }
        return 1;
    }

    /**
     * Check if user has permissions for action
     * @param action action signature
     * @return whether it was successful
     */
    public boolean hasPermission(int action){
        return ( permissions > 100000 || (permissions / action) % 10 == 1);
    }// weigh action over permission

    /**
     * Mark storage as occupied or free
     * @param occupied Is storage occupied
     */
    public void setOccupied(boolean occupied){
        this.occupied = occupied;
    }

    /**
     * Check if storage is occupied
     * @return whether it was successful
     */
    public boolean checkOccupied(){
        return occupied;
    }
}