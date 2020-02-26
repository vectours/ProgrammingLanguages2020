
import java.io.IOException;

public class LexerTest {
    private static final String testInputDirectory = "./TestInput";

    public static void main(String[] args) {
        Lexer lexer = new Lexer(testInputDirectory + "/myTest.txt");
        /*
         * try { Lexeme currentLexeme = lexer.lex(); while (currentLexeme.getTypes() !=
         * Types.END_OF_INPUT) { System.out.println(currentLexeme); currentLexeme =
         * lexer.lex(); } } catch (IOException e) {
         * System.out.println("Error while lexing: " + e); }
         */
    }
}