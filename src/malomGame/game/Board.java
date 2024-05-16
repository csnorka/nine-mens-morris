package malomGame.game;

import malomGame.menus.GameMiniMenu;
import malomGame.menus.Menu;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A játék ablakának alapjainak beállításaira létrehozott osztály.
 */
public class Board extends JFrame implements Serializable {
    /**
     *  Az ablak konstans szélessége.
     */
    private static final int WIDTH = 900;
    /**
     * Au ablak konstans magaságga.
     */
    private static final int HEIGHT = 800;
    /**
     * A játék panel.
     */
    public final PlayingField pf;

    /**
     * A tábla inicializálása, új játék konstruktora.
     * @param mode Az a mód amelyet a felhasználó választ: one/two player.
     */
    public Board(Mode mode){
        pf = new PlayingField(mode);

        setTitle("Nine Men's Morris");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        add(pf, BorderLayout.CENTER);
        pack();
        setResizable(false);
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);

        //eastPanel();
        //westPanel();
        northPanel();
    }

    /**
     * A tába inicializálása. Mentett játék folytatása.
     * @param continuePlayingField - az elmentett játék panel változója.
     */
    public Board(PlayingField continuePlayingField){
        pf = continuePlayingField;

        setTitle("Nine Men's Morris");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        add(pf, BorderLayout.CENTER);
        pack();
        setResizable(false);
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);

        //eastPanel();
        //westPanel();
        northPanel();
    }

    public void setButtonView(JButton button){
        button.setPreferredSize(new Dimension(80,50));
        button.setBackground(Color.pink);
        button.setBorderPainted(false);
    }

    /**
     * A felső panel beállítása és komponensek hozzáadása.
     */
    private void northPanel(){
        JPanel north = new JPanel(new GridBagLayout());
        north.setBackground(new Color(222, 234, 229 ));
        north.setPreferredSize(new Dimension(WIDTH, 90));

        List<JButton> buttons = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            if(i % 2 == 0){
                JButton button = new JButton(String.valueOf(i));
                setButtonView(button);
                buttons.add(button);

                north.add(button);
            }
            else{
                JLabel label = new JLabel(" ");
                label.setPreferredSize(new Dimension(70,45));
                label.setBackground(new Color(222, 234, 229 ));

                north.add(label);
            }
        }

        //Vissza a menübe gomb
        String backButtonString = "<html><p \"style=font-size:120%\">Menu</p></hml>";
        buttons.get(0).setText(backButtonString);
        buttons.get(0).addActionListener(e -> {

            int input = JOptionPane.showConfirmDialog(null, "This game will stop. Are you sure? (Or save first :))");
            if(input == 0){
                Board.super.dispose();
                new Menu();
            }
            else{
                JOptionPane.showMessageDialog(null, "The game will continue.");
            }
        });

        //Játék éppeni állásának elmentése
        String saveButtonString = "<html><p \"style=font-size:120%\">Save</p></hml>";
        buttons.get(1).setText(saveButtonString);
        buttons.get(1).addActionListener(e -> {
            try(FileOutputStream fs = new FileOutputStream("continueGame.txt")){

                ObjectOutputStream os = new ObjectOutputStream(fs);
                os.writeObject(pf);
                JOptionPane.showMessageDialog(null, "The game has been saved with the exact settings.");
                os.close();

            }catch (IOException exception){
                exception.printStackTrace();
            }
        }
        );

        //Új játék kezdése
        String newGameString = "<html><p \"style=font-size:120%\"; \"style=text-align:center\";>New Game</p></hml>";
        buttons.get(2).setText(newGameString);
        buttons.get(2).addActionListener(e -> {
            int input = JOptionPane.showConfirmDialog(null, "This game will stop. Are you sure? (Or save first :))");

            if(input == 0){
                Board.super.dispose();
                new GameMiniMenu();
            }
            else {
                JOptionPane.showMessageDialog(null, "The game will continue.");
            }
        });

        add(north, BorderLayout.NORTH);
    }
    /**
     * Baloldali panel beállításai és componensek hozzáadása.
     */
    private void westPanel(){
        JPanel west = new JPanel();
        west.setBackground(new Color(222, 234, 229 ));
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
        west.setPreferredSize(new Dimension(80, HEIGHT));

        String player1String = "<html><p \"style=font-size:120%\">Player 1:</p></hml>";
        JLabel player1 = new JLabel(player1String);
        player1.setAlignmentX(CENTER_ALIGNMENT);

        west.add(player1);

        add(west, BorderLayout.WEST);
    }
    /**
     * Jobboldali panel beállítása és komponensek hozzáadása.
     */
    private void eastPanel(){
        JPanel east = new JPanel();
        east.setBackground(new Color(222, 234, 229 ));
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.setPreferredSize(new Dimension(80, HEIGHT));

        String player2String = "<html><p \"style=font-size:120%\">Player 2:</p></hml>";
        JLabel player2 = new JLabel(player2String);
        player2.setAlignmentX(CENTER_ALIGNMENT);

        east.add(player2);

        add(east, BorderLayout.EAST);
    }
}