package it.unicam.cs.pa.jbudget100765.core;

import it.unicam.cs.pa.jbudget100765.core.json.DataManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Permette la visualizzazione di una determinata pagina
 */
public class TransactionView extends Application {
    private static final Logger logger = LoggerFactory.getLogger(TransactionView.class);

    @Override
    public void start(Stage stage) throws IOException {
        logger.debug("Transaction View loaded");
        Parent root = FXMLLoader.load(
                getClass().getResource("/home.fxml")
        );

        stage.setTitle("Home page");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void stop(){
        logger.debug("View stopped, file saving started");
        new DataManager().save(
                FileManager.getInstance().getDataFileName()
        );
    }
}
