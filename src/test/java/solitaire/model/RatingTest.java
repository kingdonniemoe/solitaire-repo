package solitaire.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RatingTest {
    @Test
    void onePegLeftIsOutstanding() {
        assertEquals(Rating.OUTSTANDING, Rating.fromPegsLeft(1));
    }

    @Test
    void labelIsReadableText() {
        assertEquals("Very Good", Rating.VERY_GOOD.getLabel());
    }
}