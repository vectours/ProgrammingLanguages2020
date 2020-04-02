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
    private void statementList() throws IOException {
        statement();
        if(statementListPending()) {
            statementList();
        }
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
        if(check(Types.OBRACKET)) {

        }
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
    private void ifStatement() throws IOException {
        match(Types.IF);
        match(Types.OBRACKET);
        boolStatement();
        match(Types.CBRACKET);
        match(Types.OBRACE);
        if(statementListPending()) {
            statementList();
        }
        match(Types.CBRACE);
    }

    private boolean whileLoopPending()  {
        return check(Types.WHILE);
    }
    private void whileLoop() throws IOException {
        match(Types.WHILE);
        match(Types.OBRACKET);
        boolStatement();
        match(Types.CBRACKET);
        match(Types.OBRACE);
        if(statementListPending()) {
            statementList();
        }
        match(Types.CBRACE);
    }
    private boolean boolStatementPending()  {
        return check(Types.EQUALS) || check(Types.GREATERTHAN) || check(Types.LESSTHAN) || check(Types.NOT);
    }
    private void boolStatement() throws IOException {
        if(check(Types.NOT)) {
            match(Types.NOT);
            boolStatement();
        }
        else if (check(Types.EQUALS)) {
            match(Types.EQUALS);
        }
        else if (check(Types.GREATERTHAN)) {
            match(Types.GREATERTHAN);
        }
        else if (check(Types.GREATERTHAN)) {
            match(Types.EQUALS);
        }
        unary();
        unary();
    }

    private boolean functionCallPending()  {
        return check(Types.AT);
    }
    private void functionCall() throws IOException {
        match(Types.AT);
        match(Types.KEY);
        if(paramListPending()){
            paramList();
        }
    }
    private boolean unaryPending()  {
        return check(Types.FNUMBER)
                || check(Types.NUMBER)
                || check(Types.KEY)
                || check(Types.MINUS)
                || check(Types.STRING)
                || functionCallPending()
                || expressionPending();
    }
    private void unary() throws IOException {
        if(check(Types.NUMBER)) {
            match(Types.NUMBER);
        }
        else if(check(Types.FNUMBER)) {
            match(Types.FNUMBER);
        }
        else if(check(Types.STRING)) {
            match(Types.STRING);
        }
        else if(check(Types.KEY)) {
            match(Types.KEY);
        }
        else if(check(Types.MINUS)) {
            match(Types.MINUS);
            unary();
        }
        else if(functionCallPending()) {
            functionCall();
        }
        else {
            expression();
        }
    }

/*
    private boolean Pending()  {

    }
    private void function() throws IOException {

    }
*/
    // PENDING FUNCTIONS
}
