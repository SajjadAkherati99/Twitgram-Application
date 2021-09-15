package Controllers.view.program.explorer;

import Controllers.Network.Request.SearchRequest;
import Controllers.Network.RequestHandler;
import Controllers.Network.Response.ResponseMessage;
import Controllers.view.PopAlert;
import Controllers.view.ProgramController;
import Controllers.view.ResponseResult;
import Controllers.view.program.profile.ProfileController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.App;
import model.shared.PublicInformation;

import java.io.DataInput;
import java.io.IOException;

public class ExplorerController implements ResponseResult {
    private static ExplorerController instance = null;
    private AnchorPane anchorPane;

    @FXML
    TextField searchedUser;

    public ExplorerController() {
    }

    public ExplorerController(AnchorPane anchorPane, Object object) {
        this.anchorPane = anchorPane;
    }

    public static ExplorerController getInstance(){
        if(instance == null){
            instance = new ExplorerController(ProgramController.getInstance().getAnchorPane2(), new Object());
        }
        return instance;
    }



    public ExplorerController(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/explorer/explorer.fxml"));
                Parent root = loader.load();
                this.anchorPane.getChildren().setAll(root);
            } catch (IOException e){
                e.printStackTrace();
            }
        });
        instance = this;
    }

    @FXML
    void search(ActionEvent event){
        if(ProgramController.isConnected()){
            String username = searchedUser.getText();
            if (username.equals(App.getInstance().getUsername())){
                PopAlert.popupAlert(Alert.AlertType.ERROR, "enter different username", "explorer error");
            }else {
                RequestHandler.getInstance().sendRequest(new SearchRequest(username));
            }
        }
        else {
            PopAlert.popupAlert(Alert.AlertType.ERROR, "you are offline!!!", "connection error");
        }
    }

    @Override
    public void showResponse(String response) {

    }

    @Override
    public <T> void showResponse(String header, T information) {
        switch (ResponseMessage.valueOf(header)){
            case USER_NOT_FOUND -> {
                Platform.runLater(() -> {
                    PopAlert.popupAlert(Alert.AlertType.ERROR, "this user does not exist!!!", "Error");
                });
            }
            case OK -> {
                ProfileController.setInfo((PublicInformation) information);
                new ProfileController(anchorPane);
            }
        }
    }
}
