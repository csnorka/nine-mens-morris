package malomGame.game;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static malomGame.game.Phase.*;
import static malomGame.game.Phase.FLYING;

/**
 * Egy játékos mód osztálya.
 */
public class OnePlayerMode implements Serializable {
    private final List<ButtonPiece> buttonPieceList;
    private final   List<Connections> connectionsList;
    private final List<JLabel> labelList;
    private final List<List<Integer>> rowMillList;
    private final List<List<Integer>> columMillList;
    private Phase phase;
    private int turn;
    private int blackPiece;
    private int whitePiece;
    private int whiteCount;
    private int blackCount;
    private int previousIndex;

    /**
     * A változók beállítása.
     * @param pieceList - a gombok listája.
     * @param connections - a kapcsolatok listája.
     * @param label - a használt label-ök listája.
     * @param row - a malomlehetőségek sorban listája.
     * @param colum - a malom lehetőségek oszlopban listája.
     */
    public OnePlayerMode(List<ButtonPiece> pieceList, List<Connections> connections, List<JLabel> label,
                         List<List<Integer>> row, List<List<Integer>> colum) {
        buttonPieceList = pieceList;
        connectionsList = connections;
        labelList = label;
        rowMillList = row;
        columMillList = colum;

        phase = PLACING;
        turn = PlayerColor.WHITE;

        blackPiece = 9;
        whitePiece = 9;
        whiteCount = 0;
        blackCount = 0;

        buttonPieceList.get(25).setEnabled(false);
    }

    /**
     * A játék fázisának lekérése.
     * @return - phase, a játék fázisa.
     */
    public Phase getPhase(){
        return phase;
    }

    /**
     * A játék fázisának beállítása.
     * @param otherPhase - amire állítjuk az állapotot.
     */
    public void setPhase(Phase otherPhase){
        phase = otherPhase;
    }

    /**
     * Kör lekérése, melyik játékos köre van épp.
     * @return - a játékos körének száma: 1/2.
     */
    public int getTurn(){
        return turn;
    }

    /**
     * A következő játékos beállítása.
     * @param otherTurn - amire állítjuk a kört.
     */
    public void setTurn(int otherTurn){
        turn = otherTurn;
    }

    /**
     * A gombok állapotát alapbeállításba állítja.
     */
    private void reset(){
        for(ButtonPiece piece : buttonPieceList){
            if(piece.getCondition().equals(ButtonCondition.EMPTY) || piece.getCondition().equals(ButtonCondition.READY)){
                piece.setCondition(ButtonCondition.EMPTY);
                piece.setBackground(Color.PINK);
            }
        }
    }

    /**
     * A törölhető gombok alaphelyzetbe állítása.
     */
    private void removeButtonReset(){
        for(ButtonPiece piece : buttonPieceList){
            if(piece.getRemovable().equals(Removable.REMOVABLE)){
                piece.setRemovable(Removable.NOT_REMOVABLE);
                piece.setBorderPainted(false);
            }
        }
    }

    /**
     * A játékban maradt bábúk megszámolása.
     */
    private void whiteAndBlackCount(){
        blackCount = 0;
        whiteCount = 0;
        for(ButtonPiece piece : buttonPieceList){
            if(piece.getCondition() == ButtonCondition.BLACK){ blackCount++; }
            if(piece.getCondition() == ButtonCondition.WHITE){ whiteCount++; }
        }
    }

    /**
     * Ha megnyerte a játékos.
     * @param text - a kiírt szöveg.
     */
    private void whiteIsTheWinner(String text){
        labelList.get(0).setText(text);
        labelList.get(1).setText(" ");
        setDisabledTheEnd();
        JOptionPane.showMessageDialog(null, "White is the winner!");
    }

    /**
     * A nyerő játékos kiírása.
     * @param text - a kiírt szövet.
     */
    private void blackIsTheWinner(String text){
        labelList.get(1).setText(text);
        labelList.get(0).setText(" ");
        setDisabledTheEnd();
        JOptionPane.showMessageDialog(null, "Black is the winner!");
    }

