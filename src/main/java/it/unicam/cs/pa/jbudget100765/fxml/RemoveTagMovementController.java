package it.unicam.cs.pa.jbudget100765.fxml;

import it.unicam.cs.pa.jbudget100765.core.Ledger;
import it.unicam.cs.pa.jbudget100765.components.tag.Tag;
import it.unicam.cs.pa.jbudget100765.components.transaction.Transaction;
import it.unicam.cs.pa.jbudget100765.components.movement.Movement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.TOMATO;

/**
 * Controller utilizzato per la rimozione dei Tag e dei Movement
 */
public class RemoveTagMovementController implements Initializable {
    private static Logger logger = LoggerFactory.getLogger(RemoveTagMovementController.class);

    private ObservableList<String> transactionIdList = FXCollections.observableArrayList();
    private ObservableList<String> movementIdList = FXCollections.observableArrayList();
    private ObservableList<String> tagIdList = FXCollections.observableArrayList();

    @FXML private ComboBox<String> transactionId;
    @FXML private ComboBox<String> movementId;
    @FXML private ComboBox<String> tagId;
    @FXML Label lblStatus;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tagId.setItems(tagIdList);
        for(Tag t : Ledger.getInstance().getTags())
            this.tagIdList.add(Integer.toString(t.getId()));

        transactionId.setItems(transactionIdList);
        for(Transaction t : Ledger.getInstance().getTransactions())
            this.transactionIdList.add(Integer.toString(t.getId()));

        movementId.setItems(movementIdList);
    }

    /**
     * Una volta selezionato un id della transazione, aggiorna gli id dei Movement
     */
    @FXML
    public void choosedTransactionId(){
        Transaction transaction = getTransactionGivenSelectedId();

        tagIdList.clear();
        for(Tag t : transaction.getTags())
            tagIdList.add(Integer.toString(t.getId()));

        movementIdList.clear();
        for(Movement m : transaction.getMovements())
            movementIdList.add(Integer.toString(m.getId()));
    }

    /**
     * Una volta selezionato il movimento (Tramite ComboBox) aggiorna gli id dei Tag
     */
    @FXML
    public void choosedMovementId(){
        Movement m = getTransactionGivenSelectedId()
                        .getMovements(movement -> movement.getId() == Integer.parseInt(movementId.getValue())).get(0);

        tagIdList.clear();
        for(Tag t : m.getTags())
            tagIdList.add(Integer.toString(t.getId()));
    }

    /**
     * Ritorna la transazione dato l'id inserito nel form
     */
    private Transaction getTransactionGivenSelectedId(){
        int idChoosedTransaction = Integer.parseInt(transactionId.getValue());
        return Ledger.getInstance().getTransactions(t -> t.getId() == idChoosedTransaction)
                .get(0);
    }

    @FXML
    public void handleButtonRemoveTag(){
        if(checkIfPresent(tagId))
            return;
        int choosedTagId = Integer.parseInt(this.tagId.getValue());
        Tag tagToRemove = getTagGivenTagId(choosedTagId);

        if(transactionId.getValue() != null){   //Caso in cui è stato inserito l'id della transazione
            Transaction transaction = getTransactionGivenTransactionId();
            if(movementId.getValue() != null) { //Caso in cui è stato inserito l'id del movimento
                logger.debug("RIMUOVO TAG");
                getMovementGivenId(transaction).removeTag(tagToRemove.getId());
            }else
                transaction.removeTag(tagToRemove);
        }else
            Ledger.getInstance().removeTag(tagToRemove);
        labelUpdate(GREEN,"Tag Rimosso!");
    }

    @FXML
    public void handleButtonRemoveMovement(){
        if(checkIfPresent(transactionId))
            return;
        Transaction transaction = getTransactionGivenTransactionId();

        if(movementId.getValue() == null) {
            Ledger.getInstance().removeTransaction(transaction);
            labelUpdate(GREEN,"Transazione Rimossa!");
            return;
        }

        Movement m = getMovementGivenId(transaction);
        transaction.removeMovement(m);
        labelUpdate(GREEN,"Movimento Rimosso!");
    }

    /**
     * Controlla se un determinato id è stato inserito nella ComboBox,
     * altrimenti aggiorna la label
     *
     * @param toCheck ComboBox da controllare se è stato inserito un qualunque valore
     * @return {@code true} se non è presente, {@code false} altrimenti
     */
    private boolean checkIfPresent(ComboBox<String> toCheck){
        if(toCheck.getValue() == null) { //Caso in cui non siano stati inseriti i dati
            logger.error("No data inserted");
            labelUpdate(TOMATO,"Inserisci tutti i dati!");
            return true;
        }
        return false;
    }

    /**
     * Aggiorna la label dell'applicativo
     *
     * @param labelColor colore del label da impostare
     * @param toPrint Testo della label da impostare
     */
    private void labelUpdate(Color labelColor, String toPrint){
        lblStatus.setText(toPrint);
        if (labelColor.equals(TOMATO)) {
            lblStatus.setTextFill(TOMATO);
        }else if (labelColor.equals(GREEN)){
            lblStatus.setTextFill(GREEN);
        }
    }

    private Movement getMovementGivenId(Transaction transaction){
        return transaction.getMovements(
                    movement -> movement.getId() == Integer.parseInt(movementId.getValue())
            ).get(0);
    }

    private Tag getTagGivenTagId(int choosedId){
        return Ledger.getInstance().getTags()
                .stream()
                .filter(
                        tag -> tag.getId() == choosedId
                ).findFirst()
                .orElseThrow(NullPointerException::new);
    }

    private Transaction getTransactionGivenTransactionId(){
        return Ledger.getInstance().getTransactions(
                t -> t.getId() == Integer.parseInt(transactionId.getValue())
        ).get(0);
    }
}
