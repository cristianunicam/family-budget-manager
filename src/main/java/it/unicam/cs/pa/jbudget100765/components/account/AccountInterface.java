package it.unicam.cs.pa.jbudget100765.components.account;

import it.unicam.cs.pa.jbudget100765.components.movement.Movement;

import java.util.List;
import java.util.function.Predicate;

public interface AccountInterface {
    /**
     * Ritorna l'ID dell'Account
     *
     * @return <code>int</code> id Account
     */
    int getId();
    /**
     * Ritorna il nome dell'Account
     *
     * @return <code>String</code> nome dell'account
     */
    String getName();

    /**
     * Ritorna la descrizione dell'account
     *
     * @return <code>String</code> descrizione dell'account
     */
    String getDescription();

    /**
     * Ritorna il Balance con il quale è stato aperto il conto
     *
     * @return <code>double</code>
     */
    double getOpeningBalance();

    /**
     * Il budget di un determinato account, viene calcolato in base al tipo di account
     * se l'account è di tipo LIABILITIES, i movimenti di tipo CREDIT, andranno a sottrarsi
     * al balance
     *
     * @return balance calcolato
     */
    double getBalance();

    /**
     * Il tipo di conto
     *
     * @return <code>AccountType</code> può essere LIABILITIES o ASSETS
     */
    AccountType getType();

    /**
     * Ritorna la lista di movimenti associati ad un determinato Account
     *
     * @return <code>List<Movement></code>
     */
    List<Movement> getMovements();

    /**
     * Lista di movimenti associati ad un determinato Account che
     * rispetti il Predicate dato
     *
     * @param predicate filtro per il quale si vogliono ottenere i movimenti
     * @return <code>List<Movement></code>
     */
    List<Movement> getMovements(Predicate<Movement> predicate);

    /**
     * Aggiunge un dato movimento alla lista
     *
     * @param movement <code>Movement da voler aggiungere</code>
     */
    void addMovement(Movement movement);
}
