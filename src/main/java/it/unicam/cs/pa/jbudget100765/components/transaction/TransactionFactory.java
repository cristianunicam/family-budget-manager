package it.unicam.cs.pa.jbudget100765.components.transaction;

import it.unicam.cs.pa.jbudget100765.components.tag.Tag;

import java.util.Date;
import java.util.List;

public class TransactionFactory {
    public Transaction createTransaction(final int id,final Date date, final List<Tag> tagList){
        return new Transaction(id,date,tagList);
    }
}
