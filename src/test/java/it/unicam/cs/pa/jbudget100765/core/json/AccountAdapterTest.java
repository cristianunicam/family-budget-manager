package it.unicam.cs.pa.jbudget100765.core.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.pa.jbudget100765.components.account.Account;
import it.unicam.cs.pa.jbudget100765.components.account.AccountFactory;
import it.unicam.cs.pa.jbudget100765.components.account.AccountType;
import org.junit.Assert;
import org.junit.Test;

public class AccountAdapterTest {
    @Test
    public void testDeserialize(){
        //CREO UN JSON DI ACCOUNT, LO DESERIALIZZO E CONTROLLO L'EQUALS DEGLI OGGETTI
        String jsonAccount =
                "    {" +
                "      \"id\": 0,"+
                "      \"name\": \"nomeaccount\"," +
                "      \"description\": \"descrizione\"," +
                "      \"type\": \"ASSETS\"," +
                "      \"openingBalance\": 111.11," +
                "      \"balance\": 111.11," +
                "      \"movements\": []" +
                "    }";

        Gson gsonAccount = new GsonBuilder()
                .registerTypeAdapter(Account.class, new AccountAdapter())
                .create();

        Account a = gsonAccount.fromJson(jsonAccount,Account.class);
        Assert.assertEquals(a,new Account(0, AccountType.ASSETS,"nomeaccount","descrizione",111.11));
    }

    @Test
    public void testSerialize(){
        String jsonAccount = "{\"id\":0,\"name\":\"nomeaccount\",\"description\":\"descrizione\",\"type\":\"ASSETS\",\"openingBalance\":111.11,\"balance\":111.11}";
        Account toSerialize = new AccountFactory().createAccount(0,AccountType.ASSETS,"nomeaccount","descrizione",111.11);

        Gson gsonAccount = new GsonBuilder()
                .registerTypeAdapter(Account.class, new AccountAdapter())
                .create();
        String serializedAccount = gsonAccount.toJson(toSerialize,Account.class);

        Assert.assertEquals(jsonAccount,serializedAccount);
    }
}
