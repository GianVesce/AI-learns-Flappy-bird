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
	
	public Bird(Rectangle size, String spritePath) {
		super(size);
		
		this.spritePath = spritePath;
	}

	@Override
	void initialize() {
		// TODO Auto-generated method stub
		try {
			sprite = ImageIO.read(new File(spritePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void render(Graphics g) {
		g.drawImage(sprite, bounds.x, bounds.y, bounds.width, bounds.height, null);
	}
}
