package ca.mcgill.ecse223.quoridor.view;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Component [] bcomponents= frame.bwalls;
		Component [] wcomponents= frame.wwalls;
		Component cursor=null;
		
		for (int i=0;i<bcomponents.length;i++) {
			if (bcomponents[i].contains(e.getX(), e.getY())) {
				cursor=bcomponents[i];
				break;
			}
			if (wcomponents[i].contains(e.getX(), e.getY())) {
				cursor=wcomponents[i];
				break;
			}
		}
		//Component cursor = frame.getContentPane().findComponentAt(e.getX(), e.getY());
		//System.out.println(cursor.getClass().toString());
		if (heldComponent == null) {
			if (cursor instanceof HoldableComponent) {
				HoldableComponent temp = (HoldableComponent) cursor;
				if(temp.isHoldable() && temp.getColor().equals(gc.getCurrentPlayerColor()) && !frame.getStageMove() ) {
					gc.grabWall();
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
				System.out.println("right");
				String d = ((WallComponent) heldComponent).rotate();
				// Ugly way of keeping the offset after rotation
				if (d.contentEquals("horizontal")) {
					int temp = this.offsetX;
					this.offsetX = WallComponent.wallH-this.offsetY;
					this.offsetY = -temp+3*WallComponent.wallW;
				}else {
					int temp = this.offsetX;
					this.offsetX = -this.offsetY+3*WallComponent.wallW;
					this.offsetY = WallComponent.wallH-temp;
				}
				heldComponent.setLocation(e.getX()-this.offsetX, e.getY()-this.offsetY);
			}
			
			// TODO For DropWall person: 
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
		// Do the same thing as mouseMoved
		if(heldComponent != null) {
			heldComponent.setLocation(e.getX()-this.offsetX, e.getY()-this.offsetY);
			frame.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//Component cursor = frame.getContentPane().findComponentAt(e.getX(), e.getY());
		if(heldComponent != null) {
			// TODO For MoveWall person: 
			// While you move your heldComponent over the board, snap to possible positions
//			if(heldComponent instanceof WallComponent && cursor instanceof WallPositionComponent) {
//				WallComponent tempWall =  (WallComponent) heldComponent;
//				WallPositionComponent tempPosition = (WallPositionComponent) cursor;
//				if(tempWall.getDirection().equals(tempPosition.getDirection())) {
//					//heldComponent.setPossiblePosition(tempPosition);
//					heldComponent.setLocation(tempPosition.getLocation());
//				}
//			}
//			else if(heldComponent instanceof WallComponent/* && cursor instanceof PawnPositionComponent*/) {
//				// TODO For anyone, once someone makes PositionComponent or equivalent
//			}
			heldComponent.setLocation(e.getX()-this.offsetX, e.getY()-this.offsetY);
			frame.repaint();
		}
	}
}
