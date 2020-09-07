package it.unicam.cs.pa.jbudget100765.components.transaction;

import it.unicam.cs.pa.jbudget100765.components.movement.Movement;
import it.unicam.cs.pa.jbudget100765.components.movement.MovementFactory;
import it.unicam.cs.pa.jbudget100765.components.movement.MovementType;
import it.unicam.cs.pa.jbudget100765.core.Ledger;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
    @Test
    public void testGetTransaction() {
        Transaction t = Ledger.getInstance().addTransaction(
                new TransactionFactory().createTransaction(11,new Date(),null)
        );
        Transaction toCompare = Ledger.getInstance().getTransactions().get(0);

        assertEquals(t,toCompare);
    }

    @Test
    public void testGetAmount(){
        Transaction t = Ledger.getInstance().addTransaction(
                new TransactionFactory().createTransaction(1,new Date(),null)
        );
        Movement m1 = new MovementFactory().createMovement(0,new Date(), MovementType.DEBIT, "",100,t,null);
        Movement m2 = new MovementFactory().createMovement(1,new Date(), MovementType.CREDITS, "",50,t,null);
        t.addMovement(m1);
        t.addMovement(m2);

        assertEquals(t.getAmount(),50,0.001);
    }
}
