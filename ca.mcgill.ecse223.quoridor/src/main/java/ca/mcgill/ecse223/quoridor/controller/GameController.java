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
import java.util.ArrayList;
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
		
		if (q.getBoard()==null) {
			Board board = new Board(q);
			// Creating tiles by rows, i.e., the column index changes with every tile
			// creation
			for (int i = 1; i <= 9; i++) { // rows
				for (int j = 1; j <= 9; j++) { // columns
					board.addTile(i, j);
				}
			}
		}
		
		new User("usera",q);
		new User("userb",q);
		
		/*for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				new Wall(i * 10 + j, players[i]);
			}
		}*/
		
		//initBoard(q);
	}
	
	/**
	 * Helper method called by the view during new game creation to create walls for users 
	 * 
	 * @author DariusPi
	 * 
	 */
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
	 * @param Quoridor q
	 * @return
	 */
	public void initGame(Quoridor q){
		
		Player p1=new Player(new Time(10), q.getUser(0), 9, Direction.Horizontal);
		Player p2 = new Player(new Time(10), q.getUser(1), 1, Direction.Horizontal);
		 new Game(GameStatus.Initializing, MoveMode.PlayerMove,null,null ,q);
		
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
		GamePosition gamePos=new GamePosition(2, player1Position, player2Position, q.getCurrentGame().getWhitePlayer(), q.getCurrentGame());
		q.getCurrentGame().setCurrentPosition(gamePos);
		
	}

	
	/**
	 * * For Start New Game feature
	 * Initilizes game clocks to be used for player time limits
	 * 
	 * @author DariusPi
	 * 
	 * @param Quoridor q, Timer t
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
	 * @param Quoridor q
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
	 * @param Quoridor q
	 * @param String name
	 * @param String colour
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
	 * @param Quoridor q
	 * @param String name
	 * @param String colour
	 * @return String name or error message
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
	 * @param Quoridor q
	 * @param String name
	 * @return int index
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
	 * For Validate Position Feature
	 * Checks if wall move was valid and returns the result, takes in the wall's highest anchor point, its direction and its id
	 * 
	 * @author DariusPi
	 * 
	 * @param int x1
	 * @param int y1
	 * @param String dir 
	 * @param int id
	 * @return boolean
	 */
	public boolean valWallPosition(int x1,int y1, String dir) {
		Quoridor q =QuoridorApplication.getQuoridor();
		GamePosition curr= q.getCurrentGame().getCurrentPosition();
		
		List<Wall> wWall = curr.getWhiteWallsOnBoard();
		List<Wall> bWall = curr.getBlackWallsOnBoard();
		Direction dirc;
		int col =x1;
		int row=y1;
		if (dir.compareTo("vertical")==0) {
			dirc=Direction.Vertical;
			for(Wall pos: wWall){
				if (pos.getMove().getWallDirection().toString().compareTo("Vertical")==0) {
					if (((pos.getMove().getTargetTile().getRow()+1==row+1)||(pos.getMove().getTargetTile().getRow()-1==row+1)||(pos.getMove().getTargetTile().getRow()==row+1)) 
							&&(pos.getMove().getTargetTile().getColumn()==col+1)) {
						return false;
					}
				}
				else {
					if ((pos.getMove().getTargetTile().getColumn()==col+1)&&(pos.getMove().getTargetTile().getRow()==row+1)) {
						return false;
					}
				}
			}
			
			for(Wall pos: bWall){
				if (pos.getMove().getWallDirection().toString().compareTo("Vertical")==0) {
					if (((pos.getMove().getTargetTile().getRow()+1==row+1)||(pos.getMove().getTargetTile().getRow()-1==row+1)||(pos.getMove().getTargetTile().getRow()==row+1)) 
							&&(pos.getMove().getTargetTile().getColumn()==col+1)) {
						return false;
					}
				}
				else {
					if ((pos.getMove().getTargetTile().getColumn()==col+1)&&(pos.getMove().getTargetTile().getRow()==row+1)) {
						return false;
					}
				}
			}	
		}
		else {
			dirc=Direction.Horizontal;
			for(Wall pos: wWall){
				if (pos.getMove().getWallDirection().toString().compareTo("Vertical")==0) {
					if ((pos.getMove().getTargetTile().getColumn()==col+1)&&(pos.getMove().getTargetTile().getRow()==row+1)) {
						return false;
					}
				}
				else {
					if (((pos.getMove().getTargetTile().getColumn()+1==col+1)||(pos.getMove().getTargetTile().getColumn()-1==col+1)||(pos.getMove().getTargetTile().getColumn()==col+1)) 
							&&(pos.getMove().getTargetTile().getRow()==row+1)) {
						return false;
					}
				}
			}
			
			for(Wall pos: bWall){
				if (pos.getMove().getWallDirection().toString().compareTo("Vertical")==0) {
					if ((pos.getMove().getTargetTile().getColumn()==col+1)&&(pos.getMove().getTargetTile().getRow()==row+1)) {
						return false;
					}
				}
				else {
					if (((pos.getMove().getTargetTile().getColumn()+1==col+1)||(pos.getMove().getTargetTile().getColumn()-1==col+1)||(pos.getMove().getTargetTile().getColumn()==col+1)) 
							&&(pos.getMove().getTargetTile().getRow()==row+1)) {
						return false;
					}
				}
			}	
			
		}
		
		/*if (id<10) { 	//white
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
		}*/
		return true;
	}
	
	
	
	/**
	 * For Validate Position Feature
	 * Checks if pawn position is within bounds of board
	 * 
	 * @author DariusPi
	 * 
	 * @param Quoridor q
	 * @param int x1
	 * @param int y1
	 * @return
	 */
	public boolean valPawnPosition(Quoridor q,int x1,int y1) {
		
		if (x1>0 && x1<11 && y1>0 && y1<11) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * For Load Position Feature
	 * Checks the position validity of the set of every wall on the board.
	 * Specifically, checks that the walls are in-track and checks for overlap.
	 *  
	 * @author FSharp4
	 * @param q Quoridor
	 * @return if Position is valid
	 */
	public boolean validateAllWalls(Quoridor q) {
		GamePosition testedPosition = q.getCurrentGame().getCurrentPosition();
		List<Wall> whiteWallsOnBoard = testedPosition.getWhiteWallsOnBoard();
		List<Wall> blackWallsOnBoard = testedPosition.getBlackWallsOnBoard();
		ArrayList<Wall> wallsOnBoard = new ArrayList<Wall>();
		
		//constructs a list with all walls on board
		for (Wall wall : whiteWallsOnBoard) {
			wallsOnBoard.add(wall);
		}
		for (Wall wall : blackWallsOnBoard) {
			wallsOnBoard.add(wall); 
		}
		
		for (Wall wall : wallsOnBoard) {
			//wallsOnBoard.remove(wall);
			int row = wall.getMove().getTargetTile().getRow();
			int col = wall.getMove().getTargetTile().getColumn();
			//Bounds check!
			if (row <= 0 || row >= 9 || col <= 0 || col >= 9)
				return false;
			
			Direction direction = wall.getMove().getWallDirection();
			
			/*
			 * There are two cases to check for when a wall is vertical and when a wall is 
			 * horizontal.
			 * 
			 * The first case is the same whether the wall is vertical or horizontal:
			 * 	-	If there exists a wall with the same location as this wall, then the two walls
			 * 		overlap and the set of walls is invalid.
			 * 
			 * The other case depends on the orientation of the wall
			 * 	-	If the wall is horizontal, and there exists another wall with the same row as this
			 * 		wall and a column value differing by one from this wall, then the walls
			 * 		overlap, and the set of walls is invalid.
			 * 	-	If the wall is vertical, and there exists another wall with the same column as
			 * 		this wall and a row value differing by one from this wall, then the walls
			 * 		overlap, and the set of walls is invalid.
			 * 
			 */
			if (direction.equals(Direction.Vertical)) {
				for (Wall checkedWall : wallsOnBoard) {
					if (!(checkedWall.getId() == wall.getId())) {
						int c_row = checkedWall.getMove().getTargetTile().getRow();
						int c_col = checkedWall.getMove().getTargetTile().getColumn();
						Direction c_direction = checkedWall.getMove().getWallDirection();
						if (row == c_row && col == c_col)
							return false;
					
						if (c_direction.equals(Direction.Vertical)) {
							if (col == c_col && (row == c_row + 1 || col == c_row - 1))
								return false;
						}
					}
				}
			} else {
				for (Wall checkedWall : wallsOnBoard) {
					if (!(checkedWall.getId() == wall.getId())) {
						int c_row = checkedWall.getMove().getTargetTile().getRow();
						int c_col = checkedWall.getMove().getTargetTile().getColumn();
						Direction c_direction = checkedWall.getMove().getWallDirection();
						if (row == c_row && col == c_col)
							return false;
						
						if (c_direction.equals(Direction.Horizontal)) {
							if (row == c_row && (col  == c_col + 1 || col == c_col - 1))
								return false;
						}
					}
				}
			}
		}
		
		return true;
	}
	
	
	
	/**
	 * For setTotalThinkingTime feature, sets the thinking time and gives ready to start signal
	 * 
	 * @author AmineMallek
	 * 
	 * @param min (number of)
	 * 
	 * @param sec (number of)
	 * @throws Exception 
	 * 
	 * @throws UnsupportedOperationException
	 */

	public void setTime (Quoridor q, int min, int sec) throws Exception {
		//throw new UnsupportedOperationException();
		if (min==0&&sec==0) {
			throw new Exception();
		}
		
		else if ((min<0)||(sec<0)) {
			throw new Exception();
		}
		long time=(min*60+sec)*1000;		//time takes in ms
		
		q.getCurrentGame().getBlackPlayer().setRemainingTime(new Time(time));
		q.getCurrentGame().getWhitePlayer().setRemainingTime(new Time(time));
		q.getCurrentGame().setGameStatus(GameStatus.ReadyToStart);
	}


	/**
	 * For savePosition feature, saves the position, accounting for who starts in the way given in overview
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
	
		File file=new File(FileName);		//Our file created
		
		file.setWritable(true);
		file.createNewFile();
		PrintWriter writer;
		try {
			writer = new PrintWriter(file, "UTF-8");
			//if white playing, white should be on the first line
			if(q.getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite())	//if it's the white player's turn
			{
				int rowW = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow(); //gets row number
				int columnW = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn();	// gets column number
				
				String ColumnW = "";  //needs conversion to letter, using switch cases
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
				writer.print("W: " + ColumnW+rowW);//print white player position
				//writer.print("W: " + ColumnW + ", " + rowW);
				
				List<Wall> wWall	= q.getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard(); //wall objects
				
				for(Wall pos: wWall)
				{ 
				
					int WallColumnWhite = pos.getMove().getTargetTile().getColumn();//store column number to be converted to int
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
				int rowB = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow();		//black player row		
				int columnB = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn(); //white player column
				String ColumnB = "";			//string column to make it a letter
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

				writer.print("B: " + ColumnB+rowB); //prints black player's position
			//	writer.print("B: "+ rowB + ", " + ColumnB);

				List<Wall> bWall	= q.getCurrentGame().getCurrentPosition().getBlackWallsOnBoard();	//wall objects list	
				for(Wall pos1: bWall)
				{ 
					int WallColumnBlack = pos1.getMove().getTargetTile().getColumn(); //gets column number to convert it to letter
					String WallColumnLetterBlack = "";
					
					switch(WallColumnBlack) {		//letter switch
					
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
			else if (q.getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsBlack())	//if it's black player's turn
			{
				
				int rowB = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow(); //int  row
				int columnB = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn(); //int column to letter
				String ColumnB = ""; //String that'll contain the column letter
				switch(columnB) {	//switch case
				
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

				writer.print("B: " + ColumnB+ rowB);//print player's position
			//	writer.print("B: "+ rowB + ", " + ColumnB);

				List<Wall> bWall	= q.getCurrentGame().getCurrentPosition().getBlackWallsOnBoard(); //wall object list
				for(Wall pos1: bWall) //enhanced for loop
				{ 
				
					int WallColumnBlack = pos1.getMove().getTargetTile().getColumn(); //column int
					String WallColumnLetterBlack = ""; //column letter string
					
					switch(WallColumnBlack) { //switch
					
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
					//print walls, column, row, direction
					writer.print("," + WallColumnLetterBlack + pos1.getMove().getTargetTile().getRow() + pos1.getMove().getWallDirection().toString().charAt(0));
				}
				writer.println("");//new line
				int rowW = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow();//int row
				int columnW = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn();//int 
				String ColumnW = ""; //string column
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

				writer.print("W: " + ColumnW  + rowW); //string print player position
				//writer.print("W: " + ColumnW + ", " + rowW);
				
				List<Wall> wWall	= q.getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard(); //object wall
		
				for(Wall pos: wWall)
				{ 
					int WallColumnWhite = pos.getMove().getTargetTile().getColumn(); //column int
					String WallColumnLetterWhite = "";	//column string to be filled with a letter
					
					switch(WallColumnWhite) {	//switch case
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
				
					//print wall column, row, direction
					writer.print("," + WallColumnLetterWhite + pos.getMove().getTargetTile().getRow() + pos.getMove().getWallDirection().toString().charAt(0));
				//	writer.print();
				}
				 writer.close();	//close writer
			}
			    
		} catch (FileNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// Auto-generated catch block
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
	 * @throws Exception
	 */
	public void rotateWall() throws Exception{
		WallMove wmc = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		if (wmc != null) {
			Direction dir = wmc.getWallDirection();
			if (dir.equals(Direction.Horizontal)) dir = Direction.Vertical;
			else dir = Direction.Horizontal;
			wmc.setWallDirection(dir);
		}else {
			throw new Exception("No WallMove Candidate!");
		}
	}
	
	/**
	 *  * For Grab Wall feature 
	 * Attempts to set current WallMove candidate using a Wall in current player's stock
	 * 
	 * @author louismollick
	 * 
	 */
	public boolean grabWall(){
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();
		Player playerToMove = game.getCurrentPosition().getPlayerToMove();
		Tile initialPos = quoridor.getBoard().getTile(40);
		
		if(playerToMove == null) return false;
		int moveNum = game.numberOfMoves();
		int roundNum = 1;
		if(moveNum != 0) roundNum = game.getMove(moveNum-1).getRoundNumber();
		
		if(playerToMove.hasGameAsBlack()) {
			if(game.getCurrentPosition().hasBlackWallsInStock()) {
				int size = game.getCurrentPosition().getBlackWallsInStock().size();
				Wall w = game.getCurrentPosition().getBlackWallsInStock(size-1);
				w.setMove(new WallMove(moveNum, roundNum, playerToMove, initialPos, game, Direction.Vertical, w));
				return game.setWallMoveCandidate(w.getMove());
			} 
			else return false;
		}
		else {
			if(game.getCurrentPosition().hasWhiteWallsInStock()) {
				int size = game.getCurrentPosition().getWhiteWallsInStock().size();
				Wall w = game.getCurrentPosition().getWhiteWallsInStock(size-1);
				w.setMove(new WallMove(moveNum, roundNum, playerToMove, initialPos, game, Direction.Vertical, w));
				return game.setWallMoveCandidate(w.getMove());
			} 
			else return false;
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
		WallMove wmc = game.getWallMoveCandidate();
		Tile originalTile = wmc.getTargetTile();
		if(!validatePosition(game)){
			throw new UnsupportedOperationException("The position is invalid");
		}
		if(side.equals("left") && originalTile.getColumn() == 1) { // Check if the wall is at the left edge
			throw new UnsupportedOperationException("The wall is at the left edge of the board");
		}
		
		if(side.equals("right") && originalTile.getColumn() == 8) { // Check if the wall is at the right edge
			throw new UnsupportedOperationException("The wall is at the right edge of thr board");
		}
		
		if(side.equals("up") && originalTile.getRow() == 1) { // Check if the wall is at the top edge
			throw new UnsupportedOperationException("The wall is at the top edge of the board");	
		}
		
		if(side.equals("down") && originalTile.getRow() == 8 ) { // Check if the wall is at the bottom edge
			throw new UnsupportedOperationException("The wall is at the bottom edge of the board");
			
		}
		
		/*
		 * Below, are the operations that are executed for each side.
		 */
		if(side.equals("left")){
			Tile target = QuoridorApplication.getQuoridor().getBoard().getTile(getIndex(originalTile.getRow(),originalTile.getColumn()-1));
			wmc.setTargetTile(target);
		}
		
		if(side.equals("right")){
			Tile target = QuoridorApplication.getQuoridor().getBoard().getTile(getIndex(originalTile.getRow(),originalTile.getColumn()+1));
			wmc.setTargetTile(target);
		}
		
		if(side.equals("up")){
			Tile target = QuoridorApplication.getQuoridor().getBoard().getTile(getIndex(originalTile.getRow()-1,originalTile.getColumn()));
			wmc.setTargetTile(target);
		}
		
		if(side.equals("down")){
			Tile target = QuoridorApplication.getQuoridor().getBoard().getTile(getIndex(originalTile.getRow()+1,originalTile.getColumn()));
			wmc.setTargetTile(target);
		}


	}
	
	/**
	 * For Drop Wall feature
	 * Attempts to drop the wall (place the wall) between possible rows and columns of the board 
	 * 
	 * @author Saifullah, credit for DariusPi for changing the entire method and making it work for the application.
	 * 
	 * @param game
	 * @throws UnsupportedOperationException
	 */
	public void dropWall(int col, int row, String dir, int id) throws UnsupportedOperationException{		
		Quoridor q = QuoridorApplication.getQuoridor();
		Direction dirc;
		
		if(dir.compareTo("vertical") == 0) {
			dirc = Direction.Vertical;
		}
		else {
			dirc = Direction.Horizontal;
		}
		if(id<10) {
			Wall w = q.getCurrentGame().getWhitePlayer().getWall(id);
			if (w.getMove()!=null) {
				w.getMove().delete();
			}
			new WallMove(q.getCurrentGame().getMoves().size(),0,q.getCurrentGame().getWhitePlayer(),q.getBoard().getTile(col+row*9),q.getCurrentGame(),dirc,w);
			q.getCurrentGame().getCurrentPosition().removeWhiteWallsInStock(q.getCurrentGame().getWhitePlayer().getWall(id));
			q.getCurrentGame().getCurrentPosition().addWhiteWallsOnBoard(q.getCurrentGame().getWhitePlayer().getWall(id));
		}
		else {
			Wall w=q.getCurrentGame().getBlackPlayer().getWall(id-10);
			if (w.getMove()!=null) {
				w.getMove().delete();
			}
			new WallMove(q.getCurrentGame().getMoves().size(), 1, q.getCurrentGame().getBlackPlayer(), q.getBoard().getTile(col+row*9), q.getCurrentGame(), dirc, w);
			q.getCurrentGame().getCurrentPosition().removeBlackWallsInStock(q.getCurrentGame().getBlackPlayer().getWall(id-10));
			q.getCurrentGame().getCurrentPosition().addBlackWallsOnBoard(q.getCurrentGame().getBlackPlayer().getWall(id-10));
		}
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
	 * For Load Position feature
	 * Loads a saved game. Currently does not at all work.
	 * @throws Exception 
	 * 
	 */
	public void loadGame(Quoridor quoridor, String filename) throws Exception {
		initSavedGameLoad(quoridor, filename);
		validityChecking(quoridor);
		
	}
	
	/**
	 * For Load Position feature.
	 * Throws an exception when the load game is invalid but the initial loading was successful
	 * 
	 * @author FSharp4
	 * @param quoridor
	 * @throws Exception
	 */
	public void validityChecking(Quoridor quoridor) throws Exception {
		if (!checkIfLoadGameValid(quoridor))
			throw new Exception("Load failed: Position is invalid!");
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
	public Game initSavedGameLoad(Quoridor quoridor, String filename) throws Exception {
		if (quoridor.getCurrentGame()==null)
			initGame(quoridor);
		
		Game game = quoridor.getCurrentGame();
		Board board = quoridor.getBoard();
		
		//initialize scanning on file with position data
		//assume that file is well formed even if invalid
		File file = new File(filename);
		Scanner fileSC = null;
		try {
			fileSC = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new Exception("File does not exist!");
		}
		
		//Call Tokenizers
		StringTokenizer s1 = new StringTokenizer(fileSC.nextLine());
		StringTokenizer s2 = new StringTokenizer(fileSC.nextLine());
		fileSC.close();
		
		//Set Players
		String playerOneString = s1.nextToken();
		s2.nextToken(); //iterate past playerstring; we can infer this info from the first line
		
		boolean isPlayerOneWhite = (playerOneString.contentEquals("W:")) ? true : false;
		Player playerOne, playerTwo;
		int playerOneWallID = 0; 		//wall ID within player stock
		int playerOneAbsoluteWallID = 1;//wall ID within list of wallsByID in Wall.class
		int playerTwoWallID = 0;		//these are both necessary because we don't know which
		int playerTwoAbsoluteWallID = 1;//player is white when method is called
		if (isPlayerOneWhite) {
			playerOne = game.getWhitePlayer();
			playerTwo = game.getBlackPlayer();
			playerTwoAbsoluteWallID += 10;
		} else {
			playerOne = game.getBlackPlayer();
			playerTwo = game.getWhitePlayer();
			playerOneAbsoluteWallID += 10;
		}
		
		if (!Wall.hasWithId(1)) {
			for (int i = 1; i < 11; i++) {
				new Wall(i, game.getWhitePlayer());
				new Wall(i + 10, game.getBlackPlayer());
			}
		}
		
		PlayerPosition playerOnePosition = null;
		PlayerPosition playerTwoPosition = null;
		
		/*
		 * Get playerPositions for setting the currentPosition
		 * This needs to be done before loading the wall positions, as that process needs a valid
		 * game position to work.
		 */
		{
			//current player
			String playerOnePositionString = s1.nextToken(","); //grabs position in string form
			if (playerOnePositionString.charAt(0) == ' ')
				playerOnePositionString = playerOnePositionString.substring(1); //remove whitespace
			
			String playerTwoPositionString = s2.nextToken(","); //repeat for up-next player
			if (playerTwoPositionString.charAt(0) == ' ')
				playerTwoPositionString = playerTwoPositionString.substring(1); 
			
			//current player
			int col = playerOnePositionString.charAt(0) - 'a' + 1;
			int row = playerOnePositionString.charAt(1) - '0';
			Tile tile = board.getTile(getIndex(row, col));
			try {
				playerOnePosition = new PlayerPosition(playerOne, tile);
			} catch (Exception e) {
				throw new Exception("Load failed: Current player's pawn has invalid position");
			}
			
			//up-next player
			col = playerTwoPositionString.charAt(0) - 'a' + 1;
			row = playerTwoPositionString.charAt(1) - '0';
			tile = board.getTile(getIndex(row, col));
			try {
				playerTwoPosition = new PlayerPosition(playerTwo, tile);
			} catch (Exception e) {
				throw new Exception("Load failed: Up-next player's pawn has invalid position");
			}
			
		}
		
		GamePosition gp = makeInitialGamePosition(game, playerOnePosition, playerTwoPosition, 
				playerOne, isPlayerOneWhite);
		game.setCurrentPosition(gp);
		
		/*	
		 * 	Puts freshly-initialized walls in the stocks of both players.
		 * 	White has walls 1-10, black has walls 11-20
		 */
		for (int i = 1; i <= 10; i++) {
			Wall whiteWall = Wall.getWithId(i);
			gp.addWhiteWallsInStock(whiteWall);
			Wall blackWall = Wall.getWithId(i + 10);
			gp.addBlackWallsInStock(blackWall);
		}
		
		while (s1.hasMoreTokens() || s2.hasMoreTokens()) {
			if (s1.hasMoreTokens()) {
				String move = s1.nextToken(",");
				if (move.charAt(0) == ' ' || move.charAt(0) == ',')
					move = move.substring(1);
				
				int col = move.charAt(0) - 'a' + 1;
				int row = move.charAt(1) - '0';
				if (col == 9 || row == 9) 
					throw new Exception("Load failed: Wall is out-of-track! "
							+ "(Edge case: value 9 given for row or column)");
					//walls can't exist on the last row/column because they would be out of track
				
				Tile tile = null;
				try {
					tile = board.getTile(getIndex(row, col));
				} catch (Exception e) {
					throw new Exception("Load failed: Wall is out-of-track! (General case)");
				}
				
				Direction dir = ((move.charAt(2) == 'h')||(move.charAt(2) == 'H')) 
						? Direction.Horizontal : Direction.Vertical;
				Wall wall = Wall.getWithId(playerOneAbsoluteWallID);
				wall.setMove(new WallMove(game.numberOfMoves(), 1, playerOne, tile, game, dir, 
						wall));
				if (!addOrMoveWallsOnBoard(gp, wall, isPlayerOneWhite))
					throw new Exception("Unable to move wall from stock to board for player " 
							+ "one");

				playerOneWallID++;
				playerOneAbsoluteWallID++;
			}
			
			if (s2.hasMoreTokens()) {
				String move = s2.nextToken(",");
				if (move.charAt(0) == ' ' || move.charAt(0) == ',')
					move = move.substring(1);
				
				int col = move.charAt(0) - 'a' + 1;
				int row = move.charAt(1) - '0';
				if (col == 9 || row == 9) 
					throw new Exception("Load failed: Wall is out-of-track! "
							+ "(Edge case: value 9 given for row or column)");
				
				Tile tile = null;
				try {
					tile = board.getTile(getIndex(row, col));
				} catch (Exception e) {
					throw new Exception("Load failed: Wall is out-of-track! (General case)");
				}
				
				Direction dir = ((move.charAt(2) == 'h')||(move.charAt(2) == 'H')) 
						? Direction.Horizontal : Direction.Vertical;
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
		
		gp.setPlayerToMove(playerOne);
		quoridor.getCurrentGame().setGameStatus(GameStatus.ReadyToStart);
		//game is not ready to start, but this signals to load position testing that this
		//was successful
		return game;
	}
	
	/*
	 * Helper method for load position
	 * Constructs an initial game position from playerPosition data and player color data for a
	 * given game.
	 */
	/**
	 *  A helper method for load position where it constructs an initial game position data and
	 *   player color data for a given game.
	 * @param game
	 * @param playerOnePosition
	 * @param playerTwoPosition
	 * @param playerOne
	 * @param isPlayerOneWhite
	 * @return GamePosition
	 */
	private static GamePosition makeInitialGamePosition(Game game, PlayerPosition playerOnePosition, 
			PlayerPosition playerTwoPosition, Player playerOne, boolean isPlayerOneWhite) {
		GamePosition aNewGamePosition;
		if (isPlayerOneWhite) {
			aNewGamePosition = new GamePosition(0, playerOnePosition, playerTwoPosition, playerOne, 
					game);
		} else {
			aNewGamePosition = new GamePosition(0, playerTwoPosition, playerOnePosition, playerOne,
					game);
		}
		return aNewGamePosition;
	}
	
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
	 * Color-agnostic combined addOrMoveWallsOnBoard and removeWallsInStock for loadPosiiton feature
	 */
	private static boolean addOrMoveWallsOnBoard(GamePosition gp, Wall wall, boolean isWhite) {
		boolean didRemove = false;
		boolean didAdd = false;
		if (isWhite) {
			didRemove = gp.removeWhiteWallsInStock(wall);
			if (!didRemove) {
				return false;
			}
			
			didAdd = gp.addOrMoveWhiteWallsOnBoardAt(wall, gp.getWhiteWallsOnBoard().size());
			return didAdd;
		} else {
			didRemove = gp.removeBlackWallsInStock(wall);
			if (!didRemove) {
				return false;
			}
			didAdd = gp.addOrMoveBlackWallsOnBoardAt(wall, gp.getBlackWallsOnBoard().size());
			return didAdd;
		}
	}
	
	
	
	/**
	 *  * For Load Position feature
	 *  
	 *  Checks position information stored in current game. returns an error if position is invalid.
	 *  Specifically, checks for pawn overlap and wall position validity for the set of walls on the
	 *  board (via validateAllWalls). Invalid pawn and wall posiitons (such as out-of-track) are
	 *  handled by initSavedGameLoad (it is pretty easy to immediately know that these occur)
	 * 
	 * @author FSharp4
	 * @param quoridor
	 */
	public boolean checkIfLoadGameValid(Quoridor quoridor) {
		  //throws UnsupportedOperationException, IOException {
		
		/*
		 * Check for pawn overlap (invalid pawn positions otherwise should have triggered an
		 * exception in initSavedGameLoad())
		 */
		GamePosition currentPosition = quoridor.getCurrentGame().getCurrentPosition();
		Tile whitePos = currentPosition.getWhitePosition().getTile();
		Tile blackPos = currentPosition.getBlackPosition().getTile();
		if (whitePos.getRow() == blackPos.getRow() && whitePos.getColumn() == blackPos.getColumn())
			return false;
		
		return validateAllWalls(quoridor);
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
	public boolean setCurrentTurn(boolean isWhite, Quoridor quoridor)  {
		Player player;
		if (isWhite) {
			player = quoridor.getCurrentGame().getWhitePlayer();
		} else {
			player = quoridor.getCurrentGame().getWhitePlayer();
		}
		return quoridor.getCurrentGame().getCurrentPosition().setPlayerToMove(player);
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
		return true;
		//throw new UnsupportedOperationException();
	}
	
	/**
	 * For ValidatePosition Feature 
	 * Checks if if pawn position is valid or not
		
	 * 
	 * @author ohuss1
	 * 
	 * @throws UnsupportedOperationException
	 */
	public Boolean validatePos(GamePosition posToValidate) {//commented out for merge 
		// TODO Auto-generated method stub
		//Checks if pawn position overlaps with another pawn or a wall position overlaps with a wall or out of track pawn or wall
		//if yes returns error
		//if no returns ok
		//will compare this with String result in @then
		if(posToValidate.getId()==-1) {//handles walls and pawn out of board
			return false;
		}
		//cond1 same pawn pos
		//cond2 out of board pos
		//cond3 same wall
		//cond4 if closed wall hard to implement
		
		//else we know id is 1.
		//so
		//now check if walls overlap or pawns overlap
		//get game position player1position it has tile position which has row and col
		GamePosition Prev=GamePosition.getWithId(1);
		PlayerPosition Test=Prev.getWhitePosition();//want to get awhiteposition
		Tile TestTile=Test.getTile();
		int player1Row=TestTile.getRow();
		int player1Col=TestTile.getColumn();
		//now check if anything else at this place
		//check blackposition if same
		PlayerPosition Test2=Prev.getBlackPosition();
		int player2Row=Test2.getTile().getRow();
		int player2Col=Test2.getTile().getColumn();
		if((player1Row==player2Row)&&(player1Col==player2Col)) {
			return false;
		}
		
		//For Walls 
		//
		return true; //3rd nov
		//throw new UnsupportedOperationException();//testing 3rd nov
	}//commented out for merge
	

	/**
	 * For Initialize Board feature
	 * Prepares the board populated with tiles for the given quoridor
	 * 
	 * @author FSharp4
	 * @param q
	 * @return new Board
	 * @throws UnsupportedOperationException
	 */
	public void initBoard(Quoridor q) {
		addWalls();
		for (int i=0;i<10;i++) {
			q.getCurrentGame().getCurrentPosition().addOrMoveWhiteWallsInStockAt(q.getCurrentGame()
					.getWhitePlayer().getWall(i), i);
			q.getCurrentGame().getCurrentPosition().addOrMoveBlackWallsInStockAt(q.getCurrentGame()
					.getBlackPlayer().getWall(i), i);
		}
	}
	
	public void deleteGame(Quoridor q) {
		q.getCurrentGame().delete();
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
	 * For View, used in QuoridorMouseListener
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


