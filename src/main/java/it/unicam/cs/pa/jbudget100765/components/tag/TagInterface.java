package it.unicam.cs.pa.jbudget100765.components.tag;

public interface TagInterface {
    /**
     * Ritorna la descrizione del Tag
     *
     * @return {@code String} descrizione
     */
    String getDescription();

    /**
     * Ritorna il nome del Tag
     *
     * @return {@code String} nome
     */
    String getName();

    /**
     * Ritorna l'id di un Tag
     *
     * @return {@code int} id tag
     */
    int getId();
}
