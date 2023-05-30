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
			if (width <= height)
				moveRobot(width / 2, false, Direction.TURN_LEFT, Direction.TURN_LEFT);
			else
				moveRobot(height / 2, false, Direction.TURN_AROUND, Direction.TURN_RIGHT);
			putBeeper();
			moveRobot(maximum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(minimum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_AROUND);
			moveRobot(minimum / 2 - blockSize - 1, false, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(2 * blockSize + 2, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(minimum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_AROUND);
			moveRobot(minimum / 2 - blockSize - 1, false, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(1, false, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize, false, Direction.NONE, Direction.TURN_AROUND); putBeeper();
			moveRobot(2 * blockSize, true, Direction.NONE, Direction.TURN_AROUND);
			moveRobot(blockSize, false, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);
		} else if (this.width % 2 == 0 && this.height % 2 == 0 && this.width != this.height) {
			if (this.width <= this.height)
				moveRobot(this.width / 2 - 1, false, Direction.TURN_LEFT, Direction.TURN_LEFT);
			else
				moveRobot(this.height / 2, false, Direction.TURN_AROUND, Direction.TURN_RIGHT);
			putBeeper();
			moveRobot(maximum - (maximum / 2 - blockSize), true, Direction.NONE, Direction.NONE);
			moveRobot(maximum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(maximum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(2 *  blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize - 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(2 * blockSize - 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);

		} else if (this.width % 2 == 0 && this.height % 2 == 0) {
			moveRobot(width / 2 - 1, false, Direction.TURN_LEFT, Direction.TURN_LEFT);
			putBeeper();
			moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);
			moveRobot(1, true, Direction.TURN_RIGHT, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize, true, Direction.NONE, Direction.NONE);
			turnMoveAndComeBack(1, Direction.TURN_AROUND, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			for (int i = 0; i < 2; i++) {
				turnMoveAndComeBack(blockSize - 2, Direction.TURN_AROUND, Direction.NONE);
				moveRobot(1, true, Direction.NONE, Direction.NONE);
				turnMoveAndComeBack(1, Direction.TURN_AROUND, Direction.TURN_RIGHT);
				moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
				moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			}
			moveRobot(1, true, Direction.TURN_AROUND, Direction.TURN_LEFT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);

		} else if (this.width % 2 == 0 && this.height % 2 == 1 && this.width < this.height) {
			moveRobot(width / 2, false, Direction.TURN_LEFT, Direction.TURN_LEFT);
			putBeeper();
			moveRobot(maximum / 2 - blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(minimum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_AROUND);
			moveRobot(minimum / 2 - blockSize - 1, false, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(2 * blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(minimum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_AROUND);
			moveRobot(minimum / 2 - blockSize - 1, false, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize, false, Direction.NONE, Direction.TURN_AROUND); putBeeper();
			moveRobot(2 * blockSize, true, Direction.NONE, Direction.TURN_AROUND);
			moveRobot(blockSize, false, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);
			moveRobot(1, true, Direction.TURN_LEFT, Direction.TURN_LEFT);
			moveRobot(maximum - 1, true, Direction.NONE, Direction.NONE);








//			if (this.width <= this.height)
//				moveRobot(this.width / 2 - 1, false, Direction.TURN_LEFT, Direction.TURN_LEFT);
//			else
//				moveRobot(this.height / 2, false, Direction.TURN_AROUND, Direction.TURN_RIGHT);
//			putBeeper();
//			moveRobot(maximum - (maximum / 2 - blockSize), true, Direction.NONE, Direction.NONE);
//			moveRobot(maximum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
//
//			moveRobot(maximum / 2 - blockSize, true, Direction.NONE, Direction.TURN_LEFT);
//
//			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
//
//			moveRobot(1, true, Direction.NONE, Direction.NONE);
//			moveRobot(1, false, Direction.TURN_AROUND, Direction.TURN_LEFT);
//
//
//			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(2 *  blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
//
//			moveRobot(1, true, Direction.NONE, Direction.TURN_AROUND);
//			moveRobot(1, false, Direction.NONE, Direction.TURN_LEFT);
//
//
//			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
//
//			moveRobot(blockSize, true, Direction.NONE, Direction.NONE);
//			moveRobot(blockSize, false, Direction.TURN_RIGHT, Direction.TURN_AROUND);
//
//			putBeeper();
//
//			moveRobot(2 * blockSize - 1, true, Direction.NONE, Direction.NONE);
//
//			moveRobot(blockSize - 1, false, Direction.TURN_AROUND, Direction.TURN_LEFT);
//
//			moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);
		} else if (this.width % 2 == 0 && this.height % 2 == 1 && this.width > this.height) {
			System.out.println(blockSize);
			moveRobot(this.height / 2, false, Direction.TURN_AROUND, Direction.TURN_RIGHT);
			putBeeper();
			moveRobot(maximum / 2 - 1 - blockSize - 1, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_LEFT);

//			moveRobot(minimum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_AROUND);
//			moveRobot(minimum / 2 - blockSize - 1, false, Direction.NONE, Direction.TURN_LEFT);

			moveRobot(1, true, Direction.NONE, Direction.NONE);
			moveRobot(1, true, Direction.TURN_RIGHT, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_LEFT);


			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.NONE);

//			moveRobot(minimum / 2 - blockSize, true, Direction.NONE, Direction.TURN_AROUND);
//			moveRobot(minimum / 2 - blockSize, false, Direction.NONE, Direction.TURN_LEFT);

			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_LEFT);

			moveRobot(1, true, Direction.NONE, Direction.NONE);
			moveRobot(1, true, Direction.TURN_RIGHT, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_LEFT);


			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);

			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(2 * blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);

//			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
//			moveRobot(blockSize, false, Direction.NONE, Direction.TURN_AROUND); putBeeper();
//			moveRobot(2 * blockSize, true, Direction.NONE, Direction.TURN_AROUND);
//			moveRobot(blockSize, false, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);

//			moveRobot(2 * blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);

		} else if (this.width % 2 == 1 && this.height % 2 == 0 && this.width < this.height) {
			moveRobot(width / 2, false, Direction.TURN_LEFT, Direction.TURN_LEFT);

			putBeeper();
			moveRobot(maximum / 2 - 1 - blockSize - 1, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_LEFT);

			moveRobot(1, true, Direction.NONE, Direction.NONE);
			moveRobot(1, true, Direction.TURN_RIGHT, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_LEFT);

			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.NONE);


			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_LEFT);

			moveRobot(1, true, Direction.NONE, Direction.NONE);
			moveRobot(1, true, Direction.TURN_RIGHT, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_LEFT);

//			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(2 * blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);


//			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);

//			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(2 * blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);

//			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);


//			moveRobot(minimum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_AROUND);
//			moveRobot(minimum / 2 - blockSize - 1, false, Direction.NONE, Direction.TURN_LEFT);
//
//			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(2 * blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
//
//			moveRobot(minimum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_AROUND);
//			moveRobot(minimum / 2 - blockSize - 1, false, Direction.NONE, Direction.TURN_LEFT);
//
//			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);

//			moveRobot(1, false, Direction.NONE, Direction.TURN_LEFT);
//			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
//
//			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
//			moveRobot(blockSize, false, Direction.NONE, Direction.TURN_AROUND); putBeeper();
//			moveRobot(2 * blockSize, true, Direction.NONE, Direction.TURN_AROUND);
//			moveRobot(blockSize, false, Direction.NONE, Direction.TURN_RIGHT);
//			moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);
//
//			// this is just for this case
//			moveRobot(1, true, Direction.TURN_LEFT, Direction.TURN_LEFT);
//			moveRobot(maximum - 1, true, Direction.NONE, Direction.NONE);
		} else if (this.width % 2 == 1 && this.height % 2 == 0 && this.width > this.height) {
			moveRobot(this.height / 2, false, Direction.TURN_AROUND, Direction.TURN_RIGHT);
			putBeeper();
			moveRobot(maximum - (maximum / 2 - blockSize) - 1, true, Direction.NONE, Direction.NONE);
			moveRobot(maximum / 2 - blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(maximum / 2 - blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);

			moveRobot(minimum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_AROUND);
			moveRobot(minimum / 2 - blockSize - 1, false, Direction.NONE, Direction.TURN_LEFT);

			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(2 *  blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);

			moveRobot(minimum / 2 - blockSize - 1, true, Direction.NONE, Direction.TURN_AROUND);
			moveRobot(minimum / 2 - blockSize - 1, false, Direction.NONE, Direction.TURN_LEFT);

			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(blockSize + 1, true, Direction.NONE, Direction.TURN_RIGHT);

			moveRobot(blockSize, true, Direction.NONE, Direction.TURN_LEFT);
			moveRobot(blockSize - 1, true, Direction.NONE, Direction.TURN_AROUND);
			moveRobot(blockSize, false, Direction.NONE, Direction.NONE);
			moveRobot(blockSize - 1, true, Direction.NONE, Direction.TURN_AROUND);
			moveRobot(blockSize, false, Direction.NONE, Direction.TURN_RIGHT);
			moveRobot(maximum / 2, true, Direction.NONE, Direction.NONE);
		}
		System.out.println(numSteps);
		numSteps = 0;
	}
}
// 7 x 50 fail
// odd x even will work now
// even x odd will work