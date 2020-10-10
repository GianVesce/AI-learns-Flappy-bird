package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RenderingPanel extends JPanel{
	private Dimension panelSize;
	
	private GameBackground gb;
	private Base base;
	private Bird bird;
	private Pipes pipes;

	public RenderingPanel() {
	}
	
	public void initialize() {
		panelSize = new Dimension(getWidth(), getHeight());
		
		gb = new GameBackground(new Rectangle(0, 0, panelSize.width, panelSize.height),   "flappy-bird-assets-master/sprites/background-day.png");
		gb.initialize();

		int baseHeight = (int)(panelSize.height/4);
		base = new Base(new Rectangle(0, panelSize.height-baseHeight, panelSize.width, baseHeight), "flappy-bird-assets-master/sprites/base.png");
		base.initialize();

		double birdWidth = panelSize.height/13;
		double birdHeight = birdWidth/1.4;
		bird = new Bird(new Rectangle(panelSize.width/15, (int)((panelSize.height-base.bounds.height)/2 - birdHeight/2), (int)birdWidth, (int)birdHeight), "flappy-bird-assets-master/sprites/bluebird-midflap.png");
		bird.initialize();

		pipes = new Pipes(new Rectangle(panelSize.width/2, 0, panelSize.width/30, panelSize.height - baseHeight), "flappy-bird-assets-master/sprites/pipe-green.png");
		pipes.initialize();
	}
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gb.render(g);
		bird.render(g);
        base.render(g);
        pipes.render(g);
    }

    public void updateGameLoop() {
		bird.update();
		System.out.println(pipes.topPipeBounds.toString());
		repaint();
		try {
			Thread.sleep(16);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
}
