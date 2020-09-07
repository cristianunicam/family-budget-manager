package it.unicam.cs.pa.jbudget100765.components.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe che gestisce un Tag e il salvataggio dei suoi dati
 */
public class Tag implements TagInterface {
    private static final Logger logger = LoggerFactory.getLogger(Tag.class);
    private final int id;
    private final String name;
    private final String description;


    public Tag(String name, String description, int id) {
        logger.debug("Tag with id \"{}\" created",id);
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public int getId() {
        logger.debug("Tag id \"{}\" returned",id);
        return id;
    }

    @Override
    public String getDescription() {
        logger.debug("Tag description whose ID is \"{}\" returned",id);
        return description;
    }

    @Override
    public String getName() {
        logger.debug("Tag name whose ID is \"{}\" returned",id);
        return name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
