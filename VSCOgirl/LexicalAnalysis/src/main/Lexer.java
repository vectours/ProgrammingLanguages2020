
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

    lexeme lex() throws IOException { skipWhiteSpace(); char c = (char)
     input.read(); // can also say input.unread(c) and it will put it back if
     (isEndOfInput(c)) { System.out.println("end of input reached."); return new
     lexeme(Types.END_OF_INPUT); }
     
     switch (c) { // single charectar tokens case '@': return new lexeme(AT); case
     '#': return new lexeme(HASHTAG); case '~': return new lexeme(TILDE);
     
     case '(': return new lexeme(OPAREN); case ')': return new lexeme(CPAREN);
     case '{': return new lexeme(OBRACE); case '}': return new lexeme(CBRACE);
     case '[': return new lexeme(OBRACKET); case ']': return new lexeme(CBRACKET);
     
     case '+': return new lexeme(PLUS); case '-': return new lexeme(MINUS); case
     '*': return new lexeme(TIMES); case '/': return new lexeme(DIVIDE); case '%':
     return new lexeme(MODULO);
     
     case '$': return new lexeme(IF); case '&': return new lexeme(WHILE);
     
     default: // numbers, variables & strings if (Character.isDigit(ch)) {
     input.pushback(ch); return lexNumber(); } else if (Character.isLetter(ch)) {
     input.pushback(ch); return lexVariable(); } else if (ch == '\"') { return
     lexString(); } else { return new Lexeme(UNKNOWN, ch); } }

    }

    private Lexeme lexNumber() throws IOException {
        var ch;
        String token = "";
        ch = input.read();
        while (isDigit(ch)) {
            token = token + ch;
            ch = input.read();
        }
        input.pushback(ch);

        return new Lexeme(NUMBER, Integer.parseInt(token));
    }

    private Lexeme lexVariable() throws IOException { // no keywords that aren't
     symbols in my language var ch; String token = ""; ch = input.read(); while
     (isLetter(ch) || isDigit(ch)) { token = token + ch; ch = input.read(); }
     input.pushback(ch);
     
     return new Lexeme(KEY, token); }

    private Lexeme lexString() throws IOException {
        var ch;
        String token = "";
        ch = input.read();
        while (ch != '\"') {
            token = token + ch;
            ch = input.read();
        }
        input.pushback(ch);

        return new Lexeme(STRING, token);
    }

    private void skipWhiteSpace() throws IOException {
        var ch1;
        while (isWhiteSpace(ch)) {
            ch = Input.read();
        }
        Input.pushback(ch);
    }

}
