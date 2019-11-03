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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
		
		Board board = new Board(q);
		// Creating tiles by rows, i.e., the column index changes with every tile
		// creation
		for (int i = 1; i <= 9; i++) { // rows
			for (int j = 1; j <= 9; j++) { // columns
				board.addTile(i, j);
			}
		}
		
		new User("user1",q);
		new User("user2",q);
		
		/*for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				new Wall(i * 10 + j, players[i]);
			}
		}*/
		
		//initBoard(q);
	}
	
	public void addWalls() {
		Quoridor q=QuoridorApplication.getQuoridor();
		GamePosition pos=q.getCurrentGame().getCurrentPosition();
		for (int j = 0; j < 10; j++) {
			pos.addWhiteWallsInStock(new Wall(j, q.getCurrentGame().getWhitePlayer()));
			//new Wall(j+10, q.getCurrentGame().getBlackPlayer());
			pos.addBlackWallsInStock(new Wall(j+10, q.getCurrentGame().getBlackPlayer()));
		}
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
		
		//for (int i = 0; i < 2; i++) {
			/*for (int j = 0; j < 10; j++) {
				new Wall(j, p1);
				new Wall(j+10, p2);
			}*/
		//}
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
	public boolean valWallPosition(int x1,int y1, String dir, int id) {
		Quoridor q =QuoridorApplication.getQuoridor();
		GamePosition curr= q.getCurrentGame().getCurrentPosition();
		
		List<Wall> wWall = q.getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard();
		List<Wall> bWall = q.getCurrentGame().getCurrentPosition().getBlackWallsOnBoard();
		Direction dirc;
		int col =x1;
		int row=y1;
		if (dir.compareTo("vertical")==0) {
			dirc=Direction.Vertical;
			for(Wall pos: wWall){
				if (pos.getMove().getWallDirection().toString().compareTo("Vertical")==0) {
					if ((pos.getMove().getTargetTile().getColumn()+1==col)||(pos.getMove().getTargetTile().getColumn()-1==col)) {
						return false;
					}
				}
				
				//check for horizontal
			}
			
			for(Wall pos: bWall){
				if (pos.getMove().getWallDirection().toString().compareTo("Vertical")==0) {
					if ((pos.getMove().getTargetTile().getColumn()+1==col)||(pos.getMove().getTargetTile().getColumn()-1==col)) {
						return false;
					}
				}
				
				//check for horizontal
			}
			
			
			//check for overl in hors
			
		}
		else {
			dirc=Direction.Horizontal;
		}
		
		if (id<10) { 	//white
			Wall w=q.getCurrentGame().getWhitePlayer().getWall(id);
			new WallMove(q.getCurrentGame().getMoves().size(), 0, q.getCurrentGame().getWhitePlayer(), q.getBoard().getTile(col+row*9), q.getCurrentGame(), dirc, w);
			q.getCurrentGame().getCurrentPosition().removeWhiteWallsInStock(q.getCurrentGame().getWhitePlayer().getWall(id));
			q.getCurrentGame().getCurrentPosition().addWhiteWallsOnBoard(q.getCurrentGame().getWhitePlayer().getWall(id));
		}
		
		else {
			Wall w=q.getCurrentGame().getBlackPlayer().getWall(id-10);
			new WallMove(q.getCurrentGame().getMoves().size(), 1, q.getCurrentGame().getBlackPlayer(), q.getBoard().getTile(col+row*9), q.getCurrentGame(), dirc, w);
			q.getCurrentGame().getCurrentPosition().removeBlackWallsInStock(q.getCurrentGame().getBlackPlayer().getWall(id-10));
			q.getCurrentGame().getCurrentPosition().addBlackWallsOnBoard(q.getCurrentGame().getBlackPlayer().getWall(id-10));
		}
		
		//TODO
		//add wall into current position's walls in board, maybe use wall id in array to map 
		
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

	public void SaveGame(Quoridor q, String FileName) throws IOException {
	
		File file=new File(FileName);
		
		file.setWritable(true);
		file.createNewFile();
		PrintWriter writer;
		try {
			writer = new PrintWriter(file, "UTF-8");
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
				writer.print("W: " + ColumnW+rowW);
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
					writer.print("," + WallColumnLetterWhite + pos.getMove().getTargetTile().getRow() + pos.getMove().getWallDirection().toString().charAt(0));

				//	writer.print();
				}
				writer.println("");
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

				writer.print("B: " + ColumnB+rowB);
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
				
					writer.print("," + WallColumnLetterBlack + pos1.getMove().getTargetTile().getRow() + pos1.getMove().getWallDirection().toString().charAt(0));
				}
				//writer.println()
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

				writer.print("B: " + ColumnB+ rowB);
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

					writer.print("," + WallColumnLetterBlack + pos1.getMove().getTargetTile().getRow() + pos1.getMove().getWallDirection().toString().charAt(0));
				}
				writer.println("");
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

				writer.print("W: " + ColumnW  + rowW);
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
				writer.print("," + WallColumnLetterWhite + pos.getMove().getTargetTile().getRow() + pos.getMove().getWallDirection().toString().charAt(0));
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
	 * @throws Exception 
	 * @throws UnsupportedOperationException
	 */
	public Game initSavedGameLoad(Quoridor quoridor, String filename) throws Exception /*throws UnsupportedOperationException*/ {
		
		
		initGame(quoridor);
		Game game = quoridor.getCurrentGame();
		Board board = quoridor.getBoard();
		GamePosition gp = game.getCurrentPosition();
		//for (int i = 0; i < 10; i++) {
			
		//}
		
		//initialize scanning on file with position data
		//assume that file is well formed even if invalid
		File file = new File(filename);
		Scanner fileSC = null;
		try {
			fileSC = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.err.println("File at filename does not exist!");
			e.printStackTrace();
			return null;
		}
		
		//Call Tokenizers
		StringTokenizer s1 = new StringTokenizer(fileSC.nextLine());
		StringTokenizer s2 = new StringTokenizer(fileSC.nextLine());
		fileSC.close();
		
		//Set Players
		String playerOneString = s1.nextToken();
		s2.nextToken(); //iterate past playerstring; we can infer this info from the first line
		
		//if (board == null) //uncomment if nullpointerexception @line with board manip
		//	board = initBoard(quoridor);
		
		boolean isPlayerOneWhite = (playerOneString.contentEquals("W:")) ? true : false;
		Player playerOne, playerTwo;
		int playerOneWallID = 0;
		int playerOneAbsoluteWallID = 1;
		int playerTwoWallID = 0;
		int playerTwoAbsoluteWallID = 1;
		
		if (isPlayerOneWhite) {
			playerOne = game.getWhitePlayer();
			playerTwo = game.getBlackPlayer();
			playerTwoAbsoluteWallID += 10;
		} else {
			playerOne = game.getBlackPlayer();
			playerTwo = game.getWhitePlayer();
			playerOneAbsoluteWallID += 10;
		}
		
		PlayerPosition playerOnePosition = null;
		PlayerPosition playerTwoPosition = null;
		
		while (s1.hasMoreTokens() || s2.hasMoreTokens()) {
			
			if (s1.hasMoreTokens()) {
				String move = s1.nextToken(",");
				if (move.charAt(0) == ' ' || move.charAt(0) == ',')
					move = move.substring(1);
				
				int col = move.charAt(0) - 'a' + 1;
				int row = move.charAt(1) - '0';
				Tile tile = board.getTile(getIndex(row, col));
				Direction dir = null;
				boolean isWallMove = false;
				if (move.length() == 3) {
					isWallMove = true;
					dir = (move.charAt(2) == 'h') ? Direction.Horizontal : Direction.Vertical;
				}
				
				if (!isWallMove) {
					playerOnePosition = new PlayerPosition(playerOne, tile);
				} else {
					Wall wall = Wall.getWithId(playerOneAbsoluteWallID);
					wall.setMove(new WallMove(game.numberOfMoves(), 1, playerOne, tile, game, dir, 
							wall));
					if (!addOrMoveWallsOnBoard(gp, wall, isPlayerOneWhite))
						throw new Exception("Unable to move wall from stock to board for player " 
								+ "one");
					
					playerOneWallID++;
					playerOneAbsoluteWallID++;
				}
			}
			
			if (s2.hasMoreTokens()) {
				String move = s2.nextToken(",");
				if (move.charAt(0) == ' ' || move.charAt(0) == ',')
					move = move.substring(1);
				
				int col = move.charAt(0) - 'a' + 1;
				int row = move.charAt(1) - '0';
				Tile tile = board.getTile(getIndex(row, col));
				Direction dir = null;
				boolean isWallMove = false;
				if (move.length() == 3) {
					isWallMove = true;
					dir = (move.charAt(2) == 'h') ? Direction.Horizontal : Direction.Vertical;
				}
				
				if (!isWallMove) {
					playerTwoPosition = new PlayerPosition(playerTwo, tile);
				} else {
					//LOOK HERE
					Wall wall = Wall.getWithId(playerTwoAbsoluteWallID);
					wall.setMove(new WallMove(game.numberOfMoves(), 1, playerTwo, tile, game, dir, 
							wall));
					if (!addOrMoveWallsOnBoard(gp, wall, !isPlayerOneWhite))
						throw new Exception("Unable to move wall from stock to board for player " 
								+ "two");
					
					playerTwoWallID++;
					playerTwoAbsoluteWallID++;
				}
			}
			
			if (playerOnePosition == null)
				playerOnePosition = new PlayerPosition(playerOne, board.getTile(isPlayerOneWhite ? 36 : 44));
			if (playerTwoPosition == null)
				playerTwoPosition = new PlayerPosition(playerTwo, board.getTile(isPlayerOneWhite ? 44 : 36));
		}
		
		
		
		//TODO: THink about separating this process into its subroutine
		game.setCurrentPosition(gp);
		return game;
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
	
	/*
	 * Color-agnostic combined addOrMoveWallsOnBoard and removeWallsInStock for loadPosiiton feature
	 */
	private static boolean addOrMoveWallsOnBoard(GamePosition gp, Wall wall, boolean isWhite) {
		//boolean didRemove = false;
		boolean didAdd = false;
		if (isWhite) {
			//didRemove = gp.removeWhiteWallsInStock(wall);
			//if (!didRemove) {
			//	return false;
			//}
			didAdd = gp.addOrMoveWhiteWallsOnBoardAt(wall, gp.getWhiteWallsOnBoard().size());
			return didAdd;
		} else {
			//didRemove = gp.removeBlackWallsInStock(wall);
			//if (!didRemove) {
			//	return false;
			//}
			didAdd = gp.addOrMoveBlackWallsOnBoardAt(wall, gp.getBlackWallsOnBoard().size());
			return didAdd;
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
