package Controllers.view.program.message.chat;

import Controllers.ImageConverter;
import Controllers.Network.Request.Chat.MessageChatSendRequest;
import Controllers.Network.Request.SearchRequest;
import Controllers.Network.RequestHandler;
import Controllers.view.program.message.bubble.BubbleSpec;
import Controllers.view.program.message.bubble.BubbledLabel;
import Controllers.view.program.message.contact.MessageContactController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import model.App;
import model.shared.Message.ChatLists;
import model.shared.Message.Message;
import model.shared.PublicInformation;

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

public class MessageChatController implements Initializable,UserSelector {

    private static MessageChatController messageChatController;
    private AnchorPane anchorPane;
    private ChatLists chatLists;
    private Image chatImage = null;
    private String chatImagStringFile;
    private PublicInformation info = new PublicInformation();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");

    @FXML
    private ListView userList;
    private Set<String> userSet = new LinkedHashSet<String>();
    ObservableList observableUserList = FXCollections.observableArrayList();

    @FXML
    AnchorPane userProfileViewer;

    @FXML
    ListView chatPane;
    private Set<String> chatSet = new LinkedHashSet<String>();
    ObservableList observableChatList = FXCollections.observableArrayList();


    @FXML
    ScrollPane scrollPaneListView;

    @FXML
    TextArea messageBox;

    @FXML
    Button buttonSend;

    @FXML
    Button insertButton;

    @FXML
    ImageView userProfileImage;

    @FXML
    Text usernameLabel;

    public MessageChatController() {
    }

    public MessageChatController(AnchorPane anchorPane, ChatLists chatLists) {
        this.anchorPane = anchorPane;
        this.chatLists = chatLists;
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/message/chat/messageChat.fxml"));
                loader.setController(this);
                Parent root = loader.load();
                //Scene scene = new Scene(root);
                this.anchorPane.getChildren().setAll(root);
            } catch (IOException e){
                e.printStackTrace();
            }
        });
        messageChatController = this;
    }

    public static MessageChatController getInstance(){
        return messageChatController;
    }

    void setUserList(){
        userSet.clear();
        for (String user : chatLists.getLists()){
            userSet.add(user);
        }
        observableUserList.setAll(userSet);
        userList.setItems(observableUserList);
        userList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell call(ListView listView) {
                return new UserListView(chatLists);
            }
        });
    }

    @FXML
    void seeProfile(MouseEvent event){
        RequestHandler.getInstance().sendRequest(new SearchRequest(usernameLabel.getText()));
    }

    @FXML
    void sendMethod(KeyEvent event){

    }

    @FXML
    void sendButtonAction(ActionEvent event){
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

            RequestHandler.getInstance().sendRequest(new MessageChatSendRequest(
                    App.getInstance().getUsername(),
                    usernameLabel.getText(),
                    message));
            chatLists.update(message, info);
        }
        chatImage = null;
        messageBox.clear();
    }

    @FXML
    void seeChats(ActionEvent event){

    }

    @FXML
    void seeGroups(ActionEvent event){

    }

    @FXML
    void seeContacts(ActionEvent event){
        new MessageContactController(anchorPane, chatLists);
    }

    @FXML
    void createNewGroup(ActionEvent event){

    }

    @FXML
    void createNewContact(ActionEvent event){

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUserList();
        userProfileViewer.setVisible(false);
        scrollPaneListView.setVisible(false);
        messageBox.setVisible(false);
        buttonSend.setVisible(false);
        insertButton.setVisible(false);
    }

    @Override
    public void userSelected(String username) {
        chatImage = null;
        userProfileViewer.setVisible(true);
        scrollPaneListView.setVisible(true);
        messageBox.setVisible(true);
        buttonSend.setVisible(true);
        insertButton.setVisible(true);

        info = chatLists.getMapList().get(username);
        if(info.getProfileImage().size() > 0){
            try {
                Image image = ImageConverter.toImage(info.getProfileImage().get(0));
                userProfileImage.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        usernameLabel.setText(username);

        try {
            showAllMessage(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showAllMessage(String username) throws IOException {
        ArrayList<Message> messages = chatLists.getChatsLists().get(username).getMessages();
        chatPane.getItems().clear();
        if(messages != null) {
            for (Message message : messages) {
                Text messageUsername = new Text("you");
                if (message.getUsername().equals(username)) {
                    messageUsername = new Text(message.getUsername());
                }
                messageUsername.setFill(Color.WHITE);
                messageUsername.setFont(Font.font(20));
                TextFlow userFlow = new TextFlow(messageUsername);

                Text messageText = new Text(message.getMsg());
                messageText.setFill(Color.WHITE);
                TextFlow messageFlow = new TextFlow(messageText);

                Text date = new Text(message.getDateAndTime());
                date.setFont(Font.font(10));
                date.setFill(Color.GRAY);
                TextFlow dateFlow = new TextFlow(date);
                dateFlow.setTextAlignment(TextAlignment.RIGHT);

                BubbledLabel bl6 = new BubbledLabel();

                if (message.getImageStr() != null) {
                    Image image = ImageConverter.toImage(message.getImageStr());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(350);
                    imageView.setFitWidth(350);
                    bl6.getChildren().addAll(userFlow, imageView, messageFlow, dateFlow);
                } else {
                    bl6.getChildren().addAll(userFlow, messageFlow, dateFlow);
                }
                if (message.getUsername().equals(username)) {
                    bl6.setBackground(new Background(new BackgroundFill(Color.BROWN, null, null)));
                    HBox x = new HBox();
                    bl6.setBubbleSpec(BubbleSpec.FACE_LEFT_BOTTOM);
                    x.getChildren().addAll(bl6);
                    chatPane.getItems().add(x);
                } else {
                    bl6.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, null, null)));
                    HBox x = new HBox();
                    x.setAlignment(Pos.TOP_RIGHT);
                    bl6.setBubbleSpec(BubbleSpec.FACE_RIGHT_BOTTOM);
                    x.getChildren().addAll(bl6);
                    chatPane.getItems().add(x);
                }
            }
        }
    }
}
