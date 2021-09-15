package Controllers.view.program.profile;

import Controllers.ImageConverter;
import Controllers.Network.Request.ObjectRequest;
import Controllers.Network.Request.ObjectType;
import Controllers.Network.Request.ToUserRequest;
import Controllers.Network.Request.UserTypeRequest;
import Controllers.Network.RequestHandler;
import Controllers.view.ProgramController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.App;
import model.shared.ContactList;
import model.shared.PublicInformation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    private static PublicInformation info = new PublicInformation();
    private int currentImage = 0;

    private AnchorPane anchorPane;

    @FXML
    ImageView profileImage;

    @FXML
    Label nameLabel;

    @FXML
    Label userNameLabel;

    @FXML
    Label lastSeenLabel;

    @FXML
    Label bioLabel;

    public ProfileController(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/profile/profile.fxml"));
                Parent root = loader.load();
                this.anchorPane.getChildren().setAll((AnchorPane)(root));
            } catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    public static void setInfo(PublicInformation info) {
        ProfileController.info = info;
    }

    public ProfileController() {
    }

    @FXML
    void seeBigger(MouseEvent event){
        Stage stage = new Stage();
        ImageView imageView = new ImageView(profileImage.getImage());
        imageView.setFitWidth(600);
        imageView.setFitHeight(600);
        imageView.setPreserveRatio(false);
        AnchorPane anchorPane = new AnchorPane(imageView);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.setTitle("Profile Image");
        Image appIcon = new Image("/resources/images/icons/appIcon.png");
        stage.getIcons().add(appIcon);
        stage.show();
    }

    @FXML
    void follow (ActionEvent event){
       RequestHandler.getInstance().sendRequest(new ToUserRequest(info.getUsername(), UserTypeRequest.FOLLOW));


    }

    @FXML
    void unFollow (ActionEvent event){
        RequestHandler.getInstance().sendRequest(new ToUserRequest(info.getUsername(), UserTypeRequest.UNFOLLOW));
    }

    @FXML
    void block (ActionEvent event){
        RequestHandler.getInstance().sendRequest(new ToUserRequest(info.getUsername(), UserTypeRequest.BLOCK));
    }

    @FXML
    void unBlock (ActionEvent event){
        RequestHandler.getInstance().sendRequest(new ToUserRequest(info.getUsername(), UserTypeRequest.UNBLOCK));
    }

    @FXML
    void report (ActionEvent event){
        RequestHandler.getInstance().sendRequest(new ToUserRequest(info.getUsername(), UserTypeRequest.REPORT));
    }

    @FXML
    void remove (ActionEvent event){
        RequestHandler.getInstance().sendRequest(new ToUserRequest(info.getUsername(), UserTypeRequest.REMOVE));
    }

    @FXML
    void next (ActionEvent event){
        if(info.getProfileImage().size() > (currentImage+1)){
            currentImage++;
            setProfileImage();
        }
    }

    @FXML
    void previous (ActionEvent event){
        if(currentImage > 0){
            currentImage--;
            setProfileImage();
        }
    }

    @FXML
    void  sendMessage(ActionEvent event){
        App.getInstance().getChatLists().goToFrame(ObjectType.CHAT_LIST_SELECTED_USER);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userNameLabel.setText(info.getUsername());
        nameLabel.setText(info.getName());
        lastSeenLabel.setText(info.getLastSeen());
        bioLabel.setText(info.getBio());
        setProfileImage();
    }

    void setProfileImage() {
        Image image;
        if(info.getProfileImage().size() > 0){
            image = null;
            try {
                image = ImageConverter.toImage(info.getProfileImage().get(currentImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            image = new Image("/resources/images/noProfile.png");
        }
        profileImage.setImage(image);
        profileImage.setPreserveRatio(false);
    }

    public static PublicInformation getInfo() {
        return info;
    }
}
