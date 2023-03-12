package assingment2;

public class King extends Piece{

    public King(int x, int y, Side side, Board board) {
    	super(x, y, side, board);
    	
    }

    @Override
    public boolean canMove(int destX, int destY) {
    	
    	int holder1 = Math.abs(x - destX);
    	int holder2 = Math.abs(y - destY);
    	
    	
    	if (holder1 <= 1 && holder2 <= 1) return true;
    	
    	else return false;
    }

    @Override
    public String getSymbol() {
    	
    	if (this.getSide() == Side.BLACK) {
    		return "♚";
    		
    	}
    	
    	else return "♔";
    	
    }
}

