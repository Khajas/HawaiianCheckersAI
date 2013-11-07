import java.util.Scanner;


public class AIMinimax implements Player{

	private char playerColor;
	private final int PLAYERTYPE = 2;
	
	public AIMinimax(char playerColor){
		this.playerColor = playerColor;		
	}
	
	public int getPlayerType(){return PLAYERTYPE;}
	public char getPlayerColor(){return playerColor;}
	
	public void move(Board board) {
		if(board.getActions(playerColor).isEmpty()){
			System.out.format("Game over, %c loses\n", playerColor);
			System.out.print("The value of getGameOver() is ");
			System.out.println(board.getGameOver());
		}
		
		if(!board.getGameOver()){
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter depth to search to");
			int depth = sc.nextInt();
			Minimax minimax = new Minimax(board, playerColor, depth);
			Board thisBoard = minimax.getMinimaxAction(playerColor);
			int expandedNodes = minimax.getExpandedNodes();
			int depthReached = minimax.getDepth();
			System.out.format("%d nodes expanded\n", expandedNodes);
			System.out.format("%d depth reached\n", depthReached);
		}
	}

}
