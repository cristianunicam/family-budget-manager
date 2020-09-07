package it.unicam.cs.pa.jbudget100765.fxml;

import it.unicam.cs.pa.jbudget100765.components.tag.Tag;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Visuazlizza i Tag all'interno di una tabella
 */
public class TagController implements Initializable {

    private List<Tag> tagList = new ArrayList<>();
    @FXML private TableView<Tag> tableView = new TableView<>();

    @FXML private TableColumn<Tag, Integer> tagId = new TableColumn<>();
    @FXML private TableColumn<Tag, String> tagName = new TableColumn<>();
    @FXML private TableColumn<Tag, String> tagDescription = new TableColumn<>();

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(()-> {
            tagId.setCellValueFactory(new PropertyValueFactory<>("id"));
            tagName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tagDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

            tableView.setItems(getObservableTags());
        });
    }

    public void setTagList(List<Tag> tagList){
        this.tagList.addAll(tagList);
    }

    private ObservableList<Tag> getObservableTags() {
        return FXCollections.observableList(
                this.tagList
        );
    }
}
