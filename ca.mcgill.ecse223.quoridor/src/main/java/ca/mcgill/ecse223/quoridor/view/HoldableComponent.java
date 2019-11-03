package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import ca.mcgill.ecse223.quoridor.controller.GameController;
import ca.mcgill.ecse223.quoridor.model.GamePosition;


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
	private Point [][] points2;
	private int x1;
	private int y1;
	
	public HoldableComponent(int w, int h, Color c) {
		super(w,h,c);
		this.holdable = true;
		
		x1=-1;
		y1=-1;
		
		this.points=new Point[8][9];	//for vertical placement
		for (int i=0;i<8;i++) {
			for (int j=0;j<9;j++) {
				this.points[i][j]= new Point(192+i*50,232+j*50);
			}
		}
		this.points2=new Point[9][9];
		for (int i=0;i<9;i++) {
			for (int j=0;j<9;j++) {
				this.points2[i][j]= new Point(172+i*50,252+j*50);
			}
		}
	}
	
	public boolean isHoldable() {
		return this.holdable;
	}
	
	/**
	 * Method returns if a wall is dropped onto an acceptable point based on its position and direction and if so sets the posX and posY positions
	 * 
	 * @param String dir
	 * @author DariusPi
	 */
	public boolean hasPossiblePosition(String dir) {
		boolean first=true;
		if (dir.compareTo("vertical")==0) {
			for (int i=0;i<8;i++) {
				for (int j=0;j<9;j++) {
					if (this.getBounds().contains(points[i][j])) {
						if (first) {
							x1=i;
							y1=j;
							first=false;
						}
						else {
							GameController gc= new GameController();
							Boolean valid=gc.valWallPosition(x1,y1, "vertical");
							if (valid) {
								gc.dropWall(x1,y1, "vertical",((WallComponent)this).wallId);
							}
							return valid;
						}
					}
				}
			}
		}
		else {
			for (int i=0;i<9;i++) {
				for (int j=0;j<8;j++) {
					if (this.getBounds().contains(points2[i][j])) {
						if (first) {
							x1=i;
							y1=j;
							first=false;
						}
						else {
							GameController gc= new GameController();
							Boolean valid=gc.valWallPosition(x1,y1, "horizontal");
							if (valid) {
								gc.dropWall(x1,y1, "horizontal",((WallComponent)this).wallId);
							}
							return valid;
						}
					}
				}
			}
		}
		return false;
	}
}
