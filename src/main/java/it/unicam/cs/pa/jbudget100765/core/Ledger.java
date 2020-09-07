package it.unicam.cs.pa.jbudget100765.core;

import it.unicam.cs.pa.jbudget100765.components.account.Account;
import it.unicam.cs.pa.jbudget100765.components.account.AccountFactory;
import it.unicam.cs.pa.jbudget100765.components.account.AccountType;
import it.unicam.cs.pa.jbudget100765.components.tag.Tag;
import it.unicam.cs.pa.jbudget100765.components.transaction.Scheduler;
import it.unicam.cs.pa.jbudget100765.components.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Classe principale che si occupa del salvataggio e gestione dei dati dell'applicativo
 */
public class Ledger implements LedgerInterface{
    private static final Logger logger = LoggerFactory.getLogger(Ledger.class);
    private static Ledger instance = null;
    private final List<Account> accounts;
    private final List<Transaction> transactions;
    private final List<Tag> tags;

    private Ledger(List<Tag> tags,List<Transaction> transactions, List<Account> accounts){
        this.tags = tags;
        this.transactions = transactions;
        this.accounts = accounts;
    }

    public static Ledger getInstance(){
        logger.debug("Ledger instance returned");
        if(instance == null){
            instance = new Ledger(new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
        }
        return instance;
    }

    @Override
    public List<Account> getAccounts() {
        logger.debug("Account lists returned");
        return accounts;
    }

    @Override
    public List<Account> getAccounts(Predicate<Account> predicate){
        return (predicate == null) ? accounts : accounts
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> returnUpdatedTransactionList(Predicate<Transaction> predicate){
        checkScheduledTransaction();
        return (predicate == null) ? transactions : transactions
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * Controlla se vi sono transazioni schedulate da dover rimuovere dallo scheduler
     **/
    private void checkScheduledTransaction(){
        transactions.addAll(
                Scheduler.getInstance().moveIfCompleted()
        );
    }

    @Override
    public List<Tag> getTags() {
        logger.debug("Tags list returned");
        return tags;
    }

    public List<Tag> getTags(Predicate<Tag> predicate){
        logger.debug("Tag list parsed through predicate returned");
        return (predicate == null) ? tags : tags
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public Transaction addTransaction(Transaction t) {
        Date dataOra = new Date();
        if(t.getDate().after(dataOra))
            addScheduledTransaction(t);
        else
            addNonScheduledTransaction(t);
        return t;
    }

    private void addScheduledTransaction(Transaction t) {
        logger.debug("Scheduled Transaction added");
        Scheduler.getInstance().addScheduledTransaction(t);
    }

    private void addNonScheduledTransaction(Transaction t){
        logger.debug("Non-Scheduled Transaction added");
        transactions.add(t);
    }

    @Override
    public Account addAccount(int id, AccountType type, String name, String description, double opening) {
        logger.debug("Account {} added",name);
        Account newAccount = new AccountFactory().createAccount(id,type,name,description,opening);
        accounts.add(newAccount);
        return newAccount;
    }

    @Override
    public void addAccount(Account a){
        logger.debug("Account {} added",a.getName());
        this.accounts.add(a);
    }

    @Override
    public Tag addTag(String name, String description) {
        logger.debug("Tag {} added",name);
        Tag newTag = (new Tag(name,description,getIdTag()));
        this.tags.add(newTag);
        return newTag;
    }

    @Override
    public int getIdTag(){
        logger.debug("First non used tag id returned");
        int result = this.tags
                .stream()
                .mapToInt(Tag::getId)
                .max().orElse(0);

        if(result == 0)
            return 0;
        else return result+1;
    }

    @Override
    public int getIdTransaction(){
        logger.debug("First non used Transaction id returned");
        int result = this.transactions
                .stream()
                .mapToInt(Transaction::getId)
                .max()
                .orElse(0);

        if(result == 0)
            return 0;
        else return result+1;

    }

    @Override
    public void removeTag(Tag t) {
        logger.debug("Tag with id {} removed",t.getId());
        try {
            if (tags.contains(t)) {
                tags.remove(t);
            } else
                throw new NoSuchElementException("Errore il tag non esiste");
        }catch (NoSuchElementException e){
            logger.error("Non existing tag error");
            System.err.println(e.getMessage());
            return;
        }
        for(Transaction tr : transactions){
            tr.removeTag(t);
        }
    }

    @Override
    public void removeTransaction(Transaction t){
        this.transactions.remove(t);
    }

    @Override
    public void addTag(Tag tag) {
        for(Tag t : this.tags){
            if(t.getId() == tag.getId())
                return;
        }
        logger.debug("Tag with id {} added",tag.getId());
        this.tags.add(tag);
    }
}
