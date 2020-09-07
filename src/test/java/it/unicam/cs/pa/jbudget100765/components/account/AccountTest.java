package it.unicam.cs.pa.jbudget100765.components.account;

import it.unicam.cs.pa.jbudget100765.components.movement.Movement;
import it.unicam.cs.pa.jbudget100765.components.movement.MovementFactory;
import it.unicam.cs.pa.jbudget100765.components.movement.MovementType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class AccountTest {
    @Test
    public void accountTestBalance (){
        //ASSETS
        Account a = new AccountFactory().createAccount(0,AccountType.ASSETS,"","",100);
        Movement m1 = new MovementFactory().createMovement(0,new Date(), MovementType.DEBIT,"MOVIMENTO DI TEST",50,null,a);
        Movement m2 = new MovementFactory().createMovement(0,new Date(), MovementType.CREDITS,"MOVIMENTO DI TEST",100,null,a);
        a.addMovement(m1);
        a.addMovement(m2);
        Assert.assertEquals(50,a.getBalance(),0.001);
        //LIABILITIES
        Account a1 = new AccountFactory().createAccount(0,AccountType.LIABILITIES,"","",100);
        Movement m3 = new MovementFactory().createMovement(0,new Date(), MovementType.DEBIT,"MOVIMENTO DI TEST",50,null,a1);
        Movement m4 = new MovementFactory().createMovement(0,new Date(), MovementType.CREDITS,"MOVIMENTO DI TEST",100,null,a1);
        a1.addMovement(m3);
        a1.addMovement(m4);
        Assert.assertEquals(150,a1.getBalance(),0.001);
    }
}
