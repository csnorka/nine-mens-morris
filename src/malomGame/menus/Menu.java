package malomGame.menus;

import malomGame.game.Board;
import malomGame.game.Mode;
import malomGame.game.PlayingField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * A játék főmenüje.
 */
public class Menu extends JFrame {
    /**
     * Az ablak alap beállításai.
     */
    public Menu() {
        setTitle("Nine Men's Morris");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(700,450);
        setLocationRelativeTo(null);

        gameNameInitialize();
        buttonsInitialize();
        panelInitialize();
    }

    /**
     * A játék címének beállítása és megjelenítése.
     */
    private void gameNameInitialize(){
        JLabel gameName = new JLabel("");
        String st = "<html><p style=\"font-size:400%;\"><b>Nine Men's Morris</b></p></html>";
        gameName.setText(st);


        JPanel panel = new JPanel();
        panel.add(gameName);
        panel.setBackground(Color.pink);
        panel.setPreferredSize(new Dimension(100,80));

        add(panel, BorderLayout.NORTH);
    }
    /**
     * A további gombok beállítása, amivel a menüben lehet navigálni.
     */
    private void buttonsInitialize(){
        JButton game = new JButton("");
        String game_st = "<html><p style=\"font-size:160%;\"><b>New Game</b></p></html>";
        game.setText(game_st);
        game.setAlignmentX(Component.CENTER_ALIGNMENT);
        game.setBackground(Color.black);
        game.setForeground(Color.pink);
        game.setPreferredSize(new Dimension(75,90));
        game.addActionListener(e -> {
            Menu.super.dispose();
            new GameMiniMenu();
        });

        JButton continueButton = new JButton("Continue");
        String continueButtonText = "<html><p style=\"font-size:160%;\"><b>Continue</b></p></html>";
        continueButton.setText(continueButtonText);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setBackground(Color.black);
        continueButton.setForeground(Color.pink);
        continueButton.setPreferredSize(new Dimension(75,90));
        continueButton.addActionListener(e -> {
            PlayingField playingField;
            File file = new File("continueGame.txt");
            if(file.exists()){
                try(FileInputStream fi = new FileInputStream("continueGame.txt")){
                    ObjectInputStream os = new ObjectInputStream(fi);

                    playingField = (PlayingField) os.readObject();

                    Menu.super.dispose();
                    playingField.refreshButtons();
                    new Board(playingField);

                    os.close();

                }catch (FileNotFoundException exception){
                    exception.printStackTrace();
                }catch (IOException exception){
                    exception.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "There is no saved game yet!");
            }
        });

        JButton info = new JButton("");
        String info_st = "<html><p style=\"font-size:160%;\"><b>Game Information</b></p></html>";
        info.setText(info_st);
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.setBackground(Color.black);
        info.setForeground(Color.pink);
        info.setPreferredSize(new Dimension(75,90));
        info.addActionListener(e -> {
            Menu.super.dispose();
            new Info();
        });

        JButton exit = new JButton("List");
        String exit_st = "<html><p style=\"font-size:160%;\"><b>Exit</b></p></html>";
        exit.setText(exit_st);
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.setBackground(Color.black);
        exit.setForeground(Color.pink);
        exit.setPreferredSize(new Dimension(75,90));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.pink);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(game);
        buttonPanel.add(Box.createRigidArea(new Dimension(100,25)));
        buttonPanel.add(continueButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(100,25)));
        buttonPanel.add(info);
        buttonPanel.add(Box.createRigidArea(new Dimension(100,25)));
        buttonPanel.add(exit);

        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * A keretek beállítása.
     */
    private void panelInitialize(){
        JPanel east = new JPanel();
        east.setPreferredSize(new Dimension(100,50));
        east.setBackground(Color.pink);

        JPanel west = new JPanel();
        west.setPreferredSize(new Dimension(100,50));
        west.setBackground(Color.pink);

        JPanel south = new JPanel();
        south.setBackground(Color.pink);
        JLabel created = new JLabel("");
        String created_st = "<html><p style=\"font-size:80%;\"  style=\"color:grey;\"><i>Created by Csabuda Nóra</i></p></html>";
        created.setText(created_st);
        south.add(created);

        add(east, BorderLayout.EAST);
        add(south, BorderLayout.SOUTH);
        add(west, BorderLayout.WEST);
    }
}
