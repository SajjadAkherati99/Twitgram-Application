package Controllers.view.program.personal.Lists;

import Controllers.Network.Request.SearchRequest;
import Controllers.Network.RequestHandler;
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
import model.shared.ContactList;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ListController implements Initializable {
    private AnchorPane anchorPane;
    private ContactList contactList;

    @FXML
    private ListView lists;
    private Set<String> stringSet = new LinkedHashSet<>();
    ObservableList observableList = FXCollections.observableArrayList();

    public ListController() {
    }

    public ListController(AnchorPane anchorPane, ContactList contactList) {
        this.anchorPane = anchorPane;
        this.contactList = contactList;
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/program/personal/Lists/lists.fxml"));
                loader.setController(this);
                Parent root = loader.load();
                //Scene scene = new Scene(root);
                this.anchorPane.getChildren().setAll((AnchorPane)root);
            } catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    public void setLists(ArrayList<String> list){
        stringSet.clear();
        for (String s:list){
            stringSet.add(s);
        }
        observableList.setAll(stringSet);
        lists.setItems(observableList);
        lists.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell call(ListView listView) {
                return new ListViewCell();
            }
        });
        lists.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Object object = lists.getSelectionModel().getSelectedItem();
                if (object != null) {
                    String username = object.toString();
                    RequestHandler.getInstance().sendRequest(new SearchRequest(username));
                }
            }
        });
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLists(contactList.getFollowings());
    }


    @FXML
    void seeFollowing(ActionEvent event){
        setLists(contactList.getFollowings());
    }

    @FXML
    void seeFollower(ActionEvent event){
        setLists(contactList.getFollowers());
    }

    @FXML
    void seeBlackList(ActionEvent event){
        setLists(contactList.getBlocked());
    }
}
