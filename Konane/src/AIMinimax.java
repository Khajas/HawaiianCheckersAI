import java.util.Scanner;
/**
 * Implements the Player interface.  The AIMinimax class provides 
 * functionality for making moves on a game board by an AI controlled 
 * player using the minimax algorithm.
 * 
 * @author Dan Wagar
 *
 */

public class AIMinimax implements Player{
	
	/**The color of the pieces controlled by a player.*/
	private char playerColor;
	/**The depth at which to stop searching.*/
	private int depth;
	/**Represents the type of Player subclass where
	 * 1=human 2=AI w/ minimax 3= AI w/ alpha beta
	 */
	private final int PLAYERTYPE = 2;
	
	
	/**The AIMinimax class constructor
	 * 
	 * @param playerColor the color of the pieces controlled by the player
	 */
	public AIMinimax(char playerColor){
		this.playerColor = playerColor;	
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter depth to search to");
		this.depth = sc.nextInt();
	}
	
	/**Returns the instance field PLAYERTYPE	
	 * 
	 * @return PLAYERTYPE  where 1=human 2=AI w/ minimax 3= AI w/ alpha beta
	 */
	public int getPlayerType(){return PLAYERTYPE;}
	
	/**Returns the instance field playerColor
	 *
	 * @return playerColor  The color of the pieces controlled by the player
	 */
	public char getPlayerColor(){return playerColor;}
	
	/**Provides functionality for an AI player to make moves on a game board.
	 * For each move made, statistics on the depth and number of nodes expanded
	 * in the minimax search are printed out.
	 * 
	 * All actual moving of pieces on the game board is handled within the Board class,
	 * allowing the interface Player class and its concrete implementations to be used
	 * for any game. 
	 * 
	 * @param board  the current state of the game board
	 */
	public void move(Board board) {
		if(board.getActions(playerColor).isEmpty()){
			System.out.format("Game over, %c loses\n", playerColor);
			System.out.print("The value of getGameOver() is ");
			System.out.println(board.getGameOver());
		}
		
		if(!board.getGameOver()){
			
			Minimax minimax = new Minimax(board, playerColor, depth);
			Board thisBoard = minimax.getMinimaxAction(board, playerColor);
			board.setBoardArray(thisBoard.getBoardArray());
			int expandedNodes = minimax.getExpandedNodes();
			int depthReached = minimax.getDepth();
			System.out.format("%d nodes expanded\n", expandedNodes);
			System.out.format("%d depth reached\n", depthReached);
		}
	}

}
