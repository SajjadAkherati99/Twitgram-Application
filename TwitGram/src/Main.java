import Controllers.view.StartUpController;
import model.App;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        new StartUpController(primaryStage);
        StartUpController.getInstance().getStage().show();
    }


    public static void main(String[] args) {
        App app = App.getInstance();
        launch(args);
    }
}
