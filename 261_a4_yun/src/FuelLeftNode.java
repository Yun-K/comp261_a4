

class FuelLeftNode extends SEN {

    public FuelLeftNode() {

    }

    public FuelLeftNode(Robot robot) {

    }

    @Override
    public String toString() {
        return "fuelLeft";// + getValue(robot);
    }

    @Override
    public int evaluate(Robot robot) {
        return robot.getFuel();
    }
}