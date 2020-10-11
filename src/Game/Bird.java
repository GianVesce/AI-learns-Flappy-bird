package Game;

import NeuralNetwork.NeuralNetwork;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Bird extends GameObject{
	private Image sprite;

	private double yVelocity;
	private final double gravityForce = 0.25;
	private double currentForce;
	private double flapForce = 6.5;

	private NeuralNetwork brain;

	public Bird(Rectangle size, Image sprite) {
		super(size);

		this.sprite = sprite;
		yVelocity = 0;
		currentForce = 0;
	}

	@Override
	void initialize() {
		//Generate brain
		this.brain = new NeuralNetwork(2, 6, 1);
	}


	@Override
	void update() {
		//Add the gravity to the bird's y velocity
		//Apply the gravity only when the bird has stopped flapping
		if(currentForce >= 0)
			yVelocity = -currentForce;
		else
			yVelocity += gravityForce;

		currentForce -= gravityForce;
		//Makes the bird go down based on his y velocity
		bounds.y += yVelocity;
	}

	@Override
	void render(Graphics g) {
		g.drawImage(sprite, bounds.x, bounds.y, bounds.width, bounds.height, null);
	}

	//The method that makes the bird jump
	void flap() {
		currentForce = flapForce;
	}

	void reset(int y) {
		yVelocity = 0;
		currentForce = 0;
		bounds.y = y;
	}

	void decide(double[] distanceFromGap) {
		if(brain.feedforward(distanceFromGap)[0] >= 0.5)
			flap();
	}

	public NeuralNetwork getNeuralNetwork() {
		return brain;
	}

	public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
		this.brain = neuralNetwork;
	}
}
