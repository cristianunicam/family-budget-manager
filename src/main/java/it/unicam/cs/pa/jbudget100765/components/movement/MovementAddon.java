package it.unicam.cs.pa.jbudget100765.components.movement;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Classe contente funzioni aggiuntive per i Movement
 */
public class MovementAddon {

    /**
     * Viene passata una lista di movimenti, questa lista di movimenti viene sommata
     * in base al tipo di Movement (CREDITS, DEBITS) per poi venire applicata la funzione passata
     * alle somme risultanti dei 2 tipi di Movement
     *
     * @param f {@code BiFunction} da voler applicare
     * @param movementList lista dei movimenti ai quali applicare la funzione
     * @return {@code double} risultato una volta applicata la funzione
     */
    public static double applyFunction(BiFunction<Double,Double,Double> f, List<Movement> movementList){
        return f.apply(
                sumMovementAmount(MovementType.DEBIT,movementList),
                sumMovementAmount(MovementType.CREDITS,movementList)
        );
    }

    /**
     * Esegue la somma di una data lista di movimenti
     *
     * @param type
     * @param movementList
     * @return
     */
    private static double sumMovementAmount(MovementType type, List<Movement> movementList){
        return movementList
                .stream()
                .filter(movement -> movement.getType() == type)
                .mapToDouble(Movement::getAmount).sum();
    }
}
