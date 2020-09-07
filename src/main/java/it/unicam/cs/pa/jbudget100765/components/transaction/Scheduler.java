package it.unicam.cs.pa.jbudget100765.components.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Questa classe serve per schedulare delle transazioni ad una certa data.
 *
 * @author Cristian Verdecchia
 */
public class Scheduler implements SchedulerInterface {
    private static Scheduler instance = null;
    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

    //Ritorna true se la data della transazione passata è successiva alla data in quell'esatto momento
    private final Predicate<Transaction> filterExecutedTransaction = transaction -> transaction.getDate().before(new Date());

    private final List<Transaction> removedTransaction;
    private final List<Transaction> scheduledTransactions;

    public Scheduler(){
        removedTransaction = new ArrayList<>();
        scheduledTransactions = new ArrayList<>();
    }

    public static Scheduler getInstance(){
        logger.debug("Scheduler instance returned");
        if(instance == null)
            instance = new Scheduler();
        return instance;
    }

    @Override
    public List<Transaction> getTransactionGivenDate(Date date){
        logger.debug("List of transaction in a given date returned");
        return this.scheduledTransactions
                .stream()
                .filter(transaction -> transaction.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getScheduledTransactions(){
        logger.debug("List of transaction returned");
        return this.scheduledTransactions;
    }

    @Override
    public void addScheduledTransaction(Transaction t){
        logger.debug("Scheduled transaction added to the scheduler");
        this.scheduledTransactions.add(t);
    }

    @Override
    public void removeScheduledTransaction(Transaction t){
        logger.debug("Scheduled transaction removed from the scheduler");
        this.scheduledTransactions.remove(t);
    }

    @Override
    public List<Transaction> moveIfCompleted(){
        removedTransaction.clear();
        logger.debug("Completed scheduled transaction moved to non-scheduled Transaction");
         this.scheduledTransactions
                 .stream()
                 .filter(filterExecutedTransaction)
                 .forEach(t -> {
                     this.removedTransaction.add(t);
                     this.removeScheduledTransaction(t);
                 });
         removedTransaction.forEach(this::setAccountGivenTransaction);
         return removedTransaction;
    }

    private void setAccountGivenTransaction(Transaction t){
        t.getMovements().forEach(movement -> movement.getAccount().addMovement(movement));
    }
}
