package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Base extends GameObject{
	private Image sprite;
	private String spritePath;

	public Base(Rectangle bounds, String spritePath) {
		super(bounds);
		this.spritePath = spritePath;
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

	}

	@Override
	void render(Graphics g) {
		g.drawImage(sprite, bounds.x, bounds.y, bounds.width, bounds.height, null);
	}
}
