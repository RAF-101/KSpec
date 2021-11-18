package Spec;

import java.util.List;

public interface Specifikacija {
    //inicijalizacija
    int initStorage(String Path); // create config and folder
    int promptInitStorage(String Path); // ask if they want to create a storage
    int connectStorage(String Path); // check if existing, update config.occupied
    int disconnectStorage(); // leave storage, update config.occupied
    int requestLogin(); // request credntials upon accessing
    int requestNewUser(); // create new user upon init

    int createFile(String name, String destPath); //empty file
    int createFolder(String name, String destPath);
    int uploadFile(String srcPath, String destPath);
    int uploadFiles(List<String> srcPaths, List<String> destPaths);
    int deleteFile(String path);
    int deleteFolder(String path);
    List<String> listFiles(String path);
    int moveFile(String srcPath, String destPath);
    int downloadPath(String srcPath, String destPath);
    int downloadFolder(String srcPath, String destPath);
    int downloadFile(String srcPath, String destPath);

    int updateConfig(); // change the file on drive/local memory
    int initConfig(); // create a config file in the working dir
}