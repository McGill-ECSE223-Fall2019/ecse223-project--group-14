package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class WallComponent extends HoldableComponent{
	public static final int wallH=70;
	public static final int wallW=12;
	private String dir;
	
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
	public void paintComponent(Graphics g) {
		if(this.dir.contentEquals("horizontal")) {
			((Graphics2D) g).rotate(Math.toRadians(-90));
			((Graphics2D) g).translate(-wallW,0);
        }
        super.paintComponent(g);
    }
}