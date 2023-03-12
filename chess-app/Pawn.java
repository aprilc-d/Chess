package assingment2;

public class Pawn extends Piece{

    public Pawn(int x, int y, Side side, Board board) {
    	super(x, y, side, board);
    	
    }

    @Override
    public boolean canMove(int destX, int destY) {
    
    	
    	
    	Piece p = board.get(this.x, this.y);
    	Piece p2 = board.get(destX, destY);
    	
    	// if the destination space is occupied
    	
    	if( p != null && p2 != null && p2.x == destX && p2.y == destY) {
    		
    		//in front
    		
    		if (this.x == p2.x) return false;
    		
    		//diagonal eating 
    		
    		if (p.getSide() == Side.BLACK) {
    			if ((this.x + 1 == destX || this.x - 1 == destX) && this.y + 1 == destY && p2.getSide() == Side.WHITE) return true;
    			
    		}
    		
    		if (p.getSide() == Side.WHITE) {
    			if ((this.x + 1 == destX || this.x - 1 == destX) && this.y - 1 == destY && p2.getSide() == Side.BLACK) return true;
    			
    		}
    		
    		
    	}
    
    	if (p.getSide() == Side.WHITE) {
    	
    		if (this.y == 6 && destY == this.y - 2 && x == destX) {
    			return true;
    			
    		}
    		
    		if (destY == y - 1  && x == destX) {
    			return true;
    			
    		}
    		
    	
    		
    	}
    	
    	
    	else if (p.getSide() == Side.BLACK) {
    		
    		if (this.y == 1 && destY == this.y + 2 && x == destX) {
    			return true;
    			
    		}
    		
    		if (destY == y + 1  && x == destX) {
    			return true;
    			
    		}
    		
    	}
    	
    	return false;
    }

    @Override
    public String getSymbol() {
    	
    	if (this.getSide() == Side.BLACK) {
    		return "♟";
    		
    	}
    	
    	else return "♙";
    	
    }

}


