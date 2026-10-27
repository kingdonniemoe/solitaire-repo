package solitaire.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class PositionTest {
    @Test
    void positionsWithSameRowAndColAreEqual() {
        Position a = new Position(3, 3);
        Position b = new Position(3, 3);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void positionsWithDifferentColAreNotEqual() {
        assertNotEquals(new Position(3, 3), new Position(3, 4));
    }

    @Test
    void stepMovesByDirectionTimesDistance() {
        Position center = new Position(3, 3);

        assertEquals(new Position(1, 3), center.step(Direction.UP, 2));
        assertEquals(new Position(4, 4), center.step(Direction.DOWN_RIGHT, 1));
    }
}