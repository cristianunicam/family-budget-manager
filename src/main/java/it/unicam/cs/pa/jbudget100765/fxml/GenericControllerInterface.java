package it.unicam.cs.pa.jbudget100765.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.controlsfx.control.table.TableRowExpanderColumn;

public interface GenericControllerInterface<T> {
    /**
     * permette il caricamento di un file fxml gestendo le eccezzioni
     *
     * @param fxmlLoader oggetto FXMLoader da voler caricare
     * @return la view del file caricato
     */
    Node loadFxml(FXMLLoader fxmlLoader);

    /**
     * Permette il caricamento del TagController data una classe generica
     * che può essere Transaction o Movement
     *
     * @param param {@code TableRowDataFeatures} tabella espandibile
     * @return {@code Node} Nodo da voler visualizzare all'interno della tabella espandibile
     */
    Node showTags(TableRowExpanderColumn.TableRowDataFeatures<T> param);
}
