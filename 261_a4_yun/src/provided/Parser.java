package provided;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;
import javax.swing.JFileChooser;

/**
 * The parser and interpreter. The top level parse function, a main method for testing, and
 * several utility methods are provided. You need to implement parseProgram and all the rest of
 * the parser.
 */
public class Parser {

    /**
     * Top level parse method, called by the World
     */
    static RobotProgramNode parseFile(File code) {
        Scanner scan = null;
        try {
            scan = new Scanner(code);

            // the only time tokens can be next to each other is
            // when one of them is one of (){},;
            scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");

            RobotProgramNode n = parseProgram(scan); // You need to implement this!!!

            scan.close();
            return n;
        } catch (FileNotFoundException e) {
            System.out.println("Robot program source file not found");
        } catch (ParserFailureException e) {
            System.out.println("Parser error:");
            System.out.println(e.getMessage());
            scan.close();
        }
        return null;
    }

    /** For testing the parser without requiring the world */

    public static void main(String[] args) {
        if (args.length > 0) {
            for (String arg : args) {
                File f = new File(arg);
                if (f.exists()) {
                    System.out.println("Parsing '" + f + "'");
                    RobotProgramNode prog = parseFile(f);
                    System.out.println("Parsing completed ");
                    if (prog != null) {
                        System.out.println("================\nProgram:");
                        System.out.println(prog);
                    }
                    System.out.println("=================");
                } else {
                    System.out.println("Can't find file '" + f + "'");
                }
            }
        } else {
            while (true) {
                JFileChooser chooser = new JFileChooser(".");// System.getProperty("user.dir"));
                int res = chooser.showOpenDialog(null);
                if (res != JFileChooser.APPROVE_OPTION) {
                    break;
                }
                RobotProgramNode prog = parseFile(chooser.getSelectedFile());
                System.out.println("Parsing completed");
                if (prog != null) {
                    System.out.println("Program: \n" + prog);
                }
                System.out.println("=================");
            }
        }
        System.out.println("Done");
    }

    // Useful Patterns

    static Pattern NUMPAT = Pattern.compile("-?\\d+"); // ("-?(0|[1-9][0-9]*)");

    static Pattern OPENPAREN = Pattern.compile("\\(");

    static Pattern CLOSEPAREN = Pattern.compile("\\)");

    static Pattern OPENBRACE = Pattern.compile("\\{");

    static Pattern CLOSEBRACE = Pattern.compile("\\}");

    /**
     * See assignment handout for the grammar.
     * <p>
     * PROG ::= STMT*
     * <p>
     * STMT ::= ACT ";" | LOOP
     * <p>
     * ACT ::= "move" | "turnL" | "turnR" | "takeFuel" | "wait"
     * <p>
     * LOOP ::= "loop" BLOCK
     * <p>
     * BLOCK ::= "{" STMT+ "}"
     * 
     * <p>
     * Example:
     * <p>
     * move; move; move; turnL ;
     * <p>
     * wait;
     * <p>
     * loop{
     * <p>
     * move; move; turnR;
     * <p>
     * move; move; turnR;
     * <p>
     * move; turnR;
     * <p>
     * move; move; turnR;
     * <p>
     * takeFuel;
     * <p>
     * }
     */
    static RobotProgramNode parseProgram(Scanner s) {
        // THE PARSER GOES HERE

        // loop until the end
        while (s.hasNext()) {

        }

        return null;
    }

    // utility methods for the parser

    /**
     * Report a failure in the parser.
     */
    static void fail(String message, Scanner s) {
        String msg = message + "\n   @ ...";
        for (int i = 0; i < 5 && s.hasNext(); i++) {
            msg += " " + s.next();
        }
        throw new ParserFailureException(msg + "...");
    }

    /**
     * Requires that the next token matches a pattern if it matches, it consumes and returns
     * the token, if not, it throws an exception with an error message
     */
    static String require(String p, String message, Scanner s) {
        if (s.hasNext(p)) {
            return s.next();
        }
        fail(message, s);
        return null;
    }

