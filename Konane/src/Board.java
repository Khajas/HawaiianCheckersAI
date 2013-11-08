import java.util.ArrayList;
import java.util.Scanner;

/**
 * Implements the game board for the game of Konane
 * along with all methods needed to maintain the
 * game board such as determining possible moves for 
 * a player, making actual moves, and determining the 
 * utility of a player given a specific board configuration.
 * 
 * 
 * @author Dan Wagar
 */

public class Board {
	/**Represents the state of the game board. The char 'b' represents a 
	  *black players piece the char 'w' represents a white players piece.*/
	private char board[][]; 
	/**The size of a row or col on the board.  Ex: an 8X8 board has size 8.*/
	private int size;	
	/**This value is true when the game reaches a  state where a player has 
	 * no available moves and the game is over.*/
	private boolean gameOver = false;   
	
	/**Constructs a non-current instance of a game board.  Namely, this constructor 
	 * is used for generating instances of possible actions for a given game or board 
	 * state.  This is necessary for game search.
	 * 
	 * @param board the representation of a possible state of the game
	 */
	public Board(char[][] board){ 
		this.board = board;
		size = board.length;
	}
	
	/**Constructs the initial instance of the game board.
	 * It creates the appropriately sized board and populates 
	 * the board with player pieces.
	 * 
	 * @param size the number of rows and cols on the game board.
	 */
	public Board(int size){
		
		board = new char[size][size];
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
	
	/**Returns a copy of the current game state.
	 * 
	 * @return boardArr the copy of the current state of the game.
	 */
	public char[][] getBoardArray(){
		char[][] boardArr = new char[size][];
		for(int i = 0; i < size; i++){
			char[] aArray = board[i];
			boardArr[i] = new char[size];
			System.arraycopy(aArray, 0, boardArr[i], 0, size);
		}
		return boardArr;
	}
	
	/**Updates the state of the game board.
	 * 
	 * @param board The new board state which will be assigned to the
	 * 				instance field board
	 */
	public void setBoardArray(char[][] board){ this.board = board; }
	
	/**Returns the instance field gameOver.
	 * 
	 * @return gameOver the instance field representing whether or not
	 * 					the game is in a state such that no more moves
	 * 					can be made by one of the players.
	 */
	public boolean getGameOver(){return gameOver;}
	
	/**Returns the size of a row or column for the game board.
	 *
	 * @return size the number of columns/rows on the game board.
	 */
	public int getSize(){return size;}
	
	/**Determines if the game board has a player piece at the given location.
	 * 
	 * @param row the index representing which row on the game board to check
	 * @param col the index representing which col on the game board to check
	 * @param playerColor the color of the piece being checked for
	 * @return true when player piece is found at given indexes
	 * @return false when player piece is not found at given indexes
	 */
	public boolean hasPlayerPiece(int row, int col, char playerColor){
		if(board[row][col] == playerColor)
			return true;
		else
			return false;
	}
	
	/**Removes a piece from the board.  This method 
	 * is a helper method for the set of startGame methods
	 * 
	 * @param row the row index for the piece to remove from the board array
	 * @param col the col index for the piece to remove from the board array
	 */
	public void removePiece(int row, int col){
		this.board[row][col] = ' ';
	}
	
	/**Prints the board array instance field.*/
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
	
	/**Returns a value representing the current utility of a state for a 
	 * given player.  If the given player is the Max player, it returns a 
	 * positive value for the number of moves available to that player in the
	 * current state.  If the player is the opponent, or Min player, it 
 	 * the negation of the number of moves available to the player in the current
 	 * state. Utility is determined by the number of moves available to a player
 	 * in a given game state.
 	 * 
 	 * @param board the game state for which a utility is to be found
 	 * @param player the representation of the color pieces controled by a player,
 	 * 				 'b' represents black, 'w' represents white
 	 * @param isMax the value is false when returning the utility for an opponent,
 	 * 				i.e. a min player, else the value is true
 	 * @return numberMoves the number of moves a max player can make in the given game state.
 	 * @return -numberMoves the number of moves a min player can make in the given game state.
	 */
	public double getUtility(Board board, char player, boolean isMax){
		
		if(board.getGameOver())
			return 0.0;
		else{
			int numberMoves = board.getActions(player).size();
			if(isMax){
				return (double)numberMoves;
			}
			else{
				return (double)-numberMoves;
			}
		}
	}
	
	/*public double getUtility(Board board, char player, boolean isMax){
		if(board.getGameOver())
			return 0.0;
		else{
			char opponent;
			if (player == 'b')
				opponent = 'w';
			else
				opponent = 'b';
			int opponentMoves = board.getActions(opponent).size();
			int numberMoves = board.getActions(player).size();
			if(isMax){
				double utility = numberMoves/(double)opponentMoves;
				return utility;
			}
			else{
				double utility = -numberMoves/(double)opponentMoves;
				return utility;
			}
		}
	}*/
	
	/**Allows a human player controlling black game pieces
	 * to make opening moves.  The method takes user input representing
	 * the coordinates of the pieces to be removed off the board and then
	 * removes those pieces if they are valid for removal.  
	 */
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
	
	/**Allows a human player controlling white game pieces
	 * to make opening moves.  The method takes user input representing
	 * the coordinates of the pieces to be removed off the board and then
	 * removes those pieces if they are valid for removal.  
	 */
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
			default: System.out.println("incorrect entry, try again");
					 count--;
			}
			count++;
			printBoard();
		}
		
	}
	
	/**Makes opening moves for an AI player.  The method does
	 * not search for opening moves but assumes that the best opening
	 * move is to remove the two center pieces.  The assumed best opening
	 * moves may not actually be the best moves possible.
	 */
	public void startGameAIBlack(){
		if(size == 4){
			this.removePiece(0,0);
			this.removePiece(size/2, size/2);
		}else{
			this.removePiece(size/2 - 1, size/2 -1);
			this.removePiece(size/2, size/2);
		}
	}
	
	/**Makes opening moves for an AI player.  The method does
	 * not search for opening moves but assumes that the best opening
	 * move is to remove the two center pieces.  The assumed best opening
	 * moves may not actually be the best moves possible.
	 */
	public void startGameAIWhite(){
		if(size == 4){
			this.removePiece(0, size - 1);
			this.removePiece(size/2 - 1, size/2 );
		}else{
			this.removePiece(size/2, size/2 - 1);
			this.removePiece(size/2 - 1, size/2);
		}
	}
	
	/**Takes input from a user for the row and column indexes locating a piece to be
	 * moved as well as the indexes for the location to move to.  Checks whether the 
	 * move given by the user is valid and, if so, makes the move, else prompts the
	 * user for the proper input.  Checks whether further jumps are legal after the 
	 * original move is made and prompts the user as to whether or not these jumps
	 * should be performed.
	 * 
	 * @param startIndexRow the row index at which a piece starts before the move is made.
	 * @param startIndexCol the col index at which a piece starts before the move is made.
	 * @param endIndexRow   the row index at which a piece will end up after the move is made.
	 * @param endIndexCol   the col index at which a piece will end up after the move is made.
	 * @param playerColor   the color of the pieces controled by the player for which a move is being made. 
	 * @return this			the instance of the game board after the move has been made
	 */
	public Board jumpPiece(int startIndexRow, int startIndexCol, int endIndexRow, int endIndexCol, char playerColor){
		char opponentColor;
		Scanner sc = new Scanner(System.in);
		System.out.println("Attempting to jump piece");
		if (playerColor == 'b')
			opponentColor = 'w';
		else
			opponentColor = 'b';
		if(board[startIndexRow][startIndexCol] == playerColor && 
		   board[(startIndexRow + endIndexRow) / 2][(startIndexCol + endIndexCol) / 2] == opponentColor &&
		   board[endIndexRow][endIndexCol] == ' '){
				//System.out.println("Conditions for jump met");
				board[startIndexRow][startIndexCol] = ' ';
				board[(startIndexRow + endIndexRow) / 2][(startIndexCol + endIndexCol) / 2] = ' ';
				board[endIndexRow][endIndexCol] = playerColor;
				//Get direction moved and determine if another jump is available
				boolean canMoveAgain = true;
				if(endIndexCol < startIndexCol){//moved up
					while(endIndexCol > 1 && canMoveAgain){
						startIndexCol = endIndexCol;
						endIndexCol = startIndexCol - 2;
						if(board[endIndexRow][(startIndexCol + endIndexCol) / 2] == opponentColor && 
								board[endIndexRow][endIndexCol] == ' '){
							System.out.println("Do you want to jump again? Enter y for yes or n for no");
							String moveAgain = sc.next();
							if(moveAgain.charAt(0) == 'y'){
								board[startIndexRow][startIndexCol] = ' ';
								board[endIndexRow][(startIndexCol + endIndexCol) / 2] = ' ';
								board[endIndexRow][endIndexCol] = playerColor;
							}
						}else canMoveAgain = false;
					}
				}else if(endIndexCol > startIndexCol){  //moved down
					while(endIndexCol < board.length - 2 && canMoveAgain){
						
						startIndexCol = endIndexCol;
						endIndexCol = startIndexCol + 2;
						
						if(board[endIndexRow][(startIndexCol + endIndexCol) / 2] == opponentColor && 
								board[endIndexRow][endIndexCol] == ' '){
							System.out.println("Do you want to jump again? Enter y for yes or n for no");
							String moveAgain = sc.next();
							if(moveAgain.charAt(0) == 'y'){
								board[startIndexRow][startIndexCol] = ' ';
								board[endIndexRow][(startIndexCol + endIndexCol ) / 2] = ' ';
								board[endIndexRow][endIndexCol] = playerColor;
							}
						}else canMoveAgain = false;
					}
				}else if(endIndexRow > startIndexRow){  //moved right
					while(endIndexCol < board.length - 2 && canMoveAgain){
						startIndexRow = endIndexRow;
						endIndexRow = startIndexRow + 1;
						if(board[(startIndexRow + endIndexRow) / 2 ][endIndexCol] == opponentColor && 
								board[endIndexRow][endIndexCol] == ' '){
							System.out.println("Do you want to jump again? Enter y for yes or n for no");
							String moveAgain = sc.next();
							if(moveAgain.charAt(0) == 'y'){
								board[startIndexRow][startIndexCol] = ' ';
								board[(startIndexRow + endIndexRow) / 2 ][endIndexCol] = ' ';
								board[endIndexRow][endIndexCol] = playerColor;
							}
						}else canMoveAgain = false;
					}
				}else if(endIndexRow < startIndexRow){ //moved left
					while(endIndexCol > 1 && canMoveAgain){
						startIndexRow = endIndexRow;
						endIndexRow = startIndexRow - 1;
						if(board[(startIndexRow + endIndexRow) / 2 ][endIndexCol] == opponentColor && 
								board[endIndexRow][endIndexCol] == ' '){
							System.out.println("Do you want to jump again? Enter y for yes or n for no");
							String moveAgain = sc.next();
							if(moveAgain.charAt(0) == 'y'){
								board[startIndexRow][startIndexCol] = ' ';
								board[(startIndexRow + endIndexRow) / 2 ][endIndexCol] = ' ';
								board[endIndexRow][endIndexCol] = playerColor;
							}
						}else canMoveAgain = false;
					}
				}
			return this;
		}else
			return null;
	}
	
	/**Finds all possible moves which can be made by a player from some game state.
	 * Iterates through each square on the board checking all possible jumps from
	 * that square using the helper methods checkJumpUp, checkJumpDown, checkJumpRight,
	 * and checkJumpLeft.
	 * 
	 * @param playerColor the color of the pieces controlled by the player for which
	 * 					  possible actions are to be found.
	 * @return actions the set of actions which could be made, if the set is empty then 
	 * 				   no moves can be made and the game is over. 
	 */
	public ArrayList<Board> getActions(char playerColor){
		ArrayList<Board> actions = new ArrayList<Board>();
		int middleRows = size - 2;
		//System.out.println("getting actions");
		//get actions for top two rows
		for(int i = 0; i < size; i++){
			int startIndexRow = i;
			for(int j = 0; j < size; j++){
				int startIndexCol = j;
				if(board[i][j] == playerColor){
					//get actions for bottom two rows
					if(i >= middleRows){
						// jumping down or left not possible
						if(j < 2){
							//System.out.format("Checking square %d,%d\n", j, i);
							ArrayList<Board> boardJumpUp = checkJumpUp(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpUp);
							ArrayList<Board> boardJumpRight = checkJumpRight(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpRight);
						}
						// 	jumping down or right not possible
						else if(j >= middleRows){
							ArrayList<Board> boardJumpUp = checkJumpUp(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpUp);
							ArrayList<Board> boardJumpLeft = checkJumpLeft(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpLeft);						
						}
						// jumping down not possible
						else{
							ArrayList<Board> boardJumpUp = checkJumpUp(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpUp);
							ArrayList<Board> boardJumpLeft = checkJumpLeft(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpLeft);
							ArrayList<Board> boardJumpRight = checkJumpRight(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpRight);						
						}
					//get actions for top two rows	
					}else if(i < 2){
						//jumping up or left not possible
						if(j < 2){
							//System.out.format("Checking square %d,%d\n", j, i);
							ArrayList<Board> boardJumpDown = checkJumpDown(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpDown);
							ArrayList<Board> boardJumpRight = checkJumpRight(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpRight);
						}
						// jumping up or right not possible
						else if(j >= middleRows){
							ArrayList<Board> boardJumpDown = checkJumpDown(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpDown);
							ArrayList<Board> boardJumpLeft = checkJumpLeft(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpLeft);						
						}
						// 	jumping up not possible
						else{
							ArrayList<Board> boardJumpDown = checkJumpDown(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpDown);
							ArrayList<Board> boardJumpLeft = checkJumpLeft(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpLeft);
							ArrayList<Board> boardJumpRight = checkJumpRight(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpRight);
						}
					}
					// get actions for middle rows
					else{
						//jumping right not possible
						if(j >= middleRows){
							ArrayList<Board> boardJumpUp = checkJumpUp(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpUp);
							ArrayList<Board> boardJumpDown = checkJumpDown(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpDown);
							ArrayList<Board> boardJumpLeft = checkJumpLeft(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpLeft);
						}
						// jumping left not possible
						else if(j < 2){	
							ArrayList<Board> boardJumpUp = checkJumpUp(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpUp);
							ArrayList<Board> boardJumpDown = checkJumpDown(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpDown);
							ArrayList<Board> boardJumpRight = checkJumpRight(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpRight);
						}
						// all jumps possible
						else{
							//	System.out.format("Checking square %d,%d\n", j, i);
							ArrayList<Board> boardJumpUp = checkJumpUp(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpUp);
							ArrayList<Board> boardJumpDown = checkJumpDown(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpDown);
							ArrayList<Board> boardJumpRight = checkJumpRight(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpRight);
							ArrayList<Board> boardJumpLeft = checkJumpLeft(startIndexRow, startIndexCol, playerColor);
							actions.addAll(boardJumpLeft);
						}
					}
				}
			}
		}
	if(actions.isEmpty())
		this.gameOver = true;
	return actions;
	}
	
	
	/**Checks whether a move can be made by jumping up from the given start index.
	 * If the move is valid the move is added to the set of moves which are to be
	 * returned.  Also checks whether subsequent moves can be legally performed and,
	 * if so, adds these moves to the set of moves to be returned. 
	 * 
	 * @param startIndexRow the row location from which a legal jump is to be checked.
	 * @param col			the col location from which a legal jump is to be checked.
	 * @param playerColor   the color of the pieces controlled by the player for which
	 * 					    possible jumps are to be found.
	 * @return boardArr		the set of moves which could be made from the given starting
	 * 						indexes. 
	 */
	public ArrayList<Board> checkJumpUp(int startIndexRow, int col, char playerColor){
		char opponentColor;
		if (playerColor == 'b')
			opponentColor = 'w';
		else
			opponentColor = 'b';
		
		int jumpsPossible;
		int endIndexRow = startIndexRow - 2;
		//boolean canMoveAgain = true;
		char[][] currentBoard = this.getBoardArray();
		Board someBoard = new Board(currentBoard);
		ArrayList<Board> boardArr = new ArrayList<Board>();
		if(startIndexRow >= 6)
			jumpsPossible = 3;
		else if(startIndexRow >= 4)
			jumpsPossible = 2;
		else if(startIndexRow >= 2)
			jumpsPossible = 1;
		else
			jumpsPossible = 0;
		for(int i = 0; i < jumpsPossible; i++){
			if(board[startIndexRow - 1][col] == opponentColor && 
					board[endIndexRow][col] == ' '){
				someBoard.board[startIndexRow][col] = ' ';
				someBoard.board[startIndexRow - 1][col] = ' ';
				someBoard.board[endIndexRow][col] = playerColor;
				boardArr.add(someBoard);
				startIndexRow = endIndexRow;
				endIndexRow = startIndexRow - 2;
			}else
				break;
		}
		return boardArr;
	}
	
	/**Checks whether a move can be made by jumping down from the given start index.
	 * If the move is valid the move is added to the set of moves which are to be
	 * returned.  Also checks whether subsequent moves can be legally performed and,
	 * if so, adds these moves to the set of moves to be returned. 
	 * 
	 * @param startIndexRow the row location from which a legal jump is to be checked.
	 * @param col			the col location from which a legal jump is to be checked.
	 * @param playerColor   the color of the pieces controlled by the player for which
	 * 					    possible jumps are to be found.
	 * @return boardArr		the set of moves which could be made from the given starting
	 * 						indexes. 
	 */
	public ArrayList<Board> checkJumpDown(int startIndexRow, int col, char playerColor){
		
		char opponentColor;
		if (playerColor == 'b')
			opponentColor = 'w';
		else
			opponentColor = 'b';
		
		int endIndexRow = startIndexRow + 2;
		char[][] currentBoard = this.getBoardArray();
		Board someBoard = new Board(currentBoard);
		ArrayList<Board> boardArr = new ArrayList<Board>();
		int jumpsPossible;
		if(startIndexRow >= 6)
			jumpsPossible = 0;
		else if(startIndexRow >= 4)
			jumpsPossible = 1;
		else if(startIndexRow >= 2)
			jumpsPossible = 2;
		else
			jumpsPossible = 3;
		
		for(int i = 0; i < jumpsPossible; i++){
			if(board[startIndexRow + 1][col] == opponentColor && 
					board[endIndexRow][col] == ' '){
				someBoard.board[startIndexRow][col] = ' ';
				someBoard.board[startIndexRow + 1][col] = ' ';
				someBoard.board[endIndexRow][col] = playerColor;
				boardArr.add(someBoard);
				startIndexRow = endIndexRow;
				endIndexRow = startIndexRow + 2;
			}else
				break;
		}
		return boardArr;
	}
	
	/**Checks whether a move can be made by jumping right from the given start index.
	 * If the move is valid the move is added to the set of moves which are to be
	 * returned.  Also checks whether subsequent moves can be legally performed and,
	 * if so, adds these moves to the set of moves to be returned. 
	 * 
	 * @param startIndexRow the row location from which a legal jump is to be checked.
	 * @param col			the col location from which a legal jump is to be checked.
	 * @param playerColor   the color of the pieces controlled by the player for which
	 * 					    possible jumps are to be found.
	 * @return boardArr		the set of moves which could be made from the given starting
	 * 						indexes. 
	 */
	public ArrayList<Board> checkJumpRight(int row, int startIndexCol, char playerColor){
		
		char opponentColor;
		if (playerColor == 'b')
			opponentColor = 'w';
		else
			opponentColor = 'b';
		
		int endIndexCol = startIndexCol + 2;
		char[][] currentBoard = this.getBoardArray();
		ArrayList<Board> boardArr = new ArrayList<Board>();
		Board someBoard = new Board(currentBoard);
		int jumpsPossible;
		if(startIndexCol >= 6)
			jumpsPossible = 0;
		else if(startIndexCol >= 4)
			jumpsPossible = 1;
		else if(startIndexCol >= 2)
			jumpsPossible = 2;
		else
			jumpsPossible = 3;
		
		for(int i = 0; i < jumpsPossible; i++){
			if(board[row][startIndexCol + 1] == opponentColor && 
					board[row][endIndexCol] == ' '){
				someBoard.board[row][startIndexCol] = ' ';
				someBoard.board[row][startIndexCol + 1] = ' ';
				someBoard.board[row][endIndexCol] = playerColor;
				boardArr.add(someBoard);
				startIndexCol = endIndexCol;
				endIndexCol = startIndexCol + 2;
			}else
				break;
		}
		return boardArr;
	}
	
	/**Checks whether a move can be made by jumping left from the given start index.
	 * If the move is valid the move is added to the set of moves which are to be
	 * returned.  Also checks whether subsequent moves can be legally performed and,
	 * if so, adds these moves to the set of moves to be returned. 
	 * 
	 * @param startIndexRow the row location from which a legal jump is to be checked.
	 * @param col			the col location from which a legal jump is to be checked.
	 * @param playerColor   the color of the pieces controlled by the player for which
	 * 					    possible jumps are to be found.
	 * @return boardArr		the set of moves which could be made from the given starting
	 * 						indexes. 
	 */
	public ArrayList<Board> checkJumpLeft(int row, int startIndexCol, char playerColor){
		
		char opponentColor;
		if (playerColor == 'b')
			opponentColor = 'w';
		else
			opponentColor = 'b';
		
		int endIndexCol = startIndexCol - 2;
		char[][] currentBoard = this.getBoardArray();
		ArrayList<Board> boardArr = new ArrayList<Board>();
		Board someBoard = new Board(currentBoard);
		int jumpsPossible;
		if(startIndexCol >= 6)
			jumpsPossible = 3;
		else if(startIndexCol >= 4)
			jumpsPossible = 2;
		else if(startIndexCol >= 2)
			jumpsPossible = 1;
		else
			jumpsPossible = 0;
		
		for(int i = 0; i < jumpsPossible; i++){
			if(board[row][startIndexCol - 1] == opponentColor && 
					board[row][endIndexCol] == ' '){
				someBoard.board[row][startIndexCol] = ' ';
				someBoard.board[row][startIndexCol - 1] = ' ';
				someBoard.board[row][endIndexCol] = playerColor;
				boardArr.add(someBoard);
				startIndexCol = endIndexCol;
				endIndexCol = startIndexCol - 2;
			}else
				break;
		}
		return boardArr;
	}
}
