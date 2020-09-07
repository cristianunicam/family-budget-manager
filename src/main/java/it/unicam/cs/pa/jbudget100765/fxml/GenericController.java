package it.unicam.cs.pa.jbudget100765.fxml;

import it.unicam.cs.pa.jbudget100765.components.tag.Tag;
import it.unicam.cs.pa.jbudget100765.components.transaction.Transaction;
import it.unicam.cs.pa.jbudget100765.components.movement.Movement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.controlsfx.control.table.TableRowExpanderColumn.TableRowDataFeatures;

/**
 * Classe generica per metodi di supporto ai controller
 *
 * @param <T> tipo da salvare per visualizzare una tabella espandibile
 */
public class GenericController<T> implements GenericControllerInterface<T>{
    private static final Logger logger = LoggerFactory.getLogger(GenericController.class);
    private final Class<T> type;

    public GenericController(Class<T> type){
        this.type = type;
    }

    public GenericController(){
        this.type = null;
    }

    @Override
    public Node loadFxml(FXMLLoader fxmlLoader){
        try{
            return (Node)fxmlLoader.load();
        }catch (IOException e){
            logger.error("File loading error!");
        }
        return null;
    }

    @Override
    public Node showTags(TableRowDataFeatures<T> param) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ButtonType.SHOWTAG.getFileName()));
        Node node = loadFxml(fxmlLoader);
        List<Tag> tagList = new ArrayList<>();

        if(type == null){
            logger.error("Tipo dell'oggetto passato errato");
            return null;
        } else if(type.equals(Transaction.class)){
            Transaction t = (Transaction) param.getValue();
            tagList = t.getTags();
        }else if(type.equals(Movement.class)) {
            Movement m = (Movement) param.getValue();
            tagList = m.getTags();
        }

        TagController tagController = fxmlLoader.getController();
        tagController.setTagList(tagList);
        return node;
    }
}
