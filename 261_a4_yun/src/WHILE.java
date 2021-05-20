import java.util.List;

public class WHILE implements RobotProgramNode {
    private COND conditions;

    private BLOCK block;

    public WHILE(COND cond, BLOCK block) {
        this.conditions = cond;
        this.block = block;
    }

    @Override
    public void execute(Robot robot) {
        while (this.conditions.compute(robot)) {
            this.block.execute(robot);
        }

    }

    @Override
    public String toString() {
        return "while(" + this.conditions.toString() + ")";
    }

}
