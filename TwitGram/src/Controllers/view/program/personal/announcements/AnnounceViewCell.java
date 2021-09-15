package Controllers.view.program.personal.announcements;

import javafx.scene.control.ListCell;

public class AnnounceViewCell extends ListCell<String> {

    AnnounceDataCell announceDataCell;
    boolean visible;

    public AnnounceViewCell(boolean visible) {
        this.visible = visible;
    }

    public void updateItem(String string, boolean empty){
        super.updateItem(string, empty);
        if (string != null){
            announceDataCell = new AnnounceDataCell();
            announceDataCell.setInfo(string, visible);
            setGraphic(announceDataCell.getHBox());
        }
    }
}
