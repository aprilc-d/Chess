package assingment2;

public class Queen extends Piece{
	
    public Queen(int x, int y, Side side, Board board) {
    	super(x, y, side, board);
    	
    }

    @Override
    public boolean canMove(int destX, int destY) {
    	
    	int holder1 = Math.abs(this.x-destX);
    	int holder2 = this.x;
    	int holder3 = this.y;
    	
    	if (holder1 == Math.abs(this.y-destY)|| holder2 == destX || holder3 == destY) return true;
    	
    	else return false;
    }

    @Override
    public String getSymbol() {
    	
    	if (this.getSide() == Side.BLACK) {
    		return "♛";
    		
    	}
    	
    	else return "♕";
    	
    }

}
