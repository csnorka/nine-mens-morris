package malomGame.game;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * A gombok/bábuk beállítása, egy szám, és különböző állapotok hozzáadásával.
 */
public class ButtonPiece extends JButton implements Serializable {
    private ButtonCondition condition;
    private Mill mill;
    private Removable removable;
    private final int number;

    /**
     * A gombok alapbeállításai.
     * @param num - a szám amit megkapnak tulajdonságként.
     */
    public ButtonPiece(int num){
        setPreferredSize(new Dimension(45,45));
        setBackground(Color.pink);
        setBorderPainted(false);
        setForeground(Color.pink);

        number = num;
        condition = ButtonCondition.EMPTY;
        mill = Mill.NOT_IN_MILL;
        removable = Removable.NOT_REMOVABLE;
    }

    /**
     * Gomb száma, egyedi.
     */
    public int getNumber(){
        return number;
    }

    /**
     * Gomb állapotának beállítása.
     */
    public void setCondition(ButtonCondition buttonCondition){
        condition = buttonCondition;
    }

    /**
     * Gomb álllapotának lekérdezése.
     */
    public ButtonCondition getCondition(){
        return condition;
    }

    /**
     * Gomb malomba állításának függvénye.
     * @param otherMill - amire állítjuk a gombot: IN_MILL/NOT_IN_MILL.
     */
    public void setMill(Mill otherMill){
        mill = otherMill;
    }

    /**
     * A gomb malombeli állapotának lekérdezése.
     * @return Mill állapot.
     */
    public Mill getMill(){
        return mill;
    }

    /**
     * A gomb levehetőség állapotának beállítása.
     * @param remove - egy állapot, amire állítjuk: REMOVABLE/NOT_REMOVABLE.
     */
    public void setRemovable(Removable remove){
        removable = remove;
    }

    /**
     * A levehőtségének állapotának lekérdezése.
     * @return Removable állapot.
     */
    public Removable getRemovable(){
        return removable;
    }
}
