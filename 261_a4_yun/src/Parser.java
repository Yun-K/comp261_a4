
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;
import javax.swing.JFileChooser;

/**
 * The parser and interpreter. The top level parse function, a main method for
 * testing, and several utility methods are provided. You need to implement
 * parseProgram and all the rest of the parser.
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

    /** my pattern: TODO */
    // static Pattern STMT_PATTERN=Pattern.compile("")
    final static Pattern ACT_PATTERN = Pattern.compile("move|turnL|turnR|takeFuel|wait|turnAround|shieldOn|shieldOff");

    final static Pattern LOOP_PATTERN = Pattern.compile("loop");

    final static Pattern RELOP_PATTERN = Pattern.compile("lt|gt|eq");

    final static Pattern SEN_PATTERN = Pattern.compile("fuelLeft|oppLR|oppFB|numBarrels|barrelLR|barrelFB|wallDist");

    final static Pattern NUM_PATTERN = Pattern.compile("-?[1-9][0-9]*|0");// -?[0-9]+");

    final static Pattern OP_PATTERN = Pattern.compile("add|sub|mul|div");

    final static Pattern CONDPattern_logic = Pattern.compile("and|or|not");

    /** for stage 3 */
    final static Pattern VAR_PATTERN = Pattern.compile("\\$[A-Za-z][A-Za-z0-9]*");

    static Map<String, EXPR> variable_expr_map = new HashMap<>();

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

    static STMT parseSTMT(Scanner scanner) {
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
        // for stage 3 & 4, VAR class
        else if (scanner.hasNext(VAR_PATTERN)) {
            return parseVAR(scanner);

        }

        // expected token is missing! execute code below
        else {
            fail("Next token can't start since it is invalid for STMT!" + "\nNext token is :"
                    + (scanner.hasNext() ? scanner.next() : null), scanner);
            return null;
        }
    }

    private static VAR parseVAR(Scanner scanner) {
        String variableName = scanner.next();// token name
        if (scanner.hasNext("=")) {
            scanner.next();// jump '=' token
            EXPR valueEXP = parseExp(scanner);
            if (!checkFor(";", scanner)) {
                fail("';' is missing after " + valueEXP.toString(), scanner);
            }

            variable_expr_map.put(variableName, valueEXP);// put it into the Map, if it's already exist, it will
                                                          // update the corresponding value
            return new VAR(variableName, valueEXP);
        }
        // the case that do not have '='
        else {
            // it has already exist in MAP,just return it
            if (variable_expr_map.containsKey(variableName)) {
                return new VAR(variableName, variable_expr_map.get(variableName));
            }
            /* not in map, use default VAR where value is 0 */
            else {
                VAR toReturn = new VAR(variableName, new NUM(0));
                variable_expr_map.put(variableName, new NUM(0));// put into map
                return toReturn;
            }
        }
    }

    public static STMT parseLOOP(Scanner scanner) {// DONE
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

        ACT node = null;
        // do the check one by one
        if (scanner.hasNext("move")) {
            // MoveNode
            node = parseMove(scanner);
        } else if (scanner.hasNext("turnL")) {
            // TurnLNode
            node = parseTurnL(scanner);
        } else if (scanner.hasNext("turnR")) {
            // TurnRNode
            node = parseTurnR(scanner);
        } else if (scanner.hasNext("takeFuel")) {
            // TakeFuelNode
            node = parseTakeFuel(scanner);

        } else if (scanner.hasNext("wait")) {
            // WaitNode
            node = parseWait(scanner);
        } else if (scanner.hasNext("turnAround")) {
            scanner.next();
            // turnAround
            node = new turnAround();
        } else if (scanner.hasNext("shieldOn")) {
            scanner.next();
            // shieldOn
            node = new shieldOn();
        } else if (scanner.hasNext("shieldOff")) {
            scanner.next();
            // shieldOff
            node = new shieldOff();
        } // else if (scanner.hasNext("")) {}
        else {
            fail("Didn't find the valid Actions that can be parsed", scanner);
            return null;
        }

        // do the check and return node
        if (!checkFor(";", scanner)) {
            fail("';' is missing after this ACT", scanner);
        }
        return node;

    }

    private static STMT parseWHILE(Scanner scanner) {
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
        COND if_cond = parseCOND(scanner);
        if (if_cond == null) {
            fail("condition can not be NULL", scanner);
        }

        // check if it has the ')'
        if (!checkFor(CLOSEPAREN, scanner)) {
            fail("')' is missing", scanner);
        }

        /*
         * check the BLOCK
         */
        BLOCK block = parseBLOCK(scanner);
        // check if it is empty
        if (block == null) {
            fail("no block found for IF ", scanner);
        }

        /*
         * check whether next token is else
         */
        boolean gotElse = checkFor("else", scanner);
        if (!gotElse) {
            // no else, so return it
            return new IF(if_cond, block);
        } else {// } if (gotElse) {
            /* found else, parse the else block */
            BLOCK elseBlock = parseBLOCK(scanner);
            return new IF(new ArrayList<COND>() {
                {
                    add(if_cond);
                }
            }, new ArrayList<BLOCK>() {
                {
                    add(block);
                    add(elseBlock);
                }
            }, gotElse);
        }

    }

    static BLOCK parseBLOCK(Scanner scanner) {// DONE
        if (!checkFor(OPENBRACE, scanner)) {
            fail("'{' is missing, so it is the invalid Block", scanner);
        }

        // add all remaining stmt into the list until find '}'
        List<STMT> stmtNodes = new ArrayList<>();
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

    private static COND parseCOND(Scanner scanner) {
        // if (!scanner.hasNext(RELOP_PATTERN)) {
        // fail("The cond is invalid", scanner);
        // }

        if (scanner.hasNext(RELOP_PATTERN)) {
            String nextToken = scanner.next();// either lt, gt or eq
            if (!checkFor(OPENPAREN, scanner)) {
                fail("'(' is missing after " + nextToken, scanner);
            }
            EXPR child1 = parseExp(scanner);
            if (!checkFor(",", scanner)) {
                fail("',' is missing after " + nextToken, scanner);
            }
            EXPR child2 = parseExp(scanner);
            if (!checkFor(CLOSEPAREN, scanner)) {
                fail("')' is missing after " + nextToken, scanner);
            }
            /*
             * Parser RELOP
             */
            RELOP relop = null;
            switch (nextToken) {
                case "lt":
                    relop = new lt(child1, child2);
                    break;
                case "gt":
                    relop = new Gt(child1, child2);
                    break;
                case "eq":
                    relop = new Eq(child1, child2);
                    break;
                default:// no default , should throw the fail message
                    fail("Didn't find the valid RELOP that can be parsed", scanner);
                    break;
            }
            return relop;
        } else if (scanner.hasNext(CONDPattern_logic)) {// either and, or , not
            String nextToken = scanner.next();// either and, or , not
            if (!checkFor(OPENPAREN, scanner)) {
                fail("'(' is missing after " + nextToken, scanner);
            }
            COND child1 = parseCOND(scanner);
            // check if it is NOT
            if (nextToken.equals("not")) {
                if (!checkFor(CLOSEPAREN, scanner)) {
                    fail("')' is missing after " + nextToken, scanner);
                }
                return new Not(child1);
            }
            /*
             * check AND , OR
             */
            if (!checkFor(",", scanner)) {
                fail("',' is missing after " + nextToken, scanner);
            }
            COND child2 = parseCOND(scanner);
            if (!checkFor(CLOSEPAREN, scanner)) {
                fail("')' is missing after " + nextToken, scanner);
            }
            /*
             * Parser Logic
             */
            Logic logic = null;
            switch (nextToken) {
                case "and":
                    logic = new And(child1, child2);
                    break;
                case "or":
                    logic = new Or(child1, child2);
                    break;
                default:// no default , should throw the fail message
                    fail("Didn't find the valid Logic(And,Or,Not) that can be parsed", scanner);
                    break;
            }
            return logic;
        }
        fail("It is invalid COND", scanner);
        // dead code, should not execute below
        return null;
    }
    // ----------------------------------------------------------------
    /*
     * below are for RELOP from stage1 Since i rewrite them for stage 2, so comment
     * them out
     */
    // private static RELOP parseEQ(Scanner scanner) {
    // // scan if it match and go to scan next
    // if (!checkFor("eq", scanner)) {
    // fail("'eq' is missing", scanner);
    // }

    // /* then check the '(',sen,num,')' one by one */
    // // check if it has the '('
    // if (!checkFor(OPENPAREN, scanner)) {
    // fail("'(' is missing", scanner);
    // }

    // /*
    // * parse
    // */
    // EXPR SEN = parseExp(scanner);
    // if (!checkFor(",", scanner)) {
    // fail("',' is missing ", scanner);
    // }

    // EXPR num = parseExp(scanner);
    // if (!(SEN instanceof SEN)) {
    // fail("The first argument should be the Sen instance", scanner);
    // }

    // if (!(num instanceof NUM)) {
    // fail("The second argument should be the instance of NUM", scanner);
    // }

    // // check if it has the ')'
    // if (!checkFor(CLOSEPAREN, scanner)) {
    // fail("')' is missing", scanner);
    // }
    // return new Eq(SEN, num);
    // }

    // private static RELOP parseGT(Scanner scanner) {
    // // scan if it match and go to scan next
    // if (!checkFor("gt", scanner)) {
    // fail("'gt' is missing", scanner);
    // }

    // /* then check the '(',sen,num,')' one by one */
    // // check if it has the '('
    // if (!checkFor(OPENPAREN, scanner)) {
    // fail("'(' is missing", scanner);
    // }

    // /*
    // * parse
    // */
    // EXPR SEN = parseExp(scanner);
    // if (!checkFor(",", scanner)) {
    // fail("',' is missing ", scanner);
    // }

    // EXPR num = parseExp(scanner);
    // if (!(num instanceof NUM)) {
    // fail("The second argument should be the instance of NUM", scanner);
    // }

    // // check if it has the ')'
    // if (!checkFor(CLOSEPAREN, scanner)) {
    // fail("')' is missing", scanner);
    // }
    // return new Gt(SEN, num);

    // }

    // private static RELOP parseLT(Scanner scanner) {
    // // scan if it match and go to scan next
    // if (!checkFor("lt", scanner)) {
    // fail("'lt' is missing", scanner);
    // }

    // /* then check the '(',sen,num,')' one by one */
    // // check if it has the '('
    // if (!checkFor(OPENPAREN, scanner)) {
    // fail("'(' is missing", scanner);
    // }

    // /*
    // * parse
    // */
    // EXPR SEN = parseExp(scanner);
    // if (!checkFor(",", scanner)) {
    // fail("',' is missing ", scanner);
    // }

    // EXPR num = parseExp(scanner);

    // // if (!(num instanceof NUM)) {
    // // fail("The second argument should be the instance of NUM", scanner);
    // // }

    // // check if it has the ')'
    // if (!checkFor(CLOSEPAREN, scanner)) {
    // fail("')' is missing", scanner);
    // }
    // return new lt(SEN, num);
    // }
    // ----------------------------------------------------------------
    private static EXPR parseExp(Scanner scanner) {

        // stage 1
        if (scanner.hasNext(Parser.SEN_PATTERN)) {
            SEN sen = null;
            String nextToken = scanner.next();// either fuelLeft, oppLR, opFB,numBarrels ....
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
                    if (scanner.hasNext(OPENPAREN)) {
                        scanner.next();// skip (
                        EXPR expr = parseExp(scanner);
                        if (!checkFor(CLOSEPAREN, scanner)) {
                            fail(") is missing after Barrel(exp", scanner);
                        }
                        sen = new BarrelLR(expr);
                    }

                    break;
                case "barrelFB":
                    sen = new BarrelFB();
                    if (scanner.hasNext(OPENPAREN)) {
                        scanner.next();// skip (
                        EXPR expr = parseExp(scanner);
                        if (!checkFor(CLOSEPAREN, scanner)) {
                            fail(") is missing after (exp", scanner);
                        }
                        sen = new BarrelFB(expr);
                    }
                    break;

                case "wallDist":
                    sen = new WallDist();
                    break;
                default:// no default , should throw the fail message
                    fail("not a valid SEN!", scanner);
                    break;
            }

            return sen;
        } else if (scanner.hasNext(NUM_PATTERN)) {
            NUM num = new NUM(Integer.valueOf(scanner.next()));
            return num;
        }
        // stage 2, OP
        else if (scanner.hasNext(OP_PATTERN)) {
            String nextToken = scanner.next();// the op can either be add, sub,mul or div
            if (!checkFor(OPENPAREN, scanner)) {
                fail("'(' is missing after " + nextToken, scanner);
            }
            EXPR child1 = parseExp(scanner);
            if (!checkFor(",", scanner)) {
                fail("',' is missing after " + nextToken, scanner);
            }
            EXPR child2 = parseExp(scanner);
            if (!checkFor(CLOSEPAREN, scanner)) {
                fail("')' is missing after " + nextToken, scanner);
            }

            OP op = null;
            switch (nextToken) {
                case "add":
                    op = new Add(child1, child2);

                    break;
                case "sub":
                    op = new Sub(child1, child2);
                    break;
                case "mul":
                    op = new Mul(child1, child2);
                    break;
                case "div":
                    op = new Div(child1, child2);
                    break;

                default:// no default , should throw the fail message
                    fail("not a valid OPerator!", scanner);
                    break;
            }
            return op;

        }
        // stage 3: VAR
        else if (scanner.hasNext(VAR_PATTERN)) {
            return parseVAR(scanner);
        }
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
        // check if it has exp
        if (scanner.hasNext(OPENPAREN)) {
            scanner.next();
            EXPR expr = parseExp(scanner);
            if (checkFor(CLOSEPAREN, scanner)) {
                return new MoveNode(expr);
            }
            fail("')' is missing for move(", scanner);

        }
        // no arg, just return it
        return new MoveNode();
    }

    private static WaitNode parseWait(Scanner scanner) {// TODO
        boolean isValid = checkFor("wait", scanner);
        if (!isValid) {
            fail("'wait' is not found", scanner);
        }
        // check if it has exp
        if (scanner.hasNext(OPENPAREN)) {
            scanner.next();
            EXPR expr = parseExp(scanner);
            // check whether it has ')'
            if (!checkFor(CLOSEPAREN, scanner)) {
                fail("')' is missing for wait(", scanner);
            }
            return new WaitNode(expr);
        }
        // no arg, just return wait
        return new WaitNode();
    }

    private static TakeFuelNode parseTakeFuel(Scanner scanner) {// DONE
        boolean isValid = checkFor("takeFuel", scanner);
        if (!isValid) {
            fail("'takeFuel' is not found", scanner);
        }
        return new TakeFuelNode();
    }

    private static TurnRNode parseTurnR(Scanner scanner) {// done
        boolean isValid = checkFor("turnR", scanner);
        if (!isValid) {
            fail("'turnR' is not found", scanner);
        }
        return new TurnRNode();
    }

    private static TurnLNode parseTurnL(Scanner scanner) {// done
        boolean isValid = checkFor("turnL", scanner);
        if (!isValid) {
            fail("'turnL' is not found", scanner);
        }
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
     * Requires that the next token matches a pattern if it matches, it consumes and
     * returns the token, if not, it throws an exception with an error message
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
     * Requires that the next token matches a pattern (which should only match a
     * number) if it matches, it consumes and returns the token as an integer if
     * not, it throws an exception with an error message
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
     * Checks whether the next token in the scanner matches the specified pattern,
     * if so, consumes the token and return true. Otherwise returns false without
     * consuming anything.
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
