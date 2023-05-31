import stanford.karel.SuperKarel;
public class BlankKarel extends SuperKarel {
	enum Direction {
		NONE,
		TURN_LEFT,
		TURN_RIGHT,
		TURN_AROUND
	}

	private int numSteps;
	private int width;
	private int height;

	private int calculateBlocksUntilReachWall() {
		int counter = 1;
		while (frontIsClear()) {
			counter += 1;
			move();
			this.numSteps += 1;
		}
		return counter;
	}
	public void calculateHeightAndWidth() {
		this.width = calculateBlocksUntilReachWall();
		turnLeft();
		this.height = calculateBlocksUntilReachWall();
	}
	private void function2(boolean flip, int blockSize, int maximum, int minimum) {
		if (!flip) {
			moveRobot(this.height / 2, false, Direction.TURN_AROUND, Direction.TURN_RIGHT);
		} else {
			moveRobot(this.width / 2, false, Direction.TURN_LEFT, Direction.TURN_LEFT);
		}
		putBeeper();
		moveRobot(maximum / 2 - 1 - blockSize - 1, true, Direction.NONE, Direction.TURN_LEFT);
		functionizeTest(blockSize, minimum, false, 1, false, true);
		moveRobot(blockSize + 1, true, Direction.NONE, Direction.NONE);
		moveRobot(blockSize, true,
				flip ? Direction.TURN_RIGHT : Direction.TURN_LEFT,
				flip ? Direction.TURN_LEFT : Direction.TURN_RIGHT);

		moveRobot(1, true, Direction.NONE, Direction.NONE);
		moveRobot(2 * blockSize, true,
				flip ? Direction.TURN_LEFT : Direction.TURN_RIGHT,
				flip ? Direction.TURN_LEFT : Direction.TURN_RIGHT);
		moveRobot(1, true, Direction.NONE, Direction.NONE);
		moveRobot(blockSize, true,
				flip ? Direction.TURN_LEFT : Direction.TURN_RIGHT,
				flip ? Direction.TURN_LEFT : Direction.TURN_RIGHT);
		moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);
	}
	private void function3(int blockSize, boolean extraMove, int maximum, int minimum) {
		if (width <= height)
			moveRobot(width / 2, false, Direction.TURN_LEFT, Direction.TURN_LEFT);
		else
			moveRobot(height / 2, false, Direction.TURN_AROUND, Direction.TURN_RIGHT);
		putBeeper();
		moveRobot(maximum / 2 - blockSize - (extraMove ? 0 : 1), true, Direction.NONE, Direction.TURN_LEFT);
		functionizeTest(blockSize, minimum, false, (extraMove ? 0 : 1), extraMove, false);

		moveRobot(blockSize + (extraMove ? 0 : 1), true, Direction.NONE, Direction.TURN_LEFT);
		moveRobot(blockSize, false, Direction.NONE, Direction.TURN_AROUND);
		putBeeper();
		moveRobot(2 * blockSize, true, Direction.NONE, Direction.TURN_AROUND);
		moveRobot(blockSize, false, Direction.NONE, Direction.TURN_RIGHT);
		moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);

		if (extraMove) {
			moveRobot(1, true, Direction.TURN_LEFT, Direction.TURN_LEFT);
			moveRobot(maximum - 1, true, Direction.NONE, Direction.NONE);
		}
	}
	private void functionizeTest(int blockSize, int minimum, boolean isDoubleLines, int shiftAmount, boolean extraMove, boolean toFixIt) {
		// blockSize, minimum, false, 1, false
		moveRobot(blockSize + (isDoubleLines ? 0 : 1), true, Direction.NONE, Direction.TURN_RIGHT);
		moveRobot(blockSize + shiftAmount, true, Direction.NONE, Direction.TURN_LEFT);
		moveRobot(minimum / 2 - blockSize - 1, true, Direction.NONE, Direction.NONE);

		if ((isDoubleLines && !extraMove) || toFixIt) {
			moveRobot(1, true, Direction.TURN_RIGHT, Direction.TURN_RIGHT);
			moveRobot(minimum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_LEFT);
		} else {
			moveRobot(minimum / 2 - blockSize - 1, false, Direction.TURN_AROUND, Direction.TURN_LEFT);
		}
		moveRobot(blockSize + shiftAmount, true, Direction.NONE, Direction.TURN_RIGHT);
		moveRobot(2 *  blockSize + 1 + shiftAmount, true, Direction.NONE, Direction.TURN_RIGHT);
		moveRobot(blockSize + shiftAmount, true, Direction.NONE, Direction.TURN_LEFT);
		moveRobot(minimum / 2 - blockSize - 1, true, Direction.NONE, Direction.NONE);

		if ((isDoubleLines && !extraMove) || toFixIt) {
			moveRobot(1, true, Direction.TURN_RIGHT, Direction.TURN_RIGHT);
			moveRobot(minimum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_LEFT);
		} else {
			moveRobot(minimum / 2 - blockSize - 1, false, Direction.TURN_AROUND, Direction.TURN_LEFT);
		}
		moveRobot(blockSize + shiftAmount, true, Direction.NONE, Direction.TURN_RIGHT);
		moveRobot(blockSize + (isDoubleLines ? 1 : 0) + shiftAmount, true, Direction.NONE, Direction.TURN_RIGHT);
	}
	private void function1(boolean extraMove, int minimum, int maximum, int blockSize) {
		if (this.width <= this.height)
			moveRobot(this.width / 2 - 1, false, Direction.TURN_LEFT, Direction.TURN_LEFT);
		else
			moveRobot(this.height / 2, false, Direction.TURN_AROUND, Direction.TURN_RIGHT);
		putBeeper();
		moveRobot(maximum - (maximum / 2 - blockSize) - (extraMove ? 1 : 0), true, Direction.NONE, Direction.NONE);
		moveRobot(maximum / 2 - blockSize - (extraMove ? 0 : 1), true, Direction.NONE, Direction.TURN_RIGHT);
		moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
		moveRobot(maximum / 2 - blockSize - (extraMove ? 0 : 1), true, Direction.NONE, Direction.TURN_LEFT);
		functionizeTest(blockSize, minimum, true, 0, extraMove, false);
		moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
		moveRobot(blockSize - 1, true, Direction.NONE, Direction.NONE);

		if (!extraMove) {
			moveRobot(1, true, Direction.TURN_RIGHT, Direction.TURN_RIGHT);
			moveRobot(2 * blockSize - 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
		} else {
			moveRobot(blockSize, false, Direction.TURN_AROUND, Direction.NONE);
			moveRobot(blockSize - 1, true, Direction.NONE, Direction.TURN_AROUND);
			moveRobot(blockSize, false, Direction.NONE, Direction.TURN_RIGHT);
		}
		moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);
	}
	private void moveRobot(int amount, boolean beep, Direction initialTurn, Direction endTurn) {
		int counter = 0;
		switch (initialTurn) {
			case TURN_AROUND: turnAround(); break;
			case TURN_LEFT: turnLeft(); break;
			case TURN_RIGHT: turnRight(); break;
			default: break;
		}
		while (counter != amount) {
			move();
			this.numSteps += 1;
			if (beep) putBeeper();
			counter += 1;
		}
		switch (endTurn) {
			case TURN_AROUND: turnAround(); break;
			case TURN_LEFT: turnLeft(); break;
			case TURN_RIGHT: turnRight(); break;
			default: break;
		}
	}
	// todo: fix the the turning (it works but i guess i can do better)
	private void turnMoveAndComeBack(int amount, Direction startDirection, Direction endDirection) {
		moveRobot(amount, true, Direction.NONE, startDirection);
		moveRobot(amount, false, Direction.NONE, endDirection);
	}
	public void run() {
		calculateHeightAndWidth();

		int minimum = Math.min(this.width, this.height);
		int maximum = Math.max(this.width, this.height);

		int blockSize = minimum / 2 - 2;

		if (this.width % 2 == 1 && this.height % 2 == 1) {
			function3(blockSize, false, maximum, minimum);
		} else if (this.width % 2 == 0 && this.height % 2 == 0) {
			function1(false, minimum, maximum, blockSize);
		} else if (this.width % 2 == 0 && this.height % 2 == 1 && this.width < this.height) {
			function3(blockSize, true, maximum, minimum);
		} else if (this.width % 2 == 0 && this.height % 2 == 1 && this.width > this.height) {
			function2(false, blockSize, maximum, minimum);
		} else if (this.width % 2 == 1 && this.height % 2 == 0 && this.width < this.height) {
			function2(true, blockSize, maximum, minimum);
		} else if (this.width % 2 == 1 && this.height % 2 == 0 && this.width > this.height) {
			function1(true, minimum, maximum, blockSize);
		}
		numSteps = 0;
	}
}