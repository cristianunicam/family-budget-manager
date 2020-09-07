package it.unicam.cs.pa.jbudget100765.core.json;

import java.io.File;

public interface DataManagerInterface {
    /**
     * Creo un oggetto File dato il nome del file
     *
     * @param file nome del file
     */
    default void save(String file) {
        save(new File(file));
    }

    /**
     * Creo un oggetto File dato il nome del file
     *
     * @param file nome del file
     */
    default void load(String file) {
        load(new File(file));
    }

    /**
     * Metodo utilizzato per effettuare il salvataggio di dati
     * in formato json sul file
     *
     * @param file file nel quale salvare i dati
     */
    void save(File file);

    /**
     * Metodo utilizzato per il caricamento di dati dato un file
     *
     * @param file file dal quale caricare i dati
     */
    void load(File file);
}
