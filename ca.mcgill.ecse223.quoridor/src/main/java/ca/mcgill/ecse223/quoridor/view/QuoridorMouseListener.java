package ca.mcgill.ecse223.quoridor.view;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class QuoridorMouseListener implements MouseListener, MouseMotionListener{
	
	private JFrame frame;
	private HoldableComponent heldComponent;
	
	private int offsetX;
	private int offsetY;
	
	public QuoridorMouseListener(JFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (heldComponent == null) {
			Component c = frame.getContentPane().findComponentAt(e.getX(), e.getY());
			if (c instanceof HoldableComponent) {
				heldComponent = (HoldableComponent) c;
				
				this.offsetX = e.getX() - heldComponent.getX();
	            this.offsetY = e.getY() - heldComponent.getY();
	            
				frame.repaint();
			}
		} else {
			heldComponent = null;
	
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(heldComponent != null) {
			heldComponent.setLocation(e.getX()-this.offsetX, e.getY()-this.offsetY);
			frame.repaint();
		}
	}
}
