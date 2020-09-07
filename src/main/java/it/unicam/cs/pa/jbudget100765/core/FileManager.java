package it.unicam.cs.pa.jbudget100765.core;

import com.google.gson.JsonObject;
import it.unicam.cs.pa.jbudget100765.core.json.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe che definisce metodi di utility per la gestione dei file
 */
public class FileManager implements FileManagerInterface {
    private static final Logger logger = LoggerFactory.getLogger(FileManager.class);

    private static FileManager instance = null;
    private final String data;
    private static final String fileSettings = "settings.json";

    private FileManager(){
        data = getDataFileNameFromSettings();
        new DataManager().load(data);
    }

    public static FileManager getInstance(){
        if(instance == null)
            instance = new FileManager();
        return instance;
    }

    @Override
    public String getDataFileNameFromSettings() {
        logger.debug("Data file name parsed from settings file: \""+fileSettings+"\"");
        String readedFile = null;
        try {
            readedFile = readFromFile(new File(fileSettings));
        }catch (IOException e){
            logger.error("Settings file not found");
            System.exit(-1);
        }

        JsonObject jo = com.google.gson.JsonParser.parseString(readedFile).getAsJsonObject();
        return jo.get("settings").getAsJsonObject().get("fileName").getAsJsonPrimitive().getAsString();
    }

    @Override
    public String getDataFileName(){
        logger.debug("File name returned");
        return this.data;
    }

    /**
     * Scrive una determinata stringa di testo su file
     */
    public static void writeOnFile(String jsonString, File file){
        logger.debug("Json string wrote on file");
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonString);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ritorna una stringa contente i dati letti dal file
     */
    public static String readFromFile(File file) throws IOException {
        logger.debug("Returning the file string");
        StringBuilder fileData = new StringBuilder();
        FileReader fileReader = new FileReader(file);
        int i;
        while ((i = fileReader.read()) != -1) {
            fileData.append((char) i);
        }
        return fileData.toString();
    }


}

