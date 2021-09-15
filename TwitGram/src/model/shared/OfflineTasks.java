package model.shared;

public class OfflineTasks {
    private static OfflineTasks offlineTasks = null;
    private UserInformation newInformation;
    private String oldUsername;

    public OfflineTasks() {
    }

    public static OfflineTasks getInstance(){
        if(offlineTasks == null){
            offlineTasks = new OfflineTasks();
        }
        return offlineTasks;
    }

    public UserInformation getNewInformation() {
        return newInformation;
    }

    public void setNewInformation(UserInformation newInformation) {
        this.newInformation = newInformation;
    }

    public String getOldUsername() {
        return oldUsername;
    }

    public void setOldUsername(String oldUsername) {
        this.oldUsername = oldUsername;
    }
}
