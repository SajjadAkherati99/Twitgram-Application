package Model.shared;

import java.util.List;

public class PublicInformation {
    private String username;
    private String name;
    private String lastSeen;
    private String bio;
    private List<String> profileImage;

    public PublicInformation(String username, String name, String lastSeen, String bio, List<String> profileImage) {
        this.username = username;
        this.name = name;
        this.lastSeen = lastSeen;
        this.bio = bio;
        this.profileImage = profileImage;
    }

    public PublicInformation() {
    }

    public PublicInformation(UserInformation user) {
        username = user.getUsername();
        name = user.getName();
        lastSeen = user.getLastOnlineTime();
        bio = user.getBio();
        profileImage = user.getProfileImages();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(List<String> profileImage) {
        this.profileImage = profileImage;
    }
}
