import java.util.ArrayList;

public class AlphaBeta {
	private Board board;  //each board instance is a state/node
	private ArrayList<Board> actions;
	private int expandedNodes,
				depth = 0,
				setDepth;
	private char player,
				 opponent;

	public AlphaBeta(Board board, char player, int setDepth){
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
	
	public int getExpandedNodes(){return expandedNodes;}
	public int getDepth(){return depth;}
	
	public Board getMinimaxAction(char player){
		expandedNodes = 0;
		Board result = null;
		int depth = 0;
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;
		long startTime = System.nanoTime();
		ArrayList<Board> actions = new ArrayList<Board>(this.actions);
		for(int i = 0; i < actions.size(); i++){
			double value = maxValue(actions.get(i), opponent, alpha, beta,  depth + 1);
			//System.out.println(value);
			if(value > alpha){
				result = actions.get(i);
				alpha = value;
			}
		}
		//System.out.println("printing board inside alphabeta");
		//result.printBoard();
		long endTime = System.nanoTime();
		long runTime = endTime - startTime;
		double seconds = (double) runTime / 1000000000.0;
		System.out.format("The search took %4.2f seconds\n", seconds);
		return result;	
	}
	
	public double minValue(Board state, char player, double alpha, double beta, int depth){
		expandedNodes++;
		if(depth == setDepth || state.getGameOver()){
			this.depth = depth;
			return state.getUtility(state, player, false);
		}
		ArrayList<Board> actions = new ArrayList<Board>(state.getActions(player));
		double value = Double.POSITIVE_INFINITY;
		for(int i = 0; i < actions.size(); i++){
			value = Math.min(value, maxValue(actions.get(i), this.player, alpha, beta, depth + 1));
			//System.out.print("Value returned by maxValue is ");
			//System.out.println(value);
			if(value <= alpha)
				return value;
			beta = Math.min(value, beta);
		}
		return value;
	}
	
	public double maxValue(Board state, char player, double alpha, double beta, int depth){
		expandedNodes++;
		if(depth == setDepth || state.getGameOver()){
			this.depth = depth;
			return state.getUtility(state, player, true);
		}
		ArrayList<Board> actions = new ArrayList<Board>(state.getActions(player));
		double value = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < actions.size(); i++){
			//System.out.println(value);
			value = Math.max(value, minValue(actions.get(i), opponent, alpha, beta, depth + 1));
			//System.out.print("Value returned by minValue is ");
			//System.out.println(value);
			if(value >= beta)
				return value;
			alpha = Math.max(alpha, value);
		}
		return value;
	}
}
