package main;

import java.io.IOException;

public class LexerTest {


    public static void main(String[] args) {
        Lexer lexer = new Lexer("../TestInput/scratchFile.txt");

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