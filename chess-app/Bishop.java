package assingment2;

public class Bishop extends Piece {
    public Bishop(int x, int y, Side side, Board board) {
    	super(x, y, side, board);
    	
    }

    @Override
    public boolean canMove(int destX, int destY) {
    	
    	int holder = Math.abs(this.x-destX);
    	if (holder == Math.abs(this.y-destY)) return true;
    	
    	else return false;
    }

    @Override
    public String getSymbol() {
    	
    	if (this.getSide() == Side.BLACK) {
    		return "♝";
    		
    	}
    	
    	else return "♗";
    	
    }
}
