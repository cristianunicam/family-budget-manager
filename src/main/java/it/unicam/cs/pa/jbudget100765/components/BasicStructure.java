package it.unicam.cs.pa.jbudget100765.components;

import it.unicam.cs.pa.jbudget100765.components.tag.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Struttura base per il salvataggio di id, lista di tag e data
 */
public abstract class BasicStructure implements BasicStructureInterface {
    private static final Logger logger = LoggerFactory.getLogger(BasicStructure.class);

    private final int id;
    private final List<Tag> tags;
    private transient Date date;

    public BasicStructure(final int id, final List<Tag> tags, final Date date){
        this.id = id;
        if(tags != null)
            this.tags = new ArrayList<>(tags);
        else
            this.tags = new ArrayList<>();
        this.date = date;
    }

    @Override
    public Date getDate() {
        logger.debug("Date returned");
        return date;
    }

    @Override
    public void setDate(Date date){
        logger.debug("Date setted");
        this.date = date;
    }

    @Override
    public List<Tag> getTags() {
        logger.debug("Tag list returned");
        return this.tags;
    }

    @Override
    public List<Tag> getTags(Predicate<Tag> predicate) {
        logger.debug("Parsed tag list returned");
        return (predicate == null) ? tags : tags
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void addTag(Tag tag) {
        logger.debug("Tag added to the list");
        this.tags.add(tag);
    }
}
