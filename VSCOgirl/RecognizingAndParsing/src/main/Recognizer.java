package main;

import sun.tools.java.Type;

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
            optStatementList();
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
            throw new IOException("Expected "+ type+ " but found " + currentLexeme);
        }
    }
    private void advance() throws IOException {
        currentLexeme = lexer.lex();
    }

    // MATCHING FUNCTIONS
    private void optStatementList() throws IOException {
        if(statementListPending()) {
            statementList();
        }
    }
    private boolean statementListPending() throws IOException {
        return statementPending() ;
        // NEED TO ADD IF EMPTY CONDITION AND WHAT TO DO
    }
    private boolean statementPending() throws IOException {
        return declarationPending() || ifStatementPending() || whileLoopPending() || functionCallPending();
    }
    private boolean declarationPending() throws IOException {
        return check(Types.HASHTAG) &&  check(Types.KEY)
                && ((optParamListPending() && expressionPending() && check(Types.TILDE))
                        || check(Types.TILDE));
    }
    private boolean ifStatementPending() throws IOException {
        return check(Types.IF)
                && check(Types.OBRACKET)
                && boolStatementPending()
                && check(Types.CBRACKET)
                && check(Types.OBRACE);
                && optStatementListPending()
                && check(Types.CBRACE);

    }
    private boolean whileLoopPending() throws IOException {

    }
    private boolean functionCallPending() throws IOException {

    }
    private void statementList() throws IOException {
        statement();
        if(statementListPending()) {
            statementList();
        }
    }

    private boolean Pending() throws IOException {

    }

    // PENDING FUNCTIONS
}
