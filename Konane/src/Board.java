import java.util.ArrayList;
import java.util.Scanner;


public class Board {
	
	private char board[][]; 
	private int numberBoardSquares,
				numberBlackPieces,
				numberWhitePieces,
				size;
	private boolean gameOver = false;
	
	public Board(char[][] board){ 
		this.board = board;
		size = board.length;
	}
	
	public Board(int size){
		
		board = new char[size][size];
		numberBoardSquares = size * size;
		numberBlackPieces = numberBoardSquares / 2;
		numberWhitePieces = numberBoardSquares / 2;
		this.size = size;
				
		//populate board
		boolean black = true;
		for(int i = 0; i < size; i+=2){
			for(int j = 0; j < size; j++){
				if(black){
					board[i][j] = 'b';
					black = false;
				}else{
					board[i][j] = 'w';
					black = true;
				}
			}
		}
		black = false;
		for(int i = 1; i < size; i+= 2){
			for(int j = 0; j < size; j++){
				if(black){
					board[i][j] = 'b';
					black = false;
				}else{
					board[i][j] = 'w';
					black = true;
				}
			}
		}
	}
		
	public char[][] getBoardArray(){
		char[][] toReturn = new char[size][];
		for(int i = 0; i < size; i++){
			char[] aArray = board[i];
			toReturn[i] = new char[size];
			System.arraycopy(aArray, 0, toReturn[i], 0, size);
		}
		return toReturn;
	}
	
	public void setBoardArray(char[][] board){ this.board = board; }
	
	public boolean getGameOver(){return gameOver;}
	
	public int getSize(){return size;}
	
	public void removePiece(int index1, int index2){
		this.board[index1][index2] = ' ';
	}
	
	public void setPiece(int index1, int index2, char color){
		this.board[index1][index2] = color;
	}
	
