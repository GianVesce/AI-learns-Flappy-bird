package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Bird extends GameObject{
	private Image sprite;
	private String spritePath;

	private double yVelocity;
	private final double gravityForce = 0.25;
	private double flapForce = 4;

	public Bird(Rectangle size, String spritePath) {
		super(size);

		this.spritePath = spritePath;
		yVelocity = 0;
	}

	@Override
	void initialize() {
		try {
			sprite = ImageIO.read(new File(spritePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	void update() {
		//Add the gravity to the bird's y velocity
		yVelocity += gravityForce;

		//Makes the bird go down based on his y velocity
		bounds.y+= yVelocity;
	}

	@Override
	void render(Graphics g) {
		g.drawImage(sprite, bounds.x, bounds.y, bounds.width, bounds.height, null);
	}

	//The method that makes the bird jump
	void flap() {
		yVelocity -= flapForce;
	}
}
