

class NumBarrels extends SEN {

    @Override
    public int evaluate(Robot robot) {
        return robot.numBarrels();
    }

    @Override
    public String toString() {
        return "numBarrels";
    }
}