    static String require(Pattern p, String message, Scanner s) {
        if (s.hasNext(p)) {
            return s.next();
        }
        fail(message, s);
        return null;
    }

    /**
     * Requires that the next token matches a pattern (which should only match a number) if it
     * matches, it consumes and returns the token as an integer if not, it throws an exception
     * with an error message
     */
    static int requireInt(String p, String message, Scanner s) {
        if (s.hasNext(p) && s.hasNextInt()) {
            return s.nextInt();
        }
        fail(message, s);
        return -1;
    }

    static int requireInt(Pattern p, String message, Scanner s) {
        if (s.hasNext(p) && s.hasNextInt()) {
            return s.nextInt();
        }
        fail(message, s);
        return -1;
    }

    /**
     * Checks whether the next token in the scanner matches the specified pattern, if so,
     * consumes the token and return true. Otherwise returns false without consuming anything.
     */
    static boolean checkFor(String p, Scanner s) {
        if (s.hasNext(p)) {
            s.next();
            return true;
        } else {
            return false;
        }
    }

    static boolean checkFor(Pattern p, Scanner s) {
        if (s.hasNext(p)) {
            s.next();
            return true;
        } else {
            return false;
        }
    }

}

class MoveNode implements RobotProgramNode {

    @Override
    public void execute(Robot robot) {
        robot.move();
    }
}

// You could add the node classes here, as long as they are not declared public (or private)
class ActionNode implements RobotProgramNode {
    /** represent the action Node */
    protected ActionNode actionNode;

    public ActionNode() {
        // TODO Auto-generated constructor stub
    }

    public ActionNode(ActionNode actionNode) {
        this.actionNode = actionNode;
    }

    @Override
    public void execute(Robot robot) {
        // TODO Auto-generated method stub

    }

}

class TurnLNode extends ActionNode implements RobotProgramNode {

    public TurnLNode() {
        // TODO Auto-generated constructor stub
    }

    TurnLNode(TurnLNode actionLNode) {
        super();

    }

    @Override
    public void execute(Robot robot) {
        robot.turnLeft();
    }

    @Override
    public String toString() {
        // return super.toString();
        return "turnLeft";
    }
}

class TurnRNode extends ActionNode implements RobotProgramNode {

    @Override
    public void execute(Robot robot) {
        robot.turnRight();
    }

}

class TakeFuelNode extends ActionNode implements RobotProgramNode {
    @Override
    public void execute(Robot robot) {
        robot.takeFuel();
    }
}

class WaitNode extends ActionNode implements RobotProgramNode {

    @Override
    public void execute(Robot robot) {
        try {
            robot.wait();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class LoopNode extends ActionNode implements RobotProgramNode {

    private RobotProgramNode block;

    public LoopNode(RobotProgramNode block) {
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
        return "loop {\n" + this.block + "\n}";
    }
}

class BlockNode implements RobotProgramNode {
    private List<RobotProgramNode> actions_program_node;

    public BlockNode(List<RobotProgramNode> actions_nodeList) {
        this.actions_program_node = actions_nodeList;
    }

    /**
     * Let the specificied robot to perform/execute the list of actions in order
     * 
     * @see RobotProgramNode#execute(Robot)
     */
    @Override
    public void execute(Robot robot) {
        //
        for (RobotProgramNode robotProgramNode : actions_program_node) {
            robotProgramNode.execute(robot);
        }

    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("{\n");
        for (RobotProgramNode robotProgramNode : actions_program_node) {
            sb.append("\t");
            sb.append(robotProgramNode.toString());
            sb.append("\n");

        }
        sb.append("\n}");
        return sb.toString();
        // return super.toString();

    }

    /**
     * Get the actions_program_node.
     *
     * @return the actions_program_node
     */
    public List<RobotProgramNode> getActions_program_node() {
        return actions_program_node;
    }
}