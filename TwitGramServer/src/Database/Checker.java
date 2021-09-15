package Database;

import Model.Request.SignInRequest;
import Model.Request.SignUpRequest;
import Model.Response.ResponseMessage;

import java.util.HashMap;

public class Checker {

    public Checker() {
    }

    public synchronized String checkSignInRequestInformation(SignInRequest user) {
        HashMap<String, String> accounts = Database.getInstance().getReader().readAccounts();
        if (accounts.containsKey(user.getUsername())){
            if (accounts.get(user.getUsername()).equals(user.getPassword())){
                return ResponseMessage.OK.toString();
            }else {
                return ResponseMessage.WRONG_PASSWORD.toString();
            }
        }else {
            return ResponseMessage.USER_NOT_FOUND.toString();
        }
    }

    public synchronized String checkUserExistence(SignUpRequest user) {
        HashMap<String, String> accounts = Database.getInstance().getReader().readAccounts();
        if (accounts.containsKey(user.getUsername())){
            return ResponseMessage.USER_EXIST_ERROR.toString();
        }else {
            Database.getInstance().getWriter().updateAccounts(accounts, user);
            Database.getInstance().getWriter().addToUsersFolder(user);
            return ResponseMessage.OK.toString();
        }
    }

    public synchronized ResponseMessage checkUserExistence(String username){
        HashMap<String, String> accounts = Database.getInstance().getReader().readAccounts();
        if (accounts.containsKey(username)){
            return ResponseMessage.USER_EXIST_ERROR;
        }else {
            return ResponseMessage.INFO_CHANGED;
        }
    }

    public ResponseMessage searchUser(String username){
        HashMap<String, String> accounts = Database.getInstance().getReader().readAccounts();
        if (accounts.containsKey(username)){
            return ResponseMessage.OK;
        }else {
            return ResponseMessage.USER_NOT_FOUND;
        }
    }
}
