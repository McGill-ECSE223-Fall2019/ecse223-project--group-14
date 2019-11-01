package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;


/*
 * A HoldableComponent is JPanel representing a Pawn or a Wall, which can be held and
 * placed by a player.
 * @author louismollick
 * 
 */
public abstract class HoldableComponent extends RectComponent{
	private boolean holdable;
	public static final int wallH=70;
	public static final int wallW=12;
	private Point [][] points;
	private int posX;
	private int posY;
	
	public HoldableComponent(int w, int h, Color c) {
		super(w,h,c);
		this.holdable = true;
		
		posX=-1;
		posY=-1;
		
		this.points=new Point[8][8];
		for (int i=0;i<8;i++) {
			for (int j=0;j<8;j++) {
				this.points[i][j]= new Point(199+i*50,257+j*50);
			}
		}
	}
	
	public boolean isHoldable() {
		return this.holdable;
	}
	
	public void generateMoves() {
		// TODO
		// To do, just call the game Controller to return the possible moves for
		// the currently held HoldableComponent and make PositionComponents 
		// for each possible move location. We will check the collision between the held component
		// and these move Panels to set the location of the held component.
	}
	
	
	/**
	 * Method returns if a mouse clicked on a wall
	 * 
	 * @param int ex, int ey
	 * @author DariusPi
	 */
	@Override
	public boolean contains(int ex, int ey) {
		int x=this.getX(); int y= this.getY();
		if ((ex>=x+wallW/2)&&(ex<=x+wallW/2+wallW)&&((ey>=y+wallH/2-5)&&(ey<=y+wallH/2+wallH-5))) {
			return true;
		}
		else {
			return false;
		}
    }
	
	/**
	 * Method returns if a wall is dropped onto an acceptable point and if so sets the posX and posY positions
	 * 
	 * @author DariusPi
	 */
	public boolean hasPossiblePosition() {
		for (int i=0;i<8;i++) {
			for (int j=0;j<8;j++) {
				if (this.contains(points[i][j])) {
					posX=this.getX();
					posY=this.getY();
					
					//TODO
					//call validate postion to ensure no overlapping walls
					return true;
				}
				
			}
		}
		return false;
	}
	
	/**
	 * Method returns dropped x position
	 * 
	 * @author DariusPi
	 */
	public int getPossibleXPostition() {
		return posX;
	}
	
	/**
	 * Method returns dropped y position
	 * 
	 * @author DariusPi
	 */
	public int getPossibleYPostition() {
		return posY;
	}
}
