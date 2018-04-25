import java.util.ArrayList;

import processing.core.PApplet;

public class MainClass extends PApplet {

	// Change this to change number of times ran:
	private int NUM_RUNS = 30;


	// Application variables:
	private int circleSize = 300; // The size of the map
	private Robot[] robots = new Robot[2];
	private Exit exit;
	// Used to randomly place robots for case 2 and 3:
	private float timer, randX, randY, randX2, randY2, angle, radius, angle2, radius2;
	// For menu options:
	private int mode = 0, count = 0, framerate = 60;
	private long worstTime = 0l, startTime, totalTime;
	private ArrayList<Long> runTimes = new ArrayList<Long>();

	public static void main(String[] args) {
		PApplet.main("MainClass");
	}

	public void settings() {
		size(400, 400);
	}

	// Setup system
	public void setup() {
		frameRate(framerate);
		fill(204, 102, 0);
		background(0);
	}

	// A loop to keep drawing on screen
	public void draw() {
		clear();
		if (mode == 0) {
			fill(255);
			text("Note: Please mouse-click on the screen before\nselecting an option using your keyboard", 10, 20);

			text("Single Visual Simulation.\nPress: \n 1 for Two Origin Placed Robots "
					+ "\n 2 for Origin and Random Robots " + "\n 3 for Two Random Placed Robots ", 10, 70);

			text("Runtime (50 runs).\nPress: \n 4 for Two Origin Placed Robots Runtime"
					+ "\n 5 for Origin and Random Robots Runtime" + "\n 6 for Two Random Placed Robots Runtime", 10,
					160);
			
			text("Developed by Mohammed Abushawish (100857775)\nand Vargha Tebyaniyan (100870761)", 10, 250);
		}
		// For options 1, 2 and 3 simply run the app
		if (mode > 0) {
			fill(255);
			text("Press M for Main Menu", 10, 12);
			fill(255);
			ellipse(width / 2, height / 2, circleSize, circleSize);
			fill(120, 50, 240);
			exit.display();
			robots[0].move(exit);
			robots[0].display();
			robots[1].move(exit);
			robots[1].display();
		}
		// For other options, run it multiple times
		if (mode == 4) {
			fill(255);
			text("Worst evactuion time thus far: " + worstTime + " milliseconds", 10, 25);
			text("Average evactuion time thus far: " + avg() + " milliseconds", 10, 37);
			text(count + " runs remaining", 10, 50);

			if (robots[0].foundExit && robots[1].foundExit && count > 0) {
				long endTime = System.currentTimeMillis();
				totalTime = endTime - startTime;
				runTimes.add(totalTime);
				if (totalTime > worstTime) {
					worstTime = totalTime;
				}
				clear();
				robots[0] = new Robot(this, width / 2, width / 2, 0.01f);
				robots[1] = new Robot(this, width / 2, width / 2, -0.01f);
				robots[0].otherRobot(robots[1]);
				robots[1].otherRobot(robots[0]);
				exit = new Exit(this);
				count--;
				startTime = System.currentTimeMillis();
			}
		}
		if (mode == 5) {
			fill(255);
			text("Worst evactuion time thus far: " + worstTime + " milliseconds", 10, 25);
			text("Average evactuion time thus far: " + avg() + " milliseconds", 10, 37);
			text(count + " runs remaining", 10, 50);

			if (robots[0].foundExit && robots[1].foundExit && count > 0) {
				long endTime = System.currentTimeMillis();
				totalTime = endTime - startTime;
				runTimes.add(totalTime);
				if (totalTime > worstTime) {
					worstTime = totalTime;
				}
				clear();
				robots[0] = new Robot(this, width / 2, height / 2, 0.01f);
				robots[1] = new Robot(this, randX, randY, -0.01f);
				robots[0].otherRobot(robots[1]);
				robots[1].otherRobot(robots[0]);
				exit = new Exit(this);
				count--;
				startTime = System.currentTimeMillis();
			}
		}
		if (mode == 6) {
			fill(255);
			text("Worst evactuion time thus far: " + worstTime + " milliseconds", 10, 25);
			text("Average evactuion time thus far: " + avg() + " milliseconds", 10, 37);
			text(count + " runs remaining", 10, 50);

			if (robots[0].foundExit && robots[1].foundExit && count > 0) {
				long endTime = System.currentTimeMillis();
				totalTime = endTime - startTime;
				runTimes.add(totalTime);
				if (totalTime > worstTime) {
					worstTime = totalTime;
				}
				clear();
				robots[0] = new Robot(this, randX2, randY2, 0.01f);
				robots[1] = new Robot(this, randX, randY, -0.01f);
				robots[0].otherRobot(robots[1]);
				robots[1].otherRobot(robots[0]);
				exit = new Exit(this);
				count--;
				startTime = System.currentTimeMillis();
			}
		}
	}

	// To handle button clicks
	public void keyPressed() {

		radius = (float) Math.random() * 150; // between 0 and the radius of the
												// circle
		angle = (float) Math.random() * 360; // between 0 and 360 (degrees)
		randX = 200 + radius * cos((float) angle);
		randY = 200 + radius * sin((float) angle);

		radius2 = (float) Math.random() * 150; // between 0 and the radius of
												// the circle
		angle2 = (float) Math.random() * 360; // between 0 and 360 (degrees)
		randX2 = 200 + radius2 * cos((float) angle2);
		randY2 = 200 + radius2 * sin((float) angle2);

		if (keyCode == 'M') {
			mode = 0;
		}
		if (key == '1') {
			frameRate(60);
			robots[0] = new Robot(this, width / 2, width / 2, 0.01f);
			robots[1] = new Robot(this, width / 2, width / 2, -0.01f);
			mode = 1;
		}
		if (key == '2') {
			frameRate(60);
			robots[0] = new Robot(this, width / 2, height / 2, 0.01f);
			robots[1] = new Robot(this, randX, randY, -0.01f);
			mode = 2;
		}
		if (key == '3') {
			frameRate(60);
			robots[0] = new Robot(this, randX2, randY2, 0.01f);
			robots[1] = new Robot(this, randX, randY, -0.01f);
			mode = 3;
		}
		if (key == '4') {
			frameRate(1000);
			robots[0] = new Robot(this, width / 2, width / 2, 0.01f);
			robots[1] = new Robot(this, width / 2, width / 2, -0.01f);
			mode = 4;
		}
		if (key == '5') {
			frameRate(1000);
			robots[0] = new Robot(this, width / 2, height / 2, 0.01f);
			robots[1] = new Robot(this, randX, randY, -0.01f);
			mode = 5;
		}
		if (key == '6') {
			frameRate(1000);
			robots[0] = new Robot(this, randX2, randY2, 0.01f);
			robots[1] = new Robot(this, randX, randY, -0.01f);
			mode = 6;
		}
		exit = new Exit(this);
		robots[0].otherRobot(robots[1]);
		robots[1].otherRobot(robots[0]);
		count = NUM_RUNS;
		worstTime = 0;
		runTimes.clear();
		startTime = System.currentTimeMillis();
	}

	// To calculate the average run times
	private long avg() {
		if (runTimes.size() == 0)
			return 0l;
		long sum = 0l;
		long average = 0l;

		for (int i = 0; i < runTimes.size(); i++) {
			sum = 1l * sum + runTimes.get(i);
		}
		average = 1l * sum / runTimes.size();
		return average;
	}

}
