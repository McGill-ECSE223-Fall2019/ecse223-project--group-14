package ca.mcgill.ecse223.quoridor.controller;

import java.io.BufferedReader;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.Scanner;
import java.util.StringTokenizer;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.List;


import javax.swing.Timer;
import javax.swing.text.html.HTMLDocument.Iterator;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
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
	 */
	public void initGame(Quoridor q){
		
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
	 * @param q,t
	 */
	public void startTheClock(Quoridor q, Timer t){
		t.start();
		q.getCurrentGame().setGameStatus(GameStatus.Running);
		//Time time=g.getBlackPlayer().getRemainingTime();
		
		
	}
	
	/**
	 * Helper method to count down clock, returns true if time out, else false
	 * 
	 * @author Darius Piecaitis
	 * @param q
	 * @return boolean
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
	 * Selects user name and assigns to colour, returns name if correct, else error message
	 * 
	 * @author DariusPi
	 * 
	 * @param q
	 * @param name
	 * @param colour
	 * @return
	 */
	public String selectUsername(Quoridor q, String name, String colour) {
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
	 * Creates new user, adds it to quoridor, assigns to colour and returns its name if valid, error message if invalid
	 * 
	 * @author DariusPi
	 * 
	 * @param q
	 * @param name
	 * @param colour
	 * @return
	 */
	public String createUsername(Quoridor q, String name, String colour) {
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
	public int doesUserExist(Quoridor q, String name){
		for (int i=0; i<q.numberOfUsers();i++) {
			if (q.getUser(i).getName().contentEquals(name)) {
				return i;
			}
		}
		return -1;
		
	}
	
	/**
	 * View method that checks if wall move was valid and returns the result
	 * 
	 * @author DariusPi
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public boolean viewValWallPosition(int x1,int y1, int x2, int y2) {
		Quoridor q =QuoridorApplication.getQuoridor();
		GamePosition curr= q.getCurrentGame().getCurrentPosition();
		/*if (curr.getPlayerToMove().hasGameAsBlack()) {
			curr.setPlayerToMove(q.getCurrentGame().getWhitePlayer());
		}
		else {
			curr.setPlayerToMove(q.getCurrentGame().getBlackPlayer());
		}*/
		
		//TODO
		//add wall into current position's walls in board, maybe use wall id in array to map 
		
		//GamePosition gp = new GamePosition(curr.getId()+1, curr.getWhitePosition(), curr.getBlackPosition(), curr.getPlayerToMove(), q.getCurrentGame());
		
		//return validatePos(curr);
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

	public void SaveGame(Quoridor q, String FileName) {
	
//		throw new UnsupportedOperationException();	

		PrintWriter writer;
		try {
			writer = new PrintWriter(FileName, "UTF-8");
			
			//if white playing, white should be on the first line
			if(q.getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite())
			{
				
				int rowW = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow();
				
				int columnW = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn();
				
				String ColumnW = "";
				
				switch(columnW) {
				
				case 1:  ColumnW = "a";
					break;
				
				case 2:  ColumnW = "b";
					break;
					
				case 3:   ColumnW = "c";
					break;
					
				case 4:   ColumnW = "d";
					break;
					
				case 5:  ColumnW = "e";
					break;
					
				case 6:  ColumnW = "f";
					break;
					
				case 7:  ColumnW = "g";
					break;
					
				case 8:  ColumnW = "h";
					break;
					
				case 9: ColumnW = "i";
					
				}
				
				
				
				writer.println("W: " + ColumnW + rowW);
				//writer.print("W: " + ColumnW + ", " + rowW);
				
				List<Wall> wWall	= q.getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard();
				
				for(Wall pos: wWall)
				{ 
				
					int WallColumnWhite = pos.getMove().getTargetTile().getColumn();
					String WallColumnLetterWhite = "";
					
					switch(WallColumnWhite) {
					
					case 1:  WallColumnLetterWhite = "a";
						break;
					
					case 2:  WallColumnLetterWhite = "b";
						break;
						
					case 3:   WallColumnLetterWhite = "c";
						break;
						
					case 4:   WallColumnLetterWhite = "d";
						break;
						
					case 5:  WallColumnLetterWhite = "e";
						break;
						
					case 6:  WallColumnLetterWhite = "f";
						break;
						
					case 7:  WallColumnLetterWhite = "g";
						break;
						
					case 8:  WallColumnLetterWhite = "h";
						break;
						
					case 9: WallColumnLetterWhite = "i";
						
					}
				
					
					writer.print(", " + WallColumnLetterWhite + pos.getMove().getTargetTile().getRow() + pos.getMove().getWallDirection());
					

				//	writer.print();
					
					
				}
				
				

int rowB = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow();
				
				int columnB = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn();
				
				String ColumnB = "";
				
				switch(columnB) {
				
				case 1:  ColumnB = "a";
					break;
				
				case 2:  ColumnB = "b";
					break;
					
				case 3:   ColumnB = "c";
					break;
					
				case 4:   ColumnB = "d";
					break;
					
				case 5:  ColumnB = "e";
					break;
					
				case 6:  ColumnB = "f";
					break;
					
				case 7:  ColumnB = "g";
					break;
					
				case 8:  ColumnB = "h";
					break;
					
				case 9: ColumnB = "i";
					
				}
				
				
				writer.println("B: "+ rowB + ColumnB);
			//	writer.print("B: "+ rowB + ", " + ColumnB);

				List<Wall> bWall	= q.getCurrentGame().getCurrentPosition().getBlackWallsOnBoard();
				
				for(Wall pos1: bWall)
				{ 
				
					int WallColumnBlack = pos1.getMove().getTargetTile().getColumn();
					String WallColumnLetterBlack = "";
					
					switch(WallColumnBlack) {
					
					case 1:  WallColumnLetterBlack = "a";
						break;
					
					case 2:  WallColumnLetterBlack = "b";
						break;
						
					case 3:   WallColumnLetterBlack = "c";
						break;
						
					case 4:   WallColumnLetterBlack = "d";
						break;
						
					case 5:  WallColumnLetterBlack = "e";
						break;
						
					case 6:  WallColumnLetterBlack = "f";
						break;
						
					case 7:  WallColumnLetterBlack = "g";
						break;
						
					case 8:  WallColumnLetterBlack = "h";
						break;
						
					case 9: WallColumnLetterBlack = "i";
						
					}
				
					
					writer.print(", " + WallColumnLetterBlack + pos1.getMove().getTargetTile().getRow() + pos1.getMove().getWallDirection());
					
				}
				
				
			    writer.close();	
			}
			
			
			//if black playing, black should be on the first line
			else if (q.getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsBlack())
				
			{
				
	

int rowB = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow();
				
				int columnB = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn();
				
				String ColumnB = "";
				
				switch(columnB) {
				
				case 1:  ColumnB = "a";
					break;
				
				case 2:  ColumnB = "b";
					break;
					
				case 3:   ColumnB = "c";
					break;
					
				case 4:   ColumnB = "d";
					break;
					
				case 5:  ColumnB = "e";
					break;
					
				case 6:  ColumnB = "f";
					break;
					
				case 7:  ColumnB = "g";
					break;
					
				case 8:  ColumnB = "h";
					break;
					
				case 9: ColumnB = "i";
					
				}
				
				
				writer.println("B: "+ rowB + ColumnB);
			//	writer.print("B: "+ rowB + ", " + ColumnB);

	List<Wall> bWall	= q.getCurrentGame().getCurrentPosition().getBlackWallsOnBoard();
				
				for(Wall pos1: bWall)
				{ 
				
					int WallColumnBlack = pos1.getMove().getTargetTile().getColumn();
					String WallColumnLetterBlack = "";
					
					switch(WallColumnBlack) {
					
					case 1:  WallColumnLetterBlack = "a";
						break;
					
					case 2:  WallColumnLetterBlack = "b";
						break;
						
					case 3:   WallColumnLetterBlack = "c";
						break;
						
					case 4:   WallColumnLetterBlack = "d";
						break;
						
					case 5:  WallColumnLetterBlack = "e";
						break;
						
					case 6:  WallColumnLetterBlack = "f";
						break;
						
					case 7:  WallColumnLetterBlack = "g";
						break;
						
					case 8:  WallColumnLetterBlack = "h";
						break;
						
					case 9: WallColumnLetterBlack = "i";
						
					}
				
					
					writer.print(", " + WallColumnLetterBlack + pos1.getMove().getTargetTile().getRow() + pos1.getMove().getWallDirection());
					
				}
				
int rowW = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow();
				
				int columnW = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn();
				
				String ColumnW = "";
				
				switch(columnW) {
				
				case 1:  ColumnW = "a";
					break;
				
				case 2:  ColumnW = "b";
					break;
					
				case 3:   ColumnW = "c";
					break;
					
				case 4:   ColumnW = "d";
					break;
					
				case 5:  ColumnW = "e";
					break;
					
				case 6:  ColumnW = "f";
					break;
					
				case 7:  ColumnW = "g";
					break;
					
				case 8:  ColumnW = "h";
					break;
					
				case 9: ColumnW = "i";
					
				}
				
				
				
				writer.println("W: " + ColumnW  + rowW);
				//writer.print("W: " + ColumnW + ", " + rowW);
				
		List<Wall> wWall	= q.getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard();
		
		for(Wall pos: wWall)
		{ 
		
			int WallColumnWhite = pos.getMove().getTargetTile().getColumn();
			String WallColumnLetterWhite = "";
			
			switch(WallColumnWhite) {
			
			case 1:  WallColumnLetterWhite = "a";
				break;
			
			case 2:  WallColumnLetterWhite = "b";
				break;
				
			case 3:   WallColumnLetterWhite = "c";
				break;
				
			case 4:   WallColumnLetterWhite = "d";
				break;
				
			case 5:  WallColumnLetterWhite = "e";
				break;
				
			case 6:  WallColumnLetterWhite = "f";
				break;
				
			case 7:  WallColumnLetterWhite = "g";
				break;
				
			case 8:  WallColumnLetterWhite = "h";
				break;
				
			case 9: WallColumnLetterWhite = "i";
				
			}
		
			
			writer.print(", " + WallColumnLetterWhite + pos.getMove().getTargetTile().getRow() + pos.getMove().getWallDirection());
			

		//	writer.print();
			
			
		}
				
				 writer.close();	
			}
			
			    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	public Game initSavedGame(Quoridor quoridor, String filename) /*throws UnsupportedOperationException*/ {
		
		Player p1=new Player(new Time(10), quoridor.getUser(0), 9, Direction.Horizontal);
		Player p2 = new Player(new Time(10), quoridor.getUser(1), 1, Direction.Horizontal);
		new Game(GameStatus.Initializing, MoveMode.PlayerMove, quoridor);
		
		quoridor.getCurrentGame().setWhitePlayer(p1);
		quoridor.getCurrentGame().setBlackPlayer(p2);
		
		File file = new File(filename);
		Scanner fileSC = null;
		try {
			fileSC = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.err.println("File at filename does not exist!");
			e.printStackTrace();
			return null;
		}
		
		//assume that file is well formed even if invalid
		//firstplayer
		StringTokenizer st = new StringTokenizer(fileSC.nextLine()); //set player
		String playerString = st.nextToken();
		
		Game game = quoridor.getCurrentGame();
		boolean isWhite = (playerString.contentEquals("W:")) ? true : false;
		
		Player player = (isWhite) ? game.getWhitePlayer() : game.getBlackPlayer();
		
		int moveID = 1;
		int wallID = (isWhite) ? 1 : 11;
		
		try {
			loadPlayerPosition(player, st, wallID, quoridor, isWhite);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//notify that load failed without validation checking 
			System.err.println("AAAAAAA");
			return null;
		}
		
		//nextplayer
		st = new StringTokenizer(fileSC.nextLine());
		isWhite = !isWhite;
		
		playerString = st.nextToken();
		player = (isWhite) ? game.getWhitePlayer() : game.getBlackPlayer();
		
		wallID = (isWhite) ? 1 : 11;
		moveID = 1;
		
		try {
			loadPlayerPosition(player, st, wallID, quoridor, isWhite);
		} catch (Exception e) {
			//same story
			return null;
		}
		
		//TODO: THink about separating this process into its subroutine
		fileSC.close();
		return game; //TODO: REPLACE
		//throw new UnsupportedOperationException();
	}
	
	//private static Player getPlayer(boolean isWhite, Game game) {
	//	return (isWhite == true) ? game.getWhitePlayer() : game.getBlackPlayer();
	//}
	
	/*
	 * getIndex from Stepdefinitions, cleaned up a bit
	 * Credit to Saifullah for getting it working properly in Iteration 2
	 */
	private static int getIndex(int row, int col) {
		
		if(row <= 0 || col <= 0 || row > 9 || col > 9) {
			return -10; //sentinel for indexNotFound
		}
		else {
		return ((((row-1)*9)+col)-1);
		}
		
	}
	
	/*
	 * Color-agnostic setPosition method for loadPosition feature
	 */
	private static boolean setPosition(PlayerPosition aPlayerPosition, GamePosition aGamePosition, 
			boolean isWhite) {
		if (isWhite) {
			if (!aGamePosition.setWhitePosition(aPlayerPosition))
				return false;
		} else {
			if (!aGamePosition.setBlackPosition(aPlayerPosition))
				return false;
		}
		return true;
	}
	

	
	private static void loadPlayerPosition(Player player, StringTokenizer st, int wallID, Quoridor quoridor, boolean isWhite) throws Exception {
		Game game = quoridor.getCurrentGame();
		Board board = quoridor.getBoard();
		GamePosition gP = game.getCurrentPosition();
		int moveID = 1;
		int runningWallID = wallID;
		while (st.hasMoreTokens()) {
			String move = st.nextToken(",");
			int col = move.charAt(0) - 'a' + 1;
			int row = move.charAt(1);
			Tile tile = board.getTile(getIndex(row, col));
			if (move.length() == 2) { //pawn move
				//need to find a playerpos at specified coord
				//need to match to specified coord first
				PlayerPosition pp = new PlayerPosition(player, tile);
				//if (!setPosition(pp, gP, isWhite)) {
				//	throw new Exception("Unable to set position of player! (White = " + isWhite + ")");
				//}
			} else {	//wall move
				Direction dir;
				if (move.charAt(2) == 'h')
					dir = Direction.Horizontal;
				else
					dir = Direction.Vertical;
				Wall wall = player.getWall(runningWallID);
				wall.setMove(new WallMove(moveID, 1, player, tile, game, dir, wall));
				runningWallID++;
				moveID++;
			}
		}
		
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
	public Boolean validatePos(GamePosition posToValidate) {
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
