package it.unicam.cs.pa.jbudget100765.core.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import it.unicam.cs.pa.jbudget100765.components.tag.Tag;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsonParserTest {

    @Test
    public void testParseDate() throws ParseException {
        JsonParser jp = new JsonParser();
        Gson gson = new Gson();
        String toJson = "{\"date\": \"2020-06-08\"}";
        String date = "2020-06-08";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date toCheck = simpleDateFormat.parse(date);

        JsonElement je = gson.fromJson(toJson, JsonElement.class);
        JsonObject jo = je.getAsJsonObject();

        assertEquals(toCheck,jp.parseDate(jo));
    }

    @Test
    public void testParsePrimitive() {
        String parseString = "{\"date\": \"2020-06-08\"}";
        String parseInt = "{\"toint\":\"100\"}";
        String parseDouble = "{\"todouble\":\"100.11\"}";

        JsonParser jsonParser = new JsonParser();
        Gson gson = new Gson();

        JsonElement je = gson.fromJson(parseString, JsonElement.class);
        JsonObject jo = je.getAsJsonObject();
        assertEquals("2020-06-08",jsonParser.parsePrimitive(jo,"date",JsonPrimitive::getAsString));

        je = gson.fromJson(parseInt,JsonElement.class);
        jo = je.getAsJsonObject();
        assertEquals(Integer.valueOf(100),jsonParser.parsePrimitive(jo,"toint",JsonPrimitive::getAsInt));

        je = gson.fromJson(parseDouble,JsonElement.class);
        jo = je.getAsJsonObject();
        assertEquals(Double.valueOf(100.11),jsonParser.parsePrimitive(jo,"todouble",JsonPrimitive::getAsDouble));

    }

    @Test
    public void testParseList() {
        String stringTags = "{\"tags\": [" +
                "            {" +
                "              \"id\": 0," +
                "              \"name\": \"primo\"," +
                "              \"description\": \"primo tag\"" +
                "            }," +
                "            {" +
                "              \"id\": 1," +
                "              \"name\": \"secondo\"," +
                "              \"description\": \"secondo Tag\"" +
                "            }" +
                "          ]}";
        List<Tag> tagListObj = new ArrayList<>();
        tagListObj.add(new Tag("primo","primo tag",0));
        tagListObj.add(new Tag("secondo","secondo Tag",1));

        Gson gson = new Gson();
        JsonParser jp = new JsonParser();
        JsonElement je = gson.fromJson(stringTags, JsonElement.class);
        JsonObject jo = je.getAsJsonObject();
        List<Tag> parsedList = jp.parseList(jo,"tags",Tag.class);

        assertEquals(tagListObj.toString(),parsedList.toString());
    }
}