/**
 *  A strategy interface for dynamically choosing a player type at run time.
 *  
 *  All implementing classes: AIMinimax.java, AIAlphaBeta.java, Human.java
 * 
 * @author Dan Wagar
 *
 */
interface Player {
	
	/**Contract method for a Player to make moves
	 * on a game board
	 */
	void move(Board board);
	
	/** Contract method for returning a players type where 
	 * 1=human 2=AI w/ minimax 3= AI w/ alpha beta
	 */
	int getPlayerType();
	
	/**Contract method for returning a player color where 
	 * color is of game pieces controlled by that player
	 */
	char getPlayerColor();
}
