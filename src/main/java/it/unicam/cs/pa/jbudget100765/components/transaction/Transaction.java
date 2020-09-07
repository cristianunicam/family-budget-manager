package it.unicam.cs.pa.jbudget100765.components.transaction;

import com.google.gson.annotations.Expose;
import it.unicam.cs.pa.jbudget100765.components.BasicStructure;
import it.unicam.cs.pa.jbudget100765.components.tag.Tag;
import it.unicam.cs.pa.jbudget100765.components.movement.Movement;
import it.unicam.cs.pa.jbudget100765.components.movement.MovementAddon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Classe che gestisce le transazioni, questa classe definisce una struttura per il salvataggio
 * e la gestione dei dati di una transazione, dati come lista dei tag, data ed id, vengono salvati
 * in una classe generica in quanto viene condivisa con i tag
 */
public class Transaction extends BasicStructure implements TransactionInterface {
    private static final Logger logger = LoggerFactory.getLogger(Transaction.class);
    @Expose(serialize = false)
    private final List<Movement> movements = new ArrayList<>();

    public Transaction(int id,Date date, List<Tag> tags){
        super(id,tags,date);
        logger.debug("Transaction with id {} created",this.getId());
    }

    @Override
    public void addMovement(Movement movement) {
        logger.debug("Movement with id {} Added",movement.getId());
        this.movements.add(movement);
    }

    @Override
    public void removeMovement(Movement movement) {
        logger.debug("Movement with id {} removed",movement.getId());
        this.movements.remove(movement);
    }

    @Override
    public void removeTag(Tag tagToDelete) {
        logger.debug("Tag with id {} removed",tagToDelete.getId());
        if (getTags().contains(tagToDelete)) {
            getTags().remove(tagToDelete);
            for (Movement mov : movements) {
                mov.removeTag(tagToDelete.getId());
            }
        }
    }

    @Override
    public double getAmount() {
        logger.debug("Amount returned");
        return MovementAddon.applyFunction((x, y) -> x-y,this.movements);
    }

    @Override
    public List<Movement> getMovements(){
        logger.debug("Movement returned");
        return this.movements;
    }

    @Override
    public List<Movement> getMovements(Predicate<Movement> predicate){
        return (predicate == null) ? movements : movements
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Transaction{ ID: " +getId()+
                "date = " + getDate() +
                '}';
    }
}
