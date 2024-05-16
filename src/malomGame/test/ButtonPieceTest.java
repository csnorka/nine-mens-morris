package malomGame.test;

import malomGame.game.ButtonCondition;
import malomGame.game.ButtonPiece;
import malomGame.game.Mill;
import malomGame.game.Removable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ButtonPieceTest {

    private final ButtonPiece pieceOne = new ButtonPiece(0);
    private final ButtonPiece pieceTwo = new ButtonPiece(1);

    @Test
    void getNumber() {
        assertEquals(0, pieceOne.getNumber());
        assertNotEquals(2, pieceTwo.getNumber());
    }

    @Test
    void setCondition() {
        pieceOne.setCondition(ButtonCondition.BLACK);

        assertEquals(ButtonCondition.BLACK, pieceOne.getCondition());
    }

    @Test
    void getCondition() {
        assertEquals(ButtonCondition.EMPTY, pieceTwo.getCondition());
        assertNotEquals(ButtonCondition.WHITE, pieceOne.getCondition());
    }

    @Test
    void setMill() {
        pieceOne.setMill(Mill.IN_MILL);

        assertEquals(Mill.IN_MILL, pieceOne.getMill());
    }

    @Test
    void getMill() {
        assertEquals(Mill.NOT_IN_MILL, pieceTwo.getMill());
    }

    @Test
    void setRemovable() {
        pieceOne.setRemovable(Removable.REMOVABLE);

        assertEquals(Removable.REMOVABLE, pieceOne.getRemovable());
    }

    @Test
    void getRemovable() {
        assertEquals(Mill.NOT_IN_MILL, pieceTwo.getMill());
    }
}