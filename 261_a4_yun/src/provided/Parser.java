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

    /** my pattern: */
    // static Pattern STMT_PATTERN=Pattern.compile("")
    final static Pattern ACT_PATTERN = Pattern.compile(
            "move|turnL|turnR|takeFuel|wait");

    final static Pattern LOOP_PATTERN = Pattern.compile("loop");

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
        // return PROG
        return new PROG(allProgramNodes);
    }

    static RobotProgramNode parseSTMT(Scanner scanner) {
        // use hasNext("") to peek nextToken
        if (scanner.hasNext(LOOP_PATTERN)) {
            return parseLOOP(scanner);
        } else if (scanner.hasNext(ACT_PATTERN)) {
            return parseACT(scanner);
        }
        // expected token is missing! execute code below
        else {
            fail("Next token can't start since it is invalid for STMT!"
                 + "\nNext token is :" + scanner.hasNext(),
                    scanner);
            return null;
        }
    }

    static RobotProgramNode parseLOOP(Scanner scanner) {
        boolean isMatch = checkFor(LOOP_PATTERN, scanner);
        if (!isMatch) {
            fail("The 'loop' is missing", scanner);
        }
        // is Mathched!
        return new LOOP(parseBLOCK(scanner));

    }

    static RobotProgramNode parseACT(Scanner scanner) {
        boolean isMatched = scanner.hasNext(ACT_PATTERN);
        if (!isMatched) {
            fail("NOt a valid ACTION", scanner);
        }

        // do the check one by one
        if (scanner.hasNext("move")) {
            RobotProgramNode node = parseMove(scanner);
            if (!checkFor(";", scanner)) {
                fail("; is missing after move", scanner);
            }
            return node;
        } else if (scanner.hasNext("turnL")) {
            RobotProgramNode node = parseTurnL(scanner);
            if (!checkFor(";", scanner)) {
                fail("; is missing after turnL", scanner);
            }
            return node;
        } else if (scanner.hasNext("turnR")) {
            RobotProgramNode node = parseTurnR(scanner);
            if (!checkFor(";", scanner)) {
                fail("; is missing after turnR", scanner);
            }
            return node;

        } else if (scanner.hasNext("takeFuel")) {
            RobotProgramNode node = parseTakeFuel(scanner);
            if (!checkFor(";", scanner)) {
                fail("; is missing after takeFuel", scanner);
            }
            return node;

        } else if (scanner.hasNext("wait")) {
            RobotProgramNode node = parseWait(scanner);
            if (!checkFor(";", scanner)) {
                fail("; is missing after wait", scanner);
            }
            return node;
        }

        fail("Didn't find the valid Actions that can be parsed", scanner);
        return null;
    }

    static RobotProgramNode parseBLOCK(Scanner scanner) {
        return null;
    }

    /*
     * ACT:
     */
    private static RobotProgramNode parseMove(Scanner scanner) {
        // TODO Auto-generated method stub
        return null;

    }

    private static RobotProgramNode parseWait(Scanner scanner) {
        // TODO Auto-generated method stub
        return null;
    }

    private static RobotProgramNode parseTakeFuel(Scanner scanner) {
        // TODO Auto-generated method stub
        return null;
    }

    private static RobotProgramNode parseTurnR(Scanner scanner) {
        // TODO Auto-generated method stub
        return null;
    }

    private static RobotProgramNode parseTurnL(Scanner scanner) {
        // TODO Auto-generated method stub
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

// You could add the node classes here, as long as they are not declared public (or private)
