package it.unicam.cs.pa.jbudget100765.core.json;

import com.google.gson.*;
import it.unicam.cs.pa.jbudget100765.core.Ledger;
import it.unicam.cs.pa.jbudget100765.components.tag.Tag;
import it.unicam.cs.pa.jbudget100765.components.transaction.Transaction;
import it.unicam.cs.pa.jbudget100765.components.transaction.TransactionFactory;
import it.unicam.cs.pa.jbudget100765.components.movement.Movement;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe che definisce metodi di deserializzazione e serializzazione personalizzati per le Transazioni
 */
public class TransactionAdapter implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {
    private static final Set<Integer> alreadySaved = new HashSet<>();
    private static final Logger logger = LoggerFactory.getLogger(TransactionAdapter.class);

    /**
     * Scompone il json ottenendo oggetti da salvare
     *
     * @param json Elemento json da voler salvare
     * @param typeOfT tipo dell'elemento da voler salvare
     * @return Transaction una nuova transazione dopo aver deserializzato un determinato oggetto
     */
    @Override
    public Transaction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        logger.debug("Transaction object deserialization started");
        JsonParser parserInstance = new JsonParser();
        JsonObject jsonObject = json.getAsJsonObject();

        List<Tag> tagList = parserInstance.parseList(jsonObject,"tags", Tag.class);
        if(tagList != null)
            tagList.forEach(tag -> Ledger.getInstance().addTag(tag));
        int ID = parserInstance.parsePrimitive(jsonObject,"id",JsonPrimitive::getAsInt);

        Transaction t = new TransactionFactory()
                .createTransaction( ID , parserInstance.parseDate(jsonObject) , tagList);

        parseMovements(parserInstance,new JSONObject(jsonObject.toString()),t);
        return t;
    }

    /**
     *  Per ciascun movimento nella data transazione, imposta la transazione di un movimento ed aggiunge
     *  tale movimento come facente parte di una data transazione
     */
    private void parseMovements(JsonParser parserInstance, JSONObject jsonObject, Transaction t){
        List<Movement> movementList = parserInstance.deserializeList(
                Movement.class , new MovementAdapter() , jsonObject.getJSONArray("movements")
        );
        for(Movement m : movementList){
            m.setTransaction(t);
            t.addMovement(m);
        }
    }

    /**
     * Serialize serve per "scomporre" gli oggetti da Object a JSON
     */
    @Override
    public JsonElement serialize(final Transaction src,final Type typeOfSrc,final JsonSerializationContext context) {
        logger.debug("Transaction object Serialization started");
        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        result.add("id", new JsonPrimitive(src.getId()));
        if(alreadySaved.contains(src.getId()))
            return null;
        alreadySaved.add(src.getId());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        result.add("date",new JsonPrimitive(df.format(src.getDate())));
        result.add("tags", gson.toJsonTree(src.getTags()));

        Gson gsonTransaction = new GsonBuilder()
                .registerTypeAdapter(Movement.class, new MovementAdapter()).create();
        JsonElement je = gsonTransaction.toJsonTree(src.getMovements());
        if(src.getMovements() != null)
            result.add("movements",je);
        return result;
    }
}

