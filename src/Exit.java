import java.util.Random;

import processing.core.PApplet;

public class Exit {
	public float x, y, size;
	public PApplet parent;
	public float angle;

	Exit(PApplet parent) {
		this.parent = parent;
		this.size = 20;
		randomLoc(randomInteger());
	}

	// Create random location for exit
	private void randomLoc(int angle) {
		x = (float) (150 * Math.cos(angle) + 200);
		y = (float) (150 * Math.sin(angle) + 200);
	}

	private int randomInteger() {
		Random rand = new Random();
		int randomNum = rand.nextInt((360) + 1);
		return randomNum;
	}

	// Show the exit on screen
	void display() {
		parent.fill(255, 0, 0);
		parent.stroke(0);
		parent.strokeWeight(2);
		parent.rect(x - 10, y - 10, size, size);
	}
}