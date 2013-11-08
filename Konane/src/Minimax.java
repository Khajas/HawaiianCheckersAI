import java.util.ArrayList;

/**
 * This class implements the minimax algorithm.
 * It returns the action corresponding to the best possible move, 
 * that is, the move that leads to the outcome with the best utility, 
 * under the assumption that the opponent plays to minimize utility. 
 * The functions maxValue and minValue go through the whole game tree, 
 * all the way to the leaves, to determine the backed-up value of a state.
 *
 * The minimax algorithm is implemented using an N-ary array list tree.
 * The minimax algorithm builds the search tree in a depth first manner.
 * 
 * @author Dan Wagar
 */

public class Minimax {
	
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
	 * Constructor for Minimax
	 * 
	 * @param board    the board representing current game board / state
	 * @param player   the color of the pieces controlled by the player
	 * @param setDepth the depth at which to quit searching
	 */
	public Minimax(Board board, char player, int setDepth){
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
	
	/** Initializes the recursive minimax search. 
	 * Calls minValue, tracks and prints time for search to be implemented
	 * 
	 * @param currentState the present board state of the game
	 * @return result The board state after the corresponding best possible move is made	
	 */
	public Board getMinimaxAction(Board currentState, char player){
		expandedNodes = 0;
		Board result = null;
		int depth = 0;
		double resultValue = Double.NEGATIVE_INFINITY;
		ArrayList<Board> actions = new ArrayList<Board>(currentState.getActions(player));
		long startTime = System.nanoTime();
		for(int i = 0; i < actions.size(); i++){
			//actions.get(i).printBoard();
			double value = Math.max(resultValue, minValue(actions.get(i), depth + 1));
			//System.out.print("The value of this node is ");
			//System.out.println(value);
			if(value > resultValue){
				//System.out.println("The value of this state is GREATER THAN resultValue");
				//System.out.print("The value of resultValue was " + resultValue + "\n");
				result = actions.get(i);
				resultValue = value;
				//System.out.print("The new value of resultValue is " + resultValue + "\n");
			}
		}
		long endTime = System.nanoTime();
		long runTime = endTime - startTime;
		double seconds = (double)runTime / 1000000000.0;
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
	public double minValue(Board state, int depth){
		expandedNodes++;
		ArrayList<Board> actions = new ArrayList<Board>(state.getActions(opponent));
		if(depth == setDepth || state.getGameOver()){
			//System.out.println("Set depth reached or leaf node.");
			this.depth = depth;
			//state.printBoard();
			double utility = state.getUtility(state, opponent, false);
			//System.out.println("in minValue, utility assigned is " + utility);
			return utility;
		}
		double value = Double.POSITIVE_INFINITY;
		for(int i = 0; i < actions.size(); i++){
			value = Math.min(value, maxValue(actions.get(i), depth + 1));
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
	public double maxValue(Board state, int depth){
		expandedNodes++;
		ArrayList<Board> actions = new ArrayList<Board>(state.getActions(player));
		if(depth == setDepth || state.getGameOver()){
			//System.out.println("Set depth reached or leaf node.");
			this.depth = depth;
			double utility = state.getUtility(state, player, true);
			//System.out.println("in maxValue, utility assigned is " + utility);
			return utility;
		}
		double value = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < actions.size(); i++){
			value = Math.max(value, minValue(actions.get(i), depth + 1)); 
		}
		return value;
	}

}
