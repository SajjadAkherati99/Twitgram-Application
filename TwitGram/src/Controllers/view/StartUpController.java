package Controllers.view;

import Controllers.Network.Request.SignInRequest;
import Controllers.Network.Request.SignUpRequest;
import Controllers.Network.RequestHandler;
import Controllers.Network.Response.ResponseMessage;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.App;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class StartUpController implements ResponseResult  {

    private static StartUpController startUpController;
    private Stage stage;
    private Scene scene;
    private Parent root;
    ReentrantLock lock = new ReentrantLock();
    Semaphore s = new Semaphore(0);
    boolean close = false;
    Task task;

    @FXML
    TextField nameField;

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    @FXML
    DatePicker birthdayField;

    @FXML
    TextField emailField;

    @FXML
    TextField phoneField;

    @FXML
    TextArea bioField;

    @FXML
    TextField ipField;

    @FXML
    TextField portField;

    public StartUpController() {

    }

    public StartUpController(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/resources/fxml/startup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/resources/styles/startup.css").toExternalForm();
        scene.getStylesheets().add(css);
        Image icon = new Image(String.valueOf(getClass().getResource("/resources/images/icons/appIcon.png")));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setTitle("TwitGram");
        stage.setScene(scene);
        this.stage = stage;
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        startUpController = this;
    }

    @FXML
    void signIn(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/resources/fxml/signIn.fxml"));
        setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        String css = this.getClass().getResource("/resources/styles/signInUp.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void signUp(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/resources/fxml/signUp.fxml"));
        setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        String css = this.getClass().getResource("/resources/styles/signInUp.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void tryLogin(ActionEvent event) throws InterruptedException {
        if (checkEmptyFieldLogin()) {
            if (RequestHandler.getInstance() == null) {
                popupAlert(Alert.AlertType.ERROR, "There is no connection yet!", "Error");
            } else {
                RequestHandler.getInstance().sendRequest(new SignInRequest(usernameField.getText(), passwordField.getText()));
                App.getInstance().setUsername(usernameField.getText());
//                task = new Task<Void>() {
//                    @Override
//                    synchronized protected Void call() throws Exception {
//                        System.out.println("waiting");
//                        wait();
//                        System.out.println("close");
//                        Platform.runLater(() -> {
//                            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
//                        });
//                        return null;
//                    }
//                };
//                new Thread(task).start();
            }
        }
    }

    @FXML
    void tryRegister(ActionEvent event) throws InterruptedException {
        if (checkEmptyFieldRegister()) {
            if (RequestHandler.getInstance() == null) {
                popupAlert(Alert.AlertType.ERROR, "There is no connection yet!", "Error");
            } else {
                RequestHandler.getInstance().sendRequest(new SignUpRequest(
                        nameField.getText(),
                        usernameField.getText(),
                        passwordField.getText(),
                        birthdayField.getValue().toString(),
                        emailField.getText(),
                        phoneField.getText(),
                        bioField.getText()
                ));
                App.getInstance().setUsername(usernameField.getText());
            }
        }
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/resources/fxml/startup.fxml"));
        setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        String css = this.getClass().getResource("/resources/styles/startup.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void connect(ActionEvent event) {
        String ip = getIp();
        int port = getPort();
        if (port > 0){
            RequestHandler.Connect(ip, port);
        }
    }

    private int getPort() {
        String portStr = portField.getText();
        int port = 5050;
        if(portStr.equals("")) {
            port = 5050;
            return port;
        } else {
            try {
                port = Integer.parseInt(portStr);
                return port;
            }catch (Exception e){
                popupAlert(Alert.AlertType.ERROR, "the port number most be an integer!", "Error");
            }
        }
        return -1;
    }

    private String getIp() {
        String ip = ipField.getText();
        if (ip.equals(""))
            ip = "localhost";
        return ip;
    }

    boolean checkEmptyFieldLogin(){
        if (usernameField.getText().equals("")){
            popupAlert(Alert.AlertType.ERROR, "enter your username please!", "Error");
            return false;
        }
        if (passwordField.getText().equals("")){
            popupAlert(Alert.AlertType.ERROR, "enter your password please!", "Error");
            return false;
        }
        return true;
    }

    boolean checkEmptyFieldRegister(){
        if (nameField.getText().equals("")){
            popupAlert(Alert.AlertType.ERROR, "enter a name please!", "Error");
            return false;
        }
        if (usernameField.getText().equals("")){
            popupAlert(Alert.AlertType.ERROR, "enter an username please!", "Error");
            return false;
        }
        if (passwordField.getText().equals("")){
            popupAlert(Alert.AlertType.ERROR, "enter a password please!", "Error");
            return false;
        }
        if (birthdayField.getValue() == null){
            popupAlert(Alert.AlertType.ERROR, "enter your birthday please!", "Error");
            return false;
        }
        if (emailField.getText().equals("")){
            popupAlert(Alert.AlertType.ERROR, "enter an email please!", "Error");
            return false;
        }
        return true;
    }

    public void popupAlert(Alert.AlertType alertType, String msg, String title) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        //alert.initOwner(stage);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.showAndWait();
    }

    public static StartUpController getInstance() {
        if (startUpController == null) {
            startUpController = new StartUpController();
        }
        return startUpController;
    }

    @Override
    public void showResponse(String response) {
        Platform.runLater(() -> {
            switch (ResponseMessage.valueOf(response)) {
                case OK -> {
                    goToProgram();
                    popupAlert(Alert.AlertType.INFORMATION, "Welcome to your account.", "Welcome");
                }
                case USER_NOT_FOUND -> {
                    popupAlert(Alert.AlertType.ERROR, "This user does not exist!!!", "Error");
                }
                case WRONG_PASSWORD -> {
                    popupAlert(Alert.AlertType.ERROR, "You entered wrong password!!!", "Error");
                }
                case USER_EXIST_ERROR -> {
                    popupAlert(Alert.AlertType.ERROR, "Sorry, this username already exist. take another one!!!", "Error");
                }
            }
        });
    }

    @Override
    public void showResponse(String header, Object body) {

    }

    private void goToProgram() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program.fxml"));
            Parent root = loader.load();
            ProgramController programController = (ProgramController) loader.getController();
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/resources/styles/program.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
//            programController.setStage(stage);
//            ProgramController.setInstance(programController);
            stage.show();
        } catch (IOException e) {

        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
