package it.unicam.cs.pa.jbudget100765.fxml;

import it.unicam.cs.pa.jbudget100765.components.BasicStructure;
import it.unicam.cs.pa.jbudget100765.components.movement.Movement;
import it.unicam.cs.pa.jbudget100765.components.movement.MovementType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.table.TableRowExpanderColumn;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Form che gestisce la visualizzazione dei Movement all'interno di una tabella
 */
public class MovementController implements Initializable {
    private List<Movement> movements = new ArrayList<>();
    @FXML private TableView<Movement> tableView = new TableView<>();

    @FXML private TableColumn<BasicStructure, Integer> movementId = new TableColumn<>();
    @FXML private TableColumn<BasicStructure, Date> movementDate = new TableColumn<>();
    @FXML private TableColumn<BasicStructure, Double> movementAmount = new TableColumn<>();
    @FXML private TableColumn<BasicStructure, String> movementDescription = new TableColumn<>();
    @FXML private TableColumn<BasicStructure, MovementType> movementType = new TableColumn<>();

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(()-> {
            TableRowExpanderColumn<Movement> tagRow = new TableRowExpanderColumn<>(new GenericController<>(Movement.class)::showTags);
            movementId.setCellValueFactory(new PropertyValueFactory<>("id"));
            movementType.setCellValueFactory(new PropertyValueFactory<>("type"));
            movementDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            movementAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            movementDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

            tagRow.setText("Movement Tags");
            tableView.getColumns().add(0,tagRow);
            tableView.setItems(getObservableMovements());
        });
    }

    /**
     * Permette il salvataggio della lista dei movimenti di una data transizione
     *
     * @param m lista movimenti
     */
    public void setMovements(List<Movement> m){
        this.movements.addAll(m);
    }

    /**
     * Ritorna un ObservableList della lista dei movimenti salvati
     *
     * @return ObservableList movimenti
     */
    public ObservableList<Movement> getObservableMovements(){
        return FXCollections.observableList(
                this.movements
        );
    }
}
