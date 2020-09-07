package it.unicam.cs.pa.jbudget100765.components.movement;

import it.unicam.cs.pa.jbudget100765.components.transaction.Transaction;
import it.unicam.cs.pa.jbudget100765.components.transaction.TransactionFactory;
import it.unicam.cs.pa.jbudget100765.core.Ledger;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;


public class MovementTest {
    @Test
    public void testGetMovements() {
        Transaction t = Ledger.getInstance().addTransaction(
                new TransactionFactory().createTransaction(0,new Date(),null)
        );
        Movement m = new MovementFactory().createMovement(0,new Date(), MovementType.CREDITS,"MOVIMENTO DI TEST",111.11,t,null);
        t.addMovement(m);
        assertTrue(
                t.getMovements().contains(m)
        );
    }
}