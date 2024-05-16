package malomGame.game;

import javax.swing.*;
import java.io.Serializable;

/**
 * Segéd osztály a kapcsolatok kialakítására.
 */
public class Connections implements Serializable {
    private final ButtonPiece source;
    private final ButtonPiece destination;

    /**
     * Kapcsolatok kezdete és vége beállítása.
     * @param source - a kapcsolat kezdete.
     * @param destination - a kapcsolat vége.
     */
    public Connections(ButtonPiece source, ButtonPiece destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * A kezdetének lekérdezése a kapcsolatnak.
     */
    public ButtonPiece getSource() {
        return source;
    }

    /**
     * A másik vége a kapcsolatnak és annak lekérdezése.
     */
    public ButtonPiece getDestination() {
        return destination;
    }
}
