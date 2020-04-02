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
    private boolean statementListPending()  {
        return statementPending() ;
        // NEED TO ADD IF EMPTY CONDITION AND WHAT TO DO
    }
    private boolean statementPending()  {
        return declarationPending() || ifStatementPending() || whileLoopPending() || functionCallPending();
    }
    private boolean declarationPending() {
        return check(Types.HASHTAG);
    }
    private void declaration() throws IOException {
        match(Types.HASHTAG);
        match(Types.KEY);
        if(check(Types.TILDE)) {
            match(Types.TILDE);
        }
        else {
            if(paramListPending()) {
                paramList();
            }
            expression();
            match(Types.TILDE);
        }
    }
    private boolean paramListPending()  {
        return check()
    }
    private boolean ifStatementPending()  {
        return check(Types.IF)
                && check(Types.OBRACKET)
                && boolStatementPending()
                && check(Types.CBRACKET)
                && check(Types.OBRACE);
                && optStatementListPending()
                && check(Types.CBRACE);

    }
    private boolean whileLoopPending()  {

    }
    private boolean functionCallPending()  {

    }
    private void statementList() throws IOException {
        statement();
        if(statementListPending()) {
            statementList();
        }
    }
/*
    private boolean Pending() throws IOException {

    }
    private void function() throws IOException {

    }
*/
    // PENDING FUNCTIONS
}
