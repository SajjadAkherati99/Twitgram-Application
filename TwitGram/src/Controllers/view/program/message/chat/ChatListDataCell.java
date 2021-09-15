package Controllers.view.program.message.chat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class ChatListDataCell {
    @FXML
    HBox hBox;

    @FXML
    ImageView profileImage;

    @FXML
    TextFlow usernameFlow;

    @FXML
    TextFlow messageFlow;

    @FXML
    TextFlow dateFlow;

    public ChatListDataCell() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/message/chat/chatList.fxml"));
        loader.setController(this);
        try {
            loader.load();
        }catch (IOException e){

        }
    }

    public void setInfo(String string){
        Text text = new Text(string);
        messageFlow.getChildren().add(text);
        messageFlow.setTextAlignment(TextAlignment.CENTER);

        Text text1 = new Text("you");
        usernameFlow.getChildren().add(text1);
        usernameFlow.setTextAlignment(TextAlignment.LEFT);
    }

    public HBox getHBox() {
        return hBox;
    }
}
