package it.unicam.cs.pa.jbudget100765.fxml;

import it.unicam.cs.pa.jbudget100765.components.account.Account;
import it.unicam.cs.pa.jbudget100765.core.Ledger;
import it.unicam.cs.pa.jbudget100765.components.transaction.Transaction;
import it.unicam.cs.pa.jbudget100765.components.transaction.TransactionFactory;
import it.unicam.cs.pa.jbudget100765.components.movement.Movement;
import it.unicam.cs.pa.jbudget100765.components.movement.MovementFactory;
import it.unicam.cs.pa.jbudget100765.components.movement.MovementType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * Controller che gestisce l'aggiunta di un nuovo Movement
 */
public class AddMovementController implements Initializable {
    private List<String> transactionId = new ArrayList<>();
    ObservableList<String> movementTypeString = FXCollections.observableArrayList("CREDITS","DEBITS");

    @FXML private Button buttonAdd;
    @FXML private ComboBox<String> transactionIdToInsert;
    @FXML private ComboBox<String> accountIdToInsert;
    @FXML private ComboBox<String> movementTypeToInsert;
    @FXML private DatePicker movementDateToInsert;
    @FXML private TextField amountToInsert;
    @FXML private TextField descriptionToInsert;
    @FXML Label lblStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(()-> {
            dataConverter();
            movementTypeToInsert.setValue("CREDITS");
            movementTypeToInsert.setItems(movementTypeString);
            accountIdToInsert.setItems(FXCollections.observableArrayList(
                    setAccountId()
            ));
            transactionIdToInsert.setItems(getTransactionIdCollection());
        });
    }

    /**
     * Salva una lista di stringhe degli id degli Account presenti nell'applicativo
     *
     * @return {@code List<String>} lista degli id
     */
    private List<String> setAccountId(){
        List<String> intList = new ArrayList<>();
        for(Account a : Ledger.getInstance().getAccounts())
            intList.add(Integer.toString(a.getId()));
        return intList;
    }

    /**
     * Imposta la lista di id di transazioni
     *
     * @param transactionId lista di id delle transazioni da voler impostare
     */
    public void setTransactionId(List<String> transactionId){
        this.transactionId = transactionId;
    }

    /**
     * Ritorna una {@code ObservableList<String>} lista contenente id di transazioni
     * @return
     */
    public ObservableList<String> getTransactionIdCollection(){
        List<String> temp = transactionId;
        temp.add("NEW Transaction");
        return FXCollections.observableArrayList(transactionId);
    }

    /**
     * Cambia il formato di una data formattandola da:
     *
     *       dd-MM-yyyy      a     yyyy-MM-dd
     */
    private void dataConverter(){
        String pattern = "yyyy-MM-dd";
        movementDateToInsert.setPromptText(pattern.toLowerCase());
        movementDateToInsert.setConverter(new StringConverter<>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else
                    return "";
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else
                    return null;
            }
        });
    }

    @FXML
    private void handleButtonAction(ActionEvent event){
        if (transactionIdToInsert.getValue() == null ||
                movementDateToInsert.getValue() == null ||
                amountToInsert.getText().isEmpty() ||
                descriptionToInsert.getText().isEmpty() ||
                accountIdToInsert.getValue() == null) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Inserisci tutti i dati!");
        } else {
            addMovement();
        }
    }

    /**
     * Prende i dati inseriti nel form e crea un nuovo oggetto {@code Movement}
     */
    private void addMovement(){
        Transaction t = getTransactionWhereToAdd();
        Account a = getAccountFromForm();
        int movementId = 0;

        if(t.getMovements().size() > 0)
            movementId = t.getMovements().stream()
                .mapToInt(Movement::getId)
                .max().orElse(0)+1;
        Movement m = new MovementFactory().createMovement(
                movementId,
                parseGivenDate(),
                parseGivenType(),
                descriptionToInsert.getText(),
                Double.parseDouble(amountToInsert.getText()),
                t,
                a
        );
        t.addMovement(m);
        a.addMovement(m);
        lblStatus.setTextFill(Color.GREEN);
        lblStatus.setText("Il movimento e' stato inserito!");
    }

    /**
     * Ritorna un oggetto di tipo Account dato un id inserito nel form
     */
    private Account getAccountFromForm(){
        return Ledger.getInstance().getAccounts(
                    account -> account.getId() == Integer.parseInt(
                            accountIdToInsert.getValue()
                    )
                ).get(0);
    }

    /**
     * Ritorna la transazione dove dover aggiungere il Movement,
     * se l'oggetto selezionato nella combobox è l'ultimo, allora creo una nuova transazione
     */
    private Transaction getTransactionWhereToAdd(){
        if(!transactionIdToInsert.getValue().equals(
                transactionIdToInsert.getItems().get(
                        transactionIdToInsert.getItems().size()-1)
                )
        ){
            Predicate<Transaction> predicate = transaction -> transaction.getId() == Integer.parseInt(transactionIdToInsert.getValue());
            return Ledger.getInstance().getTransactions(predicate).get(0);
        }else{
            Transaction t = new TransactionFactory().createTransaction(Ledger.getInstance().getIdTransaction(),parseGivenDate(),null);
            Ledger.getInstance().addTransaction(t);
            return t;
        }
    }

    /**
     * Permette di formattare la data inserita nel form
     */
    private Date parseGivenDate(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(movementDateToInsert.getValue().toString());
        }catch (ParseException e){
            System.err.println("Errore nel parse della data");
            System.exit(-1);
        }
        return null;
    }

    /**
     * Effettua il parse del MovementType inserito e ritorna il risultato
     */
    private MovementType parseGivenType(){
        if(movementTypeToInsert.getValue().isEmpty())
            return null;
        else if (movementTypeToInsert.getValue().equals(MovementType.CREDITS.toString()))
            return MovementType.CREDITS;
        else
            return MovementType.DEBIT;
    }
}
