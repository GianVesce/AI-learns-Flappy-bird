package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RenderingPanel extends JPanel implements Runnable {
	private Dimension panelSize;
	
	private GameBackground gb;
	
	public RenderingPanel() {
	}
	
	public void initialize() {
		panelSize = new Dimension(getWidth(), getHeight());
		
		gb = new GameBackground(new Rectangle(0, 0, panelSize.width, panelSize.height),   "flappy-bird-assets-master/sprites/background-day.png");
		gb.initialize();
	}
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gb.render(g);
    }
    
    //The thread that handle graphics
	@Override
	public void run() {
		while(true) {
			repaint();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
