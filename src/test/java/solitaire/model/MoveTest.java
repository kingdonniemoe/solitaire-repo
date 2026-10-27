package solitaire.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MoveTest {
    @Test
    void gettersReturnThePositionsPassedIn() {
        Position from = new Position(1, 3);
        Position over = new Position(2, 3);
        Position to = new Position(3, 3);

        Move move = new Move(from, over, to);

        assertEquals(from, move.getFrom());
        assertEquals(over, move.getOver());
        assertEquals(to, move.getTo());

    }
}