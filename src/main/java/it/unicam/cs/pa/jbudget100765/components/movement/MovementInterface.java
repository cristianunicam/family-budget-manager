package it.unicam.cs.pa.jbudget100765.components.movement;

import it.unicam.cs.pa.jbudget100765.components.account.Account;
import it.unicam.cs.pa.jbudget100765.components.transaction.Transaction;

public interface MovementInterface {
    /**
     * Ritorna la descrizione di un oggetto {@code Movement}
     */
    String getDescription();

    /**
     * Ritorna il tipo di movimento
     */
    MovementType getType();

    /**
     * Ritorna le transazione padre di un determinato {@code Movement}
     */
    Transaction getTransaction();

    /**
     * Imposta la transazione padre di un Movement
     *
     * @param t {@code Transaction} da impostare
     */
    public void setTransaction(Transaction t);

    /**
     * Ritorna l'account nel quale andare a sottrarre tale {@code Movement}
     */
    Account getAccount();

    /**
     * Permette di settare l'ammontare del movimento
     *
     * @param amount Ammontare del movimento da voler settare
     */
    void setAmount(final double amount);

    /**
     * Rimuove uno specifico Tag
     *
     * @param tagId {@code int} id del tag da voler rimuovere
     */
    void removeTag(int tagId);

    /**
     * Permette di settare l'Account del {@code Movement}
     *
     * @param account {@code Account} da voler rimuovere
     */
    void setAccount(Account account);
}
