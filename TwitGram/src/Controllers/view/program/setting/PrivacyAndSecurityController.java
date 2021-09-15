package Controllers.view.program.setting;

import Controllers.Network.Request.NewUserInformationRequest;
import Controllers.Network.RequestHandler;
import Controllers.view.ProgramController;
import Controllers.view.StartUpController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.App;
import model.shared.OfflineTasks;
import model.shared.UserInformation;
import model.shared.enums.LastSeenTypes;
import model.shared.enums.PrivacyTypes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrivacyAndSecurityController implements Initializable {
    private AnchorPane anchorPane;
    private UserInformation info;

    @FXML
    MenuButton privacyLabel;

    @FXML
    MenuButton lastSeenLabel;

    @FXML
    MenuButton activeLabel;

    @FXML
    Label passwordLabel;

    public PrivacyAndSecurityController() {
    }

    public PrivacyAndSecurityController(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/setting/privacy.fxml"));
                Parent root = loader.load();
                this.anchorPane.getChildren().setAll((AnchorPane)(root));
            } catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    @FXML
    void setPrivate(ActionEvent event){
        privacyLabel.setText("private");
        info.setPrivacy(PrivacyTypes.PRIVATE);
    }

    @FXML
    void setPublic(ActionEvent event){
        privacyLabel.setText("public");
        info.setPrivacy(PrivacyTypes.PUBLIC);
    }

    @FXML
    void setEverybody(ActionEvent event){
        lastSeenLabel.setText("everybody");
        info.setLastSeen(LastSeenTypes.EVERYBODY);
    }

    @FXML
    void setNobody(ActionEvent event){
        lastSeenLabel.setText("nobody");
        info.setLastSeen(LastSeenTypes.NOBODY);
    }

    @FXML
    void setFollowing(ActionEvent event){
        lastSeenLabel.setText("followings");
        info.setLastSeen(LastSeenTypes.FOLLOWINGS);
    }

    @FXML
    void editPassword(ActionEvent event){
        String newPassword = editWindow("edit Password");
        if (!newPassword.equals("")) {
            info.setEmail(newPassword);
            passwordLabel.setText(newPassword);
        }
    }

    @FXML
    void update(ActionEvent event){
        if(ProgramController.isConnected()){
            RequestHandler.getInstance().sendRequest(new NewUserInformationRequest(info));
        } else{
            OfflineTasks.getInstance().setNewInformation(info);
            OfflineTasks.getInstance().setOldUsername(App.getInstance().getUsername());
        }
        if (!info.isActive()){
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            new StartUpController(stage);
        }
    }

    @FXML
    void activate(ActionEvent event){
        activeLabel.setText("activate");
        info.setActive(true);
    }

    @FXML
    void deactivate(ActionEvent event){
        activeLabel.setText("deactivate");
        info.setActive(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        info = App.getInstance().getUserInformation();
        setPrivacyLabel();
        setLastSeenLabel();
        activeLabel.setText("active");
        passwordLabel.setText(info.getPassword());
    }

    private void setPrivacyLabel() {
        String privacy = "public";
        if(info.getPrivacy().equals(PrivacyTypes.PRIVATE)){
            privacy = "private";
        }
        privacyLabel.setText(privacy);
    }

    private void setLastSeenLabel() {
        String lastSeen = "everybody";
        if(info.getLastSeen().equals(LastSeenTypes.NOBODY)){
            lastSeen = "nobody";
        }else if (info.getLastSeen().equals(LastSeenTypes.FOLLOWINGS)){
            lastSeen = "followings";
        }
        privacyLabel.setText(lastSeen);
    }

    String editWindow(String title){
        Stage stage = new Stage();

        TextField textField = new TextField();
        textField.setPromptText(title);
        textField.setPrefSize(400, 100);

        Scene scene = new Scene(textField);

        stage.setWidth(400);
        stage.setHeight(100);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.setScene(scene);
        Image appIcon = new Image("/resources/images/icons/appIcon.png");
        stage.getIcons().add(appIcon);
        stage.showAndWait();
        return textField.getText();
    }
}
