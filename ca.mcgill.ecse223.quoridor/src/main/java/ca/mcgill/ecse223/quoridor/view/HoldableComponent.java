package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;


/*
 * A HoldableComponent is JPanel representing a Pawn or a Wall, which can be held and
 * placed by a player.
 * @author louismollick
 * 
 */
public abstract class HoldableComponent extends RectComponent{
	private boolean holdable;
	
	public HoldableComponent(int w, int h, Color c) {
		super(w,h,c);
		this.holdable = true;
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
}
