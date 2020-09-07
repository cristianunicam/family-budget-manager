package it.unicam.cs.pa.jbudget100765.components.transaction;

import it.unicam.cs.pa.jbudget100765.components.tag.Tag;
import it.unicam.cs.pa.jbudget100765.components.movement.Movement;

import java.util.List;
import java.util.function.Predicate;

/**
 * Definisce un'interfaccia per una classe Transaction dove viene generata una
 * transazione contente un MovementStructure ed una lista di movimenti
 *
 * @author Cristian Verdecchia
 */
public interface TransactionInterface {

    /**
     * Permette l'aggiunta di un tag per un dato oggetto Transaction
     *
     * @param tag Tag da voler aggiungere
     */
    void addTag(Tag tag);

    /**
     * Permette di rimuovere un dato tag dalla lista dei tag
     *
     * @param tag Tag da voler rimuovere
     */
    void removeTag(Tag tag);

    /**
     * Permette di aggiungere un Movement alla lista di movimenti di una determinata transazione
     *
     * @param movement {@code Movement} da voler aggiungere
     */
    void addMovement(Movement movement);

    /**
     * Ritorna una lista di movimenti di una data transazione
     *
     * @return {@code List<Movement>}
     */
    List<Movement> getMovements();

    /**
     * Ritorna una lista di movimenti di una data transazione che rispettano il dato predicate
     *
     * @return {@code List<Movement>}
     */
    List<Movement> getMovements(Predicate<Movement> predicate);
    /**
     * Permette di rimuovere un determinato Movimento dalla lista di movimenti di una data transazione
     *
     * @param movement {@code Movement} da voler rimuovere
     */
    void removeMovement(Movement movement);
}
