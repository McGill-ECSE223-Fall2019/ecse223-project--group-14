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
	 * 
	 * @author louismollick
	 * 
	 * @throws UnsupportedOperationException
	 */
	public void rotateWall() throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * For Grab Wall feature 
	 * 
	 * @author louismollick
	 * 
	 * @throws UnsupportedOperationException
	 */
	public void grabWall() throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
}
