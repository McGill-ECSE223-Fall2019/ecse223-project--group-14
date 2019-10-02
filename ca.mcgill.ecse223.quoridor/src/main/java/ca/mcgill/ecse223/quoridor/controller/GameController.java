package ca.mcgill.ecse223.quoridor.controller;

import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Quoridor;

public class GameController {
	
	public Quoridor initQ(){
		Quoridor q = new Quoridor();
		return q;
	}
	
	public Board initBoard(Quoridor q) throws InvalidInputException{
		Board board = new Board(q);
		return board;
	}
}
