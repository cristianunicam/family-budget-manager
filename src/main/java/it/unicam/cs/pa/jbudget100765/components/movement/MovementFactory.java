package it.unicam.cs.pa.jbudget100765.components.movement;

import it.unicam.cs.pa.jbudget100765.components.account.Account;
import it.unicam.cs.pa.jbudget100765.components.transaction.Transaction;

import java.util.Date;

public class MovementFactory {
    public Movement createMovement(final int id,final Date date,final MovementType movementType,final String description,final double amount,Transaction t, final Account account){
        return new Movement(id,date,movementType,description,amount,t,account);
    }
}
