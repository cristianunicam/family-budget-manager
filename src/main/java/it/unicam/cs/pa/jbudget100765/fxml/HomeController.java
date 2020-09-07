package it.unicam.cs.pa.jbudget100765.fxml;

import it.unicam.cs.pa.jbudget100765.core.Ledger;
import it.unicam.cs.pa.jbudget100765.components.tag.Tag;
import it.unicam.cs.pa.jbudget100765.components.transaction.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller che gestisce il form principale con i relativi bottoni e la pressione dei tasti
 */
public class HomeController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @FXML private Button viewTransactions;
    @FXML private Button addMovement;
    @FXML private Button addTag;
    @FXML private Button removeTagMovement;
    @FXML private Button showTag;
    @FXML private Button showAccount;

    private static HomeController instance = null;

    public static HomeController getInstance(){
        if(instance == null)
            instance = new HomeController();
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addMovement.setOnAction(e -> handleButtons(ButtonType.ADDMOVEMENT));
        viewTransactions.setOnAction(e -> handleButtons(ButtonType.SHOWTRANSACTIONS));
        addTag.setOnAction(e -> handleButtons(ButtonType.ADDTAG));
        showTag.setOnAction(e -> handleButtons(ButtonType.SHOWTAG));
        removeTagMovement.setOnAction(e -> handleButtons(ButtonType.REMOVETAGMOVEMENT));
        showAccount.setOnAction(e -> handleButtons(ButtonType.SHOWACCOUNT));
    }


    public void handleButtons(ButtonType type){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(type.getFileName()));
        try {
            Parent root = fxmlLoader.load();
            switch (type) {
                case ADDMOVEMENT:
                    setTransactionId(fxmlLoader,AddMovementController.class,getTransactionId());
                    break;
                case SHOWTAG:
                    setTag(fxmlLoader);
                    break;
                case ADDTAG:
                    setTransactionId(fxmlLoader,AddTagController.class,getTransactionId());
                    setTagId(fxmlLoader,getTagId());
                    break;
            }
            showNewStage(root,type.getDescription());
        } catch (IOException | NullPointerException e) {
            System.err.println("Errore nel caricamento del file FXML!");
            System.exit(-1);
        }
    }

    /**
     * Ritorna la lista dei tag salvati nell'applicativo all'interno di una lista di stringhe
     * @return {@code List<String>} lista dei Tag
     */
    private List<String> getTagId(){
        logger.debug("Tag id list of string returned");
        List<String> tagId = new ArrayList<>();
        for(Tag t : Ledger.getInstance().getTags())
            tagId.add(Integer.toString(t.getId()));
        return tagId;
    }

    /**
     * Imposta la lista dei tag del controller AddTag
     */
    private void setTagId(FXMLLoader fxmlLoader , List<String> listToSet){
        logger.debug("Tag id list setted for class AddTagController");
        AddTagController controller = fxmlLoader.getController();
        controller.setTagId(listToSet);
    }

    /**
     * Setta i tag del TagController
     */
    private void setTag(FXMLLoader fxmlLoader){
        logger.debug("Tag list setted for class TagController");
        TagController tagController = fxmlLoader.getController();
        tagController.setTagList(Ledger.getInstance().getTags());
    }

    /**
     * Ritorna una lista contente gli ID delle transazioni presenti
     *
     * @return {@code List<String>} lista contente gli id
     */
    private List<String> getTransactionId(){
        logger.debug("Transaction id list returned");
        List<String> transactionId = new ArrayList<>();
        for(Transaction t : Ledger.getInstance().getTransactions())
            transactionId.add(Integer.toString(t.getId()));
        return transactionId;
    }

    /**
     * Imposta la lista degli id delle transazioni di una classe controller generica
     *
     * @param classController controller del quale impostare la lista delle transazioni
     * @param listToSet Lista contente gli id da impostare
     * @param <T> Classe del controller
     */
    private <T> void setTransactionId(FXMLLoader fxmlLoader, Class<T> classController,List<String> listToSet) throws NullPointerException{
        logger.debug("Transactio ID list setted");
        T controller = fxmlLoader.getController();
        if(classController.equals(AddTagController.class)){
            AddTagController atController = (AddTagController)controller;
            atController.setTransactionId(listToSet);
        }else if(classController.equals(AddMovementController.class)){
            AddMovementController amController = (AddMovementController)controller;
            amController.setTransactionId(listToSet);
        }else
            throw new NullPointerException("Classe del controller errata!");
    }

    /**
     * Avvia un nuovo Stage dato il Parent ed il titolo del nuovo form
     */
    private void showNewStage(Parent root , String title){
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
}