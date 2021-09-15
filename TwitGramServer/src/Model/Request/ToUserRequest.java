package Model.Request;

import Controller.ClientHandler;
import Controller.Server;
import Database.Database;
import Database.Reader;
import Model.enums.PrivacyTypes;
import Model.shared.Announcements;
import Model.shared.ContactList;
import Model.shared.UserInformation;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.PrintWriter;

@JsonTypeName("toUserRequest")
public class ToUserRequest implements Request {
    private String username;
    private UserTypeRequest userTypeRequest;

    public ToUserRequest() {
    }

    public ToUserRequest(String username, UserTypeRequest userTypeRequest) {
        this.username = username;
        this.userTypeRequest = userTypeRequest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserTypeRequest getUserTypeRequest() {
        return userTypeRequest;
    }

    public void setUserTypeRequest(UserTypeRequest userTypeRequest) {
        this.userTypeRequest = userTypeRequest;
    }

    private boolean updateContacts(String sourceUser, String destUser, UserTypeRequest typeRequest){
        String sourcePath = sourceUser + "/contactList.json";
        ContactList sourceContactList = Database.getInstance().getReader().readFile(sourcePath, ContactList.class);
        String destPath = destUser + "/contactList.json";
        ContactList destContactList = Database.getInstance().getReader().readFile(destPath, ContactList.class);
        boolean update = false;


        switch (typeRequest) {
            case FOLLOW -> {
                if (!sourceContactList.getFollowings().contains(destUser)) {
                    sourceContactList.getFollowings().add(destUser);
                    destContactList.getFollowers().add(sourceUser);
                    update = true;
                }
            }
            case UNFOLLOW -> {
                if (sourceContactList.getFollowings().contains(destUser)) {
                    sourceContactList.getFollowings().remove(destUser);
                    destContactList.getFollowers().remove(sourceUser);
                    update = true;
                }
            }
            case BLOCK -> {
                if (!sourceContactList.getBlocked().contains(destUser)) {
                    sourceContactList.getFollowers().remove(destUser);
                    sourceContactList.getFollowings().remove(destUser);
                    sourceContactList.getBlocked().add(destUser);

                    destContactList.getFollowers().remove(sourceUser);
                    destContactList.getFollowings().remove(sourceUser);
                    update = true;
                }
            }
            case UNBLOCK -> {
                if (sourceContactList.getBlocked().contains(destUser)) {
                    sourceContactList.getBlocked().remove(destUser);
                    update  = true;
                }
            }
            case REMOVE -> {
                if(sourceContactList.getFollowers().contains(destUser)) {
                    sourceContactList.getFollowers().remove(destUser);
                    destContactList.getFollowings().remove(sourceUser);
                    update = true;
                }
            }
            case ACCEPT -> {
                if(!sourceContactList.getFollowers().contains(destUser)) {
                    sourceContactList.getFollowers().add(destUser);
                    destContactList.getFollowings().add(sourceUser);
                    update = true;
                }
            }
            case REJECT, REJECT_AND_TELL -> {
                update = true;
            }
            default -> {
            }
        }

        Database.getInstance().getWriter().writeFile(sourceContactList, sourcePath);
        Database.getInstance().getWriter().writeFile(destContactList, destPath);

        return update;
    }

    private void updateAnnouncements(String sourceUser, String destUser, UserTypeRequest typeRequest, boolean update){
        String sourcePath = sourceUser + "/announcements.json";
        Announcements sourceAnnouncements = Database.getInstance().getReader().readFile(sourcePath, Announcements.class);
        String destPath = destUser + "/announcements.json";
        Announcements destAnnouncements = Database.getInstance().getReader().readFile(destPath, Announcements.class);

        if (update) {
            String msg = "";
            switch (typeRequest) {
                case FOLLOW -> {
                    msg = String.format("you have followed @%s", destUser);
                    sourceAnnouncements.getSystem().add(msg);

                    msg = String.format("@%s has started following you", sourceUser);
                    destAnnouncements.getSystem().add(msg);
                }
                case UNFOLLOW -> {
                    msg = String.format("@%s has stopped following you", sourceUser);
                    destAnnouncements.getSystem().add(msg);
                }
                case BLOCK -> {
                    msg = String.format("@%s has blocked you", sourceUser);
                    destAnnouncements.getSystem().add(msg);
                }
                case UNBLOCK -> {
                    msg = String.format("@%s has unblocked you", sourceUser);
                    destAnnouncements.getSystem().add(msg);
                }
                case REMOVE -> {
                    msg = String.format("@%s has removed you from his/her follower", sourceUser);
                    destAnnouncements.getSystem().add(msg);
                }
                case REJECT -> {
                    destAnnouncements.getClientRequest().remove(("@"+sourceUser));
                    sourceAnnouncements.getPeopleRequest().remove(("@"+destUser));
                }
                case REJECT_AND_TELL -> {
                    destAnnouncements.getClientRequest().remove(("@"+sourceUser));
                    sourceAnnouncements.getPeopleRequest().remove(("@"+destUser));
                    msg = String.format("@%s has rejected your follow request", sourceUser);
                    destAnnouncements.getSystem().add(msg);
                }
                case ACCEPT -> {
                    destAnnouncements.getClientRequest().remove(("@"+sourceUser));
                    sourceAnnouncements.getPeopleRequest().remove(("@"+destUser));
                    msg = String.format("@%s has accepted your follow request", sourceUser);
                    destAnnouncements.getSystem().add(msg);
                }
                default -> {
                }
            }

            Database.getInstance().getWriter().writeFile(sourceAnnouncements, sourcePath);
            Database.getInstance().getWriter().writeFile(destAnnouncements, destPath);
        }
    }

    private void updatePrivate(String sourceUser, String destUser){
        String sourcePath = sourceUser + "/announcements.json";
        Announcements sourceAnnouncements = Database.getInstance().getReader().readFile(sourcePath, Announcements.class);
        String destPath = destUser + "/announcements.json";
        Announcements destAnnouncements = Database.getInstance().getReader().readFile(destPath, Announcements.class);
        String sourcePath1 = sourceUser + "/contactList.json";
        ContactList sourceContactList = Database.getInstance().getReader().readFile(sourcePath1, ContactList.class);

        if(!sourceAnnouncements.getClientRequest().contains(("@"+destUser)) && !(sourceContactList.getFollowings().contains(destUser))){
            sourceAnnouncements.getClientRequest().add(("@"+destUser));
            destAnnouncements.getPeopleRequest().add(("@"+sourceUser));
        }

        Database.getInstance().getWriter().writeFile(sourceAnnouncements, sourcePath);
        Database.getInstance().getWriter().writeFile(destAnnouncements, destPath);
    }

    @Override
    public void execute(BufferedReader inputStream, PrintWriter outputStream, ClientHandler clientHandler, ObjectMapper objectMapper, long id) {
        String sourceUser = Server.getInstance().getOnlineUsers().get(id).getUsername();
        switch (userTypeRequest) {
            case FOLLOW -> {
                String path = username + "/info.json";
                UserInformation destUser = Database.getInstance().getReader().readFile(path, UserInformation.class);
                if (destUser.getPrivacy().equals(PrivacyTypes.PUBLIC)) {
                    boolean update = updateContacts(sourceUser, username, userTypeRequest);
                    updateAnnouncements(sourceUser, username, userTypeRequest, update);
                } else {
                    updatePrivate(sourceUser, username);
                }
            }
            default -> {
                boolean update = updateContacts(sourceUser, username, userTypeRequest);
                updateAnnouncements(sourceUser, username, userTypeRequest, update);
            }
        }
    }
}
