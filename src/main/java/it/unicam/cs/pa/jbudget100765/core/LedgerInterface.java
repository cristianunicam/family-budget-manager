package it.unicam.cs.pa.jbudget100765.core;

import it.unicam.cs.pa.jbudget100765.components.account.Account;
import it.unicam.cs.pa.jbudget100765.components.account.AccountType;
import it.unicam.cs.pa.jbudget100765.components.tag.Tag;
import it.unicam.cs.pa.jbudget100765.components.transaction.Transaction;

import java.util.List;
import java.util.function.Predicate;

/**
 * Classe principale che gestisce l'applicativo
 *
 * @author Cristian Verdecchia
 */
public interface LedgerInterface {

    /**
     * Permette di ottenere le transazioni dell'applicativo
     *
     * @return la lista delle transazioni
     */
    default List<Transaction> getTransactions() {
        return returnUpdatedTransactionList(null);
    }

    /**
     * Ritorna la lista delle transazioni dopo essere state filtrate tramite il predicate
     *
     * @param predicate filtro per le Transazioni
     * @return {@code List<Transaction>} lista di transazioni che rispettano il criterio specificato nel predicate
     */
    default List<Transaction> getTransactions(Predicate<Transaction> predicate) {
        return returnUpdatedTransactionList(predicate);
    }

    /**
     * Controlla che le transazioni schedulate siano aggiornate e ritorna la lista di transazioni
     *
     * @param predicate da utilizzare per filtrare le transazioni
     * @return Lista di transazioni
     */
    List<Transaction> returnUpdatedTransactionList(Predicate<Transaction> predicate);
    /**
     * Permette di ottenere tutti gli account del nostro applicativo
     *
     * @return Lista degli account
     */
    List<Account> getAccounts();

    /**
     * Ritorna una lista di transazioni che rispettano un determinato predicate
     *
     * @param predicate {@code Predicate} che deve rispettare
     * @return {@code List<Account>} lista degli account
     */
    List<Account> getAccounts(Predicate<Account> predicate);

        /**
         * Permette di aggiungere una transazione
         *
         * @param transaction Transazione da aggiungere alla lista
         * @return
         */
    Transaction addTransaction(Transaction transaction);

    /**
     * Permette di ottenere la lista dei tag dell'applicativo
     *
     * @return lista dei tag
     */
    List<Tag> getTags();

    /**
     * Permette l'aggiunta di un account
     *
     * @param type          Tipo dell'account
     * @param name          Nome del nuovo account
     * @param description   Descrizione del nuovo account
     * @param opening       Liquidità del conto al momento della sua aggiunta
     * @return  L'account creato
     */
    Account addAccount(int id,AccountType type, String name, String description, double opening);

    /**
     * Permette l'aggiunta di un account alla lista del ledger
     *
     * @param a Account da aggiungere
     */
    void addAccount(Account a);

    /**
     * Permette l'aggiunta di tag
     *
     * @param name          Nome del tag da aggiungere
     * @param description   Descrizione del tag da aggiungere
     * @return  L'oggetto Tag aggiunto
     */
    Tag addTag(String name, String description);

    /**
     * Permette l'aggiunta di un Tag alla lista dei Tag del Ledger
     *
     * @param tag da voler aggiungere
     */
    void addTag(Tag tag);

    /**
     * Permette di ottenere il prossimo ID non utilizzato di un tag
     *
     * @return {@code int} id Tag
     */
    int getIdTag();

    /**
     * Permette di ottenere il prossimo ID non utilizzato per le transazioni
     *
     * @return {@code int} id Tag
     */
    int getIdTransaction();
    /**
     * Permette la cancellazione di un tag dall'applicativo
     *
     * @param t tag che si vuole eliminare
     */
    void removeTag(Tag t);

    /**
     * Permette l'eliminazione di una transazione dal Ledger
     *
     * @param t transazione da voler eliminare
     */
    void removeTransaction(Transaction t);
}
