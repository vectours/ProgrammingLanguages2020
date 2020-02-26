package LexicalAnalysis.src.main;

import javax.lang.model.util.Types;

// lexer for vsco bracket langugage
// sara ann brackett
// westminster spring 2020

class Lexeme implements Types {
    String type;
    String word;
}

class Lexeme extends Types {
    String type;
    String string;
    int integer;
    double real;
}