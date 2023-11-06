import stanford.karel.SuperKarel;
public class BlankKarel extends SuperKarel {
	enum Direction {
		NONE,
		TURN_LEFT, TURN_RIGHT,
		TURN_AROUND
	}

	private int largestSquareSize;
	private int width;
	private int height;
	private int numSteps;
	private int[] dimensions = new int[2];

	public void run() {
		initalize();
		calculateDimension();

		if (width <= 6 || height <= 6) {
			System.out.println("The dimension is not valid to create the required shape");
		} else {
			if (width % 2 == 1 && height % 2 == 1)
				handleOddCase();
			else if (width % 2 == 0 && height % 2 == 0)
				handleEvenCase();
			else if (width % 2 == 0)
				if (width < height)
					handleEvenCase();
				else
					handleMixCase(false);
			else
			if (width > height)
				handleEvenCase();
			else
				handleMixCase(true);
			System.out.println("Width: " + width +
					", Height: " + height +
					", Largest Square Size: " + largestSquareSize +
					", Number of steps taken: " + numSteps);
		}
	}
	private void initalize() {
		numSteps = 0;
		setBeepersInBag(1000);
	}
	public void calculateDimension() {
		dimensions[0] = calculateBlocksUntilReachWall();
		turnLeft();
		dimensions[1] = calculateBlocksUntilReachWall();
	}
	private int calculateBlocksUntilReachWall() {
		int counter = 1;
		while (frontIsClear()) {
			counter += 1;
			move();
			numSteps += 1;
		}
		return counter;
	}
	private int calculateLargestSquareSize(int minimum) {
		if (minimum % 2 == 0)
			minimum -= 1;
		return minimum / 2 - 2;
	}
	private void handleOddCase() {
		moveToStartingPoint();
		moveRobot(maximum / 2 - largestSquareSize - 1, true, Direction.NONE, Direction.TURN_LEFT);
		drawOuterShape(false, false);
		drawInnerShape(false, false);
	}
	private void handleEvenCase() {
		int distanceBetweenWallAndBlock = (maximum - 1) / 2 - largestSquareSize;
		Direction left = Direction.TURN_LEFT;
		Direction right = Direction.TURN_RIGHT;

		moveToStartingPoint();

		if (width > height) {
			left = Direction.TURN_RIGHT;
			right = Direction.TURN_LEFT;
		}
		boolean isDoubleLine = width % 2 == 0 && height % 2 == 0;

		moveRobot(maximum - distanceBetweenWallAndBlock, true, Direction.NONE, Direction.NONE);
		moveRobot(distanceBetweenWallAndBlock - 1, true, Direction.NONE, left);
		moveRobot(1, true, Direction.NONE, left);
		moveRobot(distanceBetweenWallAndBlock - 1, true, Direction.NONE, right);
		drawOuterShape(isDoubleLine, width <= height);
		drawInnerShape(isDoubleLine, width <= height);
	}
	private void handleMixCase(boolean leftIsRight) {
		moveToStartingPoint();
		moveRobot(maximum / 2 - largestSquareSize - 2, true, Direction.NONE, Direction.TURN_LEFT);
		drawOuterShape(true, false);
		drawInnerShape(true, leftIsRight);

	}
	private void moveToStartingPoint() {
		if (this.width <= this.height)
			moveRobot(this.width / 2, false, Direction.TURN_LEFT, Direction.TURN_LEFT);
		else
			moveRobot(this.height / 2, false, Direction.TURN_AROUND, Direction.TURN_RIGHT);
		putBeeper();
	}
	private void drawOuterShape(boolean isDoubleLines, boolean leftIsRight) {
		// this is bad it does stuff that shouldn't done
		// I should extract left right into its own method
		int fixAmount = 0;
		Direction left = Direction.TURN_LEFT;
		Direction right = Direction.TURN_RIGHT;

		if (leftIsRight) {
			left = Direction.TURN_RIGHT;
			right = Direction.TURN_LEFT;
		}
		if (minimum % 2 == 0) fixAmount = 1;

		moveRobot(largestSquareSize + 1, true, Direction.NONE, right);
		moveRobot(largestSquareSize + 1, true, Direction.NONE, left);
		moveRobot((minimum - fixAmount) / 2 - largestSquareSize - 1, true, Direction.NONE, Direction.NONE);

		if (isDoubleLines) {
			moveRobot(1, true, right, right);
			moveRobot((minimum - fixAmount) / 2 - largestSquareSize - 1, true, Direction.NONE, left);
		} else {
			moveRobot((minimum - fixAmount) / 2 - largestSquareSize - 1, false, Direction.TURN_AROUND, left);
		}
		moveRobot(largestSquareSize + 1, true, Direction.NONE, right);
		moveRobot(2 * largestSquareSize + 2 + fixAmount, true, Direction.NONE, right);
		moveRobot(largestSquareSize + 1, true, Direction.NONE, left);
		moveRobot((minimum - fixAmount) / 2 - largestSquareSize - 1, true, Direction.NONE, Direction.NONE);

		if (isDoubleLines) {
			moveRobot(1, true, right, right);
			moveRobot((minimum - fixAmount) / 2 - largestSquareSize - 1, true, Direction.NONE, left);
		} else {
			moveRobot((minimum - fixAmount) / 2 - largestSquareSize - 1, false, Direction.TURN_AROUND, left);
		}
		moveRobot(largestSquareSize + 1, true, Direction.NONE, right);
		moveRobot(largestSquareSize + 1 + fixAmount, true, Direction.NONE, right);
	}
	private void drawInnerShape(boolean isDoubleLine, boolean leftIsRight) {
		Direction left = Direction.TURN_LEFT;
		Direction right = Direction.TURN_RIGHT;
		if (leftIsRight) {
			left = Direction.TURN_RIGHT;
			right = Direction.TURN_LEFT;
		}

		moveRobot(largestSquareSize + 1, true, Direction.NONE, left);
		moveRobot(largestSquareSize, isDoubleLine, Direction.NONE, Direction.NONE);

		if (isDoubleLine) {
			moveRobot(1, true, right, right);
			moveRobot(2 * largestSquareSize + (minimum % 2 == 0 ? 1 : 0), true, Direction.NONE, right);
			moveRobot(1, true, Direction.NONE, right);
			moveRobot(largestSquareSize + (minimum % 2 == 0 ? 1 : 0), true, Direction.NONE, right);
		} else {
			putBeeper();
			moveRobot(2 * largestSquareSize + (minimum % 2 == 0 ? 1 : 0), true, Direction.TURN_AROUND, Direction.TURN_AROUND);
			moveRobot(largestSquareSize + (minimum % 2 == 0 ? 1 : 0), false, Direction.NONE, right);
		}
		moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);
	}
	private void moveRobot(int amount, boolean canBeep, Direction initialTurn, Direction endTurn) {
		int counter = 0;
		commandSwitcher(initialTurn);
		while (counter != amount) {
			move();
			numSteps += 1;
			if (canBeep)
				putBeeper();
			counter += 1;
		}
		commandSwitcher(endTurn);
	}
	private void commandSwitcher(Direction direction) {
		switch (direction) {
			case TURN_AROUND: turnAround(); break;
			case TURN_LEFT: turnLeft(); break;
			case TURN_RIGHT: turnRight(); break;
			default: break;
		}
	}
}