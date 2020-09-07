package it.unicam.cs.pa.jbudget100765.components.account;

public class AccountFactory {
    public Account createAccount(final int id, final AccountType type, final String name, final String description, final double opening){
        return new Account(id,type,name,description,opening);
    }
}
