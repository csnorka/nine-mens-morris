package malomGame.game;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static malomGame.game.Phase.*;
import static malomGame.game.Phase.FLYING;

/**
 * Két játék mód osztálya
 */
public class TwoPlayerMode implements Serializable {
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
    public TwoPlayerMode(List<ButtonPiece> pieceList, List<Connections> connections, List<JLabel> label,
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
    }

    /**
     * Gombok alaphelyzetbe állítása.
     * Ami EMPTY/READY állapotú visszaállítja alap helyzetbe: EMPTY
     */
    private void reset() {
        for (ButtonPiece piece : buttonPieceList) {
            if (piece.getCondition() == ButtonCondition.EMPTY || piece.getCondition() == ButtonCondition.READY) {
                piece.setBackground(Color.PINK);
                piece.setForeground(Color.PINK);
                piece.setCondition(ButtonCondition.EMPTY);
            }
        }
    }

    /**
     * A játék végén a gombok letiltása.
     */
    private void setDisabledTheEnd(){
        for(ButtonPiece piece : buttonPieceList){
            piece.setEnabled(false);
        }

    }

    /**
     * Az eltávolítás után minden gombot visszaállít alap kinézetre.
     */
    private void removableReset(){
        for(ButtonPiece piece : buttonPieceList){
            piece.setBorderPainted(false);
        }
    }

    /**
     * Épp kinek a köre következik és annak kiírása.
     * És a megfelelő gombok letiltása.
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
     * Ami EMPTY állapotú gomb azt READY-vé teszi a háttér beállításával.
     */
    private void setReady(){
        for (ButtonPiece piece : buttonPieceList) {
            if (piece.getCondition() == ButtonCondition.EMPTY) {
                piece.setCondition(ButtonCondition.READY);
                piece.setBackground(Color.GREEN);
            }
        }
    }

    /**
     * WHITE állapotra állítása a megfelelő gombnak.
     * @param pressedIndex - a gomb indexe, aminek az állapotát változtatjuk.
     */
    private void setWhiteButton(int pressedIndex){
        buttonPieceList.get(pressedIndex).setBackground(Color.WHITE);
        buttonPieceList.get(pressedIndex).setCondition(ButtonCondition.WHITE);
    }

    /**
     * BLACK állapotra állítása a megfelelő gombnak.
     * @param pressedIndex - a gomb indexe, aminek az állapotát változtatjuk.
     */
    private void setBlackButton(int pressedIndex){
        buttonPieceList.get(pressedIndex).setBackground(Color.BLACK);
        buttonPieceList.get(pressedIndex).setCondition(ButtonCondition.BLACK);
    }

    /**
     * REMOVABLE állapotra állítása a megfelelő gomboknak.
     */
    private void setRemovableButton(){
        for(ButtonPiece piece : buttonPieceList){
            if(piece.getRemovable() == Removable.REMOVABLE){
                piece.setBorderPainted(true);
                piece.setBorder(BorderFactory.createLineBorder(Color.RED,3));
            }
        }
    }

