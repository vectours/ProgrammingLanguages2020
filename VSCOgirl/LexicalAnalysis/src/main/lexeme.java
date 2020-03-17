package main;

//import javax.lang.model.util.Types;

// lexer for vsco bracket langugage
// sara ann brackett
// westminster spring 2020

public class Lexeme {
    Types type;
    // String typeName;
    String string;
    Integer integer;
    Float fnumber;
    boolean bool;

    public Lexeme(Types type) {
        this.type = type;
    }

    public Lexeme(Types type, String newstring) {
        this.type = type;
        string = newstring;
    }

    public Lexeme(Types type, int newint) {
        this.type = type;
        integer = newint;
    }

    public Lexeme(Types type, float fnumber) {
        this.type = type;
        this.fnumber = fnumber;
    }

    public Lexeme(Types type, boolean bool) {
        this.type = type;
        this.bool = bool;
    }

    public Types getType() {
        return type;
    }

    public String toString() {
        String token = "";
        token += type;
        if (string != null) {
            token += ": " + string;
        } else if (integer != null) {
            token += ": " + integer;
        } else if (fnumber != null) {
            token += ": " + fnumber;
        }
        return token;
    }
}