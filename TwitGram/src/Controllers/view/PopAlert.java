package Controllers.view;

import Controllers.Network.Response.ResponseMessage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopAlert implements ResponseResult {

    private static PopAlert popAlert = null;

    public PopAlert() {
    }

    public static void popupAlert(Alert.AlertType alertType, String msg, String title) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        //alert.initOwner(stage);
        alert.initModality(Modality.WINDOW_MODAL);
        Image image = new Image("/resources/images/icons/appIcon.png");
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(image);
        alert.showAndWait();
    }

    public static PopAlert getInstance(){
        if(popAlert == null){
            popAlert = new PopAlert();
        }
        return popAlert;
    }

    @Override
    public void showResponse(String response) {
        Platform.runLater(() -> {
            switch (ResponseMessage.valueOf(response)) {
                case USER_NOT_FOUND -> {
                    popupAlert(Alert.AlertType.ERROR, "This user does not exist!!!", "Error");
                }
                case WRONG_PASSWORD -> {
                    popupAlert(Alert.AlertType.ERROR, "You entered wrong password!!!", "Error");
                }
                case USER_EXIST_ERROR -> {
                    popupAlert(Alert.AlertType.ERROR, "Sorry, this username already exist. take another one!!!", "Error");
                }
                case INFO_CHANGED -> {
                    popupAlert(Alert.AlertType.INFORMATION, "your changes applied successfully", "Information");
                }
                default  -> {
                }
            }
        });
    }

    @Override
    public void showResponse(String header, Object body) {

    }
}
