public record Position(int x, int y) {
    public boolean nextTo(Position p) {
        return Math.abs(x - p.x) + Math.abs(y - p.y) == 1;
    }
}
