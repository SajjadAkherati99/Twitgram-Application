package model.shared;

import Controllers.Network.Request.ObjectType;
import Controllers.view.ProgramController;
import Controllers.view.program.personal.Lists.ListController;

import java.util.ArrayList;

public class ContactList implements GraphicInterface{
    private ArrayList<String> followers = new ArrayList<>();
    private ArrayList<String> followings = new ArrayList<>();
    private ArrayList<String> blocked = new ArrayList<>();

    public ContactList(ArrayList<String> followers, ArrayList<String> followings, ArrayList<String> blocked) {
        this.followers = followers;
        this.followings = followings;
        this.blocked = blocked;
    }

    public ContactList() {
    }

    public ArrayList<String> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public ArrayList<String> getFollowings() {
        return followings;
    }

    public void setFollowings(ArrayList<String> followings) {
        this.followings = followings;
    }

    public ArrayList<String> getBlocked() {
        return blocked;
    }

    public void setBlocked(ArrayList<String> blocked) {
        this.blocked = blocked;
    }

    @Override
    public void goToFrame() {
        new ListController(ProgramController.getInstance().getAnchorPane2(), this);
    }

    @Override
    public void goToFrame(ObjectType objectType) {

    }
}