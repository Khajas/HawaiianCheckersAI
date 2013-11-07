import java.util.ArrayList;


public class Minimax {
	
	private Board board;  //each board instance is a state/node
	private ArrayList<Board> actions;
	private int expandedNodes,
				depth = 0,
				setDepth;
	private char player,
				 opponent;
	
	public int getExpandedNodes(){return expandedNodes;}
	public int getDepth(){return depth;}
	public Minimax(Board board, char player, int setDepth){
		this.board = board;  //represents current state and root node in search tree
		//this.depth = depth;
		this.actions = board.getActions(player);
		this.setDepth = setDepth;
		this.player = player;
		if(player == 'b')
			opponent = 'w';
		else
			opponent = 'b';
	}
	
	public Board getMinimaxAction(char player){
		expandedNodes = 0;
		Board result = null;
		int depth = 0;
		double resultValue = Double.NEGATIVE_INFINITY;
		ArrayList<Board> actions = new ArrayList<Board>(this.actions);
		long startTime = System.nanoTime();
		for(int i = 0; i < actions.size(); i++){
			double value = minValue(actions.get(i), opponent,  depth + 1);
			if(value > resultValue){
				result = actions.get(i);
				resultValue = value;
			}
		}
		long endTime = System.nanoTime();
		long runTime = endTime - startTime;
		double seconds = (double)runTime / 1000000000.0;
		System.out.format("The search took %4.2f seconds\n", seconds);
		return result;	
	}
	
	public double minValue(Board state, char player, int depth){
		expandedNodes++;
		if(depth == setDepth || state.getGameOver()){
			this.depth = depth;
			return state.getUtility(state, player, false);
		}
		ArrayList<Board> actions = new ArrayList<Board>(state.getActions(player));
		double value = Double.POSITIVE_INFINITY;
		for(int i = 0; i < actions.size(); i++){
			value = Math.min(value, maxValue(actions.get(i), this.player, depth + 1));
		}
		
		return value;
	}
	
	public double maxValue(Board state, char player, int depth){
		expandedNodes++;
		if(depth == setDepth || state.getGameOver()){
			this.depth = depth;
			return state.getUtility(state, player, true);
		}
		ArrayList<Board> actions = new ArrayList<Board>(state.getActions(player));
		double value = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < actions.size(); i++){
			value = Math.max(value, minValue(actions.get(i), opponent, depth + 1)); 
		}
		
		return value;
	}

}
