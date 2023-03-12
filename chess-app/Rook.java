package assingment2;

public class Rook extends Piece{

    public Rook(int x, int y, Side side, Board board) {
    	super(x, y, side, board);
    	
    }

    @Override
    public boolean canMove(int destX, int destY) {

    	int holder1 = this.x;
    	int holder2 = this.y;
    	
    	if (holder1 == destX || holder2 == destY) return true;
    	
    	else return false;
    }

    @Override
    public String getSymbol() {
    	
    	if (this.getSide() == Side.BLACK) {
    		return "♜";
    		
    	}
    	
    	else return "♖";
    	
    }

}

