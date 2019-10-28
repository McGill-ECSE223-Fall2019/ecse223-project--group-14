package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;

public class WallComponent extends HoldableComponent{
	public static final int wallH=70;
	public static final int wallW=12;
	
	public WallComponent(Color c) {
		super(wallW, wallH, c);
	}
}