    /**
     * Ellenőzi, hogy a letett gombok mind malomban vannak-e.
     * @return Igaz/Hamis
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
     * A malomban lévő állapotok beállítása minden gombra.
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
     * Malom ellenőrzése.
     * @param pressedIndex - a gomb amit malom-ban van-e.
     * @return Igaz/Hamis az alapján, hogy malomban van-e.
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

        setRemovableButton();
        reset();
        millSet();
    }

    /**
     * Megvizsgálja, hogy a fehér játékosnak van-e megléphető lépése.
     * @return - Igaz/ Hamis
     */
    private boolean hasValidMovesWhite(){
        for(int i = 1; i < 25; i++){
            ButtonPiece temp = buttonPieceList.get(i);
            if(temp.getCondition().equals(ButtonCondition.WHITE)){

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
                if(!greenButtons.isEmpty()){
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
     * A mozgatás fázisban a fehér köre.
     * @param pressedIndex - a kiválasztott gomb indexe.
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
        }
    }

    /**
     * A mozgatás fázisban a fekete köre.
     * @param pressedIndex - a kiválasztott gomb indexe.
     */
    private void blackPlacing(int pressedIndex){
        setBlackButton(pressedIndex);
        blackPiece--;
        buttonPieceList.get(25).setText(String.valueOf(blackPiece));

        if(isItMill(pressedIndex)){
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
     * A malom utáni levétele a gombnak és az állapotok visszaállítása.
     * @param pressedIndex - a kiválasztott gomb indexe.
     */
    private void removingButtons(int pressedIndex){
        ButtonPiece pressedButton = buttonPieceList.get(pressedIndex);

        pressedButton.setRemovable(Removable.NOT_REMOVABLE);
        pressedButton.setBackground(Color.PINK);
        pressedButton.setCondition(ButtonCondition.EMPTY);
        for(ButtonPiece piece : buttonPieceList){
            if(piece.getRemovable().equals(Removable.REMOVABLE)){
                piece.setRemovable(Removable.NOT_REMOVABLE);
            }
        }

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
     * A helyezés fázis a játékban.
     * @param pressedIndex - a gomb indexe, amire rakattintunk.
     */
    private void placingPhase(int pressedIndex){
        ButtonPiece pressedButton = buttonPieceList.get(pressedIndex);

        if (pressedButton == buttonPieceList.get(0) || pressedButton == buttonPieceList.get(25)) {
            if(turn == PlayerColor.WHITE && pressedButton.getCondition() == ButtonCondition.BLACK){
                JOptionPane.showMessageDialog(null, "Not your turn!");
            }
            else if(turn == PlayerColor.BLACK && pressedButton.getCondition() == ButtonCondition.WHITE){
                JOptionPane.showMessageDialog(null, "Not your turn!");
            }
            else {
                setReady();
            }
        }
        else if(pressedButton.getCondition().equals(ButtonCondition.EMPTY)){
            JOptionPane.showMessageDialog(null, "There is no piece to move here!");
        }
        else if(turn == PlayerColor.WHITE && pressedButton.getCondition() == ButtonCondition.WHITE
                && (pressedButton != buttonPieceList.get(0) || pressedButton != buttonPieceList.get(25))){
            JOptionPane.showMessageDialog(null, "You can not move this piece yet!");
        }
        else if(turn == PlayerColor.BLACK && pressedButton.getCondition() == ButtonCondition.BLACK
                && (pressedButton != buttonPieceList.get(0) || pressedButton != buttonPieceList.get(25))){
            JOptionPane.showMessageDialog(null, "You can not move this piece yet!");
        }

        if (pressedButton.getCondition() == ButtonCondition.READY) {
            if (turn == PlayerColor.WHITE && whitePiece > 0) {
                whitePlacing(pressedIndex);

            } else if (turn == PlayerColor.BLACK && blackPiece > 0) {
                blackPlacing(pressedIndex);
            }
        }
        if(pressedButton.getRemovable() == Removable.REMOVABLE){
            removingButtons(pressedIndex);
            removableReset();
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
     * A mozgatás fázisa a játékban.
     * @param pressedIndex - a megnyomott gomb indexe.
     */
    private void movingPhase(int pressedIndex){
        ButtonPiece pressedButton = buttonPieceList.get(pressedIndex);

        if((pressedButton.getCondition() == ButtonCondition.WHITE || pressedButton.getCondition() == ButtonCondition.BLACK)
                && pressedButton.getRemovable().equals(Removable.NOT_REMOVABLE)){
            if(turn == PlayerColor.WHITE && pressedButton.getCondition() == ButtonCondition.BLACK){
                JOptionPane.showMessageDialog(null, "Not your turn!");
            }
            else if(turn == PlayerColor.BLACK && pressedButton.getCondition() == ButtonCondition.WHITE){
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
        else if(pressedButton.getCondition().equals(ButtonCondition.EMPTY)){
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
                }
            }
            else{
                setBlackButton(pressedIndex);

                if(isItMill(pressedIndex)){
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
        if(pressedButton.getRemovable().equals(Removable.REMOVABLE)){
            removingButtons(pressedIndex);
            removableReset();
        }
    }

    /**
     * A fehér játékos utolsó fázisa.
     * @param pressedIndex - az éppen megnyomott gomb indexe.
     */
    private void whiteFlying(int pressedIndex){
        ButtonPiece pressedButton = buttonPieceList.get(pressedIndex);

        if(pressedButton.getCondition() == ButtonCondition.WHITE){
            previousIndex = pressedButton.getNumber();

            setReady();
        }
        if(pressedButton.getCondition() == ButtonCondition.READY){
            buttonPieceList.get(previousIndex).setCondition(ButtonCondition.EMPTY);
            buttonPieceList.get(previousIndex).setBackground(Color.PINK);

            setWhiteButton(pressedIndex);

            if(isItMill(pressedIndex)){
                millCheckWhite();
            }
            else{
                turn = PlayerColor.BLACK;
                setLabel();
                reset();
                millSet();
            }
        }
        if(pressedButton.getRemovable().equals(Removable.REMOVABLE)){
            removingButtons(pressedIndex);
            removableReset();
        }
    }

    /**
     * A fekete játékos utolsó fázisa.
     * @param pressedIndex - a megnyomott gomb indexe.
     */
    private void blackFlying(int pressedIndex){
        ButtonPiece pressedButton = buttonPieceList.get(pressedIndex);

        if(pressedButton.getCondition() == ButtonCondition.BLACK){
            previousIndex = pressedButton.getNumber();

            setReady();
        }
        if(pressedButton.getCondition() == ButtonCondition.READY){
            buttonPieceList.get(previousIndex).setCondition(ButtonCondition.EMPTY);
            buttonPieceList.get(previousIndex).setBackground(Color.PINK);

            setBlackButton(pressedIndex);

            if(isItMill(pressedIndex)){
                millCheckBlack();
            }
            else{
                turn = PlayerColor.WHITE;
                setLabel();
                reset();
                millSet();
            }
        }
        if(pressedButton.getRemovable().equals(Removable.REMOVABLE)){
            removingButtons(pressedIndex);
            removableReset();
        }
    }

    /**
     * A nyerő játékos kiírása.
     */
    private void blackIsTheWinner(){
        labelList.get(1).setText("Winner!!");
        labelList.get(0).setText(" ");
        setDisabledTheEnd();
        JOptionPane.showMessageDialog(null, "Black is the winner!");
    }

    /**
     * A nyerő játékos kiírása.
     */
    private void whiteIsTheWinner(){
        labelList.get(0).setText("Winner!!");
        labelList.get(1).setText(" ");
        setDisabledTheEnd();
        JOptionPane.showMessageDialog(null, "White is the winner!");
    }

    /**
     * Két játékos mód kezelése.
     * @param pressedIndex - a megnyomott gomb indexe.
     */
    public void twoPlayerModeGame(int pressedIndex) {
        ButtonPiece pressedButton = buttonPieceList.get(pressedIndex);

        switch (phase) {
            case PLACING:

                placingPhase(pressedIndex);
                break;

            case MOVING:

                movingPhase(pressedIndex);
                if(!hasValidMovesWhite()){
                    blackIsTheWinner();
                    break;
                }
                else if(!hasValidMoveBlack()){
                    whiteIsTheWinner();
                    break;
                }

                blackCount = 0;
                whiteCount = 0;
                for(ButtonPiece piece : buttonPieceList){
                    if(piece.getCondition() == ButtonCondition.BLACK){ blackCount++; }
                    if(piece.getCondition() == ButtonCondition.WHITE){ whiteCount++; }
                }
                if(blackCount == 4 && whiteCount == 4){ phase = FLYING; }
                else if(whiteCount == 4){ phase = WHITE_FLYING; }
                else if(blackCount == 4){ phase = BLACK_FLYING; }

                break;

            case WHITE_FLYING:
                if(turn == PlayerColor.WHITE && pressedButton.getCondition() == ButtonCondition.BLACK
                        && pressedButton.getRemovable().equals(Removable.NOT_REMOVABLE)){
                    JOptionPane.showMessageDialog(null, "Not your turn!");
                }
                else if(turn == PlayerColor.BLACK && pressedButton.getCondition() == ButtonCondition.WHITE
                        && pressedButton.getRemovable().equals(Removable.NOT_REMOVABLE)){
                    JOptionPane.showMessageDialog(null, "Not your turn!");
                }
                else if(pressedButton.getCondition().equals(ButtonCondition.EMPTY)){
                    JOptionPane.showMessageDialog(null, "There is no piece to move here!");
                }
                else{
                    if(turn == PlayerColor.WHITE){
                        whiteFlying(pressedIndex);
                    }
                    else{
                        movingPhase(pressedIndex);
                    }

                }

                blackCount = 0;
                whiteCount = 0;
                for(ButtonPiece piece : buttonPieceList){
                    if(piece.getCondition() == ButtonCondition.BLACK){ blackCount++; }
                    if(piece.getCondition() == ButtonCondition.WHITE){ whiteCount++; }
                }
                if(blackCount == 4){ phase = FLYING; }
                if(blackCount == 3){ whiteIsTheWinner(); }
                if(whiteCount == 3){ blackIsTheWinner(); }

                break;

            case BLACK_FLYING:
                if(turn == PlayerColor.WHITE && pressedButton.getCondition() == ButtonCondition.BLACK
                        && pressedButton.getRemovable().equals(Removable.NOT_REMOVABLE)){
                    JOptionPane.showMessageDialog(null, "Not your turn!");
                }
                else if(turn == PlayerColor.BLACK && pressedButton.getCondition() == ButtonCondition.WHITE
                        && pressedButton.getRemovable().equals(Removable.NOT_REMOVABLE)){
                    JOptionPane.showMessageDialog(null, "Not your turn!");
                }
                else if(pressedButton.getCondition().equals(ButtonCondition.EMPTY)){
                    JOptionPane.showMessageDialog(null, "There is no piece to move here!");
                }
                else{
                    if(turn == PlayerColor.BLACK){
                        blackFlying(pressedIndex);
                    }
                    else{
                        movingPhase(pressedIndex);
                    }
                }

                blackCount = 0;
                whiteCount = 0;
                for(ButtonPiece piece : buttonPieceList){
                    if(piece.getCondition() == ButtonCondition.BLACK){ blackCount++; }
                    if(piece.getCondition() == ButtonCondition.WHITE){ whiteCount++; }
                }
                if(whiteCount == 4){ phase = FLYING; }
                if(blackCount == 3){ whiteIsTheWinner(); }
                if(whiteCount == 3){ blackIsTheWinner(); }

                break;

            case FLYING:
                if(turn == PlayerColor.WHITE && pressedButton.getCondition() == ButtonCondition.BLACK
                        && pressedButton.getRemovable().equals(Removable.NOT_REMOVABLE)){
                    JOptionPane.showMessageDialog(null, "Not your turn!");
                }
                else if(turn == PlayerColor.BLACK && pressedButton.getCondition() == ButtonCondition.WHITE
                        && pressedButton.getRemovable().equals(Removable.NOT_REMOVABLE)){
                    JOptionPane.showMessageDialog(null, "Not your turn!");
                }
                else if(pressedButton.getCondition().equals(ButtonCondition.EMPTY)){
                    JOptionPane.showMessageDialog(null, "There is no piece to move here!");
                }
                else{
                    if(turn == PlayerColor.BLACK){
                        blackFlying(pressedIndex);
                    }
                    else{
                        whiteFlying(pressedIndex);
                    }
                }

                blackCount = 0;
                whiteCount = 0;
                for(ButtonPiece piece : buttonPieceList){
                    if(piece.getCondition().equals(ButtonCondition.BLACK)){ blackCount++; }
                    if(piece.getCondition().equals(ButtonCondition.WHITE)){ whiteCount++; }
                }
                if(blackCount == 3){ whiteIsTheWinner(); }
                if(whiteCount == 3){ blackIsTheWinner(); }

                break;
        }

    }
}
