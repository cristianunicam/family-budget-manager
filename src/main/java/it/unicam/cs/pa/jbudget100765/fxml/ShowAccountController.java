package it.unicam.cs.pa.jbudget100765.fxml;

import it.unicam.cs.pa.jbudget100765.components.account.Account;
import it.unicam.cs.pa.jbudget100765.components.account.AccountType;
import it.unicam.cs.pa.jbudget100765.core.Ledger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Visualizza gli Account all'interno di una tabella
 */
public class ShowAccountController implements Initializable {

    @FXML private TableView<Account> accountTableView = new TableView<>();
    @FXML private TableColumn<Account, Integer> idColumn = new TableColumn<>();
    @FXML private TableColumn<Account, String> nameColumn = new TableColumn<>();
    @FXML private TableColumn<Account, String> descriptionColumn = new TableColumn<>();
    @FXML private TableColumn<Account, AccountType> accounTypeColumn = new TableColumn<>();
    @FXML private TableColumn<Account, Double> openingBalanceColumn = new TableColumn<>();
    @FXML private TableColumn<Account, Double> balanceColumn = new TableColumn<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        accounTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        openingBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("openingBalance"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        accountTableView.setItems(getAccounts());
    }

    public ObservableList<Account> getAccounts(){
        return FXCollections.observableList(
                Ledger.getInstance().getAccounts()
        );
    }
}
