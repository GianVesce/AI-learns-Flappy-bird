package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class GameBackground extends GameObject{
	private Image sprite1;
	private String sprite1Path;
	
	public GameBackground(Rectangle rectangle, String sprite1Path) {
		super(rectangle);
		
		this.sprite1Path = sprite1Path;

	}

	@Override
	void initialize() {
		try {
			sprite1 = ImageIO.read(new File(sprite1Path));
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
		g.drawImage(sprite1 ,bounds.x, bounds.y, bounds.width, bounds.height, null);
	}
	
}
