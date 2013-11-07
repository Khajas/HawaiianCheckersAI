import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	
	private static Player player1,
						  player2;
	
	public static void main(String args[]){
		
		Scanner sc = new Scanner(System.in);
		System.out.format("Select a player type for player 1\n1 if player 1 is human,\n2 if player is AI" +
							"with Minimax search\n3 if player 1 is AI with alpha-beta search");
		int player1Value = sc.nextInt();
		if(player1Value == 1)
			player1 = new Human('b');
		else if(player1Value == 2)
			player1 = new AIMinimax('b');
		else
			player1 = new AIAlphaBeta('b');
		
		System.out.format("Select a player type for player 2\n1 if player 2 is human,\n2 if player 2 is AI" +
				"with Minimax search\n3 if player 2 is AI with alpha-beta search");
		int player2Value = sc.nextInt();
		if(player2Value == 1)
			player2 = new Human('w');
		else if(player2Value == 2)
			player2 = new AIMinimax('w');
		else
			player2 = new AIAlphaBeta('w');
		
		System.out.format("Select a board size");
		int boardSize = sc.nextInt();
		Board gameBoard = new Board(boardSize);
		gameBoard.printBoard();
		
		if(player1.getPlayerType() == 1)
			gameBoard.startGameHumanBlack();
		else
			gameBoard.startGameAIBlack();
	
		if(player2.getPlayerType() == 1)
			gameBoard.startGameHumanWhite();
		else
			gameBoard.startGameAIWhite();
		
		//ArrayList<Board> availableMoves = gameBoard.getActions(player1.getPlayerColor());
		//for(int i = 0; i < availableMoves.size(); i++){
		//	availableMoves.get(i).printBoard();
		//}
		
		
		System.out.println("printing game board");
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
