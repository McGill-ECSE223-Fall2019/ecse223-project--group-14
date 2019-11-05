package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;

import ca.mcgill.ecse223.quoridor.controller.GameController;

public class PawnComponent extends HoldableComponent{
	public static final int pawnW=25;
	
	public PawnComponent(Color color) {
		super(pawnW,pawnW,color);
	}
	
	public boolean movePawn(TileComponent[][] tiles) {
		for(TileComponent[] row : tiles) {
			for(TileComponent tile : row) {
				if (tile.getBounds().contains(this.getBounds())) {
					GameController gc = new GameController();
//					boolean valid = gc.valPawnPosition();
//					if (valid) {
//						gc.movePawn();
//					}
					this.setLocation(tile.getX()+(TileComponent.tileW-pawnW)/2, tile.getY()+(TileComponent.tileW-pawnW)/2);
					return true;
				}
			}
		}
		return false;
	}
}
