package it.unicam.cs.pa.jbudget100765.core.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.json.JSONArray;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

public interface JsonParserInterface {
    /**
     * Classe che permette di ottenere un oggetto Date rappresentate la data
     * ottenuta dato un JsonObject
     *
     * @param jsonObject JsonObject contenente la data da ottenere
     * @return {@code Date} data da voler ritornare
     */
    Date parseDate(JsonObject jsonObject);

    /**
     * Permette di ottenere un valore dato un suo nome all'interno di un JsonObject
     * (es. int, double, String etc.)
     *
     * @param jsonObject JsonObject nel quale cercare il valore che si vuole ottenere
     * @param toParse nome del valore che si vuole ottenere
     * @param function funzione del JsonPrimitive che permette di ottenere un dato tipo in base
     *                 alla Function inserita. ES: JsonPrimitive::getAsInt - JsonPrimitive::getAsDouble
     *                 JsonPrimitive::getAsString
     * @param <H> tipo da voler ottenere dal JsonObject es int, double, String
     * @return valore parsato
     */
    <H> H parsePrimitive(JsonObject jsonObject, String toParse, Function<JsonPrimitive,H> function);

    /**
     * Permette di ottenere una lista di oggetti dato un JsonObject
     *
     * @param jsonObject JsonObject nel quale cercare un determinato oggetto
     * @param toGetListOf nome dell'oggetto che si vuole andare a cercare nel JsonObject
     * @param elementClass Classe dell'oggetto che si vuole andare a cercare
     * @param <T> Classe dell'oggetto che si vuole andare a cercare
     * @return {@code List<T>} lista dell'oggetto che si è trovato nel JsonObject ( se presente ), null altrimenti
     */
    <T> List<T> parseList(JsonObject jsonObject, String toGetListOf, Class<T> elementClass);

    /**
     * Permette di eseguire la deserializzazione di una lista generica
     *
     * @param obj classe dell'oggetto da deserializzare
     * @param adapter adapter dell'oggetto da voler deserializzare
     * @param toGetListOf JSONArray dell'oggetto da voler deserializzare
     * @param <T> Classe dell'oggetto da voler deserializzare
     * @param <H> Classe dell'adapter dell'oggetto da voler deserializzare
     * @return {@code List<T>} dove T è l'oggetto da voler deserializzare
     */
    <T,H> List<T> deserializeList(Class<T> obj, H adapter, JSONArray toGetListOf);

}
