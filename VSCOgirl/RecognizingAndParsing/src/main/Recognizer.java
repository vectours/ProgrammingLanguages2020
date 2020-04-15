package main;

//import sun.tools.java.Type;

import java.io.IOException;

public class Recognizer {
    private String inputfilepath;
    private Lexer lexer;
    private Lexeme currentLexeme;
    private String currentNonTerminal;

    Recognizer(String inputfilepath){
        this.inputfilepath = inputfilepath;
        lexer = new Lexer(inputfilepath);
    }

    void recognize(){
        try{
            System.out.println("Parsing" + inputfilepath + "...");
            currentLexeme = lexer.lex();
            optStatementList();
            System.out.println(inputfilepath + " is syntactically valid.");

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
            else if(type == Types.OBRACKET && ((currentNonTerminal == "ifStatement") || currentNonTerminal == "whileLoop")){
                throw new IOException("Expected a condition for an if statement or a while loop but found "+ currentLexeme);
            }
            else if(type == Types.KEY && (currentNonTerminal == "functionCall")) {
                throw new IOException("Found "+ currentLexeme + " while parsing a function call instead of the name of a function");
            }
            else if(type == Types.KEY && (currentNonTerminal == "declaration")) {
                throw new IOException("Found "+ currentLexeme + " while parsing a function or variable declaration instead of the name of the function or variable");
            }
            throw new IOException("Expected "+ type+ " but found " + currentLexeme + " while parsing a " + currentNonTerminal);
        }
    }
    private void advance() throws IOException {
        currentLexeme = lexer.lex();
    }

    // TOP LEVEL -- STATEMENT STRUCTURE
    private void optStatementList() throws IOException {
        currentNonTerminal = "optStatementList";

        if(statementListPending()) {
            statementList();
        }
        System.out.println("Completed an optStatementList.");
    }
    private void statementList() throws IOException {
        currentNonTerminal = "statementList";
        statement();
        if(statementListPending()) {
            statementList();
        }
        System.out.println("Completed a statementList.");
    }
    private void statement() throws IOException {
        currentNonTerminal = "statement";
        if (declarationPending()) {
            declaration();
        }
        else if(ifStatementPending()) {
            ifStatement();
        }
        else if(whileLoopPending()){
            whileLoop();
        }
        else if(functionCallPending()){
            functionCall();
        }
        else {
            unary();
        }
        System.out.println("Completed a statement.");
    }

    private boolean statementListPending()  {
        return statementPending() ;
    }
    private boolean statementPending()  {
        return declarationPending() || ifStatementPending() || whileLoopPending() || functionCallPending() || unaryPending();
    }

    // DECLARATION STRUCTURE
    private void declaration() throws IOException {
        currentNonTerminal = "declaration";
        match(Types.HASHTAG);
        match(Types.KEY);
        if(check(Types.TILDE)) {
            match(Types.TILDE);
        }
        else {
            if(containedParamListPending()) {
                containedParamList();
            }
            statementList();

        }
        System.out.println("Completed a declaration.");
    }
    private void containedParamList() throws IOException {
        currentNonTerminal = "containedParamList";
        match(Types.OBRACKET);
        paramList();
        match(Types.CBRACKET);
        System.out.println("Completed a containedParamList.");
    }
    private void paramList() throws IOException {
        currentNonTerminal = "paramList";
        unary();
        if(unaryPending()) {
            paramList();
        }
        System.out.println("Completed a paramList.");
    }

    private boolean declarationPending() {
        return check(Types.HASHTAG);
    }
    private boolean containedParamListPending()  {
        return check(Types.OBRACKET);
    }

    // UNARY STRUCTURE
    private void unary() throws IOException {
        currentNonTerminal = "unary";
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
        System.out.println("Completed a unary.");
    }
    private void expression() throws IOException {
        currentNonTerminal = "expression";
        operator();
        if(containedParamListPending()) {
            containedParamList();
        }
        System.out.println("Completed an expression.");
    }
    private void operator() throws IOException {
        currentNonTerminal = "operator";
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
        System.out.println("Completed an operator.");
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
        return operatorPending();
    }
    private boolean operatorPending() {
        return check(Types.KEY) || check(Types.PLUS) || check(Types.MINUS) || check(Types.TIMES) || check(Types.DIVIDE) || check(Types.MODULO);
    }

    // FUNCTION CALL
    private void functionCall() throws IOException {
        currentNonTerminal = "functionCall";
        match(Types.AT);
        match(Types.KEY);
        if(containedParamListPending()){
            containedParamList();
        }
        System.out.println("Completed a functionCall.");
    }
    private boolean functionCallPending()  {
        return check(Types.AT);
    }

    // IF & WHILE
    private void ifStatement() throws IOException {
        currentNonTerminal = "ifStatement";

        match(Types.IF);
        match(Types.OBRACKET);
        boolStatement();
        match(Types.CBRACKET);
        match(Types.OBRACE);
        optStatementList();
        match(Types.CBRACE);
        System.out.println("Completed an ifStatement.");
    }
    private void whileLoop() throws IOException {
        currentNonTerminal = "while loop";

        match(Types.WHILE);
        match(Types.OBRACKET);
        boolStatement();
        match(Types.CBRACKET);
        match(Types.OBRACE);
        optStatementList();
        match(Types.CBRACE);
        System.out.println("Completed a whileLoop.");
    }
    private void boolStatement() throws IOException {
        currentNonTerminal = "boolStatement";

        if(check(Types.NOT)) {
            match(Types.NOT);
            boolStatement();
        }
        else if (check(Types.EQUALS)) {
            match(Types.EQUALS);
            unary();
            unary();
        }
        else if (check(Types.GREATERTHAN)) {
            match(Types.GREATERTHAN);
            unary();
            unary();
        }
        else if (check(Types.LESSTHAN)) {
            match(Types.LESSTHAN);
            unary();
            unary();
        }
        else {
            match(Types.BOOL);
        }
        System.out.println("Completed a boolStatement.");
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
