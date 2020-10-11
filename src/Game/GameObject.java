package Game;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	public Rectangle bounds;
	
	public GameObject(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	//Initialize the game object i.e. assets
	abstract void initialize();
	
	//Handles the update of the game object i.e. position
	abstract void update();
	
	//Handles the rendering of the game object
	abstract void render(Graphics g);

	boolean collides(GameObject g) {
		return bounds.intersects(g.bounds);
	}
}
