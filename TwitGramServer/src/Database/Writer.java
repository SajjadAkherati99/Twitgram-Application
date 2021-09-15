package Database;

import Model.Request.SignUpRequest;
import Model.Response.ResponseMessage;
import Model.shared.UserInformation;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Writer {
    ObjectMapper objectMapper;

    public Writer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private <T,S> void writeFile(HashMap<T, S> map, String path){
        File file = new File(path);
        file.getParentFile().mkdir();
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(path);
            objectMapper.writeValue(fileWriter, map);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void writeFile(T Object, String path){
        try {
            String fullPath = "src/Database/Database/Users/" + path;
            File file = new File(fullPath);
            file.getParentFile().mkdir();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(fullPath);
            objectMapper.writeValue(fileWriter, Object);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void writeFile(String fullPath, T Object){
        try {
            File file = new File(fullPath);
            file.getParentFile().mkdir();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(fullPath);
            objectMapper.writeValue(fileWriter, Object);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToUsersFolder(SignUpRequest user) {
        UserInformation newUser = Database.getInstance().createUser(user);
        String path = String.format("%s/info", user.getUsername());
        path = path + ".json";
        writeFile(newUser, path);
    }

    public void updateAccounts(HashMap<String, String> accounts, SignUpRequest user) {
        accounts.put(user.getUsername(), user.getPassword());
        String path = "src/Database/Database/accounts.json";
        writeFile(accounts, path);
    }

    void updateDeActiveAccounts(UserInformation info){
        if (!info.isActive()) {
            String path = "src/Database/Database/deActiveAccounts.json";
            ArrayList<String> deActiveAccounts = Database.getInstance().getReader().readFile(ArrayList.class, path);
            if(deActiveAccounts == null){
                deActiveAccounts = new ArrayList<>();
            }
            deActiveAccounts.add(info.getUsername());
            writeFile(path, deActiveAccounts);
        }
    }


    public synchronized void updateDeActiveAccounts(String username){
        String path = "src/Database/Database/deActiveAccounts.json";
        ArrayList<String> deActiveAccounts = Database.getInstance().getReader().readFile(ArrayList.class, path);
        if(deActiveAccounts == null){
            deActiveAccounts = new ArrayList<>();
        }
        if (deActiveAccounts.contains(username)) {
            deActiveAccounts.remove(username);
        }
        writeFile(path, deActiveAccounts);
    }


    public synchronized String writeNewInformation(String path, String oldUsername, String newUsername, UserInformation newUserInformation) {
        ResponseMessage response = ResponseMessage.INFO_CHANGED;
        if(!newUsername.equals(oldUsername)){
            response = Database.getInstance().getChecker().checkUserExistence(newUsername);
        }
        if (response == ResponseMessage.INFO_CHANGED){
            writeFile(newUserInformation, path);
            rename(oldUsername, newUsername);
            updateAccounts(oldUsername, newUsername);
            updateDeActiveAccounts(newUserInformation);
        }
        return response.toString();
    }

    private void updateAccounts(String oldUsername, String newUsername) {
        HashMap<String, String> accounts = Database.getInstance().getReader().readAccounts();
        String password = accounts.get(oldUsername);
        accounts.remove(oldUsername);
        accounts.put(newUsername, password);
        String path = "src/Database/Database/accounts.json";
        writeFile(accounts, path);
    }

    private void rename(String oldUsername, String newUsername) {
        String sourcePath = "src/Database/Database/Users/" + oldUsername;
        String destPath = "src/Database/Database/Users/" + newUsername;
        File source = new File(sourcePath);
        File dest = new File(destPath);
        source.renameTo(dest);
    }

    public synchronized void deleteUser(String username){
        String path = "src/Database/Database/Users/" + username;
        File userFolder = new File(path);
        String[] entries = userFolder.list();
        for(String s : entries){
            File currentFile = new File(userFolder.getPath(), s);
            currentFile.delete();
        }
        userFolder.delete();

        HashMap<String, String> accounts = Database.getInstance().getReader().readAccounts();
        accounts.remove(username);
        path = "src/Database/Database/accounts.json";
        writeFile(accounts, path);
    }

}
