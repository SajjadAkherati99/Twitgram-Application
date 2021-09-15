package Controllers.view.program.message.contact;

import model.shared.Message.Contact;
import javafx.scene.control.ListCell;
import model.shared.Message.ChatLists;

public class ContactListView extends ListCell<String> {
    private  ChatLists chatLists;
    private Contact contact;

    public ContactListView(ChatLists chatLists, Contact contact) {
        this.chatLists = chatLists;
        this.contact = contact;
    }

    @Override
    public void updateItem(String string, boolean empty){
        super.updateItem(string, empty);
        if (string != null){
            ContactListDataCell contactListDataCell= new ContactListDataCell(chatLists, contact);
            contactListDataCell.setInfo(string);
            setGraphic(contactListDataCell.getStackPane());
        }
    }

}
