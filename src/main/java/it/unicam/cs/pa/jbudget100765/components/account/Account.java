package it.unicam.cs.pa.jbudget100765.components.account;

import com.google.gson.annotations.Expose;
import it.unicam.cs.pa.jbudget100765.components.movement.Movement;
import it.unicam.cs.pa.jbudget100765.components.movement.MovementAddon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Classe che definisce una struttura per gli Account, tali account contengono dei Movement
 * dai quali poi viene generato il balance per un determinato Account
 */
public class Account implements AccountInterface{
    private static final Logger logger = LoggerFactory.getLogger(Account.class);
    private final int id;
    private final String name;
    private final String description;
    private final AccountType type;
    private final double openingBalance;
    private double balance;

    @Expose(serialize = false)
    private transient final List<Movement> movements = new ArrayList<>();

    public Account(int id,AccountType type, String name, String description,final double opening) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.openingBalance = opening;
        this.balance = opening;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        logger.debug("Account name {} returned",this.name);
        return this.name;
    }

    @Override
    public String getDescription() {
        logger.debug("Account description {} returned",this.description);
        return this.description;
    }

    @Override
    public double getOpeningBalance() {
        logger.debug("Account opening balance {} returned",this.openingBalance);
        return this.openingBalance;
    }

    @Override
    public double getBalance() {
        switch (this.type){
            case ASSETS:
                this.balance = openingBalance + getBalanceFunction((credit,debit) -> credit-debit);
                break;
            case LIABILITIES:
                this.balance = openingBalance + getBalanceFunction((credit,debit) -> debit-credit);
                break;
        }
        logger.debug("Account current balance {} returned",this.balance);
        return this.balance;
    }

    /**
     * Applica una data BiFunction alla lista di movimenti presente in un dato Account
     *
     * @param function {@code BiFunction} da voler applicare
     * @return {@code double} risultato
     */
    private double getBalanceFunction(BiFunction<Double,Double,Double> function){
        return MovementAddon.applyFunction(function,this.movements);
    }

    @Override
    public AccountType getType() {
        logger.debug("Account type {} returned",this.type);
        return type;
    }

    @Override
    public List<Movement> getMovements() {
        logger.debug("Account movements list returned");
        return this.movements;
    }

    @Override
    public List<Movement> getMovements(Predicate<Movement> condition) {
        logger.debug("Account movements list given predicate returned");
        List<Movement> testedCondition = new ArrayList<>();
        for(Movement movement : this.movements){
            if(condition.test(movement))
                testedCondition.add(movement);
        }
        return testedCondition;
    }

    @Override
    public void addMovement(Movement movement){
        logger.debug("Movement added to the Account");
        this.movements.add(movement);
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Account))
            return false;

        Account that = (Account) other;

        // Custom equality check here.
        return this.type.equals(that.type)
                && this.name.equals(that.name)
                && this.description.equals(that.description)
                && this.id == that.id
                && this.openingBalance == that.openingBalance
                && this.balance == that.balance
                && this.movements.size() == that.movements.size();
    }

    /**
     * Retrieve the hashCode for this object
     * 37 is from Monte Carlo tests, it's described in Joshua Bloch's "Effective Java" book
     *
     * @return <code>int</code> object hashCode
     */
    @Override
    public int hashCode(){
        int hashCode = 1;

        hashCode = hashCode * 37 + this.type.hashCode();
        hashCode = hashCode * 37 + this.name.hashCode();
        hashCode = hashCode * 37 + this.description.hashCode();
        hashCode = hashCode + this.id + (int) this.openingBalance + (int) this.balance + this.movements.size();

        return hashCode;
    }
}
