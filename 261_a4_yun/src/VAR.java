

/**
 * Var is adapter for STMT and EXPR
 */
class VAR extends STMT implements EXPR {
    private String variableName;

    private EXPR expr_value;

    /**
     * @param variableName
     * @param value
     */
    public VAR(String variableName, EXPR value) {
        this.variableName = variableName;
        this.expr_value = value;
    }

    @Override
    public int evaluate(Robot robot) {
        return expr_value.evaluate(robot);
    }

    // @Override
    // public void execute(Robot robot) {
    // expr_value.execute(robot);
    // }
    @Override
    public String toString() {
        return variableName + " = " + expr_value.toString() + " ;";
    }

}