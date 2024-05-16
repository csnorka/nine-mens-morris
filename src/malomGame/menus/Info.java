package malomGame.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A játék leírása.
 */
public class Info extends JFrame {
    /**
     * Az Info osztály konstruktora.
     * Az ablak beállításai.
     */
    public Info(){
        setTitle("Information about the Game");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(800,700);
        setLocationRelativeTo(null);

        gameTitle();
        gameInfo();
        panelInitialize();
    }

    /**
     * A főcím beállítása.
     */
    private void gameTitle(){
        JLabel gameName = new JLabel("");
        String st = "<html><p style=\"font-size:250%;\"><b>Nine Men's Morris</b></p></html>";
        gameName.setText(st);


        JPanel panel = new JPanel();
        panel.add(gameName);
        panel.setBackground(Color.pink);
        panel.setPreferredSize(new Dimension(100,60));

        add(panel, BorderLayout.NORTH);
    }
    /**
     * A játékról információ és a játék szabályai.
     */
    private void gameInfo(){
        String setup_st = "<html><p style=\"font-size:150%;\">Setup:</p><br>" +
                "<p style=\"font-size:120%;\">- Each player starts with nine pieces (men) of a distinct color.</p>" +
                "<p style=\"font-size:120%;\">- Players take turns placing their pieces on the intersections of a grid (3x3 points each forming a <br>" +
                "square) until all pieces are on the board.</p><br></html>";
        JLabel setupLabel = new JLabel("", SwingConstants.LEFT);
        setupLabel.setText(setup_st);

        String gameplay_st = "<html><p style=\"font-size:150%;\">Gameplay:</p><br>" +
                "<p style=\"font-size:120%;\">After setup, players take turns moving one of their pieces along a line to an adjacent empty point.</p>" +
                "<p style=\"font-size:120%;\">When a player forms a row of three pieces along a straight line (mill), they remove one of their <br>" +
                "opponent's pieces from the board.</p>" +
                "<p style=\"font-size:120%;\">Once a player is reduced to three pieces, they enter the \"flying\" phase, allowing them to move to <br>" +
                "any empty point on the board during their turn.</p>" +
                "<p style=\"font-size:120%;\">The game continues until one player is unable to make a legal move or is reduced to two pieces,<br>" +
                "resulting in a loss.</p><br></html>";
        JLabel gameplayLabel = new JLabel("", SwingConstants.LEFT);
        gameplayLabel.setText(gameplay_st);


        String winning_st = "<html><p style=\"font-size:150%;\">Winning:</p><br>" +
                "<p style=\"font-size:120%;\">A player wins by either leaving their opponent with fewer than three pieces or by blocking their <br>" +
                "opponent's moves.</p><br>" +
                "<p style=\"font-size:120%;\">It's a simple yet strategic game that involves blocking, trapping, and forming mills to gain an <br>" +
                "advantage.</p>";
        JLabel winningLabel = new JLabel("", SwingConstants.LEFT);
        winningLabel.setText(winning_st);


        JPanel gameInfoPanel = new JPanel();
        gameInfoPanel.add(setupLabel);
        gameInfoPanel.add(gameplayLabel);
        gameInfoPanel.add(winningLabel);
        gameInfoPanel.setBackground(Color.pink);

        add(gameInfoPanel, BorderLayout.CENTER);
    }

    /**
     * A keretek beállítása.
     */
    private void panelInitialize(){
        JPanel east = new JPanel();
        east.setPreferredSize(new Dimension(25,50));
        east.setBackground(Color.pink);

        JPanel west = new JPanel();
        west.setPreferredSize(new Dimension(25,50));
        west.setBackground(Color.pink);

        JButton backButton = new JButton("");
        String button_st = "<html><p style=\"font-size:120%;\">Back</p></html>";
        backButton.setText(button_st);
        backButton.setBackground(Color.black);
        backButton.setForeground(Color.pink);
        backButton.setPreferredSize(new Dimension(150,50));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Info.super.dispose();
                malomGame.menus.Menu m = new Menu();
            }
        });

        JPanel south = new JPanel();
        south.setBackground(Color.pink);
        south.add(backButton);

        add(east, BorderLayout.EAST);
        add(south, BorderLayout.SOUTH);
        add(west, BorderLayout.WEST);
    }
}
