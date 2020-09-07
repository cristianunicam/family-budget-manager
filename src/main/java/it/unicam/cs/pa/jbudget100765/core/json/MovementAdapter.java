package it.unicam.cs.pa.jbudget100765.core.json;

import com.google.gson.*;
import it.unicam.cs.pa.jbudget100765.components.account.Account;
import it.unicam.cs.pa.jbudget100765.components.movement.Movement;
import it.unicam.cs.pa.jbudget100765.components.movement.MovementType;
import it.unicam.cs.pa.jbudget100765.components.tag.Tag;
import it.unicam.cs.pa.jbudget100765.core.Ledger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Classe che definisce metodi di deserializzazione e serializzazione personalizzati
 * per oggetti Movement
 */
public class MovementAdapter implements JsonDeserializer<Movement>, JsonSerializer<Movement> {
    private static final Logger logger = LoggerFactory.getLogger(MovementAdapter.class);

    @Override
    public Movement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonParser parserInstance = new JsonParser();
        MovementType movementType = MovementType.CREDITS;
        //Deserializzo i tipi primitivi
        int ID = parserInstance.parsePrimitive(jsonObject,"id",JsonPrimitive::getAsInt);
        String description = parserInstance.parsePrimitive(jsonObject,"description",JsonPrimitive::getAsString);
        double amount = parserInstance.parsePrimitive(jsonObject,"amount",JsonPrimitive::getAsDouble);
        int accountId = parserInstance.parsePrimitive(jsonObject,"accountid",JsonPrimitive::getAsInt);
        //Deseritalizzo il MovementType
        if(parserInstance.parsePrimitive(jsonObject,"type",JsonPrimitive::getAsString).equals(MovementType.DEBIT.toString()))
            movementType = MovementType.DEBIT;
        //Deserializzo account
        Account a = Ledger.getInstance().getAccounts(account -> account.getId() == accountId).get(0);
        Movement m = new Movement(ID, parserInstance.parseDate(jsonObject), movementType, description, amount, null, a);
        a.addMovement(m);
        //Deserializzo lista tag
        List<Tag> tagList = parserInstance.parseList(jsonObject,"tags", Tag.class);
        if(tagList != null)
            tagList.forEach(tag -> { Ledger.getInstance().addTag(tag); m.addTag(tag);});

        return m;
    }

    @Override
    public JsonElement serialize(Movement src, Type typeOfSrc, JsonSerializationContext context) {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();
        result.add("id", new JsonPrimitive(src.getId()));
        result.add("description",new JsonPrimitive(src.getDescription()));
        result.add("date",new JsonPrimitive(new SimpleDateFormat("yyyy-MM-dd").format(src.getDate())));
        result.add("amount",new JsonPrimitive(src.getAmount()));
        result.add("type",new JsonPrimitive(src.getType().toString()));
        result.add("accountid",new JsonPrimitive(src.getAccount().getId()));
        if(src.getTags().size() > 0)
            result.add("tags", gson.toJsonTree(src.getTags()));
        return result;
    }
}
