package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;

public class PawnComponent extends HoldableComponent{
	public static final int pawnW=25;
	
	public PawnComponent(Color color) {
		super(pawnW,pawnW,color);
	}
	
}
