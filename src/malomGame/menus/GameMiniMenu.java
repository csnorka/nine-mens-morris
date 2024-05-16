package malomGame.menus;

import malomGame.game.Board;
import malomGame.game.Mode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMiniMenu extends JFrame {
    /**
     * A jaték mód. Egy játékos vagy két játékos.
     */
    protected Mode mode;
    /**
     * A kisebb menü alapbeállításai.
     */
    public GameMiniMenu(){
        setTitle("Nine Men's Morris");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(450,310);
        setLocationRelativeTo(null);

        onePlayerButton();
        twoPlayerButton();
        backButton();
    }
    /**
     * Az egyjátékos mód kiválasztásához tartozó gomb.
     */
    private void onePlayerButton(){
        JButton onePlayerButton = new JButton();
        String button_st = "<html><p style=\"font-size:150%;\">One Player</p></html>";
        onePlayerButton.setText(button_st);
        onePlayerButton.setBackground(Color.black);
        onePlayerButton.setForeground(Color.pink);
        onePlayerButton.setPreferredSize(new Dimension(250, 80));
        onePlayerButton.addActionListener(e -> {
            mode = Mode.ONEPLAYER;
            GameMiniMenu.super.dispose();
            new Board(mode);
        });

        JPanel onePlayerPanel = new JPanel();
        onePlayerPanel.add(onePlayerButton);
        onePlayerPanel.setBackground(Color.pink);

        add(onePlayerPanel, BorderLayout.NORTH);
    }
    /**
     * A kétjátékos mód kiválasztásához tartozó gomb.
     */
    private void twoPlayerButton(){
        JButton twoPlayerButton = new JButton();
        String button_st = "<html><p style=\"font-size:150%;\">Two Player</p></html>";
        twoPlayerButton.setText(button_st);
        twoPlayerButton.setBackground(Color.black);
        twoPlayerButton.setForeground(Color.pink);
        twoPlayerButton.setPreferredSize(new Dimension(250, 80));
        twoPlayerButton.addActionListener(e -> {
            mode = Mode.TWOPLAYER;
            GameMiniMenu.super.dispose();
            new Board(mode);
        });

        JPanel twoPlayerPanel = new JPanel();
        twoPlayerPanel.add(twoPlayerButton);
        twoPlayerPanel.setBackground(Color.pink);

        add(twoPlayerPanel, BorderLayout.CENTER);
    }
    /**
      * Vissza a főmenübe gomb beállításai.
     */
    private void backButton(){
        JButton backButton = new JButton();
        String button_st = "<html><p style=\"font-size:150%;\">Back</p></html>";
        backButton.setText(button_st);
        backButton.setBackground(Color.black);
        backButton.setForeground(Color.pink);
        backButton.setPreferredSize(new Dimension(250,80));
        backButton.addActionListener(e -> {
            GameMiniMenu.super.dispose();
            new Menu();
        });

        JPanel backPanel = new JPanel();
        backPanel.add(backButton);
        backPanel.setBackground(Color.pink);

        add(backPanel, BorderLayout.SOUTH);
    }
}
