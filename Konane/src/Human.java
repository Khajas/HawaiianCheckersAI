import java.util.Scanner;

/**
 * Implements the Player interface.
 * 
 * The Human class provides functionality for making moves on a 
 * game board by a human controlled player.
 * 
 * @author Dan Wagar
 *
 */

public class Human implements Player {
	
	/*
	 *  Fields representing characteristics of a player object,
	 *  namely the color pieces that player controls, and the
	 *  player type, that is human or an AI variety.
	 */
	private char playerColor;
	private final int PLAYERTYPE = 1; //1=human 2=AI w/ minimax 3= AI w/ alpha beta
	
	/*
	 * The human class constructor
	 * @param playerColor  The color of the pieces controlled by the player
	 */
	public Human(char playerColor){
		this.playerColor = playerColor;
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
	
	/**Provides functionality for a human player to make moves on a game board.
	 * The method asks for input to select a piece to move as well as location for that
	 * piece to jump to.  If multiple jumps possible, then will prompt for user input
	 * indicating whether secondary jump should be made or not.
	 * 
	 * All actual moving of pieces on the game board is handled within the Board class,
	 * allowing the interface Player class and its concrete implementations to be used
	 * for any game. 
	 * 
	 * @param board  The current state of the game board
	 */
	public void move(Board board){
		// local fields for storing start and end
		// coordinates for making a move
		String stringStartIndexes,
			   stringEndIndexes;
		int startIndex1 = 0,
			startIndex2 = 0,
			endIndex1 = 0,
			endIndex2 = 0;
		
		//Check if player has available moves
		if(board.getActions(playerColor).isEmpty())
			System.out.format("Game over, %c loses", playerColor);
		if(!board.getGameOver()){
		
			Scanner sc = new Scanner(System.in);
			boolean hasPiece = false;
			
			//Get coordinates of piece to move			 
			while(!hasPiece){
				System.out.println("Enter coordinates of piece to move with format \"x,y\"");
				stringStartIndexes = sc.next();
				startIndex1 = stringStartIndexes.charAt(0) - 48;
				startIndex2 = stringStartIndexes.charAt(stringStartIndexes.length() - 1) - 48;
				if(board.hasPlayerPiece(startIndex1, startIndex2, this.playerColor))
					hasPiece=true;
				else
					System.out.println("That piece is not available at that location");
			}
			
			boolean legalMove = false;
			//Get coordinates of square to which a piece will be moved
			//and attempt the move by calling board.jumpPiece()
			while(!legalMove){
				System.out.println("Enter coordinates of square to jump to with format \"x,y\"");
				stringEndIndexes = sc.next();
				endIndex1 = stringEndIndexes.charAt(0) - 48;
				endIndex2 = stringEndIndexes.charAt(stringEndIndexes.length() - 1) - 48;
				board = board.jumpPiece(startIndex1, startIndex2, endIndex1, endIndex2, this.playerColor);
				if(board != null)
					legalMove = true;
				else
					System.out.println("Not a valid move, try again");
			}
		}
		
	}
	

}