	public void printBoard(){
		for(int i = 0; i < size; i++){
			if(i == 0)
				System.out.format("  %d", i);
			else
				System.out.format(" %d", i);
		}
		System.out.println();
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				if(j == 0) 
					System.out.format("%d|", i);
				System.out.format("%c|",board[j][i]);
				if(j == size - 1){
					System.out.println();
				}
			}
		}
		for(int i = 0; i < size; i++){
			if(i == 0)
				System.out.format("  %d", i);
			else
				System.out.format(" %d", i);
		}
		System.out.println();
	}
	
	public double getUtility(Board board, char player, boolean isMax){
		if(board.getGameOver())
			return 0.0;
		else{
			int numberMoves = board.getActions(player).size();
			if(isMax)
				return (double)numberMoves;
			else
				return (double)-numberMoves;
		}
	}
	
	public void startGameHumanBlack(){
		Scanner sc = new Scanner(System.in);
		int count = 0;
		while(count < 2){
			System.out.format("\nEnter 1 to remove piece at 0,0\nEnter 2 to remove piece at %d,%d\n" +
					"Enter 3 to remove piece at %d,%d\nEnter 4 to remove piece at %d,%d\n",
					(size/2) - 1, (size/2) - 1, size/2, size/2, size, size);
			int selection = sc.nextInt();
			switch(selection){
			case 1: if(board[0][0] == ' '){
						System.out.println("Piece has already been removed");
						count--;
						break;
					} else{
						this.removePiece(0,0);
						break;
					}
			case 2: if(board[size/2 - 1][size/2 - 1] == ' '){
						System.out.println("Piece has slready been removed");
						count--;
						break;
					}else{
						this.removePiece(size/2 - 1, size/2 - 1);
						break;
					}
			
			case 3: if(board[size/2][size/2] == ' '){
						System.out.println("Piece has already been removed");
						count--;
						break;
					}else{
						this.removePiece(size/2, size/2);
						break;
					}
			case 4: if(board[size - 1][size - 1] == ' '){
						System.out.println("Piece has already been removed");
						count--;
						break;
					}else{
						this.removePiece(size - 1, size - 1);
						break;
					}
			default: System.out.println("incorrect entry, try again"); count--;
			}
			count++;
			printBoard();
		}
		
	}
	
	public void startGameHumanWhite(){
		Scanner sc = new Scanner(System.in);
		int count = 0;
		while(count < 2){
			System.out.format("Enter 1 to remove piece at %d,0\nEnter 2 to remove piece at %d,%d\n" +
					"Enter 3 to remove piece at %d,%d\nEnter 4 to remove piece at 0,%d\n", size,
					size/2, (size/2) - 1, (size/2) - 1, size/2, size);
			int selection = sc.nextInt();
			switch(selection){
			case 1: if(board[size - 1][0] == ' '){
						System.out.println("Piece has already been removed");
						count--;
						break;
					} else{
						this.removePiece(size - 1, 0);
						break;
					}										
			case 2: if(board[size/2][size/2 - 1] == ' '){
						System.out.println("Piece has already been removed");
						count--;
						break;
					}else{
						this.removePiece(size/2 , size/2 - 1);
						break;
					}
			case 3: if(board[size/2 - 1][size/2] == ' '){
						System.out.println("Piece has already been removed");
						count--;
						break;
					}else{
						this.removePiece(size/2 - 1, size/2);
						break;
					}
			case 4: if(board[0][size - 1] == ' '){
						System.out.println("Piece has already been removed");
						count--;
						break;
					}else{
						this.removePiece(0, size - 1);
						break;
					}
			default: System.out.println("incorrect entry, try again"); count--;
			}
			count++;
			printBoard();
		}
		
	}
	
	public void startGameAIBlack(){
		if(size == 4){
			this.removePiece(0,0);
			this.removePiece(size/2, size/2);
		}else{
			this.removePiece(size/2 - 1, size/2 -1);
			this.removePiece(size/2, size/2);
		}
	}
	
	public void startGameAIWhite(){
		if(size == 4){
			this.removePiece(0, size - 1);
			this.removePiece(size/2 - 1, size/2 );
		}else{
			this.removePiece(size/2, size/2 - 1);
			this.removePiece(size/2 - 1, size/2);
		}
	}
	
	public Board jumpPiece(int startIndex1, int startIndex2, int endIndex1, int endIndex2, char pieceColor){
		char opponentColor;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Attempting to jump piece");
		
		if (pieceColor == 'b')
			opponentColor = 'w';
		else
			opponentColor = 'b';
		if(board[startIndex1][startIndex2] == pieceColor && 
		   board[(startIndex1 + endIndex1) / 2][(startIndex2 + endIndex2) / 2] == opponentColor &&
		   board[endIndex1][endIndex2] == ' '){
				System.out.println("Conditions for jump met");
				board[startIndex1][startIndex2] = ' ';
				board[(startIndex1 + endIndex1) / 2][(startIndex2 + endIndex2) / 2] = ' ';
				board[endIndex1][endIndex2] = pieceColor;
				//Get direction moved and determine if another jump is available
				boolean canMoveAgain = true;
				if(endIndex2 < startIndex2){		//moved up
					while(endIndex2 > 1 && canMoveAgain){
						startIndex2 = endIndex2;
						endIndex2 = startIndex2 - 2;
						if(board[endIndex1][(startIndex2 + endIndex2) / 2] == opponentColor && 
								board[endIndex1][endIndex2] == ' '){
							System.out.println("Do you want to jump again? Enter y for yes or n for no");
							String moveAgain = sc.next();
							if(moveAgain.charAt(0) == 'y'){
								board[startIndex1][startIndex2] = ' ';
								board[endIndex1][(startIndex2 + endIndex2) / 2] = ' ';
								board[endIndex1][endIndex2] = pieceColor;
							}
						}else canMoveAgain = false;
					}
			    }else if(endIndex2 > startIndex2){  //moved down
					while(endIndex2 < board.length - 2 && canMoveAgain){
						
						startIndex2 = endIndex2;
						endIndex2 = startIndex2 + 2;
						
						if(board[endIndex1][(startIndex2 + endIndex2) / 2] == opponentColor && 
								board[endIndex1][endIndex2] == ' '){
							System.out.println("Do you want to jump again? Enter y for yes or n for no");
							String moveAgain = sc.next();
							if(moveAgain.charAt(0) == 'y'){
								board[startIndex1][startIndex2] = ' ';
								board[endIndex1][(startIndex2 + endIndex2 ) / 2] = ' ';
								board[endIndex1][endIndex2] = pieceColor;
							}
						}else canMoveAgain = false;
					}
				}else if(endIndex1 > startIndex1){  //moved right
					while(endIndex2 < board.length - 2 && canMoveAgain){
						startIndex1 = endIndex1;
						endIndex1 = startIndex1 + 1;
						if(board[(startIndex1 + endIndex1) / 2 ][endIndex2] == opponentColor && 
								board[endIndex1][endIndex2] == ' '){
							System.out.println("Do you want to jump again? Enter y for yes or n for no");
							String moveAgain = sc.next();
							if(moveAgain.charAt(0) == 'y'){
								board[startIndex1][startIndex2] = ' ';
								board[(startIndex1 + endIndex1) / 2 ][endIndex2] = ' ';
								board[endIndex1][endIndex2] = pieceColor;
							}
						}else canMoveAgain = false;
					}
				}else if(endIndex1 < startIndex1){ //moved left
					while(endIndex2 > 1 && canMoveAgain){
						startIndex1 = endIndex1;
						endIndex1 = startIndex1 - 1;
						if(board[(startIndex1 + endIndex1) / 2 ][endIndex2] == opponentColor && 
								board[endIndex1][endIndex2] == ' '){
							System.out.println("Do you want to jump again? Enter y for yes or n for no");
							String moveAgain = sc.next();
							if(moveAgain.charAt(0) == 'y'){
								board[startIndex1][startIndex2] = ' ';
								board[(startIndex1 + endIndex1) / 2 ][endIndex2] = ' ';
								board[endIndex1][endIndex2] = pieceColor;
							}
						}else canMoveAgain = false;
					}
				}
			return this;
		}else
			return null;
	}
	
	public ArrayList<Board> jumpPieceAI(int startIndex1, int startIndex2, int endIndex1, int endIndex2, char pieceColor){
		ArrayList<Board> boardArr = new ArrayList<Board>();
		char opponentColor;
		if (pieceColor == 'b')
			opponentColor = 'w';
		else
			opponentColor = 'b';
		
		char[][] currentBoard = this.getBoardArray();
		Board someBoard = new Board(currentBoard);
		
		if(currentBoard[startIndex1][startIndex2] == pieceColor && 
			currentBoard[(startIndex1 + endIndex1) / 2][(startIndex2 + endIndex2) / 2] == opponentColor &&
			currentBoard[endIndex1][endIndex2] == ' '){
				
				someBoard.removePiece(startIndex1, startIndex2);
				someBoard.removePiece((startIndex1 + endIndex1) / 2, (startIndex2 + endIndex2) / 2);
				//board[(startIndex1 + endIndex1) / 2][(startIndex2 + endIndex2) / 2] = ' ';
				someBoard.setPiece(endIndex1, endIndex2, pieceColor);
				//tempBoard.board[endIndex1][endIndex2] = pieceColor;
				//System.out.println("adding the following board to boardArr");
				//tempBoard.printBoard();
				
				boardArr.add(someBoard);
				currentBoard = someBoard.getBoardArray();
				//Get direction moved and determine if another jump is available
				boolean canMoveAgain = true;
				if(endIndex2 < startIndex2){		//moved up
					while(endIndex2 > 1 && canMoveAgain){
						startIndex2 = endIndex2;
						endIndex2 = startIndex2 - 2;
						if(currentBoard[endIndex1][(startIndex2 + endIndex2) / 2] == opponentColor && 
								currentBoard[endIndex1][endIndex2] == ' '){
								
							
							someBoard.removePiece(startIndex1, startIndex2);
							someBoard.removePiece(endIndex1, (startIndex2 + endIndex2) / 2);
							someBoard.setPiece(endIndex1,endIndex2, pieceColor);
							boardArr.add(someBoard);
							
						}else
							canMoveAgain = false;
					}
			    }else if(endIndex2 > startIndex2){  //moved down
					while(endIndex2 < (currentBoard.length - 2) && canMoveAgain){
						startIndex2 = endIndex2;
						endIndex2 = startIndex2 + 2;
						if(currentBoard[endIndex1][(startIndex2 + endIndex2) / 2] == opponentColor && 
								currentBoard[endIndex1][endIndex2] == ' '){
								
							
							someBoard.removePiece(startIndex1,startIndex2);
							someBoard.removePiece(endIndex1, (startIndex2 + endIndex2) / 2);
							someBoard.setPiece(endIndex1,endIndex2, pieceColor);
							boardArr.add(someBoard);
						}else
							canMoveAgain = false;
					}
				}else if(endIndex1 > startIndex1){  //moved right
					while(endIndex1 < (currentBoard.length - 2) && canMoveAgain){
						startIndex1 = endIndex1;
						endIndex1 = startIndex1 + 2;
						if(currentBoard[(startIndex1 + endIndex1) / 2 ][endIndex2] == opponentColor && 
								currentBoard[endIndex1][endIndex2] == ' '){
								
							
							currentBoard[startIndex1][startIndex2] = ' ';
							currentBoard[(startIndex1 + endIndex1) / 2 ][endIndex2] = ' ';
							currentBoard[endIndex1][endIndex2] = pieceColor;
							boardArr.add(someBoard);
						}else
							canMoveAgain = false;
					}
				}else if(endIndex1 < startIndex1){  //moved left
					while(endIndex1 > 1 && canMoveAgain){
						startIndex1 = endIndex1;
						endIndex1 = startIndex1 - 2;
						//System.out.format("endIndex1 = %d and endIndex2 = %d\n", endIndex1, endIndex2); 
						if(currentBoard[(startIndex1 + endIndex1) / 2][endIndex2] == opponentColor && 
								currentBoard[endIndex1][endIndex2] == ' '){

						
								someBoard.removePiece(startIndex1, startIndex2);
								someBoard.removePiece((startIndex1 + endIndex1) / 2, endIndex2);
								someBoard.setPiece(endIndex1, endIndex2, pieceColor);
								boardArr.add(someBoard);							
						}else
							canMoveAgain = false;
					}
				}
		}
		
		return boardArr;
		
	}
	
	public boolean hasPlayerPiece(int index1, int index2, char playerColor){
		if(board[index1][index2] == playerColor)
			return true;
		else
			return false;
	}
	
	public ArrayList<Board> getActions(char playerColor){
		
		ArrayList<Board> actions = new ArrayList<Board>();
		//System.out.println("getting actions");
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				//System.out.format("Checking square %d,%d\n", j, i);
				int startIndex1 = j,
					startIndex2 = i;
				
				if(startIndex2 <= 1){
					if(startIndex1 <= 1){ //jumping up or left not possible	
						ArrayList<Board> boardJumpDown = jumpPieceAI(startIndex1, startIndex2, startIndex1, startIndex2 + 2, playerColor);
						if(boardJumpDown != null)
							actions.addAll(boardJumpDown);
						ArrayList<Board> boardJumpRight = jumpPieceAI(startIndex1, startIndex2, startIndex1 + 2, startIndex2, playerColor);
						if(boardJumpRight != null)
							actions.addAll(boardJumpRight);
					}else if(startIndex1 >= (size - 2)){ // jumping up or right not possible
						ArrayList<Board> boardJumpDown = jumpPieceAI(startIndex1, startIndex2, startIndex1, startIndex2 + 2, playerColor);
						if(boardJumpDown != null)
							actions.addAll(boardJumpDown);
						ArrayList<Board> boardJumpLeft = jumpPieceAI(startIndex1, startIndex2, startIndex1 - 2, startIndex2, playerColor);
						if(boardJumpLeft != null)
							actions.addAll(boardJumpLeft);
					}else{ // jumping up not possible
						ArrayList<Board> boardJumpDown = jumpPieceAI(startIndex1, startIndex2, startIndex1, startIndex2 + 2, playerColor);
						if(boardJumpDown != null)
							actions.addAll(boardJumpDown);
						ArrayList<Board> boardJumpRight = jumpPieceAI(startIndex1, startIndex2, startIndex1 + 2, startIndex2, playerColor);
						if(boardJumpRight != null)
							actions.addAll(boardJumpRight);
						ArrayList<Board> boardJumpLeft = jumpPieceAI(startIndex1, startIndex2, startIndex1 - 2, startIndex2, playerColor);
						if(boardJumpLeft != null)
							actions.addAll(boardJumpLeft);						
					}
				}else if(startIndex2 >= (size - 2)){
					if(startIndex1 <= 1){ //jumping down or left not possible
						ArrayList<Board> boardJumpUp = jumpPieceAI(startIndex1, startIndex2, startIndex1, startIndex2 - 2, playerColor);
						if(boardJumpUp != null)
							actions.addAll(boardJumpUp);
						ArrayList<Board> boardJumpRight = jumpPieceAI(startIndex1, startIndex2, startIndex1 + 2, startIndex2, playerColor);
						if(boardJumpRight != null)
							actions.addAll(boardJumpRight);
					}else if(startIndex1 >= (size - 2)){ // jumping down or right not possible
						ArrayList<Board> boardJumpUp = jumpPieceAI(startIndex1, startIndex2, startIndex1, startIndex2 - 2, playerColor);
						if(boardJumpUp != null)
							actions.addAll(boardJumpUp);
						ArrayList<Board> boardJumpLeft = jumpPieceAI(startIndex1, startIndex2, startIndex1 - 2, startIndex2, playerColor);
						if(boardJumpLeft != null)
							actions.addAll(boardJumpLeft);		
					}else{ // jumping down not possible
						ArrayList<Board> boardJumpUp = jumpPieceAI(startIndex1, startIndex2, startIndex1, startIndex2 - 2, playerColor);
						if(boardJumpUp != null)
							actions.addAll(boardJumpUp);
						ArrayList<Board> boardJumpRight = jumpPieceAI(startIndex1, startIndex2, startIndex1 + 2, startIndex2, playerColor);
						if(boardJumpRight != null)
							actions.addAll(boardJumpRight);
						ArrayList<Board> boardJumpLeft = jumpPieceAI(startIndex1, startIndex2, startIndex1 - 2, startIndex2, playerColor);
						if(boardJumpLeft != null)
							actions.addAll(boardJumpLeft);	
					}
				}else{
					if(startIndex1 <= 1){ //jumping left not possible
						ArrayList<Board> boardJumpUp = jumpPieceAI(startIndex1, startIndex2, startIndex1, startIndex2 - 2, playerColor);
						if(boardJumpUp != null)
							actions.addAll(boardJumpUp);
						ArrayList<Board> boardJumpDown = jumpPieceAI(startIndex1, startIndex2, startIndex1, startIndex2 + 2, playerColor);
						if(boardJumpDown != null)
							actions.addAll(boardJumpDown);
						ArrayList<Board> boardJumpRight = jumpPieceAI(startIndex1, startIndex2, startIndex1 + 2, startIndex2, playerColor);
						if(boardJumpRight != null)
							actions.addAll(boardJumpRight);
					}else if(startIndex1 >= (size - 2)){ // jumping right not possible
						ArrayList<Board> boardJumpUp = jumpPieceAI(startIndex1, startIndex2, startIndex1, startIndex2 - 2, playerColor);
						if(boardJumpUp != null)
							actions.addAll(boardJumpUp);
						ArrayList<Board> boardJumpDown = jumpPieceAI(startIndex1, startIndex2, startIndex1, startIndex2 + 2, playerColor);
						if(boardJumpDown != null)
							actions.addAll(boardJumpDown);
						ArrayList<Board> boardJumpLeft = jumpPieceAI(startIndex1, startIndex2, startIndex1 - 2, startIndex2, playerColor);
						if(boardJumpLeft != null)
							actions.addAll(boardJumpLeft);	
					}else{ // all jumps possible
						ArrayList<Board> boardJumpUp = jumpPieceAI(startIndex1, startIndex2, startIndex1, startIndex2 - 2, playerColor);
						if(boardJumpUp != null)
							actions.addAll(boardJumpUp);
						ArrayList<Board> boardJumpDown = jumpPieceAI(startIndex1, startIndex2, startIndex1, startIndex2 + 2, playerColor);
						if(boardJumpDown != null)
							actions.addAll(boardJumpDown);
						ArrayList<Board> boardJumpRight = jumpPieceAI(startIndex1, startIndex2, startIndex1 + 2, startIndex2, playerColor);
						if(boardJumpRight != null)
							actions.addAll(boardJumpRight);
						ArrayList<Board> boardJumpLeft = jumpPieceAI(startIndex1, startIndex2, startIndex1 - 2, startIndex2, playerColor);
						if(boardJumpLeft != null)
							actions.addAll(boardJumpLeft);							
					}
				}
			}
		}
		if(actions.isEmpty())
			this.gameOver = true;
		return actions;
	}

	
}
