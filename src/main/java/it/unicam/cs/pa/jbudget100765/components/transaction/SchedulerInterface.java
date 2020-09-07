package it.unicam.cs.pa.jbudget100765.components.transaction;

import java.util.Date;
import java.util.List;

public interface SchedulerInterface {
    /**
     * Permette l'aggiunta di una transazione nella lista
     */
    void addScheduledTransaction(Transaction t);

    /**
     * Rimuove una transazione dallo scheduler
     *
     * @param t {@code Transaction} transazione da eliminare dallo scheduler
     */
    void removeScheduledTransaction(Transaction t);

    /**
     * Ritorna la lista delle transizioni schedulate in una data data
     *
     * @param date data del quale si vogliono ritornare le transazioni
     * @return Lista di {@code Transaction}
     */
    List<Transaction> getTransactionGivenDate(Date date);

    /**
     * Ritorna la lista delle transazioni schedulate
     *
     * @return Lista di {@code Transaction}
     */
    List<Transaction> getScheduledTransactions();

    List<Transaction> moveIfCompleted();
}
