package Controllers.view.program.message.contact;

import model.shared.Message.Contact;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.shared.Message.ChatLists;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ContactListEditor implements Initializable {
    private AnchorPane anchorPane;
    private ChatLists chatLists;
    private Contact contact;
    private ArrayList<Node> previousWindow = new ArrayList<>();

    @FXML
    private TextField contactRenameTextField;

    @FXML
    private Text contactName;

    @FXML
    private ListView userContactView;
    private Set<String> userSet = new LinkedHashSet<String>();
    ObservableList observableUserList = FXCollections.observableArrayList();

    public ContactListEditor(AnchorPane anchorPane, ChatLists chatLists, Contact contact) {
        this.anchorPane = anchorPane;
        previousWindow.addAll(anchorPane.getChildren());
        this.chatLists = chatLists;
        this.contact = contact;
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/message/contact/contactList.fxml"));
                loader.setController(this);
                Parent root = loader.load();
                //Scene scene = new Scene(root);
                this.anchorPane.getChildren().setAll(root);
            } catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    @FXML
    void renameContactName(ActionEvent event){
        if(!contactRenameTextField.getText().isEmpty()){
            contactName.setText(contactRenameTextField.getText());
            contact.setName(contactRenameTextField.getText());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (contact.getName().equals("")){
            contactName.setText("new contact");
        } else {
            contactName.setText(contact.getName());
        }
        showList();
    }

    private void showList() {
        userSet.clear();
        for (String user : chatLists.getLists()){
            userSet.add(user);
        }
        observableUserList.setAll(userSet);
        userContactView.setItems(observableUserList);
        userContactView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell call(ListView listView) {
                return new ContactListView(chatLists, contact);
            }
        });
    }
}
