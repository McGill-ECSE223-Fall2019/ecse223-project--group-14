package ca.mcgill.ecse223.quoridor.controller;

import java.sql.Time;

import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;

public class GameController {
	
	/**
	 * For Start New Game feature 
	 * 
	 * @author DariusPi
	 * 
	 * @throws UnsupportedOperationException
	 */
	public Game initGame(Quoridor q)throws UnsupportedOperationException{
		
		GameStatus aGameStatus = GameStatus.Initializing; MoveMode aMoveMode = null; Player aWhitePlayer = null; Player aBlackPlayer = null; 
		
		Game g = new Game(aGameStatus, aMoveMode, aWhitePlayer, aBlackPlayer, q);
		throw new UnsupportedOperationException();
		//return g;
	}
	
	/**
	 * For Start New Game feature
	 * 
	 * @author DariusPi
	 * 
	 * @throws UnsupportedOperationException
	 */
	public void assignUsername(Quoridor q, String colour, String name)throws UnsupportedOperationException {
		
		
	}
	
	/**
	 * For Start New Game feature
	 * 
	 * @author DariusPi
	 * 
	 * @throws UnsupportedOperationException
	 */
	public void startTheClock(Game g)throws UnsupportedOperationException{
		Time time=g.getBlackPlayer().getRemainingTime();
		
	}
	
	/**
	 * For Start New Game feature
	 * 
	 * @author DariusPi
	 * 
	 * @throws UnsupportedOperationException
	 */
	public Boolean doesUserExist(Quoridor q, String name) {
		
		for (int i=0; i<q.numberOfUsers();i++) {
			if (q.getUser(i).hasWithName(name)) {
				return true;
			}
		}
		return false;
		
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
	public void rotateWall(Game g, Player currentplayer) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * For Grab Wall feature 
	 * 
	 * @author louismollick
	 * 
	 * @throws UnsupportedOperationException
	 */
	public void grabWall(Game g, Player currentplayer) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
}
