package malomGame.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class PlayingField extends JPanel  implements Serializable {
    public final List<ButtonPiece> buttonPieceList = new ArrayList<>(26);
    private final List<Connections> connectionsList = new ArrayList<>(32);
    private final List<JLabel> labelList = new ArrayList<>(2);
    private final List<List<Integer>> rowMillList = new ArrayList<>();
    private final List<List<Integer>> columMillList = new ArrayList<>();
    private final Mode mode;
    public final TwoPlayerMode twoPlayerMode;
    public final OnePlayerMode onePlayerMode;

    /**
     * A játék terület létrehozása.
     * @param m - Az átadott játékmód.
     */
    public PlayingField(Mode m){
        setBackground(new Color(222, 234, 229 ));
        boardInitialize();

        mode = m;

        setRowMillList();
        setColumMillList();

        twoPlayerMode = new TwoPlayerMode(buttonPieceList, connectionsList, labelList, rowMillList, columMillList);
        onePlayerMode = new OnePlayerMode(buttonPieceList,connectionsList,labelList,rowMillList, columMillList);
    }

    public void refreshButtons(){
        for(ButtonPiece piece : buttonPieceList){
            piece.addActionListener(new EventHandler());
        }
    }

    /**
     * A tábla felépítése gombok segítségével.
     * Az egyedi gombok beállításainak elvégzése.
     */
    private void boardInitialize(){
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        int count = 0;
        for(int i = 0; i < 17; i++){
            for(int j = 0; j < 13; j++){
                if(buttonPlacement(i, j)){
                    ButtonPiece button = new ButtonPiece(count);
                    constraintsSet(constraints, i, j);
                    add(button, constraints);
                    buttonPieceList.add(button);
                    EventHandler eventHandler = new EventHandler();
                    button.addActionListener(eventHandler);
                    count++;
                }
                //Spaces
                else{
                    JLabel label = new JLabel(" ");
                    label.setPreferredSize(new Dimension(40,40));
                    constraintsSet(constraints, i, j);
                    add(label, constraints);
                    if((i == 0 || i == 16) && j == 1){
                        labelList.add(label);
                    }
                }
            }
        }
        buttonPieceList.get(0).setBackground(Color.WHITE);
        buttonPieceList.get(0).setCondition(ButtonCondition.WHITE);
        buttonPieceList.get(0).setText(String.valueOf(9));
        buttonPieceList.get(0).setForeground(Color.BLACK);

        buttonPieceList.get(25).setBackground(Color.BLACK);
        buttonPieceList.get(25).setCondition(ButtonCondition.BLACK);
        buttonPieceList.get(25).setText(String.valueOf(9));
        buttonPieceList.get(25).setForeground(Color.WHITE);

        String st = "<html><p style=\"text-align:center;\">Your<br>turn!</p></html>";
        labelList.get(0).setText(st);
        labelList.get(0).setHorizontalAlignment(SwingConstants.CENTER);
        labelList.get(0).setVisible(true);
        labelList.get(1).setText(st);
        labelList.get(1).setHorizontalAlignment(SwingConstants.CENTER);
        labelList.get(1).setVisible(false);

        setConnectionsList();
    }

    /**
     * A feltételek, ami szerint helyezzük a gombokat.
     * @param i - az x tengelyen lévő kordináta.
     * @param j - az y tengelyen lévő kordináta.
     * @return Igaz/hamis a feltételnek megfelelően.
     */
    private boolean buttonPlacement(int i, int j){
        return  ((i == 4 || i == 12) && (j == 2 || j == 6 || j == 10))
                || ((i == 2 || i == 14) && (j == 0 || j == 6 || j == 12))
                || ((i == 6 || i == 10) && (j == 4 || j == 6 || j == 8))
                || (i == 8 && (j == 0 || j == 2 || j == 4 || j == 8 || j == 10 || j == 12))
                || ((i == 0 || i == 16) && j == 0);
    }

    /**
     * A gombokhoz tartozó kényszer beállítása.
     * @param constraints - a kényszer változója.
     * @param i - kordináta x megfelelője.
     * @param j - kordináta y megfelelője.
     */
    private void constraintsSet(GridBagConstraints constraints ,int i, int j){
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = i;
        constraints.gridy = j;
    }

    /**
     * A kapcsolatok beállítása, amely meghatározza az egyes mezők közötti kapcsolatot.
     */
    private void setConnectionsList(){
        connectionsList.add(new Connections(buttonPieceList.get(1), buttonPieceList.get(2)));
        connectionsList.add(new Connections(buttonPieceList.get(1), buttonPieceList.get(10)));
        connectionsList.add(new Connections(buttonPieceList.get(2), buttonPieceList.get(3)));
        connectionsList.add(new Connections(buttonPieceList.get(2), buttonPieceList.get(5)));
        connectionsList.add(new Connections(buttonPieceList.get(3), buttonPieceList.get(15)));
        connectionsList.add(new Connections(buttonPieceList.get(4), buttonPieceList.get(5)));
        connectionsList.add(new Connections(buttonPieceList.get(4), buttonPieceList.get(11)));
        connectionsList.add(new Connections(buttonPieceList.get(5), buttonPieceList.get(6)));
        connectionsList.add(new Connections(buttonPieceList.get(5), buttonPieceList.get(8)));
        connectionsList.add(new Connections(buttonPieceList.get(6), buttonPieceList.get(14)));
        connectionsList.add(new Connections(buttonPieceList.get(7), buttonPieceList.get(8)));
        connectionsList.add(new Connections(buttonPieceList.get(7), buttonPieceList.get(12)));
        connectionsList.add(new Connections(buttonPieceList.get(8), buttonPieceList.get(9)));
        connectionsList.add(new Connections(buttonPieceList.get(9), buttonPieceList.get(13)));
        connectionsList.add(new Connections(buttonPieceList.get(10), buttonPieceList.get(11)));
        connectionsList.add(new Connections(buttonPieceList.get(10), buttonPieceList.get(22)));
        connectionsList.add(new Connections(buttonPieceList.get(11), buttonPieceList.get(12)));
        connectionsList.add(new Connections(buttonPieceList.get(11), buttonPieceList.get(19)));
        connectionsList.add(new Connections(buttonPieceList.get(12), buttonPieceList.get(16)));
        connectionsList.add(new Connections(buttonPieceList.get(13), buttonPieceList.get(14)));
        connectionsList.add(new Connections(buttonPieceList.get(13), buttonPieceList.get(18)));
        connectionsList.add(new Connections(buttonPieceList.get(14), buttonPieceList.get(15)));
        connectionsList.add(new Connections(buttonPieceList.get(14), buttonPieceList.get(21)));
        connectionsList.add(new Connections(buttonPieceList.get(15), buttonPieceList.get(24)));
        connectionsList.add(new Connections(buttonPieceList.get(16), buttonPieceList.get(17)));
        connectionsList.add(new Connections(buttonPieceList.get(17), buttonPieceList.get(18)));
        connectionsList.add(new Connections(buttonPieceList.get(17), buttonPieceList.get(20)));
        connectionsList.add(new Connections(buttonPieceList.get(19), buttonPieceList.get(20)));
        connectionsList.add(new Connections(buttonPieceList.get(20), buttonPieceList.get(21)));
        connectionsList.add(new Connections(buttonPieceList.get(20), buttonPieceList.get(23)));
        connectionsList.add(new Connections(buttonPieceList.get(22), buttonPieceList.get(23)));
        connectionsList.add(new Connections(buttonPieceList.get(23), buttonPieceList.get(24)));
    }

    /**
     * A sorokbal lévő malom lehetőségek helyeinek elmentése.
     */
    private void setRowMillList(){
        List<Integer> row1 = new ArrayList<>();
        row1.add(1);
        row1.add(10);
        row1.add(22);
        rowMillList.add(row1);

        List<Integer> row2 = new ArrayList<>();
        row2.add(4);
        row2.add(11);
        row2.add(19);
        rowMillList.add(row2);

        List<Integer> row3 = new ArrayList<>();
        row3.add(7);
        row3.add(12);
        row3.add(16);
        rowMillList.add(row3);

        List<Integer> row4 = new ArrayList<>();
        row4.add(9);
        row4.add(13);
        row4.add(18);
        rowMillList.add(row4);

        List<Integer> row5 = new ArrayList<>();
        row5.add(6);
        row5.add(14);
        row5.add(21);
        rowMillList.add(row5);

        List<Integer> row6 = new ArrayList<>();
        row6.add(3);
        row6.add(15);
        row6.add(24);
        rowMillList.add(row6);

        List<Integer> row7 = new ArrayList<>();
        row7.add(2);
        row7.add(5);
        row7.add(8);
        rowMillList.add(row7);

        List<Integer> row8= new ArrayList<>();
        row8.add(17);
        row8.add(20);
        row8.add(23);
        rowMillList.add(row8);
    }

    /**
     * Az oszlopokban lévő malom lehetőségek helyeinek elmentése.
     */
    private void setColumMillList(){
        List<Integer> colum1 = new ArrayList<>();
        colum1.add(1);
        colum1.add(2);
        colum1.add(3);
        columMillList.add(colum1);

        List<Integer> colum2 = new ArrayList<>();
        colum2.add(4);
        colum2.add(5);
        colum2.add(6);
        columMillList.add(colum2);

        List<Integer> colum3 = new ArrayList<>();
        colum3.add(7);
        colum3.add(8);
        colum3.add(9);
        columMillList.add(colum3);

        List<Integer> colum4 = new ArrayList<>();
        colum4.add(10);
        colum4.add(11);
        colum4.add(12);
        columMillList.add(colum4);

        List<Integer> colum5 = new ArrayList<>();
        colum5.add(13);
        colum5.add(14);
        colum5.add(15);
        columMillList.add(colum5);

        List<Integer> colum6 = new ArrayList<>();
        colum6.add(16);
        colum6.add(17);
        colum6.add(18);
        columMillList.add(colum6);

        List<Integer> colum7 = new ArrayList<>();
        colum7.add(19);
        colum7.add(20);
        colum7.add(21);
        columMillList.add(colum7);

        List<Integer> colum8= new ArrayList<>();
        colum8.add(22);
        colum8.add(23);
        colum8.add(24);
        columMillList.add(colum8);
    }

    /**
     * A kapcsolatok megrajzolása, hogy a pálya kirajzolódjon.
     * @param g - a felülíráshoz használt változó.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.PINK);
        g2d.setStroke(new BasicStroke(5));
        for (Connections connection : connectionsList) {
            JButton source = connection.getSource();
            JButton dest = connection.getDestination();

            if (source.getX() == dest.getX()) {
                // Same column...
                g2d.drawLine(source.getX() + source.getWidth() / 2, source.getY() + source.getWidth() / 2,
                        dest.getX() + source.getWidth() / 2, dest.getY() + source.getWidth() / 2 );
            } else if (source.getY() == dest.getY()) {
                // Same row...
                g2d.drawLine(source.getX()  + source.getHeight() / 2, source.getY() + source.getHeight() / 2,
                        dest.getX() +  source.getHeight() / 2, dest.getY() + dest.getHeight() / 2);
            }
        }
        g2d.dispose();
    }
    /**
     * Osztály az események megvalósítására.
     */
    public class EventHandler implements ActionListener {
        /**
         * Maga az eseményfigyelő felülírva.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = buttonPieceList.indexOf((ButtonPiece) e.getSource());

            switch (mode) {
                case ONEPLAYER:
                    onePlayerMode.onePlayerModeGame(index);
                    break;
                case TWOPLAYER:
                    twoPlayerMode.twoPlayerModeGame(index);
                    break;
            }
        }

    }

}