    /**
     * A jaték végén a gombok letiltása.
     */
    private void setDisabledTheEnd(){
        for(ButtonPiece piece : buttonPieceList){
            piece.setEnabled(false);
        }
    }

    /**
     * A használt label beállítása.
     */
    private void setLabel(){
        if(turn == PlayerColor.WHITE){
            labelList.get(0).setVisible(true);
            labelList.get(1).setVisible(false);
            buttonPieceList.get(25).setEnabled(false);
            buttonPieceList.get(0).setEnabled(true);
        }
        else{
            labelList.get(0).setVisible(false);
            labelList.get(1).setVisible(true);
            buttonPieceList.get(25).setEnabled(true);
            buttonPieceList.get(0).setEnabled(false);
        }
    }

    /**
     * A megfelelő fázisban elérhető gombok kijelölése.
     */
    private void setReady(){
        for(ButtonPiece piece : buttonPieceList){
            if(piece.getCondition().equals(ButtonCondition.EMPTY)){
                piece.setCondition(ButtonCondition.READY);
                piece.setBackground(Color.GREEN);
            }
        }
    }

    /**
     * A gomb fehérre alakítása, a megfelelő beállításokkal.
     * @param pressedIndex - a megnyomott gomb indexe.
     */
    private void setWhiteButton(int pressedIndex){
        buttonPieceList.get(pressedIndex).setCondition(ButtonCondition.WHITE);
        buttonPieceList.get(pressedIndex).setBackground(Color.WHITE);
    }

    /**
     * Az eltávolítható gombok
     */
    private void setRemovableButton(){
        for(ButtonPiece piece : buttonPieceList){
            if(piece.getRemovable().equals(Removable.REMOVABLE)){
                piece.setBorderPainted(true);
                piece.setBorder(BorderFactory.createLineBorder(Color.RED,3));
            }
        }
    }

    /**
     * VÁizsgálat. hogy a megadott indexű gomb malomban van-e.
     * @param pressedIndex - az épp megnyomott gomb indexe.
     * @return - Igaz/Hamis a megfelelő feltételek alapján.
     */
    private boolean isItMill(int pressedIndex){
        if(!(buttonPieceList.get(pressedIndex).getCondition().equals(ButtonCondition.WHITE) ||
                buttonPieceList.get(pressedIndex).getCondition().equals(ButtonCondition.BLACK))){
            return false;
        }

        java.util.List<Integer> rowList = new ArrayList<>();
        for(java.util.List<Integer> row : rowMillList){
            if(row.contains(pressedIndex)){
                rowList.addAll(row);
                break;
            }
        }
        boolean row1 = buttonPieceList.get(rowList.get(0)).getCondition().equals(buttonPieceList.get(rowList.get(1)).getCondition());
        boolean row3 = buttonPieceList.get(rowList.get(1)).getCondition().equals(buttonPieceList.get(rowList.get(2)).getCondition());

        java.util.List<Integer> columList =  new ArrayList<>();
        for(java.util.List<Integer> colum : columMillList){
            if(colum.contains(pressedIndex)){
                columList.addAll(colum);
                break;
            }
        }
        boolean colum1 = buttonPieceList.get(columList.get(0)).getCondition().equals(buttonPieceList.get(columList.get(1)).getCondition());
        boolean colum3 = buttonPieceList.get(columList.get(1)).getCondition().equals(buttonPieceList.get(columList.get(2)).getCondition());
        if(row1 && row3){
            return true;
        }
        if(colum1 && colum3){
            return true;
        }

        return false;
    }

