

class shieldOn extends ACT {

    @Override
    public void execute(Robot robot) {
        robot.setShield(true);// ();
    }

    @Override
    public String toString() {
        return "shieldOn;";
    }
}