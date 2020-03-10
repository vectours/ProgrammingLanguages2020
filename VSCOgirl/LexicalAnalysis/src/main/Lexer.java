package main;

import java.io.*;

class Lexer {
    private PushbackReader input;

    Lexer(String inputFilePath) {
        try {
            System.out.println("Lexing " + inputFilePath + "...\n");

            File inputFile = new File(inputFilePath);
            input = new PushbackReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: could not find file " + inputFilePath + " for lexing. " + e);
        }

    }

    public Lexeme lex() throws IOException {
        skipWhiteSpace();
        char c = (char) input.read();

        switch (c) { // single charectar tokens
            case '@':
                return new Lexeme(Types.AT);
            case '#':
                return new Lexeme(Types.HASHTAG);
            case '~':
                return new Lexeme(Types.TILDE);

            case '(':
                return new Lexeme(Types.OPAREN);
            case ')':
                return new Lexeme(Types.CPAREN);
            case '{':
                return new Lexeme(Types.OBRACE);
            case '}':
                return new Lexeme(Types.CBRACE);
            case '[':
                return new Lexeme(Types.OBRACKET);
            case ']':
                return new Lexeme(Types.CBRACKET);

            case '+':
                return new Lexeme(Types.PLUS);
            case '-':
                return new Lexeme(Types.MINUS);
            case '*':
                return new Lexeme(Types.TIMES);
            case '/':
                return new Lexeme(Types.DIVIDE);
            case '%':
                return new Lexeme(Types.MODULO);

            case '=':
                return new Lexeme(Types.EQUALS);
            case '>':
                return new Lexeme(Types.GREATERTHAN);
            case '<':
                return new Lexeme(Types.LESSTHAN);

            case '$':
                return new Lexeme(Types.IF);
            case '&':
                return new Lexeme(Types.WHILE);

            default:
                if (Character.isLetter(c)) {
                    input.unread(c);
                    return lexVariable();
                } else if (c == '\"') {
                    return lexString();
                } else if (Character.isDigit(c)){
                    input.unread(c);
                    return lexNumber();
                }
                else if (isEndOfInput(c)){
                    return new Lexeme(Types.END_OF_INPUT);
                }
                else {
                    return new Lexeme(Types.UNKNOWN, c);
                }
        }
    }

    private Lexeme lexNumber() throws IOException {
        char ch = (char) input.read();;
        String token = "";
        while (Character.isDigit(ch)) {
            token = token + ch;
            ch = (char) input.read();
        }
        if (ch == '.') {
            token = token + ch;
            ch = (char) input.read();
            while (Character.isDigit(ch)) {
                token = token + ch;
                ch = (char) input.read();
            }
            return new Lexeme(Types.FNUMBER, Float.parseFloat(token));
        }
        else {
            input.unread(ch);
            return new Lexeme(Types.NUMBER, Integer.parseInt(token));
        }
    }

    private Lexeme lexVariable() throws IOException { // no keywords that aren't symbols in my language var ch;
        String token = "";
        char ch = (char) input.read();
        while
        (Character.isLetter(ch) || Character.isDigit(ch)) {
            token = token + ch;
            ch = (char) input.read();
        }
        input.unread(ch);

        return new Lexeme(Types.KEY, token);
    }

    private Lexeme lexString() throws IOException {
        String token = "";
        char ch = (char) input.read();
        while (ch != '\"') {
            token = token + ch;
            ch = (char) input.read();
        }
        input.unread(ch);

        return new Lexeme(Types.STRING, token);
    }

    private boolean isEndOfInput(char c) {
        int EOF = 65535;
        return c == EOF;
    }

    public void skipWhiteSpace() throws IOException {
        char ch = (char) input.read();
        while (Character.isWhitespace(ch)) {
            ch = (char) input.read();
        }
        input.unread(ch);
    }

}
