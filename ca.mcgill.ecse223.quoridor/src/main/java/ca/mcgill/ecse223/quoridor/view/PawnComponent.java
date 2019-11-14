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
		int i=0,j=0;
		boolean texists=false;
		outer: for(i=0;i<9;i++) {
			for(j=0;j<9;j++) {
				if (tiles[j][i].getBounds().contains(this.getBounds())) {
					if (orow==i&&ocol==j) {
						return false;
					}
					texists=true;
					break outer;
				}
			}
		}
		if (!texists) {
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
			if (!((i==orow+1)&&(j==ocol))) {
				return false;
			}
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
			if (!((i==orow-1)&&(j==ocol))) {
				return false;
			}
			isSJump=true;
		}
		
		else if ((j==ccol+1)&&i==crow) {	//right
			side="right";
		}
		else if ((j==ccol-1)&&i==crow) {
			side="left";
		}
		else if ((j==ccol+2)&&i==crow) {	//right
			side="right";
			if (!((i==orow)&&(j==ocol+1))) {
				return false;
			}
			isSJump=true;
		}
		else if ((j==ccol-2)&&i==crow) {
			side="left";
			if (!((i==orow)&&(j==ocol-1))) {
				return false;
			}
			isSJump=true;
		}
		else {
			return false; 
		}
		GameController gc = new GameController();
		
		PawnBehavior pb=new PawnBehavior(false,side,"invalid");
		pb.setCurrentGame(qp.getQ().getCurrentGame());
		pb.setPlayer(curr.getPlayerToMove());
		//TODO
		
		if (side.length()>5) {
			System.out.print("diagonal");
			System.out.print(side); 
			pb.initiateDiagonalJump(side);
		}
		else {
			//straight jump
			System.out.print("straight or step");
			pb.initiateSorJ(side);
		}
		pb.dropPawn();
		if (pb.getStatus().compareTo("success")==0) {
			this.setLocation(tiles[j][i].getX()+(TileComponent.tileW-pawnW)/2, tiles[j][i].getY()+(TileComponent.tileW-pawnW)/2);
			
			return true;
		}
		else {
			return false;
		}
		/*if (pb.getIsValid()) {
			pb.dropPawn();
		}
		else {	 
			pb.cancel();
			return false;
		}*/
		
	}
}
