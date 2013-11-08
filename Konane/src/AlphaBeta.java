import java.util.ArrayList;

/**
 * This class implements an alpha-beta pruning minimax search algorithm.
 * It returns the action corresponding to the best possible move, 
 * that is, the move that leads to the outcome with the best utility, 
 * under the assumption that the opponent plays to minimize utility. 
 * The functions maxValue and minValue go through the whole game tree, 
 * all the way to the leaves, to determine the backed-up value of a state.
 * 
 * The alpha-beta differs from the minimax algorithm in that it
 * prunes the branches of the search tree which cannot impact the 
 * outcome of the search, allowing for deeper searching of the game tree.
 *  
 * The alpha-beta algorithm is implemented using an N-ary array list tree.
 * The alpha-beta algorithm builds the search tree in a depth first manner.
 * 
 * @author Dan Wagar
 */

public class AlphaBeta {
	
	/**Track number of nodes expanded in the search.*/
	private int expandedNodes;
	/**The depth reached in the search tree.*/
	private int depth = 0;
	/**The depth at which to quit searching.*/
	private int	setDepth;			  
	/**Color of player for current alpha-beta search.*/
	private char player;
	/**Color of opponent for current alpha-beta search.*/
	private char opponent;
	

	/**
	 * Constructor for AlphaBeta
	 * 
	 * @param board    the board representing current game board / state
	 * @param player   the color of the pieces controlled by the player
	 * @param setDepth the depth at which to quit searching
	 */
	public AlphaBeta(char player, int setDepth){
		this.setDepth = setDepth;
		this.player = player;
		if(player == 'b')
			opponent = 'w';
		else
			opponent = 'b';
	}
	
	/**Returns instance field expandedNodes
	 * 
	 * @return expandedNodes The number of nodes expanded in the search
	 */
	public int getExpandedNodes(){return expandedNodes;}
	
	/**Returns instance field depth
	 * 
	 * @return depth The number of levels reached in search tree 
	 */
	public int getDepth(){return depth;}
	
	/** Initializes the recursive alpha-beta search. 
	 * Calls minValue, tracks and prints time for search to be implemented
	 * 
	 * @param currentState the present board state of the game
	 * @return result The board state after the corresponding best possible move is made	
	 */
	public Board getMinimaxAction(Board currentState){
		expandedNodes = 0;
		Board result = null;
		int depth = 0;
		double highestValue = Double.NEGATIVE_INFINITY;
		double value = Double.NEGATIVE_INFINITY;
		long startTime = System.nanoTime();
		ArrayList<Board> actions = new ArrayList<Board>(currentState.getActions(player));
		for(int i = 0; i < actions.size(); i++){
			value = minValue(actions.get(i), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,  depth + 1);
			if(value > highestValue){
				highestValue = value;
				result = actions.get(i);
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
	
	/**Chooses the move that minimizes the utility for the 
	 * MAX player, i.e. the MIN player moves in such a way as to
	 * minimize the utility of the moves that will become available
	 * to the opponent 
	 * 
	 * @param board the board instance represents current node/state in search tree 
	 * @param alpha starting value is negative infinity. Used to track max value node
	 * 				at a given level
	 * @param beta  starting value is positive infinity. Used to track min value node
	 * 				at a give level
	 * @param depth the search depth of the current node
	 * @return value the opponents move with the worst utility at the current depth
	 * 				 of the search
	 */
	public double minValue(Board state, double alpha, double beta, int depth){
		
		expandedNodes++;
		ArrayList<Board> actions = new ArrayList<Board>(state.getActions(opponent));
		if(depth == setDepth || state.getGameOver()){
			this.depth = depth;
			double utility = state.getUtility(state, opponent, false);
			//state.printBoard();
			//System.out.println("in minValue, utility assigned is " + utility);
			return utility;
		}
		double value = Double.POSITIVE_INFINITY;
		for(int i = 0; i < actions.size(); i++){
			value = Math.min(value, maxValue(actions.get(i), alpha, beta, depth + 1));
			//System.out.print("Value returned by maxValue is ");
			//System.out.println(value);
			if(value <= alpha)
				return value;
			beta = Math.min(value, beta);
		}
		return value;
	}
	
	/**Chooses the move that maximizes the utility for the 
	 * MIN player, i.e. the MAX player moves in such a way as to
	 * maximize (remember MIN wants to minimize its utility)
	 * the utility of the moves that will become available to the 
	 * opponent 
	 * 
	 * @param board the board instance represents current node/state in search tree 
	 * @param alpha starting value is negative infinity. Used to track max value node
	 * 				at a given level
	 * @param beta  starting value is positive infinity. Used to track min value node
	 * 				at a give level
	 * @param depth the search depth of the current node
	 * @return value the opponents move with the worst utility at the current depth
	 * 				 of the search
	 */
	public double maxValue(Board state, double alpha, double beta, int depth){
		expandedNodes++;
		ArrayList<Board> actions = new ArrayList<Board>(state.getActions(player));
		if(depth == setDepth || state.getGameOver()){
			this.depth = depth;
			//state.printBoard();
			double utility = state.getUtility(state, player, true);
			//System.out.println("in maxValue, utility assigned is " + utility);
			return utility;
		}
		double value = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < actions.size(); i++){ 
			//System.out.println(value);
			value = Math.max(value, minValue(actions.get(i), alpha, beta, depth + 1));
			//System.out.print("Value returned by minValue is ");
			//System.out.println(value);
			if(value >= beta)
				return value;
			alpha = Math.max(alpha, value);
		}
		return value;
	}
}