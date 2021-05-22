public class WHILE extends STMT implements RobotProgramNode {
    private COND conditions;

    private BLOCK block;

    public WHILE(COND cond, BLOCK block) {
        this.conditions = cond;
        this.block = block;
    }

    @Override
    public void execute(Robot robot) {
        while (this.conditions.evaluate(robot)) {
            this.block.execute(robot);
        }

    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("while(" + this.conditions.toString() + ")");
        sb.append(this.block.toString());

        return sb.toString();
    }

}
