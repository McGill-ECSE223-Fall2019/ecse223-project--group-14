package ca.mcgill.ecse223.quoridor.controller;

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
	 * @author dariu
	 * 
	 * @throws UnsupportedOperationException
	 */
	public Game initGame(Quoridor q)throws UnsupportedOperationException{
		
		GameStatus aGameStatus = GameStatus.Initializing; MoveMode aMoveMode = null; Player aWhitePlayer = null; Player aBlackPlayer = null; 
		
		Game g = new Game(aGameStatus, aMoveMode, aWhitePlayer, aBlackPlayer, q);
		throw new UnsupportedOperationException();
		//return g;
	}
	
	
	public Board initBoard(Quoridor q) throws UnsupportedOperationException{
		Board board = new Board(q);
		return board;
	}
	
	/**
	 * For Start New Game feature 
	 * 
	 * @author AmineMallek
	 * 
	 * @throws UnsupportedOperationException
	 */
	
	
	public void setTime (int <min>, int <sec>) {
		int min = <min>;
		int sec = <sec>;
		
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
