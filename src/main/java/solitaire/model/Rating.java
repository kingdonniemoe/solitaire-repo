package solitaire.model;

public enum Rating {
    OUTSTANDING("Outstanding"),
    VERY_GOOD("Very Good"),
    GOOD("Good"),
    AVERAGE("Average");

    private final String label;

    Rating(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Rating fromPegsLeft(int pegsLeft) {
        if (pegsLeft < 1) {
            throw new IllegalArgumentException("A finished game always has at least one peg left");
        }
        return switch (pegsLeft) {
            case 1 -> OUTSTANDING;
            case 2 -> VERY_GOOD;
            case 3 -> GOOD;
            default -> AVERAGE;
        };
    }
}