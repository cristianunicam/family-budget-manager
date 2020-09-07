package it.unicam.cs.pa.jbudget100765;

import it.unicam.cs.pa.jbudget100765.core.FileManager;
import it.unicam.cs.pa.jbudget100765.core.TransactionView;
import javafx.application.Application;

public class JBudget{
    public static void main(String[] args) {
        FileManager.getInstance();
        Application.launch(TransactionView.class,args);
    }
}
