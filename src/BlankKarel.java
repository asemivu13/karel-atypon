import stanford.karel.SuperKarel;
public class BlankKarel extends SuperKarel {
	enum Direction {
		NONE,
		TURN_LEFT, TURN_RIGHT,
		TURN_AROUND
	}

	private int blockSize;
	private int width;
	private int height;
	private int numSteps;

	private int calculateBlocksUntilReachWall() {
		int counter = 1;
		while (frontIsClear()) {
			counter += 1;
			move();
			numSteps += 1;
		}
		return counter;
	}
	public void calculateHeightAndWidth() {
		this.width = calculateBlocksUntilReachWall();
		turnLeft();
		this.height = calculateBlocksUntilReachWall();
	}
	private void handleMixCase(boolean leftIsRight, int minimum, int maximum) {
		System.out.println("Mix Case::");
		findStartingPoint();
		moveRobot(maximum / 2 - blockSize - 2, true, Direction.NONE, Direction.TURN_LEFT);
		drawCommonPattern(true, minimum, false);
		moveRobot(blockSize + 1, true, Direction.NONE, Direction.NONE);

		Direction left = Direction.TURN_LEFT;
		Direction right = Direction.TURN_RIGHT;
		if (leftIsRight) {
			left = Direction.TURN_RIGHT;
			right = Direction.TURN_LEFT;
		}
		moveRobot(blockSize, true, left, right);
		moveRobot(1, true, Direction.NONE, Direction.NONE);
		moveRobot(2 * blockSize, true, right, right);
		moveRobot(1, true, Direction.NONE, Direction.NONE);
		moveRobot(blockSize, true, right, right);
		moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);
	}
	private void handleOddCase(int minimum, int maximum) {
		System.out.println("Odd Case::");
		findStartingPoint();
		moveRobot(maximum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_LEFT);
		drawCommonPattern(false, minimum, false);
		moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_LEFT);
		moveRobot(blockSize, false, Direction.NONE, Direction.TURN_AROUND);
		putBeeper();
		moveRobot(2 * blockSize, true, Direction.NONE, Direction.TURN_AROUND);
		moveRobot(blockSize, false, Direction.NONE, Direction.TURN_RIGHT);
		moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);
	}
	private void findStartingPoint() {
		if (this.width <= this.height)
			moveRobot(this.width / 2, false, Direction.TURN_LEFT, Direction.TURN_LEFT);
		else
			moveRobot(this.height / 2, false, Direction.TURN_AROUND, Direction.TURN_RIGHT);
		putBeeper();
	}
	private void handleEvenCase(boolean isDoubleLine, int minimum, int maximum) {
		System.out.println("Even Case::");
		findStartingPoint();

		Direction left = Direction.TURN_LEFT;
		Direction right = Direction.TURN_RIGHT;
		if (width > height) {
			left = Direction.TURN_RIGHT;
			right = Direction.TURN_LEFT;
		}

		int distanceBetweenWallAndBlock = (maximum - 1) / 2 - blockSize;

		moveRobot(maximum - distanceBetweenWallAndBlock, true, Direction.NONE, Direction.NONE);
		moveRobot(distanceBetweenWallAndBlock - 1, true, Direction.NONE, left);
		moveRobot(1, true, Direction.NONE, left);
		moveRobot(distanceBetweenWallAndBlock - 1, true, Direction.NONE, right);
		drawCommonPattern(isDoubleLine, minimum, width <= height);
		moveRobot(blockSize + 1, true, Direction.NONE, left);
		moveRobot(blockSize + 1, true, Direction.NONE, Direction.NONE);

		if (isDoubleLine) {
			moveRobot(1, true, right, right);
			moveRobot(2 * blockSize + 1, true, Direction.NONE, right);
			moveRobot(1, true, Direction.NONE, right);
			moveRobot(blockSize, true, Direction.NONE, right);
		} else {
			moveRobot(blockSize, false, Direction.TURN_AROUND, Direction.NONE);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_AROUND);
			moveRobot(blockSize, false, Direction.NONE, right);
		}
		moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);
	}
	private void drawCommonPattern(boolean isDoubleLines, int minimum, boolean leftIsRight) {
		Direction left = Direction.TURN_LEFT;
		Direction right = Direction.TURN_RIGHT;
		int fixAmount = 0;

		if (leftIsRight) {
			left = Direction.TURN_RIGHT;
			right = Direction.TURN_LEFT;
		}
		if (minimum % 2 == 0) fixAmount = 1;

		moveRobot(blockSize + 1, true, Direction.NONE, right);
		moveRobot(blockSize + 1, true, Direction.NONE, left);
		moveRobot((minimum - fixAmount) / 2 - blockSize - 1, true, Direction.NONE, Direction.NONE);

		if (isDoubleLines) {
			moveRobot(1, true, right, right);
			moveRobot((minimum - fixAmount) / 2 - blockSize - 1, true, Direction.NONE, left);
		} else {
			moveRobot((minimum - fixAmount) / 2 - blockSize - 1, false, Direction.TURN_AROUND, left);
		}
		moveRobot(blockSize + 1, true, Direction.NONE, right);
		moveRobot(2 * blockSize + 2 + fixAmount, true, Direction.NONE, right);
		moveRobot(blockSize + 1, true, Direction.NONE, left);
		moveRobot((minimum - fixAmount) / 2 - blockSize - 1, true, Direction.NONE, Direction.NONE);

		if (isDoubleLines) {
			moveRobot(1, true, right, right);
			moveRobot((minimum - fixAmount) / 2 - blockSize - 1, true, Direction.NONE, left);
		} else {
			moveRobot((minimum - fixAmount) / 2 - blockSize - 1, false, Direction.TURN_AROUND, left);
		}
		moveRobot(blockSize + 1, true, Direction.NONE, right);
		moveRobot(blockSize + 1 + fixAmount, true, Direction.NONE, right);
	}
	private void commandSwitcher(Direction direction) {
		switch (direction) {
			case TURN_AROUND: turnAround(); break;
			case TURN_LEFT: turnLeft(); break;
			case TURN_RIGHT: turnRight(); break;
			default: break;
		}
	}
	private void moveRobot(int amount, boolean beep, Direction initialTurn, Direction endTurn) {
		int counter = 0;
		commandSwitcher(initialTurn);
		while (counter != amount) {
			move();
			numSteps += 1;
			if (beep) putBeeper();
			counter += 1;
		}
		commandSwitcher(endTurn);
	}
	private int calculateBlockSize(int minimum) {
		if (minimum % 2 == 0)
			minimum -= 1;
		return minimum / 2 - 2;
	}
	public void run() {
		calculateHeightAndWidth();

		int minimum = Math.min(this.width, this.height);
		int maximum = Math.max(this.width, this.height);
		blockSize = calculateBlockSize(minimum);
		numSteps = 0;

		if (this.width < 6 || this.height < 6) {
			System.out.println("The dimension is not valid to create the required shape");
		} else {
			if (this.width % 2 == 1 && this.height % 2 == 1)
				handleOddCase(minimum, maximum);
			else if (this.width % 2 == 0 && this.height % 2 == 0)
				handleEvenCase(true, minimum, maximum);
			else if (this.width % 2 == 0)
				if (this.width < this.height)
					handleEvenCase(false, minimum, maximum);
				else
					handleMixCase(false, minimum, maximum);
			else
				if (this.width > this.height)
					handleEvenCase(false, minimum, maximum);
				else
					handleMixCase(true, minimum, maximum);
			System.out.println("Width: " + width + ", Height: " + height + ", BlockSize: " + blockSize + ", Number of steps taken: " + numSteps);
		}
	}
}