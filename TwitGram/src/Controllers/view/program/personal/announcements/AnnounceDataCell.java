package Controllers.view.program.personal.announcements;

import Controllers.Network.Request.ToUserRequest;
import Controllers.Network.Request.UserTypeRequest;
import Controllers.Network.RequestHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.io.IOException;

public class AnnounceDataCell {
    @FXML
    HBox hBox;

    @FXML
    TextFlow textFlow;

    @FXML
    MenuButton menuButton;

    Text text;

    public AnnounceDataCell() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/personal/announcements/announceCell.fxml"));
        loader.setController(this);
        try {
            loader.load();
        }catch (IOException e){

        }
    }

    public void setInfo(String string, boolean visible){
        text = new Text(string);
        text.setFill(Color.WHITE);
        textFlow.getChildren().add(text);
        textFlow.setTextAlignment(TextAlignment.CENTER);
        menuButton.setVisible(visible);
    }

    public HBox getHBox() {
        return hBox;
    }

    @FXML
    void accept(ActionEvent event){
        String username = text.getText().substring(1);
        RequestHandler.getInstance().sendRequest(new ToUserRequest(username, UserTypeRequest.ACCEPT));
    }

    @FXML
    void reject(ActionEvent event){
        String username = text.getText().substring(1);
        RequestHandler.getInstance().sendRequest(new ToUserRequest(username, UserTypeRequest.REJECT));
    }

    @FXML
    void rejectAndTell(ActionEvent event){
        String username = text.getText().substring(1);
        RequestHandler.getInstance().sendRequest(new ToUserRequest(username, UserTypeRequest.REJECT_AND_TELL));
    }
}
