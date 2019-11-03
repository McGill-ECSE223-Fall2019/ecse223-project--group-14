package ca.mcgill.ecse223.quoridor.view;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import ca.mcgill.ecse223.quoridor.controller.GameController;


public class QuoridorMouseListener implements MouseListener, MouseMotionListener{
	
	private QuoridorPage frame;
	private GameController gc;
	private HoldableComponent heldComponent;
	
	private int pickedUpX;
	private int pickedUpY;
	
	private int offsetX;
	private int offsetY;
	
	public QuoridorMouseListener(QuoridorPage frame, GameController gc) {
		this.frame = frame;
		this.gc = gc;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Component cursor = frame.getContentPane().findComponentAt(e.getX(), e.getY());
		if (heldComponent == null) {
			if (cursor instanceof HoldableComponent) {
				HoldableComponent temp = (HoldableComponent) cursor;
				if(temp.isHoldable() && temp.getColor().equals(gc.getCurrentPlayerColor()) && !frame.getStageMove() ) {
					if (temp instanceof WallComponent) {
						if ((cursor.getY()>150)&&(cursor.getY()<660)) {	//wall already placed
							return;
						}
					}
					try {
						gc.grabWall();
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
					heldComponent = temp;
					
					this.pickedUpX = heldComponent.getX(); // Remember where it was last placed
					this.pickedUpY = heldComponent.getY();
					
					this.offsetX = e.getX() - heldComponent.getX(); // Pick up component at cursor location 
		            this.offsetY = e.getY() - heldComponent.getY();
		            
					frame.repaint();
				}
			}
		} else {
			if(heldComponent instanceof WallComponent && SwingUtilities.isRightMouseButton(e)) {
				//System.out.println("right");
				String d = ((WallComponent) heldComponent).rotate();
				// Ugly way of keeping the offset after rotation
				if (d.contentEquals("horizontal")) {
					this.offsetX = WallComponent.wallH/2;
					this.offsetY = WallComponent.wallW/2;
				}else {
					this.offsetX = WallComponent.wallW/2;
					this.offsetY = WallComponent.wallH/2;
				}
				heldComponent.setLocation(e.getX()-this.offsetX, e.getY()-this.offsetY);
			}
			
			// If heldComponent has a position it's above, then drop it there
			else if(heldComponent instanceof WallComponent && heldComponent.hasPossiblePosition(((WallComponent) heldComponent).getDirection())) {
				//heldComponent.setLocation(heldComponent.getPossibleXPostition(), heldComponent.getPossibleYPostition());
				frame.setStageMove(true); // lock in move, and prevent player from picking up anything else
				// until he presses the End Turn button. Otherwise, the player can pick the pawn/wall back up,
				// and chose another move.
				this.pickedUpX = 0;
				this.pickedUpY = 0;
				heldComponent=null;
			} 
			else { // If no position is clicked (and not rotating), just put the component back
				heldComponent.setLocation(this.pickedUpX, this.pickedUpY); // put back to position when pickedUp
				this.pickedUpX = 0;
				this.pickedUpY = 0;
				if (heldComponent instanceof WallComponent) ((WallComponent) heldComponent).setDirection("vertical");
				heldComponent = null;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// Do the same thing as mouseMoved
		if(heldComponent != null) {
			heldComponent.setLocation(e.getX()-this.offsetX, e.getY()-this.offsetY);
			frame.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(heldComponent != null) {
			heldComponent.setLocation(e.getX()-this.offsetX, e.getY()-this.offsetY);
			frame.repaint();
		}
	}
}
