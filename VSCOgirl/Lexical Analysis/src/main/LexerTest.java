import java.io.IOException;

public class LexerTest {
    private static final String testInputDirectory = "./VSCOgirl/Lexical Analysis/Test Input";

    public static void main(String[] args) {
        Lexer lexer = new Lexer(testInputDirectory + "/myTest.txt");
        try {
            Lexeme currentLexeme = lexer.lex();
            while (currentLexeme.getType() != Types.END_OF_INPUT) {
                System.out.println(currentLexeme);
                currentLexeme = lexer.lex();
            }
        } catcj (IOException e) {
            System.out.println("Error while lexing: " + e);
        }
    }
}