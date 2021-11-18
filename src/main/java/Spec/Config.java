package Spec;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Config {

    private boolean occupied = false;
    private int permissions = 0;
    private int filesSize = 0;
    private int filesCount = 0;
    private int filesSizeLimit = 0;
    private int filesCountLimit = 0;

    private List<User> users = new ArrayList<User>();
    private List<String> bans = new ArrayList<String>();

    // json string
    Config(File config){
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

    public void addFileCount(int count){
        filesCount += count;
    }

    public void addFileSize(int size){
        filesSize += size;
    }

    public boolean setStorageSize(int bytes){
        if(filesSizeLimit == 0) {
            filesSizeLimit = bytes;
            return true;
        } return false;
    }

    boolean checkStorageSizeLimit(int fileSize){
        return (filesSize + fileSize < filesSizeLimit);
    } // added size

    boolean setFileCountLimit(int limit){
        if(filesCountLimit == 0){
            filesCountLimit = limit;
            return true;
        } return false;
    }

    boolean checkFileCountLimit(int fileCount){
        return (filesCount + fileCount < filesCountLimit);
    }

    boolean addExtensionBan(String ext){
        if(bans.contains(ext)) return false;
        bans.add(ext);
        return true;
    }

    boolean removeExtensionBan(String ext){
        if(bans.contains(ext)) {
            bans.remove(ext);
            return true;
        }
        return false;
    }

    boolean extensionAllowed(String ext){
        return !bans.contains(ext);
    }

    int createUser(String Username, String Password, int Permission){
        User newUser = new User(Username, Password, Permission);
        if(!User.existingUser(newUser, users)) {
            users.add(newUser);
            return 0;
        }
        else return 1; // user already exists
    }

    boolean hasPermission(int action){
        return ( permissions > 100000 || (permissions / action) % 10 == 1);
    }// weigh action over permission

    void setOccupied(boolean occupied){
        this.occupied = occupied;
    }

    boolean checkOccupied(){
        return occupied;
    }
}