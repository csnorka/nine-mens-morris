package malomGame;

import malomGame.menus.Menu;

import javax.swing.*;

/**
 * A main függvény, ami elindítja a programot.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Menu::new);
    }
}