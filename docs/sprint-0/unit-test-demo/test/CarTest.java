import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class CarTest {
    @Test
    void increaseSpeed(){
        Car car = new Car();
        car.accelerate(10);
        assertEquals(10, car.getSpeed());
    }

    @Test
    void brakeStopsCar() {
        Car car = new Car();
        car.accelerate(20);
        car.brake();
        assertEquals(0, car.getSpeed());
    }
}