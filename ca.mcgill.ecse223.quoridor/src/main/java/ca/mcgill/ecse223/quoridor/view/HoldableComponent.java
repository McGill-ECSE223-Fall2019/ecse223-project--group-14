package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;

public abstract class HoldableComponent extends RectComponent{
	
	public HoldableComponent(int w, int h, Color c) {
		super(w,h,c);
	}
	
	public void generateMoves() {
		// TODO
		// To do, just call the game Controller to return the possible moves for
		// the currently held HoldableComponent and make RectComponents/any JPanel 
		// for each possible move location. We will check the collision between the held component
		// and these move Panels to set the location of the held component.
	}
}
