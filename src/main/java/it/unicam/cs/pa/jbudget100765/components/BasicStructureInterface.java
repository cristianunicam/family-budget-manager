package it.unicam.cs.pa.jbudget100765.components;

import it.unicam.cs.pa.jbudget100765.components.tag.Tag;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public interface BasicStructureInterface {
    /**
     * Ritorna la data
     */
    Date getDate();

    /**
     * Imposta la data
     */
    void setDate(Date date);

    /**
     * Ritorna la lista dei {@code Tag}
     */
    List<Tag> getTags();

    /**
     * Ritorna la lista dei {@code Tag} che rispettano il dato {@code Predicate}
     *
     * @return {@code List<Tag} ritorna una lista di tag
     */
    List<Tag> getTags(Predicate<Tag> predicate);

    /**
     * Ritorna l'id
     */
    int getId();

    /**
     * Permette l'aggiunta di un nuovo {@code Tag}
     */
    void addTag(Tag tag);

    /**
     * Metodo astratto che permette di rimuovere il {@code Tag} passato
     */
    void removeTag(Tag t);

    /**
     * Medoto astratto che ritorna il balance calcolato
     */
    double getAmount();
}