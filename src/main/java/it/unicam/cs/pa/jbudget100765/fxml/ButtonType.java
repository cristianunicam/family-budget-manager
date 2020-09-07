package it.unicam.cs.pa.jbudget100765.fxml;

/**
 * Classe enumeration contenente i differenti tipi di Button,
 * viene utilizzata per ottenere il path del file fxml ed il titolo del form
 */
public enum ButtonType {
    ADDMOVEMENT ("/addMovement.fxml","Aggiungi un movimento"),
    ADDTAG ("/addTag.fxml","Aggiungi un Tag"),
    SHOWTRANSACTIONS ("/transaction.fxml","Visualizza le transazioni"),
    SHOWMOVEMENTS ("/movement.fxml","Visualizza i movimenti"),
    SHOWTAG ("/tag.fxml","Visualizza i tag"),
    REMOVETAGMOVEMENT ("/removeTagMovement.fxml","Rimozione Tag e movimenti"),
    SHOWACCOUNT("/showAccount.fxml", "Visualizza gli account presenti");

    private final String fileName;
    private final String description;

    ButtonType(String fileName, String description) {
        this.fileName = fileName;
        this.description = description;
    }

    public String getFileName(){
        return this.fileName;
    }

    public String getDescription(){
        return this.description;
    }
}
