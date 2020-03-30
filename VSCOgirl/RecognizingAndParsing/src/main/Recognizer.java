package main;

import java.io.IOException;

public class Recognizer {
    private String inputfilepath;
    private Lexer lexer;
    private Lexeme currentLexeme;

    Recognizer(String inputfilepath){
        this.inputfilepath = inputfilepath;
        lexer = new Lexer(inputfilepath);
    }

    void recognize(){
        try{
            System.out.println("Parsing" + inputfilepath + "...");
            currentLexeme = lexer.lex();
            //program();
            System.out.println(inputfilepath + "is syntactically valid.");

        }
        catch(IOException e) {
            System.out.println("Syntax error " + e);
        }
    }

    // CORE RECOGNIZER FUNCTIONS
    private boolean check(Types type) {
        return currentLexeme.getType() == type;
    }
    private void match(Types type) throws IOException {
        matchNoAdvance(type);
        advance();
    }
    private void matchNoAdvance(Types type) throws IOException{
        if(!(check(type))) {
            throw new IOException("Syntax Error");
        }
    }
    private void advance() throws IOException {
        currentLexeme = lexer.lex();
    }

    // MATCHING FUNCTIONS

    // PENDING FUNCTIONS
}
