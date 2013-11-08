import java.util.Scanner;
/**Simulates a game of konane.  Gets user input for player types
 * and the depths AI players should search to.  After receiving user
 * input the game is simulated.  The game board is printed each time
 * a player makes a move.  
 * 
 * For a board size of 8 the recommended maximum search depth for a
 * minimax player is 5, and for the alpha-beta player the recommended 
 * maximum depth is 7.
 * 
 * 
 * @author Dan Wagar
 *
 */
public class Main {
	 
	public static void main(String args[]){
		
		Scanner sc = new Scanner(System.in);
		System.out.println("***************************************");
		System.out.println("    WELCOME TO THE GAME OF KONANE!     ");
		System.out.println("***************************************");
		
		Player player1,
		       player2;
		System.out.format("Select a player type for player 1:\nEnter 1 if player 1 is human,\nEnter 2 if player is AI" +
							"with Minimax search\nEnter 3 if player 1 is AI with alpha-beta search\n");
		int player1Value = sc.nextInt();
		if(player1Value == 1)
			player1 = new Human('b');
		else if(player1Value == 2)
			player1 = new AIMinimax('b');
		else
			player1 = new AIAlphaBeta('b');
		
		System.out.format("Select a player type for player 2\n1 if player 2 is human,\n2 if player 2 is AI" +
				"with Minimax search\n3 if player 2 is AI with alpha-beta search\n");
		int player2Value = sc.nextInt();
		if(player2Value == 1)
			player2 = new Human('w');
		else if(player2Value == 2)
			player2 = new AIMinimax('w');
		else
			player2 = new AIAlphaBeta('w');
		
		int boardSize = 8;
		Board gameBoard = new Board(boardSize);
		gameBoard.printBoard();
		
		//Run the simulation
		//Get starting moves
		if(player1.getPlayerType() == 1)
			gameBoard.startGameHumanBlack();
		else
			gameBoard.startGameAIBlack();
	
		if(player2.getPlayerType() == 1)
			gameBoard.startGameHumanWhite();
		else
			gameBoard.startGameAIWhite();
		gameBoard.printBoard();
		while(!gameBoard.getGameOver()){
			player1.move(gameBoard);
			gameBoard.printBoard();
			if(!gameBoard.getGameOver()){
				player2.move(gameBoard);
				gameBoard.printBoard();
			}
		}
	}
}
