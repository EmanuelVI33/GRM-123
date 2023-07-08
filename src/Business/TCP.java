package Business;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Emanuel
 */
public class TCP {
    public static final String MAIN = "main";
    public static final String VOID = "void";
    public static final String IF = "if";
    public static final String ELSE = "else";
    public static final String FOR = "for";
    public static final String TO = "to";
    public static final String WHILE = "while";
    public static final String REPEAT = "repeat";
    public static final String UNTIL = "until";
    public static final String READLN = "readln";
    public static final String PRINT = "print";
    public static final String PRINTLN = "println";
    public static final String NOT = "not";
    public static final String AND = "and";
    public static final String OR = "or";
    public static final String FALSE = "false";
    public static final String TRUE = "true";
    public static final String MOD = "mod";
    public static final String DIV = "div";
    public static final String RETURN = "return";
    public static final String BOOLEAN = "boolean";
    public static final String INTEGER = "int";
    public static final String STRING = "string";
    
    public  Map<String, Token> tcp;
    
    public TCP() {
        tcp = new HashMap<>();
        
        // Nombre token
        tcp.put(MAIN, new Token(Token.MAIN));
        tcp.put(VOID, new Token(Token.VOID));
        tcp.put(IF, new Token(Token.IF));
        tcp.put(ELSE, new Token(Token.ELSE));
        tcp.put(FOR, new Token(Token.FOR));
        tcp.put(TO, new Token(Token.TO));
        tcp.put(WHILE, new Token(Token.WHILE));
        tcp.put(REPEAT, new Token(Token.REPEAT));
        tcp.put(UNTIL, new Token(Token.UNTIL));
        tcp.put(READLN, new Token(Token.READLN));
        tcp.put(PRINT, new Token(Token.PRINT));
        tcp.put(PRINTLN, new Token(Token.PRINTLN));
        tcp.put(NOT, new Token(Token.NOT));
        tcp.put(AND, new Token(Token.AND));
        tcp.put(OR, new Token(Token.OR));
        tcp.put(FALSE, new Token(Token.FALSE));
        tcp.put(TRUE, new Token(Token.TRUE));
        tcp.put(MOD, new Token(Token.MOD));
        tcp.put(DIV, new Token(Token.DIV));
        tcp.put(RETURN, new Token(Token.RETURN));
        
        // TIPO DE DATOS
        tcp.put(BOOLEAN, new Token(Token.TIPO, Token.BOOLEAN));
        tcp.put(INTEGER, new Token(Token.TIPO, Token.INT));
        tcp.put(STRING, new Token(Token.TIPO, Token.STRING));
    }
    
    
}
