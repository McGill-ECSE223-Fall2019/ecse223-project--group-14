package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;

import ca.mcgill.ecse223.quoridor.controller.GameController;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;

public class PawnComponent extends HoldableComponent{
	public static final int pawnW=25;
	
	public PawnComponent(Color color) {
		super(pawnW,pawnW,color);
	}
	
	public boolean movePawn(TileComponent[][] tiles, QuoridorPage qp) {
		GamePosition curr=qp.getQ().getCurrentGame().getCurrentPosition();
		int crow, ccol,orow,ocol;
		if (super.getColor().equals(Color.WHITE)) {
			crow=curr.getWhitePosition().getTile().getRow()-1;
			ccol=curr.getWhitePosition().getTile().getColumn()-1;
			orow=curr.getBlackPosition().getTile().getRow()-1;
			ocol=curr.getBlackPosition().getTile().getColumn()-1;
		}
		else {
			orow=curr.getWhitePosition().getTile().getRow()-1;
			ocol=curr.getWhitePosition().getTile().getColumn()-1;
			crow=curr.getBlackPosition().getTile().getRow()-1;
			ccol=curr.getBlackPosition().getTile().getColumn()-1;
		}
		System.out.print(crow+""+ccol);
		System.out.print(orow+""+ocol);
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if (tiles[j][i].getBounds().contains(this.getBounds())) {
					if (orow==i&&ocol==j) {
						return false;
					}
					boolean isSJump;
					String side;
					if (i==crow+1) {	//down
						if (j==ccol+1) {	//right
							side="downright";
						}
						else if (j==ccol-1) {
							side="downleft";
						}
						else if (j==ccol){
							side ="down";
							isSJump=false;
						}
						else {
							return false;
						}
					}
					else if((i==crow+2)&&j==ccol) {
						side="down";
						isSJump=true;
					}
					
					else if (i==crow-1) {	//up
						if (j==ccol+1) {	//right
							side="upright";
						}
						else if (j==ccol-1) {
							side="upleft";
						}
						else if (j==ccol){
							side ="up";
							isSJump=false;
						}
						else {
							return false;
						}
					}
					else if ((i==crow-2)&&j==ccol) {
						side="up";
						isSJump=true;
					}
					
					else if ((j==ccol+1)&&i==crow) {	//right
						side="right";
						isSJump=false;
						System.out.print("here");
					}
					else if ((j==ccol-1)&&i==crow) {
						side="left";
						isSJump=false;
					}
					else if ((j==ccol+2)&&i==crow) {	//right
						side="right";
						isSJump=true;
					}
					else if ((j==ccol-2)&&i==crow) {
						side="left";
						isSJump=true;
					}
					else {
						return false;
					}
					GameController gc = new GameController();
//					boolean valid = gc.valPawnPosition();
//					if (valid) {
//						gc.movePawn();
//					}
					this.setLocation(tiles[j][i].getX()+(TileComponent.tileW-pawnW)/2, tiles[j][i].getY()+(TileComponent.tileW-pawnW)/2);
					if (super.getColor().equals(Color.WHITE)) {
						curr.setWhitePosition(new PlayerPosition(curr.getPlayerToMove(), qp.getQ().getBoard().getTile(i*9+j)));
					}
					else {
						curr.setBlackPosition(new PlayerPosition(curr.getPlayerToMove(), qp.getQ().getBoard().getTile(i*9+j)));
					}
					return true;
				}
			}
		}
		return false;
	}
}
