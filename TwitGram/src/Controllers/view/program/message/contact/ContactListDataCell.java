package Controllers.view.program.message.contact;

import Controllers.ImageConverter;
import Controllers.Network.Request.Chat.ContactsUpdateRequest;
import Controllers.Network.RequestHandler;
import model.App;
import model.shared.Message.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import model.shared.Message.ChatLists;

import java.io.IOException;

public class ContactListDataCell{
    private ChatLists chatLists;
    private Contact contact;

    @FXML
    private StackPane stackPane;

    @FXML
    private ImageView profileImage;

    @FXML
    private Text usernameText;

    @FXML
    private Button addRemoveButton;

    public ContactListDataCell(ChatLists chatLists, Contact contact) {
        this.contact = contact;
        this.chatLists = chatLists;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/message/contact/userListsContact.fxml"));
        loader.setController(this);
        try {
            loader.load();
        }catch (IOException e){

        }
    }

    @FXML
    void addRemove(ActionEvent event){
        if(contact.getUserList().contains(usernameText.getText())){
            contact.getUserList().remove(usernameText.getText());
            addRemoveButton.setText("Add");
        } else{
            contact.getUserList().add(usernameText.getText());
            addRemoveButton.setText("Remove");
        }
        RequestHandler.getInstance().sendRequest(new ContactsUpdateRequest(
                App.getInstance().getUsername(),
                App.getInstance().getContacts()));
    }

    void  setInfo(String username){
        usernameText.setText(username);
        if(chatLists.getMapList().get(username).getProfileImage().size()>0){
            try {
                Image image = ImageConverter.toImage(chatLists.getMapList().get(username).getProfileImage().get(0));
                profileImage.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(contact.getUserList().contains(username)){
            addRemoveButton.setText("Remove");
        }
    }

    public StackPane getStackPane() {
        return stackPane;
    }
}
