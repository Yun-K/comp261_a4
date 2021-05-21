
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

    /** my pattern: */
    // static Pattern STMT_PATTERN=Pattern.compile("")
    final static Pattern ACT_PATTERN = Pattern
            .compile("move|turnL|turnR|takeFuel|wait|turnAround|shieldOn|shieldOff");

    final static Pattern LOOP_PATTERN = Pattern.compile("loop");

    final static Pattern RELOP_PATTERN = Pattern.compile("lt|gt|eq");

    final static Pattern SEN_PATTERN = Pattern
            .compile("fuelLeft|oppLR|oppFB|numBarrels|barrelLR|barrelFB|wallDist");

    final static Pattern NUM_PATTERN = Pattern.compile("-?[0-9]+");

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
     */
    static RobotProgramNode parseProgram(Scanner s) {
        // THE PARSER GOES HERE: TODO
        List<RobotProgramNode> allProgramNodes = new ArrayList<RobotProgramNode>();
        // loop until the end
        while (s.hasNext()) {
            allProgramNodes.add(parseSTMT(s));
        }

        s.close();// close to save resources
        return new PROG(allProgramNodes);
    }

    static RobotProgramNode parseSTMT(Scanner scanner) {
        // use hasNext("") to peek nextToken
        if (scanner.hasNext(LOOP_PATTERN)) {
            return parseLOOP(scanner);// DONE
        } else if (scanner.hasNext(ACT_PATTERN)) {
            return parseACT(scanner);
        } else if (scanner.hasNext("if")) {
            return parseIF(scanner);
        } else if (scanner.hasNext("while")) {
            return parseWHILE(scanner);
        }
        // else if (scanner.hasNext()) {
        //
        //
        // }

        // expected token is missing! execute code below
        else {
            fail("Next token can't start since it is invalid for STMT!" + "\nNext token is :"
                 + (scanner.hasNext() ? scanner.next() : null), scanner);
            return null;
        }
    }

    public static RobotProgramNode parseLOOP(Scanner scanner) {// DONE
        boolean isMatch = checkFor(LOOP_PATTERN, scanner);
        if (!isMatch) {
            fail("The 'loop' is missing", scanner);
        }
        // is Mathched, so return new LOOP
        return new LOOP(parseBLOCK(scanner));

    }

    public static ACT parseACT(Scanner scanner) {
        boolean isMatched = scanner.hasNext(ACT_PATTERN);
        if (!isMatched) {
            fail("NOt a valid ACTION", scanner);
        }

        // do the check one by one
        if (scanner.hasNext("move")) {
            MoveNode node = parseMove(scanner);
            if (!checkFor(";", scanner)) {
                fail("';' is missing after move", scanner);
            }
            return node;
        } else if (scanner.hasNext("turnL")) {
            TurnLNode node = parseTurnL(scanner);
            if (!checkFor(";", scanner)) {
                fail("';' is missing after turnL", scanner);
            }
            return node;
        } else if (scanner.hasNext("turnR")) {
            TurnRNode node = parseTurnR(scanner);
            if (!checkFor(";", scanner)) {
                fail("';' is missing after turnR", scanner);
            }
            return node;

        } else if (scanner.hasNext("takeFuel")) {
            TakeFuelNode node = parseTakeFuel(scanner);
            if (!checkFor(";", scanner)) {
                fail("';' is missing after takeFuel", scanner);
            }
            return node;

        } else if (scanner.hasNext("wait")) {
            WaitNode node = parseWait(scanner);
            if (!checkFor(";", scanner)) {
                fail("';' is missing after wait", scanner);
            }
            return node;
        }

        fail("Didn't find the valid Actions that can be parsed", scanner);
        return null;
    }

    private static WHILE parseWHILE(Scanner scanner) {
        boolean isMatch = checkFor("while", scanner);
        if (!isMatch) {
            fail("'while' is missing", scanner);
        }
        // check if it has the '('
        if (!checkFor(OPENPAREN, scanner)) {
            fail("'(' is missing", scanner);
        }

        /*
         * check the CONDITION
         */
        // add all remaining cond into the list until find '}'
        COND conditions = null;
        conditions = parseCOND(scanner);
        if (conditions == null) {
            fail("The if condition can not be null!", scanner);
        }

        // check if it has the ')'
        if (!checkFor(CLOSEPAREN, scanner)) {
            fail("')' is missing", scanner);
        }

        /*
         * check the BLOCK
         */

        // add all remaining block into the list until find '}'
        BLOCK block = null;
        block = parseBLOCK(scanner);

        // check if it is empty
        if (block == null) {
            fail("no block found for while ", scanner);
        }

        // is Mathched, so return new LOOP
        return new WHILE(conditions, block);
    }

    private static IF parseIF(Scanner scanner) {/// TODO
        boolean isMatch_if = checkFor("if", scanner);
        if (!isMatch_if) {
            fail("'if' is missing", scanner);
        }

        // check if it has the '('
        if (!checkFor(OPENPAREN, scanner)) {
            fail("'(' is missing", scanner);
        }

        /*
         * check the CONDITION
         */
        // add all remaining cond into the list until find '}'
        COND conditions = null;
        conditions = parseCOND(scanner);
        // check if it is empty
        if (conditions == null) {
            fail("no codition found inside IF ", scanner);
        }

        // check if it has the ')'
        if (!checkFor(CLOSEPAREN, scanner)) {
            fail("')' is missing", scanner);
        }

        /*
         * check the BLOCK
         */
        BLOCK block = null;
        block = parseBLOCK(scanner);
        // check if it is empty
        if (block == null) {
            fail("no block found for IF ", scanner);
        }

        // is Mathched, so return new LOOP
        return new IF(conditions, block);
    }

    static BLOCK parseBLOCK(Scanner scanner) {// DONE
        if (!checkFor(OPENBRACE, scanner)) {
            fail("'{' is missing, so invalid Block", scanner);
        }

        // add all remaining stmt into the list until find '}'
        List<RobotProgramNode> stmtNodes = new ArrayList<>();
        while (!scanner.hasNext(CLOSEBRACE)) {
            stmtNodes.add(parseSTMT(scanner));
        }
        // check if it is empty
        if (stmtNodes.isEmpty()) {
            fail("no statement inside BLOCK ", scanner);
        }

        // check if it is the closing '}'
        if (!checkFor(CLOSEBRACE, scanner)) {
            fail("'}' is missing, so invalid Block", scanner);
        }

        return new BLOCK(stmtNodes);
    }

    private static COND parseCOND(Scanner scanner) {// TODO
        if (!scanner.hasNext(RELOP_PATTERN)) {
            fail("The cond is invalid", scanner);
        }

        /*
         * Parser RELOP first
         */
        RELOP relop = null;
        // like parseACT to check RELOP one by one
        if (scanner.hasNext("lt")) {
            relop = parseLT(scanner);
        } else if (scanner.hasNext("gt")) {
            relop = parseGT(scanner);
        } else if (scanner.hasNext("eq")) {
            relop = parseEQ(scanner);
        }
        // /*
        // * check if it has the bracket
        // */
        // scanner.hasNext(OPENPAREN)

        // below are stage 2
        // else if() {}

        /*
         * return statement
         */
        if (relop != null) {
            return relop;
        } else {
            // invalid
            fail("Didn't find the valid cond that can be parsed", scanner);
            return null;
        }
    }

    private static RELOP parseEQ(Scanner scanner) {
        // scan if it match and go to scan next
        if (!checkFor("eq", scanner)) {
            fail("'eq' is missing", scanner);
        }

        /* then check the '(',sen,num,')' one by one */
        // check if it has the '('
        if (!checkFor(OPENPAREN, scanner)) {
            fail("'(' is missing", scanner);
        }

        /*
         * parse
         */
        EXPR SEN = parseExp(scanner);
        if (!checkFor(",", scanner)) {
            fail("',' is missing ", scanner);
        }

        EXPR NUM = parseExp(scanner);

        // check if it has the ')'
        if (!checkFor(CLOSEPAREN, scanner)) {
            fail("')' is missing", scanner);
        }
        return new Eq(SEN, NUM);
    }

    private static RELOP parseGT(Scanner scanner) {
        // scan if it match and go to scan next
        if (!checkFor("gt", scanner)) {
            fail("'gt' is missing", scanner);
        }

        /* then check the '(',sen,num,')' one by one */
        // check if it has the '('
        if (!checkFor(OPENPAREN, scanner)) {
            fail("'(' is missing", scanner);
        }

        /*
         * parse
         */
        EXPR SEN = parseExp(scanner);
        if (!checkFor(",", scanner)) {
            fail("',' is missing ", scanner);
        }

        EXPR num = parseExp(scanner);
        if (num instanceof NUM) {
            
        }

        // check if it has the ')'
        if (!checkFor(CLOSEPAREN, scanner)) {
            fail("')' is missing", scanner);
        }
        return new Gt(SEN, num);

    }

    private static RELOP parseLT(Scanner scanner) {
        // scan if it match and go to scan next
        if (!checkFor("lt", scanner)) {
            fail("'lt' is missing", scanner);
        }

        /* then check the '(',sen,num,')' one by one */
        // check if it has the '('
        if (!checkFor(OPENPAREN, scanner)) {
            fail("'(' is missing", scanner);
        }

        /*
         * parse
         */
        EXPR SEN = parseExp(scanner);
        if (!checkFor(",", scanner)) {
            fail("',' is missing ", scanner);
        }

        EXPR NUM = parseExp(scanner);

        // check if it has the ')'
        if (!checkFor(CLOSEPAREN, scanner)) {
            fail("')' is missing", scanner);
        }
        return new lt(SEN, NUM);
    }

    private static EXPR parseExp(Scanner scanner) {

        // stage 1
        if (scanner.hasNext(Parser.SEN_PATTERN)) {
            SEN sen = null;
            String nextToken = scanner.next();
            switch (nextToken) {
            case "fuelLeft":
                sen = new FuelLeftNode();
                break;
            case "oppLR":
                sen = new OppLR();
                break;
            case "oppFB":
                sen = new OppFB();
                break;
            case "numBarrels":
                sen = new NumBarrels();
                break;
            case "barrelLR":
                sen = new BarrelLR();
                break;
            case "barrelFB":
                sen = new BarrelFB();
                break;

            case "wallDist":
                sen= new WallDist();
                break;
            default:// no default , should throw the fail message
                fail("not a valid SEN!", scanner);
                break;
            }

            return sen;
        } else if (scanner.hasNext(NUMPAT)) {
            NUM num = new NUM(Integer.valueOf(scanner.next()));
            return num;
        }
        // stage 2

        fail("the EXPR is invalid", scanner);
        return null;
    }

    /*
     * ACT:
     */
    private static MoveNode parseMove(Scanner scanner) {// TODO
        boolean isValid = checkFor("move", scanner);
        if (!isValid) {
            fail("'move' is not found", scanner);
        }

        // TODO Auto-generated method stub
        return new MoveNode();

    }

    private static WaitNode parseWait(Scanner scanner) {// TODO
        boolean isValid = checkFor("wait", scanner);
        if (!isValid) {
            fail("'wait' is not found", scanner);
        }
        return new WaitNode();
    }

    private static TakeFuelNode parseTakeFuel(Scanner scanner) {// DONE
        boolean isValid = checkFor("takeFuel", scanner);
        if (!isValid) {
            fail("'takeFuel' is not found", scanner);
        }
        // TODO Auto-generated method stub
        return new TakeFuelNode();
    }

    private static TurnRNode parseTurnR(Scanner scanner) {// done
        boolean isValid = checkFor("turnR", scanner);
        if (!isValid) {
            fail("'turnR' is not found", scanner);
        }
        // TODO Auto-generated method stub
        return new TurnRNode();
    }

    private static TurnLNode parseTurnL(Scanner scanner) {// done
        boolean isValid = checkFor("turnL", scanner);
        if (!isValid) {
            fail("'turnL' is not found", scanner);
        }
        // TODO Auto-generated method stub
        return new TurnLNode();
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

// You could add the node classes here, as long as they are not declared public
// (or private)
