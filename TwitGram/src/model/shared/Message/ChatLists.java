package model.shared.Message;

import Controllers.Network.Request.ObjectType;
import Controllers.view.ProgramController;
import Controllers.view.program.message.chat.MessageChatController;
import Controllers.view.program.profile.ProfileController;
import model.shared.GraphicInterface;
import model.shared.PublicInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ChatLists implements GraphicInterface {
    private LinkedList<String> lists = new LinkedList<>();
    private HashMap<String, PublicInformation> mapList = new HashMap<>();
    private HashMap<String, Chat> chatsLists = new HashMap<>();

    public ChatLists() {
    }

    public ChatLists(LinkedList<String> lists) {
        this.lists = lists;
    }

    public ChatLists(LinkedList<String> lists, HashMap<String, PublicInformation> mapList, HashMap<String, Chat> chatsLists) {
        this.lists = lists;
        this.mapList = mapList;
        this.chatsLists = chatsLists;
    }

    public LinkedList<String> getLists() {
        return lists;
    }

    public void setLists(LinkedList<String> lists) {
        this.lists = lists;
    }

    public HashMap<String, PublicInformation> getMapList() {
        return mapList;
    }

    public void setMapList(HashMap<String, PublicInformation> mapList) {
        this.mapList = mapList;
    }

    public void addPublicInfo(String username, PublicInformation information){
        mapList.put(username, information);
    }

    public HashMap<String, Chat> getChatsLists() {
        return chatsLists;
    }

    public void setChatsLists(HashMap<String, Chat> chatsLists) {
        this.chatsLists = chatsLists;
    }

    public void addToChatsLists(String username, Chat chat){
        chatsLists.put(username, chat);
    }

    @Override
    public void goToFrame() {
    }

    @Override
    public void goToFrame(ObjectType objectType) {
        checkSelectedUser(objectType);
        new MessageChatController(ProgramController.getInstance().getAnchorPane2(), this);
    }

    void checkSelectedUser(ObjectType objectType){
        if (objectType == ObjectType.CHAT_LIST_SELECTED_USER) {
            PublicInformation information = ProfileController.getInfo();
            if (lists.contains(information.getUsername())) {
                lists.remove(information.getUsername());
                lists.addFirst(information.getUsername());
            } else {
                lists.addFirst(information.getUsername());
                mapList.put(information.getUsername(), information);
                chatsLists.put(information.getUsername(), new Chat());
            }
        }
    }

    public void update(Message message, PublicInformation info) {
        if(lists.contains(info.getUsername())){
            lists.remove(info.getUsername());
            lists.addFirst(info.getUsername());
            chatsLists.get(info.getUsername()).getMessages().add(message);
        } else {
            lists.addFirst(info.getUsername());
            mapList.put(info.getUsername(), info);
            ArrayList<Message> messages = new ArrayList<>();
            messages.add(message);
            Chat chat = new Chat(messages);
            chatsLists.put(info.getUsername(), chat);
        }
    }
}
