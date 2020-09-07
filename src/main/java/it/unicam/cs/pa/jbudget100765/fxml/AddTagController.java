package it.unicam.cs.pa.jbudget100765.fxml;

import it.unicam.cs.pa.jbudget100765.core.Ledger;
import it.unicam.cs.pa.jbudget100765.components.tag.Tag;
import it.unicam.cs.pa.jbudget100765.components.transaction.Transaction;
import it.unicam.cs.pa.jbudget100765.components.movement.Movement;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Classe che gestisce l'aggiunta di un nuovo Tag
 */
public class AddTagController implements Initializable {
    private List<String> transactionIdList = new ArrayList<>();
    private List<String> movementIdList = new ArrayList<>();
    private List<String> tagIdList = new ArrayList<>();

    @FXML private ComboBox<String> tagId;
    @FXML private ComboBox<String> transactionId;
    @FXML private ComboBox<String> movementId;
    @FXML private TextField nameToInsert;
    @FXML private TextField descriptionToInsert;
    @FXML Label lblStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(()-> {
            transactionId.setItems(
                    FXCollections.observableArrayList(
                            transactionIdList
                    )
            );
            tagId.setItems(
                    FXCollections.observableArrayList(
                            tagIdList
                    )
            );
        });
    }

    @FXML
    public void handleButton() {
        if(transactionId.getValue() == null){
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Inserisci tutti i dati!");
        }else if (nameToInsert.getText().isEmpty() && descriptionToInsert.getText().isEmpty() && tagId.getValue() != null) {
            int idTag = Integer.parseInt(tagId.getValue());
            Tag t = Ledger.getInstance().getTags(tag -> tag.getId() == idTag).get(0);
            addTag(t);
        }else if(!nameToInsert.getText().isEmpty() && !descriptionToInsert.getText().isEmpty() && tagId.getValue() == null){
            Tag tag = Ledger.getInstance().addTag(
                    nameToInsert.getText(), descriptionToInsert.getText()
            );
            addTag(tag);
        }else {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Inserisci tutti i dati!");
        }
    }

    /**
     * Aggiunge un tag in base ai dati che sono stati compilati nel form
     *
     * @param tag tag da voler aggiungere
     */
    private void addTag(final Tag tag){
        Transaction t = Ledger.getInstance().getTransactions(
                transaction -> transaction.getId() == Integer.parseInt(transactionId.getValue())
        ).get(0);
        if(t.getTags(tagT -> tagT.getId() == tag.getId()).size() == 0)
            t.addTag(tag);
        if(movementId.getValue() != null) {
            for (Movement m : t.getMovements()) {
                if (m.getId() == Integer.parseInt(movementId.getValue())) {
                    m.addTag(tag);
                    break;
                }
            }
        }
        lblStatus.setTextFill(Color.GREEN);
        lblStatus.setText("Tag aggiunto!");
    }

    /**
     * Imposta la lista dei id dei Movement una volta che viene
     * scelto l'id della transazione
     */
    @FXML
    public void setMovement(){
        String value = this.transactionId.getValue();

        if(value != null){
            this.movementIdList = new ArrayList<>();

            Ledger.getInstance().getTransactions(
                    t -> t.getId() == Integer.parseInt(value)
            )
                    .get(0)
                    .getMovements()
                    .forEach(
                            movement -> movementIdList.add(
                                    Integer.toString(movement.getId()
                                    )
                            )
                    );
            this.movementId.setItems(FXCollections.observableArrayList(movementIdList));
        }
    }

    /**
     * Imposta la lista degli id delle transazioni
     */
    public void setTransactionId(List<String> transactionId) {
        this.transactionIdList = transactionId;
    }

    /**
     * Imposta la lista degli id dei tag
     */
    public void setTagId(List<String> tagId){
        this.tagIdList = tagId;
    }
}
