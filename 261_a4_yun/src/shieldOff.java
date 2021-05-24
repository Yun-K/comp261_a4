

class shieldOff extends ACT {

    @Override
    public void execute(Robot robot) {
        robot.setShield(false);

    }

    @Override
    public String toString() {
        return "shieldOff;";
    }
}