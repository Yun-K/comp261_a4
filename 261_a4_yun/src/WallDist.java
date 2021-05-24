

class WallDist extends SEN {

    @Override
    public int evaluate(Robot robot) {
        return robot.getDistanceToWall();
    }

    @Override
    public String toString() {
        return "wallDist";
    }
}