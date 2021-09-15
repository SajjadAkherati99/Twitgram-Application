package Controllers.view.program.personal.announcements;

import Controllers.Network.Request.SearchRequest;
import Controllers.Network.RequestHandler;
import Controllers.view.program.personal.Lists.ListViewCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.shared.Announcements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AnnounceController implements Initializable {
    private AnchorPane anchorPane;
    private Announcements announcements;

    @FXML
    private ListView announces;
    private Set<String> stringSet = new LinkedHashSet<>();
    ObservableList observableList = FXCollections.observableArrayList();

    public AnnounceController() {
    }

    public AnnounceController(AnchorPane anchorPane, Announcements announcements) {
        this.anchorPane = anchorPane;
        this.announcements = announcements;
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/personal/announcements/announce.fxml"));
                loader.setController(this);
                Parent root = loader.load();
                //Scene scene = new Scene(root);
                this.anchorPane.getChildren().setAll((AnchorPane)root);
            } catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    public void setLists(ArrayList<String> list, boolean visible){
        stringSet.clear();
        for (String s: list){
            stringSet.add(s);
        }
        observableList.setAll(stringSet);
        announces.setItems(observableList);
        announces.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell call(ListView listView) {
                return new AnnounceViewCell(visible);
            }
        });
        announces.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object object = announces.getSelectionModel().getSelectedItem();
                if (object != null) {
                    String [] strArray = object.toString().split(" ");
                    for (String s: strArray){
                        if (s.startsWith("@")){
                            String username = s.substring(1);
                            RequestHandler.getInstance().sendRequest(new SearchRequest(username));
                            break;
                        }
                    }
                }
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLists(announcements.getSystem(), false);
    }

    @FXML
    void seeSystem(ActionEvent event){
        setLists(announcements.getSystem(), false);
    }

    @FXML
    void seePeopleRequest(ActionEvent event){
        setLists(announcements.getPeopleRequest(), true);
    }

    @FXML
    void seeYourRequest(ActionEvent event){
        setLists(announcements.getClientRequest(), false);
    }
}
