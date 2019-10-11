package ca.mcgill.ecse223.quoridor.controller;

import java.sql.Time;

import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
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
	 * 
	 * @throws UnsupportedOperationException
	 */
	public Game initGame(Quoridor q)throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
		/*GameStatus aGameStatus = GameStatus.Initializing; MoveMode aMoveMode = null; Player aWhitePlayer = null; Player aBlackPlayer = null; 
		
		Game g = new Game(aGameStatus, aMoveMode, aWhitePlayer, aBlackPlayer, q);
		q.setCurrentGame(g);
		return g;*/
	}
	
	/**
	 * For Start New Game feature
	 * Assigns username to player with given colour by either choosing an existing one or creating a new one
	 * 
	 * @author DariusPi
	 * 
	 * @throws UnsupportedOperationException
	 */
	public void assignUsername(Quoridor q, Player colour)throws UnsupportedOperationException {
		
		throw new UnsupportedOperationException();
		/*User user= new User(null,q);
		Time time =null;
		Player play= new Player(null,user,null);
		if (colour.equals("white")) {
			q.getCurrentGame().setWhitePlayer(play);
		}
		else {
			q.getCurrentGame().setBlackPlayer(play);
		}
		throw new UnsupportedOperationException();*/
	}
	
	/**
	 * For Start New Game feature
	 * Initilizes game clocks to be used for player time limits
	 * 
	 * @author DariusPi
	 * 
	 * @throws UnsupportedOperationException
	 */
	public void startTheClock(Game g)throws UnsupportedOperationException{
		
		throw new UnsupportedOperationException();
		//Time time=g.getBlackPlayer().getRemainingTime();
		
		
	}
	
	/**
	 * For Provide Or Select User Name feature
	 * Selects and returns name
	 * 
	 * @author DariusPi
	 * 
	 * @throws UnsupportedOperationException
	 */
	public String selectUsername(Quoridor q, String name)throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
		/*int i=doesUserExist(q,name);
		if (i==-1) {
			//error statement
		}
		else {
			return q.getUser(i).getName();
		}*/
		
	}
	
	/**
	 * For Provide Or Select User Name feature
	 * Creates new user, adds it to quoridor and returns its name
	 * 
	 * @author DariusPi
	 * 
	 * @throws UnsupportedOperationException
	 */
	public String createUsername(Quoridor q, String name)throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
		/*int i=doesUserExist(q,name);
		if (i==-1) {
			q.addUser(name);
			return name;
		}
		else {
			String msg= name +" already exists";
			return  msg;
		}*/
		
	}
	
	/**
	 * For Provide Or Select User Name feature
	 * Determines if a user with the given name has already been created, if so return index else return -1
	 * 
	 * @author DariusPi
	 * 
	 * @throws UnsupportedOperationException
	 */
	public int doesUserExist(Quoridor q, String name) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
		/*for (int i=0; i<q.numberOfUsers();i++) {
			if (q.getUser(i).getName().contentEquals(name)) {
				return i;
			}
		}
		return -1;*/
		
	}
	
	
	
	
	public Board initBoard(Quoridor q) throws UnsupportedOperationException{
		Board board = new Board(q);
		return board;
	}
	
	/**
	 * For Rotate Wall feature 
	 * Attempts to rotate the current WallMove candidate's Direction
	 * 
	 * @author louismollick
	 * 
	 * @throws UnsupportedOperationException
	 */
	public void rotateWall(Game game) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * For Grab Wall feature 
	 * Attempts to set current WallMove candidate using a Wall in current player's stock
	 * 
	 * @author louismollick
	 * 
	 * @throws UnsupportedOperationException
	 */
	public void grabWall(Game game) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * For Load Position feature
	 * Initiates loading a saved game
	 * 
	 * @author FSharp4
	 * @throws UnsupportedOperationException
	 */
	public Game initSaveGameLoad(Quoridor quoridor, String filename) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * For Load Position feature
	 * Validates position, returns whether position is valid
	 * 
	 * @author FSharp4
	 * @throws UnsupportedOperationException
	 */
	
	public boolean isValid(GamePosition gamePosition) {
		throw new UnsupportedOperationException();
		//todo: Do validation of of GamePosition
	}
	
	
	/**
	 * For Load Position feature
	 * Attempts to set load position. Returns an error if position is invalid
	 * 
	 * @author FSharp4
	 * @throws UnsupportedOperationException
	 */
	public void loadGame(Quoridor quoridor) 
			throws UnsupportedOperationException {
		  //throws UnsupportedOperationException, IOException {
		
		GamePosition gamePosition = quoridor.getCurrentGame().getCurrentPosition();
		
		if (isValid(gamePosition)) {
			//update GUI here
			quoridor.getCurrentGame().setGameStatus(GameStatus.ReadyToStart);
			//return true
		} else {
			//throw new IOException("Load error: Position invalid");
		}
		
		
		throw new UnsupportedOperationException();
	}
	
	/**
	 * For Load Position feature
	 * Sets current turn, returns true if this is successful
	 * 
	 * @author FSharp4
	 * @throws UnsupportedOperationException
	 */
	public boolean setCurrentTurn(Player player, Quoridor quoridor) 
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * For InitializeBoard Feature
	 * Checks if Board Initialization is Initiated (such as when user selects new game and has 
	 * entered in the necessary info (i.e. game is ready to start)
	 * 
	 * @author FSharp4
	 * @throws UnsupportedOperationException
	 */
	public boolean isBoardInitializationInitiated(Quoridor quoridor) throws UnsupportedOperationException {
		//Check if board going to be initialized @GUI
		throw new UnsupportedOperationException();
	}
	
	/**
	 * For InitializeBoard Feature
	 * Checks if player's clock is counting down
	 */
	
	public boolean isClockCountingDown(Player player) throws UnsupportedOperationException {
		//This interacts with the clock Time object and checks GUI to see if a countdown is shown
		throw new UnsupportedOperationException();
	}
	/**
	 * For ValidatePosition Feature 
	 * 
	 * @author ohuss1
	 * 
	 * @throws UnsupportedOperationException
	 */
	public void validatePawnPos() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
		
	}
}
