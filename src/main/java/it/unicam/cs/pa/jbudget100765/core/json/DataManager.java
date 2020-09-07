package it.unicam.cs.pa.jbudget100765.core.json;

import com.google.gson.*;
import it.unicam.cs.pa.jbudget100765.components.account.Account;
import it.unicam.cs.pa.jbudget100765.core.FileManager;
import it.unicam.cs.pa.jbudget100765.core.Ledger;
import it.unicam.cs.pa.jbudget100765.components.transaction.Transaction;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Classe che si occupa del salvataggio e caricamento dei dati
 */
public class DataManager implements DataManagerInterface {
    private static final Logger logger = LoggerFactory.getLogger(DataManager.class);

    @Override
    public void save(File file) {
        JsonObject jo = new JsonObject();
        jo.add("account",accountToJson());
        jo.add("transaction",transactionsToJson());
        FileManager.writeOnFile(
                jo.toString(),file
        );
        logger.debug("Json saved on file");
    }

    @Override
    public void load(File file) {
        logger.debug("File loaded, starting to deserialize");
        String fileText;
        try {
             fileText = FileManager.readFromFile(file);
        }catch (IOException e) {
            logger.debug("Data file not found");
            return;
        }
        JSONObject jsonObject = new JSONObject(fileText);
        it.unicam.cs.pa.jbudget100765.core.json.JsonParser jp = new JsonParser();

        List<Account> deserializedAccount = jp.deserializeList(
                Account.class,new AccountAdapter(),jsonObject.getJSONArray("account")
        );
        deserializedAccount.forEach(
                a -> Ledger.getInstance().addAccount(a)
        );
        List<Transaction> deserializedTransactions = jp.deserializeList(
                Transaction.class,new TransactionAdapter(),jsonObject.getJSONArray("transaction")
        );
        for(Transaction t : deserializedTransactions)
            Ledger.getInstance().addTransaction(t);
    }

    /**
     * Serializza gli Account presi dal Ledger
     *
     * @return {@code JsonArray} contenente gli Account serializzati
     */
    private JsonArray accountToJson(){
        logger.debug("Account list seialized");
        Gson gson = new Gson();
        return gson.toJsonTree(
                Ledger.getInstance().getAccounts()
        ).getAsJsonArray();
    }

    /**
     * Serializza le transazioni prese dal Ledger
     *
     * @return JsonElement contenente le transazioni serializzate
     */
    private JsonElement transactionsToJson(){
        logger.debug("Transaction list serialized");
        Gson gsonTransaction = new GsonBuilder()
                .registerTypeAdapter(Transaction.class, new TransactionAdapter())
                .create();

        for(Transaction t : Ledger.getInstance().getTransactions())
            logger.error("Transazione da salvare: "+t.getId());

        return gsonTransaction.toJsonTree(
                Ledger.getInstance().getTransactions()
        );
    }
}
