import java.util.Scanner;


public class AIAlphaBeta implements Player {
	
	private char playerColor;
	private final int PLAYERTYPE = 3;
	
	public AIAlphaBeta(char playerColor){
		this.playerColor = playerColor;		
	}
	
	public int getPlayerType(){return PLAYERTYPE;}
	public char getPlayerColor(){return playerColor;}
	
	public void move(Board board){
		if(board.getActions(playerColor).isEmpty()){
			System.out.format("Game over, %c loses\n", playerColor);
			//System.out.print("The value of getGameOver() is ");
			//System.out.println(board.getGameOver());
		}
		if(!board.getGameOver()){
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter depth to search to");
			int depth = sc.nextInt();
			AlphaBeta alphaBeta = new AlphaBeta(board, playerColor, depth);
			Board thisBoard = alphaBeta.getMinimaxAction(playerColor);
			//thisBoard.printBoard();
			board.setBoardArray(thisBoard.getBoardArray());
			int expandedNodes = alphaBeta.getExpandedNodes();
			int depthReached = alphaBeta.getDepth();
			System.out.format("%d nodes expanded\n", expandedNodes);
			System.out.format("%d depth reached\n", depthReached);
		}
	}

}
