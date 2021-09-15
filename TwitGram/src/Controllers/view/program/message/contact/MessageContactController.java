package Controllers.view.program.message.contact;

import Controllers.ImageConverter;
import Controllers.Network.Request.Chat.MessageChatSendRequest;
import Controllers.Network.RequestHandler;
import Controllers.view.program.message.bubble.BubbleSpec;
import Controllers.view.program.message.bubble.BubbledLabel;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import model.App;
import model.shared.Message.Contact;
import Controllers.view.program.message.chat.MessageChatController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.shared.Message.ChatLists;
import javafx.scene.text.Text;
import model.shared.Message.Message;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class MessageContactController implements Initializable {

    private AnchorPane anchorPane;
    private ChatLists chatLists;
    private ArrayList<Contact> contacts = App.getInstance().getContacts();

    private Contact selectedContact;

    private Image chatImage = null;
    private String chatImagStringFile;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox vBox;

    @FXML
    private AnchorPane contactListViewer;

    @FXML
    private Text contactName;

    @FXML
    private ListView chatPane;

    @FXML
    private TextArea messageBox;

    @FXML
    private Button insertButton;

    @FXML
    private Button buttonSend;

    @FXML
    private ListView contactList;
    private Set<String> contactSet = new LinkedHashSet<String>();
    ObservableList observableContactList = FXCollections.observableArrayList();

    public MessageContactController(AnchorPane anchorPane, ChatLists chatLists) {
        this.anchorPane = anchorPane;
        this.chatLists = chatLists;
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/message/contact/messageContact.fxml"));
                loader.setController(this);
                Parent root = loader.load();
                this.anchorPane.getChildren().setAll(root);
            } catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    @FXML
    void seeContactList(MouseEvent event){
        new ContactListEditor(anchorPane, chatLists, selectedContact);
    }

    @FXML
    void insertImage(ActionEvent event){
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = chooser.showOpenDialog(null);

        if(returnValue == JFileChooser.APPROVE_OPTION) {
            File file = new File(chooser.getSelectedFile().getAbsolutePath());
            String localURL = null;
            try {
                chatImagStringFile = ImageConverter.toString(file);
                localURL = file.toURI().toURL().toString();
                chatImage = new Image(localURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void sendButtonAction(ActionEvent event){
        System.out.println(selectedContact.getUserList());
        String msg = messageBox.getText();
        if (!msg.trim().isEmpty()){
            Message message = new Message(
                    App.getInstance().getUsername(),
                    msg,
                    LocalDateTime.now().format(formatter)
            );

            Text messageUsername = new Text("you");
            messageUsername.setFill(Color.WHITE);
            messageUsername.setFont(Font.font(20));
            TextFlow userFlow = new TextFlow(messageUsername);

            Text messageText = new Text(msg);
            messageText.setFill(Color.WHITE);
            TextFlow messageFlow = new TextFlow(messageText);

            Text date = new Text(message.getDateAndTime());
            date.setFont(Font.font(10));
            date.setFill(Color.GRAY);
            TextFlow dateFlow = new TextFlow(date);
            dateFlow.setTextAlignment(TextAlignment.RIGHT);

            BubbledLabel bl6 = new BubbledLabel();

            if (chatImage != null){
                ImageView imageView = new ImageView(chatImage);
                imageView.setFitHeight(350);
                imageView.setFitWidth(350);
                bl6.getChildren().addAll(userFlow, imageView, messageFlow, dateFlow);
                message.setImageStr(chatImagStringFile);
            }else {
                bl6.getChildren().addAll(userFlow, messageFlow, dateFlow);
            }
            bl6.setBackground(new Background(new BackgroundFill(Color.DARKBLUE,null, null)));
            HBox x = new HBox();
            x.setAlignment(Pos.TOP_RIGHT);
            bl6.setBubbleSpec(BubbleSpec.FACE_RIGHT_BOTTOM);
            x.getChildren().addAll(bl6);
            chatPane.getItems().add(x);

            for (String username:selectedContact.getUserList()) {
                RequestHandler.getInstance().sendRequest(new MessageChatSendRequest(
                        App.getInstance().getUsername(),
                        username,
                        message));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                chatLists.update(message, chatLists.getMapList().get(username));
            }
        }
        chatImage = null;
        messageBox.clear();
    }

    @FXML
    void seeChats(ActionEvent event){
        new MessageChatController(anchorPane, chatLists);
    }

    @FXML
    void seeGroups(ActionEvent event){

    }

    @FXML
    void createNewContact(ActionEvent event){
        Contact contact = new Contact();
        new ContactListEditor(anchorPane, chatLists, contact);
        contacts.add(contact);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vBox.setVisible(false);

        for (Contact contact : contacts) {

            Text text = new Text(contact.getName());
            text.setFill(Color.WHITE);

            contactList.getItems().add(text);
        }
        contactList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedContact = contacts.get(contactList.getSelectionModel().getSelectedIndex());
                contactName.setText(selectedContact.getName());
                vBox.setVisible(true);
            }
        });
    }
}
