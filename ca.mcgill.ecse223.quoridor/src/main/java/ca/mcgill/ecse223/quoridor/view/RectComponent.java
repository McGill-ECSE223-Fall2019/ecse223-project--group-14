package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

public abstract class RectComponent extends JPanel{
	private static final long serialVersionUID = 1L;
	private Rectangle rect;
	private Color color;
	
    public RectComponent(int w, int h, Color c) {
		this.rect=new Rectangle(w,h);
    	this.color=c;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.draw(rect);
        g2.setColor(color);
        g2.fill(rect);
    }
}
