/** A car class that tracks its speed. */
public class Car {
    private int speed = 0;

    public void accelerate(int amount) {
        speed += amount;
    }

    public void brake() {
        speed = 0;
    }

    public int getSpeed() {
        return speed;
    }
}