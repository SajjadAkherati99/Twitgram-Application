package Controllers.view.program.personal.Lists;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class ListDataCell {

    @FXML
    private HBox hBOX;

    @FXML
    private TextFlow usernameFlow;

    public ListDataCell() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/personal/Lists/listCell.fxml"));
        loader.setController(this);
        try {
            loader.load();
        }catch (IOException e){

        }
    }

    public void setInfo(String string){
        Text text = new Text(string);
        text.setFill(Color.WHITE);
        usernameFlow.getChildren().add(text);
        usernameFlow.setTextAlignment(TextAlignment.CENTER);
    }

    public HBox getHBox() {
        return hBOX;
    }
}
