package solitaire.model;

import java.util.Objects;

public final class Move {
    private final Position from;
    private final Position over;
    private final Position to;

    public Move(Position from, Position over, Position to) {
        this.from = from;
        this.over = over;
        this.to = to;
    }

    public Position getFrom() {
        return from;
    }

    public Position getOver() {
        return over;
    }

    public Position getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Move other)) {
            return false;
        }
        return from.equals(other.from) && over.equals(other.over) && to.equals(other.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, over, to);
    }

    @Override
    public String toString() {
        return "Move(" + from + " over " + over + " to " + to + ")";
    }
}