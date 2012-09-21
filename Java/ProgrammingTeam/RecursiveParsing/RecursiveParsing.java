import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;


public class RecursiveParsing 
{
	/**
     * Lexical analysis using zero-width lookaround splitting.
     * Tokens are kept as strings, for simplicity.
     * Tokens are *, /, +, -, (, ), or number or variable
     */
    final static String MATCH_BEFORE_OR_AFTER = "((?<=%1$s)|(?=%1$s))";
    // matches whitespace or  right before or after +, -, *, /, =, (, )
    // consumes whitespace - but no +/-/*//
    final static String delim = "\\p{javaWhitespace}+|" + String.format(MATCH_BEFORE_OR_AFTER, "[\\+\\-\\*\\/=\\)\\(]");
    
    /* Place all tokens eagerly into queue so that we can peek at the next one. */
    Deque<String> tokens = new ArrayDeque<String>();
    RecursiveParsing(String equation) {
        Scanner tokenScanner = new Scanner(equation).useDelimiter(delim);
        while (tokenScanner.hasNext())
            tokens.add(tokenScanner.next());
        // System.out.println(tokens);
    }
    
    /* Return the first char of the next, unconsumed token, or 0 otherwise. */
    char lookahead() {
        if (tokens.isEmpty())
            return 0;
        return tokens.peek().charAt(0);
    }
    
}
