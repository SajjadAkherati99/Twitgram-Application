package Controllers.view.program.personal;

import Controllers.ImageConverter;
import Controllers.Network.Request.NewUserInformationRequest;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.App;
import model.shared.OfflineTasks;
import model.shared.UserInformation;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class EditController implements Initializable {
    private AnchorPane anchorPane;
    private int currentImage = 0;
    private List<String> profileImages = new ArrayList<>();
    private UserInformation user;

    @FXML
    ImageView profileImage;

    @FXML
    Label nameLabel;

    @FXML
    Label usernameLabel;

    @FXML
    Label birthdayLabel;

    @FXML
    Label emailLabel;

    @FXML
    Label phoneNumberLabel;

    @FXML
    Label bioLabel;

    public EditController(){
    }

    public EditController(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/personal/edit.fxml"));
                Parent root = loader.load();
                this.anchorPane.getChildren().setAll((AnchorPane)(root));
            } catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    @FXML
    void deleteImage(ActionEvent event){
        if(profileImages.size() > 0){
            if(profileImages.size() == 1){
                profileImages.remove(0);
                Image image = new Image("/resources/images/noProfile.png");
                profileImage.setImage(image);
                profileImage.setFitWidth(350);
                profileImage.setFitHeight(350);
                profileImage.setPreserveRatio(false);
            } else{
                profileImages.remove(currentImage);
                if(currentImage > 0)
                    currentImage--;
                showImage();
            }
        }
    }

    @FXML
    void insertImage(ActionEvent event) throws MalformedURLException {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = chooser.showOpenDialog(null);

        if(returnValue == JFileChooser.APPROVE_OPTION){
            File file = new File(chooser.getSelectedFile().getAbsolutePath());
            String localURL = file.toURI().toURL().toString();
            profileImage.setImage(new Image(localURL));
            profileImage.setFitWidth(350);
            profileImage.setFitHeight(350);
            profileImage.setPreserveRatio(false);
            profileImages.add(ImageConverter.toString(file));
            currentImage++;
        }
    }

    @FXML
    void nextImage(ActionEvent event){
        if(profileImages.size() > (currentImage+1)){
            currentImage++;
            showImage();
        }
    }

    @FXML
    void previousImage(ActionEvent event){
        if(currentImage > 0){
            currentImage--;
            showImage();
        }
    }

    private void showImage() {
        try {
            Image image = ImageConverter.toImage(profileImages.get(currentImage));
            profileImage.setImage(image);
            profileImage.setFitWidth(350);
            profileImage.setFitHeight(350);
            profileImage.setPreserveRatio(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editName(ActionEvent event){
        String newName = editWindow("edit name");
        if (!newName.equals("")) {
            user.setName(newName);
            nameLabel.setText(newName);
        }
    }

    @FXML
    void editUsername(ActionEvent event){
        String newUserName = editWindow("edit username");
        if (!newUserName.equals("")) {
            user.setUsername(newUserName);
            usernameLabel.setText(newUserName);
        }
    }

    @FXML
    void editBirthDay(ActionEvent event){
        String newBirthDay = editWindow("edit birthday");
        if (!newBirthDay.equals("")) {
            user.setBirthday(newBirthDay);
            birthdayLabel.setText(newBirthDay);
        }
    }

    @FXML
    void editEmail(ActionEvent event){
        String newGmail = editWindow("edit Gmail");
        if (!newGmail.equals("")) {
            user.setEmail(newGmail);
            emailLabel.setText(newGmail);
        }
    }

    @FXML
    void editPhoneNumber(ActionEvent event){
        String newPhoneNumber = editWindow("edit phone number");
        user.setPhoneNumber(newPhoneNumber);
        phoneNumberLabel.setText(newPhoneNumber);
    }

    @FXML
    void editBio(ActionEvent event){
        String newBio = editWindow("edit bio");
        user.setBio(newBio);
        bioLabel.setText(newBio);
    }

    @FXML
    void update(ActionEvent event){
        if(ProgramController.isConnected()){
            RequestHandler.getInstance().sendRequest(new NewUserInformationRequest(user));
        } else{
            OfflineTasks.getInstance().setNewInformation(user);
            OfflineTasks.getInstance().setOldUsername(App.getInstance().getUsername());
        }
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

    @FXML
    void seeBiggerProfile(MouseEvent event){
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
    public void setAll(){
        user = App.getInstance().getUserInformation();
        nameLabel.setText(App.getInstance().getUserInformation().getName());
        usernameLabel.setText(App.getInstance().getUserInformation().getUsername());
        birthdayLabel.setText(App.getInstance().getUserInformation().getBirthday());
        emailLabel.setText(App.getInstance().getUserInformation().getEmail());
        phoneNumberLabel.setText(App.getInstance().getUserInformation().getPhoneNumber());
        bioLabel.setText(App.getInstance().getUserInformation().getBio());
        profileImages = App.getInstance().getUserInformation().getProfileImages();
        if(profileImages.size() == 0){
            Image image = new Image("/resources/images/noProfile.png");
            profileImage.setImage(image);
            profileImage.setFitWidth(350);
            profileImage.setFitHeight(350);
            profileImage.setPreserveRatio(false);
        }
        else {
            showImage();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAll();
    }
}
