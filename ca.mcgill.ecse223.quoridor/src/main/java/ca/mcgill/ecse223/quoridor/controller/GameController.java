package ca.mcgill.ecse223.quoridor.controller;

import java.io.File;
import java.sql.Time;

import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.GamePosition;

public class GameController {
	
	/**
	 * For Start New Game feature 
	 * initializes a game with null parameters
	 * 
	 * @author DariusPi
	 * @param q
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public void initGame(Quoridor q)throws UnsupportedOperationException{
		
		Player p1=new Player(new Time(10), q.getUser(0), 9, Direction.Horizontal);
		Player p2 = new Player(new Time(10), q.getUser(1), 1, Direction.Horizontal);
		new Game(GameStatus.Initializing, MoveMode.PlayerMove, q);
		
		q.getCurrentGame().setWhitePlayer(p1);
		q.getCurrentGame().setBlackPlayer(p2);
		
	}

	
	/**
	 * * For Start New Game feature
	 * Initilizes game clocks to be used for player time limits
	 * 
	 * @author DariusPi
	 * 
	 * @param g
	 * @throws UnsupportedOperationException
	 */
	public void startTheClock(Game g)throws UnsupportedOperationException{
		
		throw new UnsupportedOperationException();
		//Time time=g.getBlackPlayer().getRemainingTime();
		
		
	}
	
