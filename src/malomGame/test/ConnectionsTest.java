package malomGame.test;

import malomGame.game.ButtonPiece;
import malomGame.game.Connections;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionsTest {

    private final ButtonPiece source = new ButtonPiece(0);
    private final ButtonPiece destination = new ButtonPiece(1);
    private final Connections connections = new Connections(source, destination);


    @org.junit.jupiter.api.Test
    void getSource() {
        assertEquals(source, connections.getSource());
    }

    @org.junit.jupiter.api.Test
    void getDestination() {
        assertEquals(destination, connections.getDestination());
    }
}