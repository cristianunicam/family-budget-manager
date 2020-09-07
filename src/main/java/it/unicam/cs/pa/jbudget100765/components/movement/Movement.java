package it.unicam.cs.pa.jbudget100765.components.movement;

import com.google.gson.annotations.Expose;
import it.unicam.cs.pa.jbudget100765.components.account.Account;
import it.unicam.cs.pa.jbudget100765.components.tag.Tag;
import it.unicam.cs.pa.jbudget100765.components.BasicStructure;
import it.unicam.cs.pa.jbudget100765.components.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Classe che gestisce il salvatagggio e l'utilizzo dei dati di un Movimento
 * i dati quali lista di tag, id e data vengono salvati all'interno di una classe generica
 * in quanto la stessa classe può essere condivisa anche con le transazioni
 */
public class Movement extends BasicStructure implements MovementInterface{
    private static final Logger logger = LoggerFactory.getLogger(Movement.class);

    private final String description;
    private final MovementType type;
    private double amount;
    @Expose(serialize = false)
    private Account account;
    @Expose(serialize = false)
    private Transaction transaction;

    public Movement(final int id, final Date date, final MovementType type, final String description, final double amount, Transaction transaction, final Account account){
        super(id,null,date);
        logger.debug("New Movement with id \"{}\" created",id);
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.transaction = transaction;
        this.account = account;
    }

    @Override
    public String getDescription() {
        logger.debug("Movement description of movement with ID \"{}\" returned",getId());
        return this.description;
    }

    @Override
    public MovementType getType() {
        logger.debug("Movement type of movement with ID \"{}\" returned",getId());
        return this.type;
    }

    @Override
    public Transaction getTransaction() {
        logger.debug("Transaction parent of movement with ID \"{}\" returned",getId());
        return transaction;
    }

    @Override
    public void setTransaction(Transaction t){
        logger.debug("Transaction parent of movement with ID \"{}\" setted",getId());
        this.transaction = t;
    }

    @Override
    public Account getAccount() {
        logger.debug("Account of Movement with ID \"{}\" returned",getId());
        return this.account;
    }

    @Override
    public void setAccount(Account account) {
        logger.debug("Account of Movement with ID \"{}\" setted",getId());
        this.account = account;
    }

    @Override
    public double getAmount() {
        logger.debug("Amount of Movement with ID \"{}\" returned",getId());
        return this.amount;
    }

    @Override
    public void setAmount(final double amount) {
        logger.debug("Amount of Movement with ID \"{}\" setted",getId());
        this.amount = amount;
    }

    @Override
    public void removeTag(Tag tagToDelete) {
        logger.debug("Tag with ID \"{}\" removed from Movement with ID \"{}\" and transaction with ID: \"{}\"", tagToDelete.getId(), getId(), this.transaction.getId());
        getTags().remove(tagToDelete);
    }

    @Override
    public void removeTag(int tagId) {
        logger.debug("Tag removed from Movement with ID \"{}\" and transaction with ID: \"{}\"",getId(),this.transaction.getId());
        this.getTags()
                .stream()
                .filter(tag -> tag.getId() == tagId)
                .findFirst()
                .ifPresent(this::removeTag);
    }
}
