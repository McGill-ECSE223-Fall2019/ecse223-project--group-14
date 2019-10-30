package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;

public class WallPositionComponent extends RectComponent{ //maybe should extend a PositionComponent??
	private String dir; // "horizontal" or "vertical", dunno if can use Direction enum
	
	public WallPositionComponent(int w, int h, Color c, String dir) {
		super(WallComponent.wallW, WallComponent.wallH, c);
		this.dir = dir;
	}
	public String getDirection() {
		return this.dir;
	}
}
