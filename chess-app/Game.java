package assingment2;

import java.util.Stack;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import javax.swing.*;

import static java.lang.Thread.*;

public class Game{

    Board b;
    Stack<String> moveHistory = new Stack<String>();
    Side currentTurn;

    public Game(){
    	Board b = new Board();
    	this.b = b;
    	b.fillBoard();
    	currentTurn = Side.WHITE;
    }

    public void moveBlack() {
            this.findBestMove();
    }

    public boolean findBestMove() {

        //this ai is only used for black, if the turn is white, send an error

        ArrayList<Piece> pieces = (ArrayList<Piece>) this.b.getPieces(Side.BLACK);

        Collections.shuffle(pieces);

        ArrayList<Piece> randomList = new ArrayList<Piece>();

        if (pieces.size() < 5)
            randomList = pieces;

        else {
            for (int i = 0; i < 5; i++) {
                Piece p = pieces.get(i);
                randomList.add(p);
            }
        }


        return this.randomChoice(randomList);


    }

    public boolean randomChoice(ArrayList<Piece> pieces) {

        ArrayList<MoveChoice> moves = new ArrayList<MoveChoice>();

        for (Piece p : pieces) {
            for (int i=0; i <8; i++) {
                for (int j=0; j<8; j++) {
                    if (canMove(p.x, p.y, i, j, Side.BLACK))
                        moves.add(new MoveChoice(p.x, p.y, i, j));
                }
            }

        }
        if (!(moves.isEmpty())) {
            Collections.shuffle(moves);
            MoveChoice m = moves.get(0);
            this.move(m.x, m.y, m.destX, m.destY);
            return true;
        }

       return this.findBestMove();
    }

    public static String getName() {
        return "Queen's Gamebit";
    }

    public boolean canMove(int x, int y, int destX, int destY, Side s){
    	
    	//out of bounds check
    	if (x < 0 || y < 0 || x > 7 || y > 7) return false; 
    	if(destY < 0 || destY > 7) return false;
    	if(destX < 0 || destX > 7) return false;
    	
    	//piece is null
    	Piece p = b.get(x, y);
    	if (p == null) return false;
    	
    	// destination is starting position
    	if(x == destX && y == destY) return false;
    	
    	// checking if piece is right side
    	if (p.getSide() != s) return false;
    	
    	//checking piece rules
    	if (p.canMove(destX, destY) == false) return false;
    	
    	//checking if destination spot is occupied by piece on the same team
    	Piece p2 = b.get(destX, destY);
    	if (p2 != null && p2.getSide() == s) return false;

    	//special knight condition, since knight can jump over other pieces
    	if (!((Piece) p instanceof Knight) && !(isVisible(x, y, destX, destY))) return false;

        return true;
    }

    //if another piece is in the way, helper function for canMove
    private boolean isVisible(int x, int y, int destX, int destY) {
        int diffX = destX - x;
        int diffY = destY - y;

        boolean validCheck = (diffX == 0 || diffY == 0) || (Math.abs(diffX) == Math.abs(diffY));
        if (!validCheck) {
            try {
                throw new Exception("The 'path' between the squares (" + x + ", " + y + ") and (" + destX + ", " + destY +") is undefined");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Diagonal
        if (Math.abs(diffX) == Math.abs(diffY) && Math.abs(diffX) > 1) {
            //Determine direction of move
            int dirX = diffX > 0 ? 1 : -1;
            int dirY = diffY > 0 ? 1 : -1;
            for (int i = x + dirX, j = y + dirY; i != destX && j != destY; i += dirX, j += dirY) {
                if (b.get(i, j) != null) {
                    return false;
                }
            }
        }

        //Linear
        if ((diffX == 0 && Math.abs(diffY) > 1) || (diffY == 0 && Math.abs(diffX) > 1)) {
            if (diffX == 0) {
                int dirY = diffY > 0 ? 1 : -1;
                for (int j = y + dirY; j != destY; j += dirY) {
                    if (b.get(x, j) != null) {
                        return false;
                    }
                }
            } else {

                int dirX = diffX > 0 ? 1 : -1;
                for (int i = x + dirX; i != destX; i += dirX) {
                    if (b.get(i, y) != null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void appendCheckToHistory(Side side){
        moveHistory.push(side.toString()+ " is in check");
    }

    private void appendWinToHistory(Side side){
        moveHistory.push(side.toString()+ " has won");
    }

    //helper function, add valid move to history
    private void appendMoveToHistory(int x, int y, int destX, int destY, Side side) {

        Piece toMove = b.get(x,y);
        Piece toCapture = b.get(destX, destY);

        if (toCapture == null)
            moveHistory.push(side.toString() + toMove.getSymbol() + " at "+ x + ", " + y + " to " + destX + ", " + destY);
        else
            moveHistory.push(side.toString() + toMove.getSymbol() + " at " + x + ", " + y + " captures " + toCapture.getSymbol() + " at " + destX + ", " + destY);
    }

    //false if invalid move, otherwise true
    public boolean move(int x, int y, int destX, int destY) {

        try {
            Thread.sleep(200);
        } catch (Exception e) { }

        //starting position cannot be ending position
    	if(x == destX && y == destY) return false;  
    	
        if (canMove(x, y, destX, destY, currentTurn) == true) {

        	appendMoveToHistory(x, y, destX, destY, currentTurn);
        	b.get(x,y).move(destX, destY);

            //in check
        	if (isInCheck(Side.WHITE)) appendCheckToHistory(Side.WHITE);
        	if (isInCheck(Side.BLACK)) appendCheckToHistory(Side.BLACK);

            //if king was taken, send win message, end game
        	if (b.getKing(Side.negate(this.getCurrentTurn())) == null) {

                //printing win message
                final JDialog winMessage = new JDialog();
                winMessage.setTitle(this.getCurrentTurn().toString() + " has won the game!");
                winMessage.setSize(500,100);
                winMessage.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                JLabel label = new JLabel();
                winMessage.add(label);
                winMessage.setVisible(true);

    			appendWinToHistory(this.getCurrentTurn());
                this.reset();

    	    }

            //switch turns
        	currentTurn = Side.negate(currentTurn);

            if (currentTurn == Side.BLACK) {

                this.moveBlack();


                return true;
            }
                this.moveBlack();

            //return true, move executed as expected
        	return true;
        }

        return false;
    }

    //returns true if in check
    public boolean isInCheck(Side side) {
    	
    	Piece kingChecker = b.getKing(side);

    	for (int i=0; i<8; i++) {
    		for (int j=0; j<8; j++) {
    			
    			Piece checker = b.get(i, j);
    			
    			if (checker != null && kingChecker != null && checker.getSide() != side &&
                        canMove(checker.x, checker.y, kingChecker.x, kingChecker.y, Side.negate(side)))
                    return true;
    			}
    		}

    	return false;
    }

    //reinitializes to new game
    public void reset(){
        while(!moveHistory.empty()){
            System.out.println(moveHistory.pop());
        }
        b.fillBoard();
        currentTurn = Side.WHITE;
    }


    public static void main(String[] args){
        Board b = new Board();
        System.out.println(b);
    }

    //returns array of previous moves
    public String[] moveHistory(){
        String[] out = new String[moveHistory.size()];
        for(int i = 0; i < moveHistory.size(); i++){
            out[i] = moveHistory.get(i);
        }
        return out;
    }

    public Side getCurrentTurn(){
        return currentTurn;
    }
}

