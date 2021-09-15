package Controllers.view;

import Controllers.Network.Request.DeleteAccountRequest;
import Controllers.Network.Request.InformationRequest;
import Controllers.Network.Request.ObjectRequest;
import Controllers.Network.Request.ObjectType;
import Controllers.Network.RequestHandler;
import Controllers.view.program.connection.ConnectionController;
import Controllers.view.program.explorer.ExplorerController;
import Controllers.view.program.personal.EditController;
import Controllers.view.program.setting.PrivacyAndSecurityController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.App;

import java.util.LinkedList;

public class ProgramController {
    private static ProgramController program;
    private Stage stage;
    private static boolean connected = true;
    Window currentWindow;
    private LinkedList<LinkedList<Node>> previousWindow = new LinkedList<>();

    @FXML
    AnchorPane anchorPane2;

    @FXML
    MenuItem connect;

    public ProgramController(){
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static ProgramController getInstance() {
        return program;
    }

    public static void setInstance(ProgramController program) {
        ProgramController.program = program;
    }

    public void goToAppropriateWindow(){
        if(currentWindow == Window.INFORMATION) {
            EditController editController = new EditController(anchorPane2);
        } else {
            PrivacyAndSecurityController privacyAndSecurityController = new PrivacyAndSecurityController(anchorPane2);
        }
    }

    public static boolean isConnected() {
        return connected;
    }

    public static void setConnected(boolean connected) {
        ProgramController.connected = connected;
    }

    public AnchorPane getAnchorPane2() {
        return anchorPane2;
    }

    public void setAnchorPane2(AnchorPane anchorPane2) {
        this.anchorPane2 = anchorPane2;
    }

    @FXML
    void explore(ActionEvent event){
        LinkedList list = new LinkedList();
        list.addAll(anchorPane2.getChildren());
        previousWindow.add(list);
        setInstance(this);
        new ExplorerController(anchorPane2);
    }

    @FXML
    void showLists(ActionEvent event){
        LinkedList list = new LinkedList();
        list.addAll(anchorPane2.getChildren());
        previousWindow.add(list);
        setInstance(this);
        if (RequestHandler.getInstance() == null) {
            PopAlert.popupAlert(Alert.AlertType.ERROR, "your have disconnected.", "Error");
        } else {
            RequestHandler.getInstance().sendRequest(new ObjectRequest(App.getInstance().getUsername(), ObjectType.LISTS));
        }
    }

    @FXML
    void edit(ActionEvent event){
        currentWindow = Window.INFORMATION;
        LinkedList list = new LinkedList();
        list.addAll(anchorPane2.getChildren());
        previousWindow.add(list);
        setInstance(this);
        if (RequestHandler.getInstance() == null) {
            PopAlert.popupAlert(Alert.AlertType.ERROR, "your have disconnected.", "Error");
        } else {
            RequestHandler.getInstance().sendRequest(new InformationRequest(App.getInstance().getUsername()));
        }
    }

    @FXML
    void displayTwits(ActionEvent event){
        System.out.println("display twits");
    }

    @FXML
    void  connectDisConnect(ActionEvent event){
        if (connected){
            connected = false;
            RequestHandler.getInstance().closeSocket();
            connect.setText("connect");
        } else{
            new ConnectionController(new Stage());
            connected = true;
            connect.setText("disconnect");
        }
    }

    @FXML
    void info(ActionEvent event){
        LinkedList list = new LinkedList();
        list.addAll(anchorPane2.getChildren());
        previousWindow.add(list);
        currentWindow = Window.INFORMATION;
        setInstance(this);
        if (RequestHandler.getInstance() == null) {
            PopAlert.popupAlert(Alert.AlertType.ERROR, "your have disconnected.", "Error");
        } else {
            RequestHandler.getInstance().sendRequest(new InformationRequest(App.getInstance().getUsername()));
        }
    }

    @FXML
    void privacy(ActionEvent event){
        LinkedList list = new LinkedList();
        list.addAll(anchorPane2.getChildren());
        previousWindow.add(list);
        currentWindow = Window.PRIVACY;
        setInstance(this);
        if (RequestHandler.getInstance() == null) {
            PopAlert.popupAlert(Alert.AlertType.ERROR, "your have disconnected.", "Error");
        } else {
            RequestHandler.getInstance().sendRequest(new InformationRequest(App.getInstance().getUsername()));
        }
    }

    @FXML
    void deleteAccount(ActionEvent event){
        RequestHandler.getInstance().sendRequest(new DeleteAccountRequest(App.getInstance().getUsername()));
        PopAlert.popupAlert(Alert.AlertType.INFORMATION, "your account deleted successfully", "information");
        Stage stage = (Stage)((MenuItem)event.getTarget()).getParentPopup().getOwnerWindow();
        new StartUpController(stage);
    }

    @FXML
    void logOut(ActionEvent event){
        Stage stage = (Stage)((MenuItem)event.getTarget()).getParentPopup().getOwnerWindow();
        App.setInstance();
        new StartUpController(stage);
    }

    @FXML
    void back(ActionEvent event){
        if(previousWindow.size()>1) {
            anchorPane2.getChildren().clear();
            anchorPane2.getChildren().addAll(previousWindow.removeLast().remove());
        }
    }

    @FXML
    void announce(ActionEvent event){
        LinkedList list = new LinkedList();
        list.addAll(anchorPane2.getChildren());
        previousWindow.add(list);
        setInstance(this);
        if (RequestHandler.getInstance() == null) {
            PopAlert.popupAlert(Alert.AlertType.ERROR, "your have disconnected.", "Error");
        } else {
            RequestHandler.getInstance().sendRequest(new ObjectRequest(App.getInstance().getUsername(), ObjectType.ANNOUNCEMENTS));
        }
    }

    @FXML
    void seeMessagePanel(ActionEvent event){
        LinkedList list = new LinkedList();
        list.addAll(anchorPane2.getChildren());
        previousWindow.add(list);
        setInstance(this);
        App.getInstance().getChatLists().goToFrame(ObjectType.CHAT_LIST);
    }

}
