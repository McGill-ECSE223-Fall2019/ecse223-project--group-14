package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;

import ca.mcgill.ecse223.quoridor.controller.GameController;
import ca.mcgill.ecse223.quoridor.controller.PawnBehavior;
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
					boolean isSJump=false;
					String side;
					if (i==crow+1) {	//down
						if (j==ccol+1) {	//right
							side="downright";
							isSJump=true;
						}
						else if (j==ccol-1) {
							side="downleft";
							isSJump=true;
						}
						else if (j==ccol){
							side ="down";
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
							isSJump=true;
						}
						else if (j==ccol-1) {
							side="upleft";
							isSJump=true;
						}
						else if (j==ccol){
							side ="up";
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
						System.out.print("here");
					}
					else if ((j==ccol-1)&&i==crow) {
						side="left";
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
					PawnBehavior pb=new PawnBehavior(false,crow,ccol,side,"invalid");
					pb.setCurrentGame(qp.getQ().getCurrentGame());
					pb.setPlayer(curr.getPlayerToMove());
					//TODO
					if (isSJump) {
						if (side.length()>5) {
							//Diagonal Jump
							pb.initiateDiagonalJump(crow, ccol, side);
						}
						else {
							//straight jump
							pb.initiateStraightJump(crow, ccol, side);
						}
					}
					else {
						//step
						pb.initiateStep(crow, ccol, side);
					}
					pb.dropPawn(i+1, j+1);
					/*if (!pb.getIsValid()) {	//TODO bugged
						pb.cancel();
						return false;
					}*/
//					boolean valid = gc.valPawnPosition();
//					if (valid) {
//						gc.movePawn();
//					}
					this.setLocation(tiles[j][i].getX()+(TileComponent.tileW-pawnW)/2, tiles[j][i].getY()+(TileComponent.tileW-pawnW)/2);
					
					//this should be handled by state machine
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
