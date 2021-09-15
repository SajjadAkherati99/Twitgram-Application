package Database;
import Model.shared.Message.Chat;
import Model.shared.PublicInformation;
import Model.shared.UserInformation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Reader {
    ObjectMapper objectMapper;
    ObjectWriter objectWriter;

    public Reader(ObjectMapper objectMapper, ObjectWriter objectWriter) {
        this.objectMapper = objectMapper;
        this.objectWriter = objectWriter;
    }

    public Reader() {
    }

    HashMap<String, String> readAccounts(){
        HashMap<String, String> accounts = new HashMap<>();
        FileReader fileReader;
        try {
            fileReader = new FileReader("src/Database/Database/accounts.json");
            accounts = objectMapper.readValue(fileReader, new TypeReference<>() {});
        } catch (IOException e) {
        }
        return accounts;
    }

    public synchronized <T> String readFileString(String path, Class<T> clazz){
        T object;
        String newPath = "src/Database/Database/Users/" + path;
        FileReader fileReader;
        try {
            fileReader = new FileReader(newPath);
            object = objectMapper.readValue(fileReader, clazz);
            fileReader.close();
            return objectWriter.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized <T> T readFile(String path, Class<T> clazz){
        T object;
        String newPath = "src/Database/Database/Users/" + path;
        FileReader fileReader;
        try {
            fileReader = new FileReader(newPath);
            object = objectMapper.readValue(fileReader, clazz);
            fileReader.close();
            return object;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized <T> T readFile(Class<T> clazz, String fullPath){
        T object;
        FileReader fileReader;
        try {
            fileReader = new FileReader(fullPath);
            object = objectMapper.readValue(fileReader, clazz);
            fileReader.close();
            return object;
        } catch (IOException e) {
            return null;
        }
    }

    public synchronized PublicInformation readPublicInformation(String username){
        String path = username + "/info.json";
        UserInformation info = readFile(path, UserInformation.class);
        return new PublicInformation(info);
    }
}
