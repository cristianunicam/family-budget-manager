package it.unicam.cs.pa.jbudget100765.core;

/**
 * Classe utilizzata per salvare ed importare i dati
 */
public interface FileManagerInterface {
    /**
     * Ritorna il path del file nel quale salvare i dati
     *
     * @return {@code String} path file
     */
    String getDataFileName();

    /**
     * Ritorna il path del file contente i dati, dato il file {@code fileSettings}
     *
     * @return {@code String} path del file contenente i dati
     */
    String getDataFileNameFromSettings();

}
