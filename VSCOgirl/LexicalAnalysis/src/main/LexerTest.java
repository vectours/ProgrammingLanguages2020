
import java.io.IOException;
import javax.lang.model.util.Types;

public class LexerTest {
    private static final String testInputDirectory = "./VSCOgirl/LexicalAnalysis/TestInput";

    public static void main(String[] args) {
        Lexer lexer = new Lexer(testInputDirectory + "/myTesting.txt");
        /*
         * try { Lexeme currentLexeme = lexer.lex(); while (currentLexeme.getTypes() !=
         * Types.END_OF_INPUT) { System.out.println(currentLexeme); currentLexeme =
         * lexer.lex(); } } catch (IOException e) {
         * System.out.println("Error while lexing: " + e); }
         */
    }
}