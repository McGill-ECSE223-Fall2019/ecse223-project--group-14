package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class WallComponent extends HoldableComponent{
	public static final int wallH=70;
	public static final int wallW=12;
	private String dir;
	private AffineTransform transform;
	
	public WallComponent(Color c) {
		super(wallW, wallH, c);
		this.dir = "vertical";
	}
	public String rotate() {
		if (this.dir.contentEquals("vertical")) {
			this.setBounds(this.getX(), this.getY(), wallH, wallW);
			this.dir = "horizontal";
		} else {
			this.setBounds(this.getX(), this.getY(), wallW, wallH);
			this.dir = "vertical";
		}
		return this.dir;
	}
	public String getDirection() {
		return this.dir;
	}
	public void setDirection(String dir) {
		this.setBounds(this.getX(), this.getY(), wallW, wallH);
		this.dir = dir;
	}
	
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
	
	@Override
	public void paintComponent(Graphics g) {
		if(this.dir.contentEquals("horizontal")) {
			((Graphics2D) g).rotate(Math.toRadians(-90));
			((Graphics2D) g).translate(-wallW,0);
        }
        super.paintComponent(g);
    }
}