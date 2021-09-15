package Controllers.view.program.connection;

import Controllers.Network.RequestHandler;
import Controllers.view.ProgramController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionController implements Initializable {

    @FXML
    TextField ipField;

    @FXML
    TextField portField;

    public ConnectionController() {
    }

    public ConnectionController(Stage newStage) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/connection/connection.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            Image icon = new Image(String.valueOf(getClass().getResource("/resources/images/icons/appIcon.png")));
//            Stage newStage = new Stage();
            newStage.getIcons().add(icon);
            newStage.setResizable(false);
            newStage.setTitle("TwitGram");
            newStage.setScene(scene);
            newStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void connect (ActionEvent event){
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        String ip = ipField.getText();
        String portStr = portField.getText();
        ip = setIp(ip);
        int port =  setPort(portStr);
        RequestHandler.Connect(ip, port);
    }

    private int setPort(String portStr) {
        if(portStr.equals("")){
            return 5050;
        } else{
            return Integer.parseInt(portStr);
        }
    }

    private String setIp(String ip) {
        if(ip.equals("")){
            return "localhost";
        }
        return ip;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
