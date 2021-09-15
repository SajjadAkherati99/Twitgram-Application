package Database;

import Model.shared.UserInformation;

import java.time.LocalDateTime;

public class DateAndTimeHandler {

    public DateAndTimeHandler() {
    }

    public static synchronized void updateLastSeen(String username){
        String path = username + "/info.json";
        UserInformation userInformation = Database.getInstance().getReader().readFile(path, UserInformation.class);
        userInformation.setLastOnlineTime(LocalDateTime.now().toString());
        Database.getInstance().getWriter().writeFile(userInformation, path);
    }
}
