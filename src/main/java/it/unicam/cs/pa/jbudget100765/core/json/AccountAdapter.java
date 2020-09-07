package it.unicam.cs.pa.jbudget100765.core.json;

import com.google.gson.*;
import it.unicam.cs.pa.jbudget100765.components.account.Account;
import it.unicam.cs.pa.jbudget100765.components.account.AccountFactory;
import it.unicam.cs.pa.jbudget100765.components.account.AccountType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

/**
 * Classe che definisce una deserializzazione personalizzata per gli Account
 */
public class AccountAdapter implements JsonDeserializer<Account> {
    private static final Logger logger = LoggerFactory.getLogger(AccountAdapter.class);

    @Override
    public Account deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        logger.debug("Account deserialization started");
        JsonParser parserInstance = new JsonParser();
        JsonObject jsonObject = json.getAsJsonObject();
        AccountType accountType = AccountType.LIABILITIES;

        int id = parserInstance.parsePrimitive(jsonObject,"id",JsonPrimitive::getAsInt);
        String name = parserInstance.parsePrimitive(jsonObject,"name",JsonPrimitive::getAsString);
        String description = parserInstance.parsePrimitive(jsonObject,"description",JsonPrimitive::getAsString);
        double openingBalance = parserInstance.parsePrimitive(jsonObject,"openingBalance",JsonPrimitive::getAsDouble);
        if(parserInstance.parsePrimitive(jsonObject,"type",JsonPrimitive::getAsString).equals(AccountType.ASSETS.toString()))
            accountType = AccountType.ASSETS;

        return new AccountFactory().createAccount(id,accountType,name,description,openingBalance);
    }
}
