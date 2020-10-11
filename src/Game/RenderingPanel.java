package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RenderingPanel extends JPanel implements KeyListener {
	private Dimension panelSize;
	
	private GameBackground gb;
	private Base base;
	private Bird bird;

	private ArrayList<Pipes> pipes;

	private boolean isKeyPressed;

	private int gapSize;
	private int numberOfPipes;

	private int score;

	//Gap between every pipes
	private int gap;

	public RenderingPanel() {
		isKeyPressed = false;
		pipes = new ArrayList<Pipes>();
		numberOfPipes = 4;
	}
	
	public void initialize() {
		panelSize = new Dimension(getWidth(), getHeight());
		int baseHeight = (int)(panelSize.height/4);
		gap = panelSize.width/3;

		gb = new GameBackground(new Rectangle(0, 0, panelSize.width, panelSize.height-baseHeight),   "flappy-bird-assets-master/sprites/background-day.png");
		gb.initialize();

		gapSize = (int)(gb.bounds.height/3.5);

		base = new Base(new Rectangle(0, panelSize.height-baseHeight, panelSize.width, baseHeight), "flappy-bird-assets-master/sprites/base.png");
		base.initialize();

		double birdWidth = panelSize.height/13;
		double birdHeight = birdWidth/1.4;
		bird = new Bird(new Rectangle(panelSize.width/15, (int)((panelSize.height-base.bounds.height)/2 - birdHeight/2), (int)birdWidth, (int)birdHeight), "flappy-bird-assets-master/sprites/bluebird-midflap.png");
		bird.initialize();

		for(int i=0; i<numberOfPipes; i++) {
			Pipes p = new Pipes(new Rectangle(panelSize.width + (gap*i), 0, panelSize.width/15, panelSize.height - baseHeight), "flappy-bird-assets-master/sprites/pipe-green.png", this);
			p.initialize();
			p.setxSpeed(5.5);
			p.setGapSize(gapSize);
			p.generateRandomGapHeight();
			pipes.add(p);
		}

	}
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gb.render(g);
		bird.render(g);
        for(Pipes p : pipes) {
        	p.render(g);
		}

		base.render(g);
    }

    public void updateGameLoop() {
		bird.update();
		for(Pipes p : pipes) {
			p.update();
		}

		//Check if the bird is dead
		if(base.collides(bird) || (bird.bounds.y < 0) || getMostLeftPipe().collides(bird)) {
			reset();
		}

		//System.out.println(pipes.topPipeBounds.toString());
		//System.out.println(pipes.isOutOfLeftMap());
		//System.out.println(pipes.passed(bird));

		repaint();
		try {
			Thread.sleep(16);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if((e.getKeyChar() == ' ') && (!isKeyPressed)) {
			bird.flap();
			isKeyPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar() == ' ')
			isKeyPressed = false;
		if(e.getKeyChar() == 'r')
			reset();
	}

	private Pipes getMostRightPipe() {
		Pipes lowest = pipes.get(0);

		for (Pipes p: pipes) {
			if(p.bounds.x > lowest.bounds.x)
				lowest = p;
		}

		return lowest;
	}

	private Pipes getMostLeftPipe() {
		Pipes lowest = pipes.get(0);

		for (Pipes p: pipes) {
			if(p.bounds.x < lowest.bounds.x)
				lowest = p;
		}

		return lowest;
	}

	public Dimension getPanelSize() {
		return panelSize;
	}

	public void pipeExited(Pipes p) {
		if(pipes.contains(p)) {
			Pipes mostRight = getMostRightPipe();

			p.reset(mostRight.bounds.x + gap);
		}
	}

	public void reset() {
		//Reset the bird's and pipe's positon
		bird.reset((int)((panelSize.height-base.bounds.height)/2 - bird.bounds.height/2));

		for(int i=0; i<pipes.size(); i++) {
			pipes.get(i).reset(panelSize.width + (gap*i));
		}
	}
}
