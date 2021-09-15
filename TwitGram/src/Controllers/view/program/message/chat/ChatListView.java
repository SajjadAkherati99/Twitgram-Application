package Controllers.view.program.message.chat;

import javafx.scene.control.ListCell;

public class ChatListView extends ListCell<String> {

    @Override
    public void updateItem(String string, boolean empty){
        super.updateItem(string, empty);
        if (string != null){
            ChatListDataCell chatListDataCell = new ChatListDataCell();
            chatListDataCell.setInfo(string);
            setGraphic(chatListDataCell.getHBox());
        }
    }
}
