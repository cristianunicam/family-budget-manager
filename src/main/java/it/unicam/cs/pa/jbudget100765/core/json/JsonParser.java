package it.unicam.cs.pa.jbudget100765.core.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.pa.jbudget100765.exceptions.WrongDataException;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Classe che definisce metodi di utility per il parse di oggetti JsonObject
 */
public class JsonParser implements JsonParserInterface{
    private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);

    public JsonParser(){}

    @Override
    public Date parseDate(JsonObject jsonObject){
        logger.debug("Parsing data from jsonObject");
        String data;
        try {
            if((data = jsonObject.getAsJsonPrimitive("date").getAsString()) != null) {
                return new SimpleDateFormat("yyyy-MM-dd").parse(data);
            }else
                throw new WrongDataException();
        } catch (ParseException | WrongDataException e) {
            logger.error("Data parsing error");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    @Override
    public <H> H parsePrimitive(JsonObject jsonObject, String toParse, Function<JsonPrimitive,H> function){
        logger.debug("Primitive type parsed from JsonObject");
        return function.apply(
                    jsonObject.getAsJsonPrimitive(toParse)
                );
    }

    @Override
    public <T> List<T> parseList(JsonObject jsonObject, String toGetListOf,Class<T> elementClass){
        logger.debug("List parsed from JsonObject");
        Gson gson = new Gson();
        if(jsonObject.has(toGetListOf)) {
            JsonArray element = jsonObject.getAsJsonArray(toGetListOf);
            if(element != null && !element.isJsonNull()) {
                Type type = TypeToken.getParameterized(ArrayList.class,elementClass).getType();
                return gson.fromJson(element,type);
            }
        }
        return null;
    }

    @Override
    public <T,H> List<T> deserializeList(Class<T> obj, H adapter, JSONArray toGetListOf){
        logger.debug("Deserializing a list of {}",obj.getName());
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(obj,adapter)
                .create();
        Type type = TypeToken.getParameterized(ArrayList.class,obj).getType();
        return gson.fromJson(toGetListOf.toString(),type);
    }
}
