package Game;

import NeuralNetwork.NeuralNetwork;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Map;

public class RenderingPanel extends JPanel {
	private Dimension panelSize;
	
	private GameBackground gb;
	private Base base;

	private ArrayList<Bird> aliveBirds;

	private ArrayList<Bird> toKill;

	private ArrayList<Pipes> pipes;

	private int gapSize;
	private int numberOfPipes;
	private int numberOfBirds;

	private int generationNumber;

	//Long value => score calculated based on the time alive in the current generation
	private HashMap<Bird, Long> deadBirds;
	private long generationStartTime;
	private double mutationRate;

	//Gap between every pipes
	private int gap;

	public RenderingPanel() {
		pipes = new ArrayList<Pipes>();
		numberOfPipes = 4;

		aliveBirds = new ArrayList<Bird>();
		deadBirds = new HashMap<Bird, Long>();
		toKill = new ArrayList<Bird>();

		numberOfBirds = 500;
		mutationRate = 1.7;
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

		String[] possibleSpritePath = {
				"flappy-bird-assets-master/sprites/bluebird-midflap.png",
				"flappy-bird-assets-master/sprites/redbird-midflap.png",
				"flappy-bird-assets-master/sprites/yellowbird-midflap.png"
		};

		Image[] possibleSprites = new Image[possibleSpritePath.length];

		for (int i=0; i< possibleSpritePath.length; i++) {
			try {
				possibleSprites[i] = ImageIO.read(new File(possibleSpritePath[i]));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (int i=0; i<numberOfBirds; i++) {

			Bird b = new Bird(new Rectangle(panelSize.width/30, (int)((panelSize.height/2) - (birdHeight/2)), (int)(birdWidth), (int)(birdHeight)), possibleSprites[(int)(Math.random()*possibleSprites.length)]);
			b.initialize();

			aliveBirds.add(b);
		}

		for(int i=0; i<numberOfPipes; i++) {
			Pipes p = new Pipes(new Rectangle(panelSize.width + (gap*i), 0, panelSize.width/15, panelSize.height - baseHeight), "flappy-bird-assets-master/sprites/pipe-green.png", this);
			p.initialize();
			p.setxSpeed(5.5);
			p.setGapSize(gapSize);
			p.generateRandomGapHeight();
			pipes.add(p);
		}

		generationStartTime = System.nanoTime();
	}
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gb.render(g);
		for(Bird b : aliveBirds)
			b.render(g);
        for(Pipes p : pipes) {
        	p.render(g);
		}

		base.render(g);
    }

    public void updateGameLoop() {
		//Check if the generation should advance
		if(aliveBirds.size() == 0)
			advanceGeneration();

		//Make the birds decide to flap or not
		Pipes nextPipe = getMostLeftPipe();
		for (Bird b : aliveBirds) {
			b.decide(nextPipe.distanceFromGap(b));
		}

		//Update the position of the objects
		for(Bird b : aliveBirds)
			b.update();

		for(Pipes p : pipes) {
			p.update();
		}

		//Check which bird has to die
		for(Bird b : aliveBirds) {
			if(base.collides(b) || (b.bounds.y < 0) || nextPipe.collides(b)) {
				toKill.add(b);
			}
		}

		//Kill every bird who didn't make it
		for(Bird b : toKill) {
			aliveBirds.remove(b);
			//Assign the birds a score based on how long they survived
			deadBirds.put(b, System.nanoTime()-generationStartTime);
		}
		//Clear the kill list
		toKill.clear();

		//Check if the bird is dead
		//if(base.collides(bird) || (bird.bounds.y < 0) || getMostLeftPipe().collides(bird)) {
		//	reset();
		//}

		//System.out.println(pipes.topPipeBounds.toString());
		//System.out.println(pipes.isOutOfLeftMap());
		//System.out.println(pipes.passed(bird));

		repaint();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	void advanceGeneration() {
		//Makes the top bird reproduce and alive
		Bird topBird = getTopBird();
		aliveBirds.add(topBird);
		deadBirds.remove(topBird);

		//List of the new neural networks
		NeuralNetwork[] reproducedNetworks = topBird.getNeuralNetwork().reproduce(deadBirds.size(), mutationRate);

		//Assign every bird a new neural network except for the top a new neural network
		int i=0;
		for(Bird b : deadBirds.keySet()) {
			b.setNeuralNetwork(reproducedNetworks[i]);
			aliveBirds.add(b);
			i++;
		}

		deadBirds.clear();

		System.out.println(aliveBirds.size());

		//Reset the generation
		reset();
	}

	private Bird getTopBird() {
		Entry<Bird, Long> highest = null;

		for(Entry<Bird, Long> entry : deadBirds.entrySet()) {
			if((highest == null) || (entry.getValue() > highest.getValue()))
				highest = entry;
		}

		return highest.getKey();
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
		//Reset the birds and pipe's position
		for (Bird b : aliveBirds)
			b.reset((int)((panelSize.height-base.bounds.height)/2 - b.bounds.height/2));

		for(int i=0; i<pipes.size(); i++) {
			pipes.get(i).reset(panelSize.width + (gap*i));
		}
		generationStartTime = System.nanoTime();
	}
}
