package Controllers.view.program.personal.Lists;

import javafx.scene.control.ListCell;

public class ListViewCell extends ListCell<String> {
    @Override
    public void updateItem(String string, boolean empty){
        super.updateItem(string, empty);
        if (string != null){
            ListDataCell listDataCell = new ListDataCell();
            listDataCell.setInfo(string);
            setGraphic(listDataCell.getHBox());
        }
    }
}