	/**
	 * For Provide Or Select User Name feature
	 * Selects user name and assigns to colour, returns name
	 * 
	 * @author DariusPi
	 * 
	 * @param q
	 * @param name
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public String selectUsername(Quoridor q, String name, String colour)throws UnsupportedOperationException {
		//throw new UnsupportedOperationException();
		int i=doesUserExist(q,name);
		if (i==-1) {
			String msg= name +" does not exist";
			return msg;
		}
		else {
			int thinkingTime=180;
			if (colour.compareTo("white")==0) {
				
				Player player1 = new Player(new Time(thinkingTime), q.getUser(i), 9, Direction.Horizontal);
				q.getCurrentGame().setWhitePlayer(player1);  //(new Player(null, q.getUser(i), null));
			}
			else {
				Player player2 = new Player(new Time(thinkingTime), q.getUser(i), 1, Direction.Horizontal);
				q.getCurrentGame().setBlackPlayer(player2);			//(new Player(null, q.getUser(i), null));
			}
			return name;
			
		}
		
	}
	
	/**
	 *  * For Provide Or Select User Name feature
	 * Creates new user, adds it to quoridor, assigns to colour and returns its name
	 * 
	 * @author DariusPi
	 * 
	 * @param q
	 * @param name
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public String createUsername(Quoridor q, String name, String colour)throws UnsupportedOperationException {
		//throw new UnsupportedOperationException();
		int i=doesUserExist(q,name);
		if (i==-1) {
			User u=new User(name, q);
			q.addUser(u);
			//int thinkingTime=180;
			if (colour.compareTo("white")==0) {
				/*Player player1 = new Player(new Time(thinkingTime), u, 9, Direction.Horizontal);
				q.getCurrentGame().setWhitePlayer(player1);*/
				q.getCurrentGame().getWhitePlayer().setUser(u);
			}
			else {
				//Player player2 = new Player(new Time(thinkingTime), u, 1, Direction.Horizontal);
				q.getCurrentGame().getBlackPlayer().setUser(u);
			}
			return name;
		}
		else {
			String msg= name +" already exists";
			return  msg;
		}
		
	}
	
	/**
	 * * For Provide Or Select User Name feature
	 * Determines if a user with the given name has already been created, if so return index else return -1
	 * 
	 * @author DariusPi
	 * 
	 * @param q
	 * @param name
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public int doesUserExist(Quoridor q, String name) throws UnsupportedOperationException{
		//throw new UnsupportedOperationException();
		for (int i=0; i<q.numberOfUsers();i++) {
			if (q.getUser(i).getName().contentEquals(name)) {
				return i;
			}
		}
		return -1;
		
	}
	
	
	/**
	 * For setTotalThinkingTime feature 
	 * 
	 * @author AmineMallek
	 * 
	 * @param min (number of)
	 * 
	 * @param sec (number of)
	 * 
	 * @throws UnsupportedOperationException
	 */

	public void setTime (int min, int sec) {
		throw new UnsupportedOperationException();
	}


	/**
	 * For savePosition feature 
	 * 
	 * @author AmineMallek
	 * 
	 * @param FileName
	 * 
	 * @throws UnsupportedOperationException
	 */

	public Boolean filename_exists (String FileName) {
		File filename = new File(FileName);
		if(filename.exists()) return true;
		else return false;
	}

	public void SaveGame(String FileName) {
		throw new UnsupportedOperationException();	
		//This saves the game
	}


	
	
	/**
	 * * For Rotate Wall feature 
	 * Attempts to rotate the current WallMove candidate's Direction
	 * 
	 * @author louismollick
	 * 
	 * @param game
	 * @throws UnsupportedOperationException
	 */
	public void rotateWall(Game game) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 *  * For Grab Wall feature 
	 * Attempts to set current WallMove candidate using a Wall in current player's stock
	 * 
	 * @author louismollick
	 * 
	 * @param game
	 * @throws UnsupportedOperationException
	 */
	public void grabWall(Game game) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * For Move Wall feature
	 * Attempts to move the wall between possible rows and columns of the board 
	 * 
	 * @author Saifullah
	 * 
	 * @param game
	 * @param side
	 * @throws UnsupportedOperationException
	 */
	public void moveWall(Game game, String side) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * For Move Wall feature
	 * Attempts to drop the wall (place the wall) between possible rows and columns of the board 
	 * 
	 * @author Saifullah
	 * 
	 * @param game
	 * @throws UnsupportedOperationException
	 */
	public void dropWall(Game game) throws UnsupportedOperationException{		
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Testing method validatePosition
	 * 
	 *I just added this for the sake of testing till the actual method is done
	 * @author Saifullah
	 * @param game
	 * @throws UnsupportedOperationException
	 */
	public boolean validatePosition(Game game) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * * For Load Position feature
	 * Initiates loading a saved game
	 * 
	 * @author FSharp4
	 * @param quoridor
	 * @param filename
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public Game initSaveGameLoad(Quoridor quoridor, String filename) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 *  * For Load Position feature
	 * Attempts to set load position. Returns an error if position is invalid
	 * 
	 * @author FSharp4
	 * @param quoridor
	 * @throws UnsupportedOperationException
	 */
	public void loadGame(Quoridor quoridor) 
			throws UnsupportedOperationException {
		  //throws UnsupportedOperationException, IOException {
		
		Game game = quoridor.getCurrentGame();
		
		if (validatePosition(game)) {
			//update GUI here
			quoridor.getCurrentGame().setGameStatus(GameStatus.ReadyToStart);
			//return true
		} else {
			//throw new IOException("Load error: Position invalid");
		}
		
		
		throw new UnsupportedOperationException();
	}
	
	/**
	 * * For Load Position feature
	 * Sets current turn, returns true if this is successful
	 * 
	 * @author FSharp4
	 * @param player
	 * @param quoridor
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public boolean setCurrentTurn(Player player, Quoridor quoridor) 
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 *  * For InitializeBoard Feature
	 * Checks if Board Initialization is Initiated (such as when user selects new game and has 
	 * entered in the necessary info (i.e. game is ready to start)
	 * 
	 * @author FSharp4
	 * @param quoridor
	 * @return
	 * @throws UnsupportedOperationException
	 */
	public boolean isBoardInitializationInitiated(Quoridor quoridor) throws UnsupportedOperationException {
		//Check if board going to be initialized @GUI
		throw new UnsupportedOperationException();
	}
	
	/**
	 * * For InitializeBoard Feature
	 * Checks if player's clock is counting down
	 * 
	 * @param player
	 * @return
	 * @throws UnsupportedOperationException
	 */
	
	public boolean isClockCountingDown(Player player) throws UnsupportedOperationException {
		//This interacts with the clock Time object and checks GUI to see if a countdown is shown
		throw new UnsupportedOperationException();
	}
	/**
	 * For ValidatePosition Feature 
	 * Checks if if pawn position is valid or not
		
	 * 
	 * @author ohuss1
	 * 
	 * @throws UnsupportedOperationException
	 */
	public String validatePos(GamePosition posToValidate) {
		// TODO Auto-generated method stub
		//Checks if pawn position overlaps with another pawn or a wall position overlaps with a wall or out of track pawn or wall
		//if yes returns error
		//if no returns ok
		//will compare this with String result in @then
		throw new UnsupportedOperationException();
	}

	/**
	 * For Initialize Board feature
	 * Prepares a board populated with tiles in a 9x9 grid, returning it.
	 * 
	 * @author FSharp4
	 * @param q
	 * @return new Board
	 * @throws UnsupportedOperationException
	 */
	public Board initBoard(Quoridor q) {
		Board board = new Board(q);
		for (int col = 1; col <= 9; col++) {
			for(int row = 1; row <= 9; row ++) {
				Tile tile = new Tile(row, col, board);
			}
		}
		return board;
	}
}
