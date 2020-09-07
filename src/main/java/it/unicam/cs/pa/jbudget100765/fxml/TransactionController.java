package it.unicam.cs.pa.jbudget100765.fxml;

import it.unicam.cs.pa.jbudget100765.core.Ledger;
import it.unicam.cs.pa.jbudget100765.components.BasicStructure;
import it.unicam.cs.pa.jbudget100765.components.transaction.Transaction;
import it.unicam.cs.pa.jbudget100765.components.movement.Movement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.table.TableRowExpanderColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Classe che visualizza le transazioni all'interno di una tabella,
 * inoltre aggiunge delle tabelle espandibili selezionabili tramite bottone
 * che contengono i movimenti ed i Tag
 */
public class TransactionController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @FXML private Button buttonMovement;
    @FXML private TableView<Transaction> tagTableView = new TableView<>();
    @FXML private StackPane root = new StackPane();
    @FXML private TableView<Transaction> tableView = new TableView<>();
    @FXML private TableColumn<BasicStructure, Integer> idColumn = new TableColumn<>();
    @FXML private TableColumn<BasicStructure, Date> dateTransaction = new TableColumn<>();
    @FXML private TableColumn<BasicStructure, Double> amount = new TableColumn<>();

    /**
     * Inizializza la tabella delle transazioni
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Tabella espandibile di una data transizione
        TableRowExpanderColumn<Transaction> movementRow = new TableRowExpanderColumn<>(this::showMovements);
        //Tabella espandibile dei tag presenti
        TableRowExpanderColumn<Transaction> tagRow = new TableRowExpanderColumn<>(new GenericController<>(Transaction.class)::showTags);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateTransaction.setCellValueFactory(new PropertyValueFactory<>("date"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        movementRow.setText("Movements");
        tableView.getColumns().add(0,movementRow);
        tagRow.setText("Transaction Tags");
        tagTableView.getColumns().add(tagRow);
        tagTableView.setItems(getTransactions());
        tableView.setItems(getTransactions());
    }

    /**
     * Ritorna una tabella contente la lista dei movimenti per una data transazione
     *
     * @param param Transazione scelta
     * @return Node contente la tabella con lista movimenti
     */
     private Node showMovements(TableRowExpanderColumn.TableRowDataFeatures<Transaction> param) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ButtonType.SHOWMOVEMENTS.getFileName()));
        Node node = new GenericController<>(Movement.class).loadFxml(fxmlLoader);
        MovementController movementController = fxmlLoader.getController();
        movementController.setMovements(param.getValue().getMovements());
        return node;
    }

    public ObservableList<Transaction> getTransactions(){
        return FXCollections.observableList(
                Ledger.getInstance().getTransactions()
        );
    }

    @FXML
    private void handleAddMovement(ActionEvent event) {
        HomeController.getInstance().handleButtons(ButtonType.ADDMOVEMENT);
    }
}
