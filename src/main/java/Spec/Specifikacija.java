package Spec;

import java.util.List;

/**
 * Interaface for GDrive and Local storage implementations
 */
public interface Specifikacija {
    //inicijalizacija

    /**
     * Private method
     * @param Path absolute path to storage
     * @return whether it was successful
     */
    private int initStorage(String Path) // create config and folder
    {
        return 404;
    }
    /**
     * Private method
     * @param Path absolute path to storage
     * @return whether it was successful
     */
    private int promptInitStorage(String Path) // ask if they want to create a storage
    {
        return 404;
    }
    /**
     * Private method
     * @return whether it was successful
     */
    private int requestLogin() // request credntials upon accessing
    {
        return 404;
    }
    /**
     * Private method
     * @return whether it was successful
     */
    private int updateConfig() // change the file on drive/local memory
    {
        return 404;
    }

    /**
     * Private method
     * @return whether it was successful
     */
    private int initConfig() // create a config file in the working dir
    {
        return 404;
    }

    /**
     * Private method
     * @param ext filepath to get ext from
     * @return extension from filepath
     */
    private String retrieveExt(String ext)
    {
        return "";
    }

    /**
     * Searches for a storage under the given directory. And begins login process if found. Otherwise prompt for storage creation.
     * @param Path Absolute path to the root of the storage
     * @return Returns 0 if successful, 1 if the login info is invalid, 2 if the storage is currently in use, 3 if it's not a storage and storage wasn't created.
     */
    public int connectStorage(String Path); // check if existing, update config.occupied

    /**
     * Disconects the user from the storage.
     * @return Returns 0 if successful, 404 if no storage is active and 1 if OS error occurs.
     */
    public int disconnectStorage(); // leave storage, update config.occupied

    /**
     * Begins the process of creating a new user.
     * @return Returns 0 if successful, 404 if no storage is active, 403 if insufficient permission, 402 if invalid permission is given.
     */
    public int requestNewUser(); // create new user upon init


    /**
     * Create a new empty file with the given name at the specified path.
     * @param name New file name
     * @param destPath File based off the root of the storage
     * @return Returns 0 if successful, 1 if insufficient permission, 7 if banned extension, 401 if OS error, 404 if no active storage
     */
    public int createFile(String name, String destPath); //empty file

    /**
     * Create a new empty folder with the given name at the specified path. Supports multiple parent creation.
     * @param name New folder name
     * @param destPath Folder based off the root of the storage
     * @return Returns 0 if successful, 1 if insufficient permission, 401 if OS error, 404 if no active storage
     */
    public int createFolder(String name, String destPath);


    /**
     * Upload file from srcPath to the destPath inside of the storage
     * @param srcPath Absolute path to the source file
     * @param destPath Path based off the root of the storage
     * @return Returns 0 if successful, 1 if insufficient permission, 2 if not a file, 3 if not a directory, 4 if file count is passed, 5 if total file size is maxed, 6 if max file size is passed, 401 if OS error, 404 if no active storage
     */
    public int uploadFile(String srcPath, String destPath);

    /**
     * Upload multiple files from srcPaths to the destPaths inside of the storage
     * @param srcPaths Absolute path to the source files
     * @param destPaths Path based off the root of the storage
     * @return Returns 0 if successful, 1 if insufficient permission, 2 if not a file, 3 if not a directory, 4 if file count is passed, 5 if total file size is maxed, 6 if max file size is passed, 401 if OS error, 404 if no active storage
     */
    public int uploadFiles(List<String> srcPaths, List<String> destPaths);

    /**
     * Delete a file from the path if inside of storage
     * @param path Path based off the roof of the storage
     * @return Returns 0 if successful, 1 if insufficient permission, 2 if not a file, 3 if not existing, 401 if OS error, 404 if no active storage
     */
    public int deleteFile(String path);

    /**
     * Delete a folder and all the child files and folders inside
     * @param path Path based off the roof of the storage
     * @return Returns 0 if successful, 1 if insufficient permission, 2 if not a folder, 3 if not existing, 401 if OS error, 404 if no active storage
     */
    public int deleteFolder(String path);

    /**
     * List all files in the given directory, based off the storage root
     * @param path Path based off the roof of the storage
     * @return Returns list of strings type of List if successful and null otherwise
     */
    public List<String> listFiles(String path);

    /**
     * Move file from srcPath to destPath within the storage.
     * @param srcPath Path to the file based off the roof of the storage
     * @param destPath Path to the destination based off the roof of the storage
     * @return Returns 0 if successful, 1 if insufficient permission, 2 if source is not a file, 3 if destination is not a folder, 401 if OS error, 404 if no active storage
     */
    public int moveFile(String srcPath, String destPath);

    /**
     * Download folder and all child files and folders from storage to the machine
     * @param srcPath Path to the folder based off the roof of the storage
     * @param destPath Absolute path to the destination
     * @return Returns 0 if successful, 1 if insufficient permission, 2 if source is not a folder, 3 if dest is not a folder, 401 if OS error, 404 if no active storage
     */
    public int downloadFolder(String srcPath, String destPath);

    /**
     * Download file from storage to the machine
     * @param srcPath Path to the folder based off the roof of the storage
     * @param destPath Absolute path to the destination
     * @return Returns 0 if successful, 1 if insufficient permission, 2 if source is not a file, 3 if dest is not a folder, 401 if OS error, 404 if no active storage
     */
    public int downloadFile(String srcPath, String destPath);

    /**
     * Add a ban for the extension.
     * @param ext Extension to ban.
     * @return Returns 0 if successful, 1 if extension is already banned, 404 if no active storage
     */
    public int addExtBan(String ext);

    /**
     * Remove a ban for the extension.
     * @param ext Extension to unban.
     * @return Returns 0 if successful, 1 if extension isn't banned, 404 if no active storage
     */
    public int removeExtBan(String ext);
}