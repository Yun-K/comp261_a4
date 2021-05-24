

class OppFB extends SEN {

    @Override
    public int evaluate(Robot robot) {
        return robot.getOpponentFB();
    }

    @Override
    public String toString() {
        return "oppFB";
    }
}