
package main;

import java.io.*;

class Lexer {
    private PushbackReader input;
    Lexer(String inputFilePath) {
        try {
            System.out.println("Lexing "+ inputFilePath + "...\n");

            File.inputFile = new File(inputFilePath);
            input = new PushbackReader(new FileReader(inputFile));
            } catch (FileNotFoundException e) {
                System.out.println("ERROR: could not find file " + inputFilePath + " for lexing. " + error);
            }

    }
    Lexeme lex() throws IOException {
        skipWhiteSpace();
        char c = (char) input.read();
//can also say input.unread(c) and it will put it back
        if(isEndOfInput(c)) {
            System.out.println("end of input reached.");
            return new Lexeme(Types.END_OF_INPUT);
        }

        switch(c) {
            
        }
    }

    private Lexeme lexNumber() throws IOException {

    }
    private Lexeme lexVariableOrKeyword() throws IOException {

    }
    private Lexeme lexString() throws IOException {

    }
    private void skipWhiteSpace() throws IOException {

    }
}