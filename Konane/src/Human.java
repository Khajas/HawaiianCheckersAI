import java.util.Scanner;


public class Human implements Player {
	
	private char playerColor;
	private final int PLAYERTYPE = 1; //1=human 2=AI w/ minimax 3= AI w/ alpha beta
	
	public Human(char playerColor){
		this.playerColor = playerColor;
	}
	
	public int getPlayerType(){return PLAYERTYPE;}
	public char getPlayerColor(){return playerColor;}
	
	public void move(Board board){
		String stringStartIndexes,
			   stringEndIndexes;
		int startIndex1 = 0,
			startIndex2 = 0,
			endIndex1 = 0,
			endIndex2 = 0;
	
		if(board.getActions(playerColor).isEmpty())
			System.out.format("Game over, %c loses", playerColor);
		System.out.print(board.getGameOver());
		if(!board.getGameOver()){
		
			Scanner sc = new Scanner(System.in);
			System.out.println("getting move");
			boolean hasPiece = false;
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
