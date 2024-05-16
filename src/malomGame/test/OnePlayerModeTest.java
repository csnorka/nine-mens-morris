package malomGame.test;

import malomGame.game.Mode;
import malomGame.game.Phase;
import malomGame.game.PlayingField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OnePlayerModeTest {
    private PlayingField playingField = new PlayingField(Mode.ONEPLAYER);

    @Test
    void getPhase() {
        assertEquals(Phase.PLACING, playingField.onePlayerMode.getPhase());
    }

    @Test
    void setPhase() {
        playingField.onePlayerMode.setPhase(Phase.MOVING);

        assertEquals(Phase.MOVING, playingField.onePlayerMode.getPhase());
    }

    @Test
    void getTurn() {
        assertEquals(1, playingField.onePlayerMode.getTurn());
    }

    @Test
    void setTurn() {
        playingField.onePlayerMode.setTurn(2);

        assertEquals(2, playingField.onePlayerMode.getTurn());
    }
}