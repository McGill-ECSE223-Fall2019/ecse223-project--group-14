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
	int flag;
	
	public QuoridorMouseListener(QuoridorPage frame, GameController gc) {
		this.frame = frame;
		this.gc = gc;
		flag=0;
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
				flag=1;
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
//			}
			
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
		if (heldComponent==null) {
			return;
		}
		else if (SwingUtilities.isLeftMouseButton(e)) {
			if (flag==1) {
				flag=0;
				return;
			}
			else if(heldComponent instanceof WallComponent && heldComponent.hasPossiblePosition(((WallComponent) heldComponent).getDirection())) {
				//heldComponent.setLocation(heldComponent.getPossibleXPostition(), heldComponent.getPossibleYPostition());
				frame.setStageMove(true); // lock in move, and prevent player from picking up anything else
				// until he presses the End Turn button. Otherwise, the player can pick the pawn/wall back up,
				// and chose another move.
//			}
			
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
			// Auto-generated method stub
		}
		
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
		Component cursor = frame.getContentPane().findComponentAt(e.getX(),e.getY());	
		if(heldComponent != null) {
			// TODO For MoveWall person: 
			// When moving your held component, snap to possible positions
			if(heldComponent instanceof WallComponent && cursor instanceof WallPositionComponent) {
				WallComponent tempWall = (WallComponent) heldComponent;
				WallPositionComponent tempPosition = (WallPositionComponent) cursor;
				if(tempWall.getDirection().equals(tempPosition.getDirection())) {
					heldComponent.setLocation(tempPosition.getLocation());
				}
			}
			else if(heldComponent instanceof PawnComponent/* && cursor instanceof PawnPositionComponent*/) {
				// TODO For anyone, once someone makes PositionComponent or equivalent
			}

		}
	}
	
	/*
	 * Method gets currently held component by player
	 * @author louismollick
	 */
	public HoldableComponent getHeldComponent() {
		return heldComponent;
	}
	
	/*
	 * Method sets currently heldComponent
	 * @author louismollick
	 */
	public void setHeldComponent(HoldableComponent hold) {
		this.heldComponent = hold;
	}
	
	/*
	 * Method returns whether the player has a wall in his hand
	 * @author louismollick
	 */
	public boolean hasHeldWall() {
		return (heldComponent != null && heldComponent instanceof WallComponent);
	}
}
