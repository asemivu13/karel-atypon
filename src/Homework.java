import stanford.karel.SuperKarel;
public class Homework extends SuperKarel {
    public void run() {
        int numSteps = 0;
        while (frontIsClear()) {
            move();
            numSteps += 1;
        }
        System.out.println(numSteps);
    }
}