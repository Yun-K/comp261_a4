package provided;

public class LOOP extends ACT implements RobotProgramNode {

    private RobotProgramNode block;

    public LOOP(RobotProgramNode block) {
        this.block = block;
    }

    @Override
    public void execute(Robot robot) {
        while (true) {
            block.execute(robot);
        }
    }

    public String toString() {
        // return "loop" + this.block;
        return "loop {\n" + this.block + "\n}\n";
    }
}