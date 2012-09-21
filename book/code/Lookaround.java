/**
 * Examples of zero-width lookahead/lookbehind splitting.
 * For each example, study the input and output.
 *
 * http://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
 *
 * @author Godmar <godmar@gmail.com>
 */

import java.util.*;

public class Lookaround 
{
    /* String.format patterns for ease of use */
    final static String MATCH_BEFORE_OR_AFTER = "((?<=%1$s)|(?=%1$s))";
    final static String MATCH_AFTER = "(?<=%1$s)";
    final static String MATCH_BEFORE = "(?=%1$s)";

    static void example(String input, String delim) {
        Scanner s = new Scanner(input).useDelimiter(delim);
        System.out.println("Delimiter: " + delim);
        System.out.println("Input: " + input);
        System.out.print("Output: '" + s.next() + "'");
        while (s.hasNext()) {
            System.out.print(", '" + s.next() + "'");
        }
        System.out.println();
        System.out.println();
    }

    public static void main(String []av) {
        // match right before or after +, -, *, /
        // consumes nothing
        String delim = String.format(MATCH_BEFORE_OR_AFTER, "[\\+\\-\\*\\/]");
        example("10+21*32-43/5+60", delim);

        // matches whitespace or  right before or after +, -, *, /
        // consumes whitespace - but no +/-/*//
        delim = "\\p{javaWhitespace}|" 
            + String.format(MATCH_BEFORE_OR_AFTER, "[\\+\\-\\*\\/]");
        example("10 + 21*32 -43 / 5+60", delim);

        // match whitespace or right before or after ( )
        // consumes whitespace - but does not consume ( or )
        delim = "\\p{javaWhitespace}|" 
            + String.format(MATCH_BEFORE_OR_AFTER, "[\\(\\)]");
        example("((F1 (A1 1 2 3)) (F2 (A2 4 5) ( A3 5 )))", delim);

        // match at word boundaries
        // consumes nothing
        delim = "\\b";
        example("This text has words, and some---wrongly set---punctuation characters."
            +"Note that words can contain alphanumericals such as babe1234 and"
            +" underscores.  Underscores do_not_form_a_word_boundary.", delim);

        // match at before or after anything that is not alphanumeric
        // (which matches every boundary except within a word of alphanumeric chars.)
        // consumes nothing
        delim = String.format(MATCH_BEFORE_OR_AFTER, "[^A-Za-z0-9]");
        example("This delimiter identifies word boundaries, but unlike the previous "
            +"one returns all characters between words as individual tokens. "
            +"Underscores do_form_a_word_boundary with this delimiter.",
             delim);

        // match after ., !, ? or empty line.
        delim = String.format(MATCH_AFTER, "[\\.\\!\\?]|\\n\\n");
        example("This matches sentences. And questions too?  Yes!  "
            +"Even breaks between\n\nparagraphs.", delim);
    }
}