    /**
     * Annak ellenőrzése, hogy minden gomb malomban van-e.
     * @return - Igaz/Hamis.
     */
    private boolean allInMill(){
        boolean ret = true;
        if(turn == PlayerColor.WHITE){
            for(ButtonPiece piece : buttonPieceList){
                if(piece.getMill().equals(Mill.NOT_IN_MILL) && piece.getCondition().equals(ButtonCondition.BLACK)
                        && piece != buttonPieceList.get(25)){
                    ret = false;
                    break;
                }
            }
        }
        else{
            for(ButtonPiece piece : buttonPieceList){
                if(piece.getMill().equals(Mill.NOT_IN_MILL) && piece.getCondition().equals(ButtonCondition.WHITE)
                        && piece != buttonPieceList.get(0)){
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * A malom állapotának ellenőrzése és beállítása.
     */
    private void millSet(){
        for(int i = 1; i < 25; i++){
            if(isItMill(buttonPieceList.get(i).getNumber())){
                buttonPieceList.get(i).setMill(Mill.IN_MILL);
            }
            else{
                buttonPieceList.get(i).setMill(Mill.NOT_IN_MILL);
            }
        }
    }

    /**
     * Malom ellenőrzése fehér lépés esetén.
     */
    private void millCheckWhite(){
        if(allInMill()){
            for(ButtonPiece piece : buttonPieceList){
                if(piece.getCondition() == ButtonCondition.BLACK && piece != buttonPieceList.get(25)){
                    piece.setRemovable(Removable.REMOVABLE);
                }
            }
        }else{
            for(ButtonPiece piece : buttonPieceList){
                if(piece.getMill() == Mill.NOT_IN_MILL && piece.getCondition() == ButtonCondition.BLACK
                        && piece != buttonPieceList.get(25)){
                    piece.setRemovable(Removable.REMOVABLE);
                }
            }
        }

        setRemovableButton();
        reset();
        millSet();
    }

    /**
     * Malom ellenőrzése és folyamat folyatatása fekete lépés esetén.
     */
    private void millCheckBlack(){
        if(allInMill()){
            for(ButtonPiece piece : buttonPieceList){
                if(piece.getCondition().equals(ButtonCondition.WHITE)  && piece != buttonPieceList.get(0)){
                    piece.setRemovable(Removable.REMOVABLE);
                }
            }
        }
        else{
            for(ButtonPiece piece : buttonPieceList){
                if(piece.getMill() == Mill.NOT_IN_MILL && piece.getCondition() == ButtonCondition.WHITE
                        && piece != buttonPieceList.get(0)){
                    piece.setRemovable(Removable.REMOVABLE);
                }
            }
        }

        reset();
        millSet();

        List<Integer> removableButtons = new ArrayList<>();
        for(ButtonPiece piece : buttonPieceList){
            if(piece.getRemovable().equals(Removable.REMOVABLE) && piece.getCondition().equals(ButtonCondition.WHITE)){
                removableButtons.add(piece.getNumber());
            }
        }

        Random random = new Random();
        int rand = random.nextInt(removableButtons.size());
        buttonPieceList.get(removableButtons.get(rand)).setCondition(ButtonCondition.EMPTY);
        buttonPieceList.get(removableButtons.get(rand)).setBackground(Color.PINK);

        System.out.println("The removed white: " + buttonPieceList.get(removableButtons.get(rand)).getNumber());

        removeButtonReset();

        turn = PlayerColor.WHITE;
        reset();
        setLabel();
        millSet();
    }

    /**
     * Leellenőrzi, hogy a fehér játékosnak van-e megléphető lépése.
     * @return - Igaz/Hamis
     */
    private boolean hasValidMovesWhite() {
        for (int i = 1; i < 25; i++) {
            ButtonPiece temp = buttonPieceList.get(i);
            if (temp.getCondition().equals(ButtonCondition.WHITE)) {

                List<ButtonPiece> greenButtons = new ArrayList<>();
                for (Connections connect : connectionsList) {
                    if (connect.getSource() == temp && (connect.getDestination().getCondition() == ButtonCondition.EMPTY
                            || connect.getDestination().getCondition() == ButtonCondition.READY)) {
                        greenButtons.add(connect.getDestination());
                    } else if (connect.getDestination() == temp && (connect.getSource().getCondition() == ButtonCondition.EMPTY
                            || connect.getSource().getCondition() == ButtonCondition.READY)) {
                        greenButtons.add(connect.getSource());
                    }
                }
                if (!greenButtons.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Megvizsgálja, hogy a fekete játékosnak van-e megléphető lépése.
     * @return - Igaz/ Hamis
     */
    private boolean hasValidMoveBlack(){
        for(int i = 1; i < 25; i++){
            ButtonPiece temp = buttonPieceList.get(i);
            if(temp.getCondition().equals(ButtonCondition.BLACK)){

                List<ButtonPiece> greenButtons = new ArrayList<>();
                for (Connections connect : connectionsList) {
                    if (connect.getSource() == temp && (connect.getDestination().getCondition() == ButtonCondition.EMPTY
                            || connect.getDestination().getCondition() == ButtonCondition.READY))  {
                        greenButtons.add(connect.getDestination());
                    } else if (connect.getDestination() == temp && (connect.getSource().getCondition() == ButtonCondition.EMPTY
                            || connect.getSource().getCondition() == ButtonCondition.READY))  {
                        greenButtons.add(connect.getSource());
                    }
                }
                if(!greenButtons.isEmpty()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Egy gomb eltávolítása és utána a gombok alaphelyzetbe állítása.
     * @param pressedIndex - az épp megnyomott gomb, amit el akarunk távolítani.
     */
    private void removingButtons(int pressedIndex){
        ButtonPiece pressedButton = buttonPieceList.get(pressedIndex);

        pressedButton.setRemovable(Removable.NOT_REMOVABLE);
        pressedButton.setBackground(Color.PINK);
        pressedButton.setCondition(ButtonCondition.EMPTY);
        pressedButton.setBorderPainted(false);

        removeButtonReset();

        if(turn == PlayerColor.WHITE){
            turn = PlayerColor.BLACK;
        }
        else{
            turn = PlayerColor.WHITE;
        }

        reset();
        setLabel();
        millSet();
    }

    /**
     * Az első fázisban a fehér lépések.
     * @param pressedIndex - az épp megnyomott gomb indexe.
     */
    private void whitePlacing(int pressedIndex){
        setWhiteButton(pressedIndex);
        whitePiece--;
        buttonPieceList.get(0).setText(String.valueOf(whitePiece));

        if(isItMill(pressedIndex)){
            millCheckWhite();
        }

        else{
            turn = PlayerColor.BLACK;
            reset();
            setLabel();
            millSet();
            blackPlacing();
        }
    }

    /**
     * Az első fázisban lévő fekete lépések.
     */
    private void blackPlacing(){
        List<Integer> emptyButtons = new ArrayList<>();
        for(ButtonPiece piece : buttonPieceList){
            if(piece.getCondition().equals(ButtonCondition.EMPTY) && piece.getNumber() != 0 && piece.getNumber() != 25){
                emptyButtons.add(piece.getNumber());
            }
        }

        Random random = new Random();
        int rand = random.nextInt(emptyButtons.size());

        ButtonPiece newBlack = buttonPieceList.get(emptyButtons.get(rand));

        newBlack.setCondition(ButtonCondition.BLACK);
        newBlack.setBackground(Color.BLACK);
        blackPiece--;
        buttonPieceList.get(25).setText(String.valueOf(blackPiece));

        System.out.println("Black moved to here " + newBlack.getNumber());

        if(isItMill(newBlack.getNumber())){
            System.out.println("Black MILL!");
            millCheckBlack();
        }

        else{
            turn = PlayerColor.WHITE;
            reset();
            setLabel();
            millSet();
        }
    }

    /**
     * Az első fázis, a mozgatás megvalasósítása.
     * @param pressedIndex - a megnyomott gomb indexe.
     */
    private void placingPhase(int pressedIndex){
        ButtonPiece pressedButton = buttonPieceList.get(pressedIndex);

        if (pressedButton == buttonPieceList.get(0) && pressedButton.getRemovable().equals(Removable.NOT_REMOVABLE)) {
            if(turn == PlayerColor.WHITE && pressedButton.getCondition() == ButtonCondition.BLACK){
                JOptionPane.showMessageDialog(null, "Not your turn!");
            }
            else if(pressedButton.getCondition().equals(ButtonCondition.EMPTY)){
                JOptionPane.showMessageDialog(null, "There is no piece there to move!");
            }
            else {
                setReady();
            }
        }
        else if(pressedButton.getCondition().equals(ButtonCondition.EMPTY)) {
            JOptionPane.showMessageDialog(null, "There is no piece to move here!");
        }

        if(pressedButton.getCondition().equals(ButtonCondition.READY)){
            if (turn == PlayerColor.WHITE && whitePiece > 0) {
                whitePlacing(pressedIndex);
            }

        }

        if(pressedButton.getRemovable() == Removable.REMOVABLE){
            removingButtons(pressedIndex);
            removeButtonReset();

            turn = PlayerColor.BLACK;
            blackPlacing();
        }

        if(whitePiece == 0){
            buttonPieceList.get(0).setEnabled(false);
            buttonPieceList.get(0).setText("");
        }
        if(blackPiece == 0){
            buttonPieceList.get(25).setEnabled(false);
            buttonPieceList.get(25).setText("");
        }

        if(whitePiece == 0 && blackPiece == 0){ phase = MOVING; }
    }

    /**
     * Összegyűjti egy megadott indexű feketének a lépése lehetőségeit.
     * @param pressedIndex - az épp kiszemelt fekete.
     * @return - egy lista a fekete elérhető szomszéfjairól
     */
    private List<ButtonPiece> blackSearching(int pressedIndex){
        ButtonPiece pressedButton = buttonPieceList.get(pressedIndex);

        List<ButtonPiece> greenButtons = new ArrayList<>();

        reset();
        previousIndex = pressedButton.getNumber();
        for (Connections connect : connectionsList) {
            if((pressedButton.getCondition() == ButtonCondition.BLACK && turn == PlayerColor.BLACK)){
                if (connect.getSource() == pressedButton && connect.getDestination().getCondition() == ButtonCondition.EMPTY) {
                    greenButtons.add(connect.getDestination());
                } else if (connect.getDestination() == pressedButton && connect.getSource().getCondition() == ButtonCondition.EMPTY) {
                    greenButtons.add(connect.getSource());
                }
            }

        }
        return greenButtons;
    }

    /**
     * A fekete lépése a mozgatás fázisában.
     */
    private void blackMoving(){
        List<Integer> blackButtons = new ArrayList<>();
        for(ButtonPiece piece : buttonPieceList){
            if(piece.getCondition().equals(ButtonCondition.BLACK) && piece.getNumber() != 25){
                blackButtons.add(piece.getNumber());
            }
        }
        Random rand = new Random();
        int random = rand.nextInt(blackButtons.size());
        ButtonPiece pressedButton = buttonPieceList.get(blackButtons.get(random));
        System.out.println("Black moving from: " + pressedButton.getNumber());

        List<ButtonPiece> selected = blackSearching(pressedButton.getNumber());

        if(selected.isEmpty()){
            blackMoving();
        }
        else{
            int otherRandom = rand.nextInt(selected.size());
            ButtonPiece newBlack = buttonPieceList.get(selected.get(otherRandom).getNumber());
            System.out.println("Black moved to: " + newBlack.getNumber());

            buttonPieceList.get(previousIndex).setCondition(ButtonCondition.EMPTY);
            buttonPieceList.get(previousIndex).setBackground(Color.PINK);

            newBlack.setCondition(ButtonCondition.BLACK);
            newBlack.setBackground(Color.BLACK);

            if(isItMill(newBlack.getNumber())){
                millCheckBlack();
            }
            else{
                turn = PlayerColor.WHITE;
                setLabel();
                reset();
                millSet();
            }
        }

    }

    /**
     * A mozgatás fázisa a játékban.
     * @param pressedIndex - a megnyomott gomb indexe.
     */
    private void movingPhase(int pressedIndex){
        ButtonPiece pressedButton = buttonPieceList.get(pressedIndex);

        if(turn == PlayerColor.BLACK && phase.equals(BLACK_FLYING)){
            blackFlying();
        }
        if(pressedButton.getCondition() == ButtonCondition.WHITE && pressedButton.getRemovable().equals(Removable.NOT_REMOVABLE)){
            if(turn == PlayerColor.WHITE && pressedButton.getCondition() == ButtonCondition.BLACK){
                JOptionPane.showMessageDialog(null, "Not your turn!");
            }
            else{
                List<ButtonPiece> greenButtons = new ArrayList<>();
                reset();
                previousIndex = pressedButton.getNumber();
                for (Connections connect : connectionsList) {
                    if((pressedButton.getCondition() == ButtonCondition.BLACK && turn == PlayerColor.BLACK) ||
                            (pressedButton.getCondition() == ButtonCondition.WHITE && turn == PlayerColor.WHITE)){
                        if (connect.getSource() == pressedButton && connect.getDestination().getCondition() == ButtonCondition.EMPTY) {
                            greenButtons.add(connect.getDestination());
                        } else if (connect.getDestination() == pressedButton && connect.getSource().getCondition() == ButtonCondition.EMPTY) {
                            greenButtons.add(connect.getSource());
                        }
                    }

                }
                for (ButtonPiece piece : greenButtons) {
                    piece.setBackground(Color.GREEN);
                    piece.setCondition(ButtonCondition.READY);
                }
            }
        }
        else if(pressedButton.getCondition().equals(ButtonCondition.EMPTY)) {
            JOptionPane.showMessageDialog(null, "There is no piece to move here!");
        }

        if (pressedButton.getCondition() == ButtonCondition.READY) {
            buttonPieceList.get(previousIndex).setCondition(ButtonCondition.EMPTY);
            buttonPieceList.get(previousIndex).setBackground(Color.PINK);

            if(turn == PlayerColor.WHITE){
                setWhiteButton(pressedIndex);

                if(isItMill(pressedIndex)){
                    millCheckWhite();
                }
                else{
                    turn = PlayerColor.BLACK;
                    setLabel();
                    reset();
                    millSet();
                    if(phase.equals(BLACK_FLYING)){
                        blackFlying();
                    }
                    else{
                        blackMoving();
                    }
                }
            }
        }
        if(pressedButton.getRemovable().equals(Removable.REMOVABLE)){
            removingButtons(pressedIndex);
            blackCount = 0;
            for(ButtonPiece piece : buttonPieceList){
                if(piece.getCondition().equals(ButtonCondition.BLACK)){
                    blackCount++;
                }
            }
            if(blackCount < 4){
                whiteIsTheWinner("Winner!!");
            }
            else if(!phase.equals(BLACK_FLYING)){
                blackMoving();
            }
            else{
                blackFlying();
            }
        }
    }

    /**
     * A fehér az utolsó fázisban.
     * @param pressedIndex - a megnyomott gomb indexe.
     */
    private void whiteFlying(int pressedIndex) {
        ButtonPiece pressedButton = buttonPieceList.get(pressedIndex);

        if (pressedButton.getCondition() == ButtonCondition.WHITE) {
            previousIndex = pressedButton.getNumber();

            setReady();
        }
        if (pressedButton.getCondition() == ButtonCondition.READY) {
            buttonPieceList.get(previousIndex).setCondition(ButtonCondition.EMPTY);
            buttonPieceList.get(previousIndex).setBackground(Color.PINK);

            setWhiteButton(pressedIndex);

            if (isItMill(pressedIndex)) {
                millCheckWhite();
            } else {
                setLabel();
                reset();
                millSet();

                if(phase.equals(WHITE_FLYING)){
                    blackMoving();
                }
                else if(phase.equals(FLYING)){
                    blackFlying();
                }
            }
        }
        if (pressedButton.getRemovable().equals(Removable.REMOVABLE)) {
            removingButtons(pressedIndex);
            blackCount = 0;
            for(ButtonPiece piece : buttonPieceList){
                if(piece.getCondition().equals(ButtonCondition.BLACK)){
                    blackCount++;
                }
            }
            if(blackCount < 4){
                whiteIsTheWinner("Winner!!");
            }
            else if(phase.equals(WHITE_FLYING)){
                blackMoving();
            }
            else if(phase.equals(FLYING)){
                blackFlying();
            }
        }
    }

    /**
     * Fekete lépése az utolsó fázisban.
     */
    private void blackFlying(){
        List<Integer> blackButtons = new ArrayList<>();
        for(ButtonPiece piece : buttonPieceList){
            if(piece.getCondition().equals(ButtonCondition.BLACK) && piece != buttonPieceList.get(25)){
                blackButtons.add(piece.getNumber());
            }
        }
        Random random = new Random();
        int firstRandom = random.nextInt(blackButtons.size());

        ButtonPiece selectedButton = buttonPieceList.get(blackButtons.get(firstRandom));
        System.out.println("The black is moving from: " + selectedButton.getNumber());

        List<Integer> emptyButtons = new ArrayList<>();
        for(ButtonPiece piece : buttonPieceList){
            if(piece.getCondition().equals(ButtonCondition.EMPTY)){
                emptyButtons.add(piece.getNumber());
            }
        }
        int secondRandom = random.nextInt(emptyButtons.size());

        ButtonPiece newBlack = buttonPieceList.get(emptyButtons.get(secondRandom));

        selectedButton.setCondition(ButtonCondition.EMPTY);
        selectedButton.setBackground(Color.PINK);
        newBlack.setCondition(ButtonCondition.BLACK);
        newBlack.setBackground(Color.BLACK);

        if(isItMill(newBlack.getNumber())){
            millCheckBlack();
        }
        else{
            turn = PlayerColor.WHITE;
            setLabel();
            reset();
            millSet();
        }
    }

    /**
     * Az egyjátékos mód játék menete.
     * @param pressedIndex - az épp megnyomot gomb indexe.
     */
    public void onePlayerModeGame(int pressedIndex) {
        ButtonPiece pressedButton = buttonPieceList.get(pressedIndex);
        String text = "<html><p style=\"text-align:center;\">Winner!!</p></html>";

        switch (phase) {
            case PLACING:

                placingPhase(pressedIndex);
                break;

            case MOVING:

                movingPhase(pressedIndex);


                whiteAndBlackCount();
                if(blackCount == 4 && whiteCount == 4){ phase = FLYING; }
                else if(whiteCount == 4){ phase = WHITE_FLYING; }
                else if(blackCount == 4){ phase = BLACK_FLYING; }

                break;

            case WHITE_FLYING:

                if(turn == PlayerColor.WHITE && pressedButton.getCondition() == ButtonCondition.BLACK
                        && pressedButton.getRemovable().equals(Removable.NOT_REMOVABLE)){
                    JOptionPane.showMessageDialog(null, "Not your turn!");
                }
                else if(pressedButton.getCondition().equals(ButtonCondition.EMPTY)) {
                    JOptionPane.showMessageDialog(null, "There is no piece to move here!");
                }
                else{
                    whiteFlying(pressedIndex);
                }

                whiteAndBlackCount();
                if(blackCount == 4){ phase = FLYING; }
                if(whiteCount == 3){ blackIsTheWinner(text); }

                break;

            case BLACK_FLYING:
                if(turn == PlayerColor.WHITE && pressedButton.getCondition() == ButtonCondition.BLACK
                        && pressedButton.getRemovable().equals(Removable.NOT_REMOVABLE)){
                    JOptionPane.showMessageDialog(null, "Not your turn!");
                }
                else if(pressedButton.getCondition().equals(ButtonCondition.EMPTY)) {
                    JOptionPane.showMessageDialog(null, "There is no piece to move here!");
                }
                else{
                    movingPhase(pressedIndex);
                }

                whiteAndBlackCount();
                if(whiteCount == 4){ phase = FLYING; }
                if(whiteCount == 3){ blackIsTheWinner(text); }

                break;

            case FLYING:
                if(turn == PlayerColor.WHITE && pressedButton.getCondition() == ButtonCondition.BLACK
                        && pressedButton.getRemovable().equals(Removable.NOT_REMOVABLE)){
                    JOptionPane.showMessageDialog(null, "Not your turn!");
                }
                else if(pressedButton.getCondition().equals(ButtonCondition.EMPTY)) {
                    JOptionPane.showMessageDialog(null, "There is no piece to move here!");
                }
                else{
                    whiteFlying(pressedIndex);
                }

               whiteAndBlackCount();
                if(blackCount == 3){ whiteIsTheWinner(text); }
                if(whiteCount == 3){ blackIsTheWinner(text); }

                break;
        }

    }
}
