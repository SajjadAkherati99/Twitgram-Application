package Controllers.view.program.message.chat;

import Controllers.ImageConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.shared.PublicInformation;

import java.io.IOException;

public class UserListDataCell {
    @FXML
    HBox hBox;

    @FXML
    ImageView profileImage;

    @FXML
    Label userLabel;

    public UserListDataCell() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/message/chat/userList.fxml"));
        loader.setController(this);
        try {
            loader.load();
        }catch (IOException e){

        }
    }

    public void setInfo(String string, PublicInformation info){
        userLabel.setText(string);
        if(info.getProfileImage().size() > 0){
            try {
                Image image = ImageConverter.toImage(info.getProfileImage().get(0));
                profileImage.setImage(image);
                profileImage.setPreserveRatio(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public HBox getHBox() {
        return hBox;
    }

    @FXML
    void goToChat(MouseEvent event){
        MessageChatController.getInstance().userSelected(userLabel.getText());
    }
}
