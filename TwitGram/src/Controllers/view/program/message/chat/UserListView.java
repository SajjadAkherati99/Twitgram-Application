package Controllers.view.program.message.chat;

import javafx.scene.control.ListCell;
import model.shared.Message.ChatLists;

public class UserListView extends ListCell<String> {
    ChatLists chatLists;

    public UserListView(ChatLists chatLists) {
        this.chatLists = chatLists;
    }

    @Override
    public void updateItem(String string, boolean empty){
        super.updateItem(string, empty);
        if (string != null){
            UserListDataCell userListDataCell = new UserListDataCell();
            userListDataCell.setInfo(string, chatLists.getMapList().get(string));
            setGraphic(userListDataCell.getHBox());
        }
    }
}
