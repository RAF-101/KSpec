package Spec;

import java.util.List;

public interface Specifikacija {
    //inicijalizacija

    private int initStorage(String Path) // create config and folder
    {
        return 404;
    }
    private int promptInitStorage(String Path) // ask if they want to create a storage
    {
        return 404;
    }
    private int requestLogin() // request credntials upon accessing
    {
        return 404;
    }
    private int updateConfig() // change the file on drive/local memory
    {
        return 404;
    }

    private int initConfig() // create a config file in the working dir
    {
        return 404;
    }

    public int connectStorage(String Path); // check if existing, update config.occupied
    public int disconnectStorage(); // leave storage, update config.occupied


    public int requestNewUser(); // create new user upon init

    public int createFile(String name, String destPath); //empty file
    public int createFolder(String name, String destPath);
    public int uploadFile(String srcPath, String destPath);
    public int uploadFiles(List<String> srcPaths, List<String> destPaths);
    public int deleteFile(String path);
    public int deleteFolder(String path);
    public List<String> listFiles(String path);
    public int moveFile(String srcPath, String destPath);
    public int downloadFolder(String srcPath, String destPath);
    public int downloadFile(String srcPath, String destPath);
    public int addExtBan(String ext);
    public int removeExtBan(String ext);
}