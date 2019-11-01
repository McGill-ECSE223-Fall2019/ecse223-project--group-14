package ca.mcgill.ecse223.quoridor.controller;

import java.awt.Color;
import java.io.File;
import java.sql.Time;

import javax.swing.Timer;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.view.QuoridorPage;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.GamePosition;

public class GameController {
	
	/**Helper method to be called when app starts to initialize board and default users
	 * 
	 * @author DariusPi
	 * 
	 */
	public void initQuorridor(){
		Quoridor q=QuoridorApplication.getQuoridor();
		initBoard(q);
		new User("user1",q);
		new User("user2",q);
	}
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
		
		//this can be taken out when init board is correct
		Tile player1StartPos = q.getBoard().getTile(36);
		Tile player2StartPos = q.getBoard().getTile(44);
		PlayerPosition player1Position = new PlayerPosition(q.getCurrentGame().getWhitePlayer(), player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(q.getCurrentGame().getBlackPlayer(), player2StartPos);
		
		GamePosition gamePos=new GamePosition(1, player1Position, player2Position, q.getCurrentGame().getWhitePlayer(), q.getCurrentGame());
		q.getCurrentGame().setCurrentPosition(gamePos);
		
		//q.getCurrentGame().getCurrentPosition().setPlayerToMove(p1);
		
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
	public void startTheClock(Quoridor q, Timer t)throws UnsupportedOperationException{
		t.start();
		q.getCurrentGame().setGameStatus(GameStatus.Running);
		//Time time=g.getBlackPlayer().getRemainingTime();
		
		
	}
	
	/**
	 * Helper method to count down clock
	 * 
	 * @author Darius Piecaitis
	 * 
	 */
	public boolean countdown(Quoridor q) {
		long tb=q.getCurrentGame().getCurrentPosition().getPlayerToMove().getRemainingTime().getTime();
		long ta=tb-1000;
		if (ta<=0) {
			return true;
		}
		q.getCurrentGame().getCurrentPosition().getPlayerToMove().setRemainingTime(new Time(ta));
		return false;
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
			User u =q.getUser(i);
			int thinkingTime=180;
			if (colour.compareTo("white")==0) {
				if (q.getCurrentGame().getWhitePlayer()!=null) {
					q.getCurrentGame().getWhitePlayer().setUser(u);
				}
				else {
					Player player1 = new Player(new Time(thinkingTime), q.getUser(i), 9, Direction.Horizontal);
					q.getCurrentGame().setWhitePlayer(player1);  //(new Player(null, q.getUser(i), null));
				}
				
				
			}
			else {
				if (q.getCurrentGame().getWhitePlayer().getUser().getName().compareTo(name)==0) {
					return "User Already Selected";
				}
				
				if (q.getCurrentGame().getBlackPlayer()!=null) {
					q.getCurrentGame().getBlackPlayer().setUser(u);
				}
				else {
					Player player2 = new Player(new Time(thinkingTime), q.getUser(i), 1, Direction.Horizontal);
					q.getCurrentGame().setBlackPlayer(player2);
				}
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
		if (name==null || name.length()==0) {
			return "Invalid Input";
		}
		if (i==-1) {
			User u=new User(name, q);
			q.addUser(u);
			int thinkingTime=180;
			
			if (colour.compareTo("white")==0) {
				if (q.getCurrentGame().getWhitePlayer()!=null) {
					q.getCurrentGame().getWhitePlayer().setUser(u);
				}
				else {
					Player player1 = new Player(new Time(thinkingTime), u, 9, Direction.Horizontal);
					q.getCurrentGame().setWhitePlayer(player1);  //(new Player(null, q.getUser(i), null));
				}
				
				
			}
			else {
				if (q.getCurrentGame().getBlackPlayer()!=null) {
					q.getCurrentGame().getBlackPlayer().setUser(u);
				}
				else {
					Player player2 = new Player(new Time(thinkingTime), u, 1, Direction.Horizontal);
					q.getCurrentGame().setBlackPlayer(player2);
				}
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
	
	public boolean viewValWallPosition(int x1,int y1, int x2, int y2) {
		//GamePosition gp = new GamePosition();
		return true;
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

	public void setTime (Quoridor q, int min, int sec) {
		//throw new UnsupportedOperationException();
		long time=(min*60+sec)*1000;		//time takes in ms
		q.getCurrentGame().getBlackPlayer().setRemainingTime(new Time(time));
		q.getCurrentGame().getWhitePlayer().setRemainingTime(new Time(time));
		q.getCurrentGame().setGameStatus(GameStatus.ReadyToStart);
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
	public void rotateWall() throws UnsupportedOperationException{
		WallMove wmc = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		if (wmc != null) {
			Direction dir = wmc.getWallDirection();
			if (dir.equals(Direction.Horizontal)) dir = Direction.Vertical;
			else dir = Direction.Vertical;
			wmc.setWallDirection(dir);
		}
	}
	
	/**
	 *  * For Grab Wall feature 
	 * Attempts to set current WallMove candidate using a Wall in current player's stock
	 * 
	 * @author louismollick
	 * 
	 * @throws UnsupportedOperationException
	 */
	public void grabWall() throws UnsupportedOperationException{
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		Player playerToMove = game.getCurrentPosition().getPlayerToMove();
		
		if(playerToMove == null) throw new UnsupportedOperationException();
		if(playerToMove.hasGameAsBlack()) {
			
		}
		else if (playerToMove.hasGameAsWhite()) {
			
		}
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
	
	public void switchPlayer(Quoridor q) {
		if (q.getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite()) {
			q.getCurrentGame().getCurrentPosition().setPlayerToMove(q.getCurrentGame().getBlackPlayer());
		}
		else {
			q.getCurrentGame().getCurrentPosition().setPlayerToMove(q.getCurrentGame().getWhitePlayer());
		}
	}
	
	/**
	 *  * For View, used in QuoridorMouseListener
	 * Helper function which returns the Color of the current player
	 * 
	 * @author louismollick
	 */
	public Color getCurrentPlayerColor() {
		Player p = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
		if (p.hasGameAsBlack()) {
			return Color.BLACK;
		}
		else if (p.hasGameAsWhite()) {
			return Color.WHITE;
		}
		return null;
	}
}
