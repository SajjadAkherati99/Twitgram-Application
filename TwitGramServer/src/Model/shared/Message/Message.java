package Model.shared.Message;

public class Message {
    private String username;
    private String imageStr;
    private String msg;
    private String dateAndTime;

    public Message(String username, String imageStr, String msg, String dateAndTime) {
        this.username = username;
        this.imageStr = imageStr;
        this.msg = msg;
        this.dateAndTime = dateAndTime;
    }

    public Message() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageStr() {
        return imageStr;
    }

    public void setImageStr(String imageStr) {
        this.imageStr = imageStr;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
