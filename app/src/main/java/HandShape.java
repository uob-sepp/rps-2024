public enum HandShape {
    ROCK,
    PAPER,
    SCISSORS;

    public HandShape beats() {
        if(this == HandShape.ROCK) return HandShape.SCISSORS;
        else if(this == HandShape.PAPER) return HandShape.ROCK;
        else return HandShape.PAPER;
    }
}
