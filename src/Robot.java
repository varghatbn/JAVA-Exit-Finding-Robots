import processing.core.PApplet;

public class Robot {

	// Location of the robot and other attributes
	public float x, y, size = 10, angle = 0, dirx, diry, clock;
	public boolean foundCircle = false, foundExit = false, receivedExit = false, foundAngle = false, didMeet = false;
	public Robot otherRobot;
	public PApplet parent;
	public Exit exit; // Exit on circle

	Robot(PApplet parent, float x, float y, float spinDirection) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.clock = spinDirection;
		closestPointCircle();
	}

	// Tell it who other robot is
	public void otherRobot(Robot partner) {
		this.otherRobot = partner;
	}

	// Draw robots
	public void display() {
		parent.fill(120, 50, 240);
		parent.noStroke();
		parent.ellipse(x, y, size, size);
	}

	// Move robots
	public void move(Exit exit) {
		parent.fill(120, 50, 240);
		if ((x - 200) * (x - 200) + (y - 200) * (y - 200) < (150 * 150) && !foundCircle || receivedExit && !foundExit) {

			y += diry;
			x += dirx;
			checkExit(exit);

			angle = (float) Math.atan2(y - 200, x - 200);
		} else {
			if (!foundExit) {
				foundCircle = true;
				if (otherRobot.foundCircle && !foundAngle) {
					communicateAngle();
				}
				x = (float) (150 * Math.cos(angle) + 200);
				y = (float) (150 * Math.sin(angle) + 200);
				angle += clock;
				checkExit(exit);
			}
		}
	}

	// Finding the closest point in the circle to the robot
	private void closestPointCircle() {
		if (x >= 200 && y >= 200) {
			dirx = 1;
			diry = 1;
		}
		if (x < 200 && y > 200) {
			dirx = -1;
			diry = 1;
		}
		if (x > 200 && y < 200) {
			dirx = 1;
			diry = -1;
		}
		if (x < 200 && y < 200) {
			dirx = -1;
			diry = -1;
		}
	}

	// To listen to other robot about exits found
	public void listenForExit(Exit exit) {
		float bigger = Math.max((exit.x - x), (exit.y - y));
		dirx = (exit.x - x) / bigger;
		diry = (exit.y - y) / bigger;
		this.exit = exit;
		receivedExit = true;
	}

	// Communicate with other robot about the shared angle
	private void communicateAngle() {
		float angle = (float) Math.toDegrees(Math.atan2(otherRobot.y - y, otherRobot.x - x));

		if (angle < 0) {
			angle += 360;
		}

		if (angle <= 180) {
			clock = 0.01f;
			otherRobot.clock = -0.01f;
		} else {
			clock = -0.01f;
			otherRobot.clock = 0.01f;
		}

		foundAngle = true;
	}

	// Tells partner robot that exit has been located
	private void communicateExit(Exit exit) {
		otherRobot.listenForExit(exit);
	}

	// Keeps checking if reached exit
	private void checkExit(Exit exit) {
		if (x - 5 > exit.x - 10 && y - 5 > exit.y - 10 && x + 5 < exit.x + exit.size / 2
				&& y + 5 < exit.y + exit.size / 2) {
			foundExit = true;
			communicateExit(exit);
		}
		if (x < 0 || x > parent.width || y > parent.height || y < 0) {
			foundExit = true;
		}
	}
}