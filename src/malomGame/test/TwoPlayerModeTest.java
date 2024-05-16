package malomGame.test;

import malomGame.game.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TwoPlayerModeTest {
    private Board board;
    private PlayingField playingField;
    private TwoPlayerMode twoPlayerMode;
    private List<ButtonPiece> buttonPieceList;

    @BeforeEach
    void setUp(){
        board = new Board(Mode.TWOPLAYER);
        playingField = board.pf;
        twoPlayerMode = board.pf.twoPlayerMode;

        buttonPieceList = board.pf.buttonPieceList;
    }

    @Test
    void twoPlayerModeGame() {
        //White turn
        twoPlayerMode.twoPlayerModeGame(0);
        assertEquals(buttonPieceList.get(1).getCondition(), ButtonCondition.READY);

        twoPlayerMode.twoPlayerModeGame(1);
        assertEquals(buttonPieceList.get(1).getCondition(), ButtonCondition.WHITE);

        //Black turn
        twoPlayerMode.twoPlayerModeGame(25);
        assertEquals(buttonPieceList.get(11).getCondition(), ButtonCondition.READY);

        twoPlayerMode.twoPlayerModeGame(11);
        assertEquals(buttonPieceList.get(11).getCondition(), ButtonCondition.BLACK);

        //White turn
        twoPlayerMode.twoPlayerModeGame(0);
        assertEquals(buttonPieceList.get(2).getCondition(), ButtonCondition.READY);

        twoPlayerMode.twoPlayerModeGame(2);
        assertEquals(buttonPieceList.get(2).getCondition(), ButtonCondition.WHITE);

        //Black turn
        twoPlayerMode.twoPlayerModeGame(25);
        assertEquals(buttonPieceList.get(4).getCondition(), ButtonCondition.READY);

        twoPlayerMode.twoPlayerModeGame(4);
        assertEquals(buttonPieceList.get(4).getCondition(), ButtonCondition.BLACK);

        //White turn
        twoPlayerMode.twoPlayerModeGame(0);
        assertEquals(buttonPieceList.get(3).getCondition(), ButtonCondition.READY);

        twoPlayerMode.twoPlayerModeGame(3);
        assertEquals(buttonPieceList.get(3).getCondition(), ButtonCondition.WHITE);

        assertEquals(buttonPieceList.get(1).getMill(), Mill.IN_MILL);
        assertEquals(buttonPieceList.get(2).getMill(), Mill.IN_MILL);
        assertEquals(buttonPieceList.get(3).getMill(), Mill.IN_MILL);

        assertEquals(buttonPieceList.get(4).getRemovable(), Removable.REMOVABLE);
        assertEquals(buttonPieceList.get(11).getRemovable(), Removable.REMOVABLE);

        twoPlayerMode.twoPlayerModeGame(4);
        assertEquals(buttonPieceList.get(4).getCondition(), ButtonCondition.EMPTY);

        //Black turn
        twoPlayerMode.twoPlayerModeGame(25);
        assertEquals(buttonPieceList.get(12).getCondition(), ButtonCondition.READY);

        twoPlayerMode.twoPlayerModeGame(12);
        assertEquals(buttonPieceList.get(12).getCondition(), ButtonCondition.BLACK);

    }
}