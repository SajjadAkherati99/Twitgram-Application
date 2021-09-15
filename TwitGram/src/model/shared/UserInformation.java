package model.shared;

import model.shared.enums.LastSeenTypes;
import model.shared.enums.PrivacyTypes;

import java.util.ArrayList;
import java.util.List;

public class UserInformation extends User{
    private PrivacyTypes privacy;
    private LastSeenTypes lastSeen;
    private boolean active;
    private List<String> profileImagesPath = new ArrayList<>();
    private List<String> profileImages = new ArrayList<>();
    private String lastOnlineTime = null;


    public UserInformation() {
    }

    public UserInformation(String name, String username, String password, String birthday, String email, String phoneNumber, String bio, PrivacyTypes privacy, LastSeenTypes lastSeen, boolean active) {
        super(name, username, password, birthday, email, phoneNumber, bio);
        this.privacy = privacy;
        this.lastSeen = lastSeen;
        this.active = active;
    }

    public PrivacyTypes getPrivacy() {
        return privacy;
    }

    public void setPrivacy(PrivacyTypes privacy) {
        this.privacy = privacy;
    }

    public LastSeenTypes getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LastSeenTypes lastSeen) {
        this.lastSeen = lastSeen;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<String> getProfileImagesPath() {
        return profileImagesPath;
    }

    public void setProfileImagesPath(List<String> profileImagesPath) {
        this.profileImagesPath = profileImagesPath;
    }

    public List<String> getProfileImages() {
        return profileImages;
    }

    public void setProfileImages(List<String> profileImages) {
        this.profileImages = profileImages;
    }

    public String getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(String lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }
}
