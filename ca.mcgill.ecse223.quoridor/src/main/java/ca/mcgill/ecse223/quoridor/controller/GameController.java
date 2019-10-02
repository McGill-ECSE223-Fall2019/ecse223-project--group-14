package ca.mcgill.ecse223.quoridor.controller;

import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;

public class GameController {
	
	public Game initGame(Quoridor q)throws InvalidInputException{
		GameStatus aGameStatus = GameStatus.Initializing; MoveMode aMoveMode = null; Player aWhitePlayer = null; Player aBlackPlayer = null; 
		
		Game g = new Game(aGameStatus, aMoveMode, aWhitePlayer, aBlackPlayer, q);
		return g;
	}
	
	public Board initBoard(Quoridor q) throws InvalidInputException{
		Board board = new Board(q);
		return board;
	}
}
