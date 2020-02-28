package main;

import java.io.IOException;

public class LexerTest {
    private static final String testInputDirectory = "./src/main/TestInput";

    public static void main(String[] args) {
        Lexer lexer = new Lexer("VSCOgirl/LexicalAnalysis/src/main/Test Input/myTest.txt");

        try {
            Lexeme currentLexeme = lexer.lex();
            while (currentLexeme.getType() != Types.END_OF_INPUT) {
                System.out.println(currentLexeme);
                currentLexeme = lexer.lex();
            }
        } catch (IOException e) {
         System.out.println("Error while lexing: " + e);
        }


    }
}