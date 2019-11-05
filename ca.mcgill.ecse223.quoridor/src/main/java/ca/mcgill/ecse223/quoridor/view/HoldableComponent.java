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
	
	public HoldableComponent(int w, int h, Color c) {
		super(w,h,c);
		this.holdable = true;
	}
	
	public boolean isHoldable() {
		return this.holdable;
	}
}
