package main;

//import sun.tools.java.Type;

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
        System.out.println("Matched: "+ type);
        advance();
    }
    private void matchNoAdvance(Types type) throws IOException{
        if(!(check(type))) {
            if(type == Types.MODULO) {
                throw new IOException("Expected an operator or function but found " + currentLexeme);
            }
            throw new IOException("Expected "+ type+ " but found " + currentLexeme);
        }
    }
    private void advance() throws IOException {
        currentLexeme = lexer.lex();
    }

    // TOP LEVEL -- STATEMENT STRUCTURE
    private void optStatementList() throws IOException {
        System.out.println("optStatementList");
        if(statementListPending()) {
            statementList();
        }
    }
    private void statementList() throws IOException {
        System.out.println("statementList");
        statement();
        if(statementListPending()) {
            statementList();
        }
    }
    private void statement() throws IOException {
        System.out.println("statement");
        if (declarationPending()) {
            declaration();
        }
        else if(ifStatementPending()) {
            ifStatement();
        }
        else if(whileLoopPending()){
            whileLoop();
        }
        else {
            functionCall();
        }
    }

    private boolean statementListPending()  {
        return statementPending() ;
    }
    private boolean statementPending()  {
        return declarationPending() || ifStatementPending() || whileLoopPending() || functionCallPending();
    }

    // DECLARATION STRUCTURE
    private void declaration() throws IOException {
        System.out.println("declaration");
        match(Types.HASHTAG);
        match(Types.KEY);
        if(check(Types.TILDE)) {
            match(Types.TILDE);
        }
        else {
            if(containedParamListPending()) {
                containedParamList();
            }
            unary();

        }
    }
    private void containedParamList() throws IOException {
        System.out.println("containedParamList");
        match(Types.OBRACKET);
        paramList();
        match(Types.CBRACKET);
    }
    private void paramList() throws IOException {
        System.out.println("paramList");
        unary();
        if(unaryPending()) {
            paramList();
        }
    }

    private boolean declarationPending() {
        return check(Types.HASHTAG);
    }
    private boolean containedParamListPending()  {
        return check(Types.OBRACKET);
    }

    // UNARY STRUCTURE
    private void unary() throws IOException {
        System.out.println("unary");
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
        else if(expressionPending()) {
            expression();
        }
        else if(boolStatementPending()) {
            boolStatement();
        }
        else{
            match(Types.OPAREN);
            unary();
            match(Types.CPAREN);
        }
    }
    private void expression() throws IOException {
        System.out.println("expression");
        operator();
        if(containedParamListPending()) {
            containedParamList();
        }
    }
    private void operator() throws IOException {
        System.out.println("operator");
        if(check(Types.KEY)) {
            match(Types.KEY);
        }
        else if(check(Types.PLUS)){
            match(Types.PLUS);
        }
        else if(check(Types.MINUS)){
            match(Types.MINUS);
        }
        else if(check(Types.TIMES)){
            match(Types.TIMES);
        }
        else if(check(Types.DIVIDE)){
            match(Types.DIVIDE);
        }
        else {
            match(Types.MODULO);
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
    private boolean expressionPending()  {
        return check(Types.KEY) || check(Types.PLUS) || check(Types.MINUS) || check(Types.TIMES) || check(Types.DIVIDE) || check(Types.MODULO);
    }

    // FUNCTION CALL
    private void functionCall() throws IOException {
        System.out.println("functionCall");
        match(Types.AT);
        match(Types.KEY);
        if(containedParamListPending()){
            containedParamList();
        }
    }
    private boolean functionCallPending()  {
        return check(Types.AT);
    }

    // IF & WHILE
    private void ifStatement() throws IOException {
        System.out.println("ifStatement");
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
    private void whileLoop() throws IOException {
        System.out.println("whileLoop");
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
    private void boolStatement() throws IOException {
        System.out.println("boolStatement");
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
        else if (check(Types.LESSTHAN)) {
            match(Types.LESSTHAN);
        }

        if (!(check(Types.BOOL))){
            boolStatement();
            boolStatement();
        }
        else {
            match(Types.BOOL);
        }
    }

    private boolean ifStatementPending()  {
        return check(Types.IF);

    }
    private boolean whileLoopPending()  {
        return check(Types.WHILE);
    }
    private boolean boolStatementPending()  {
        return check(Types.EQUALS) || check(Types.GREATERTHAN) || check(Types.LESSTHAN) || check(Types.NOT) || check(Types.BOOL);
    }
}
