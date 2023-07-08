package Business;

//import java.util.HashMap;
import java.util.Map;

public class Analex {
    private final Cinta M;
    private final Token R;
    private String ac;
    private int pos;        //Posición de inicio del lexema del preanalisis(), calculado en el dt(). 
                            //Use Cinta.getPos() o sea pos=M.getPos();
    private Map<String, Token> tcp;
    
    public Analex(Cinta c){
        M = c;
        R = new Token();
        init();
        tcp = new TCP().tcp;
    }
    
    public final void init(){
        M.init();
        avanzar();      //Calcular el primer preanalisis.
    }
    
    // Devuelve el token que esta viendo el cabezal
    public Token Preanalisis(){
        return R;
    }
    
    // El lexema del lenguake  for, to, ++, %
    public String lexema(){
        return ac;
    }
    
    public void avanzar(){
        dt();
    }
    
    private boolean espacio(char cc) {
        return (cc == 9 || cc == 32 || cc == Cinta.EOLN);
    } 
    
    public boolean esDigito(char c) {
        return Character.isDigit(c);
    }
    
    public boolean esLetra(char c) {
        return Character.isLetter(c);
    }
    
    private void dt(){
        int estado = 0;
        ac = "";
        pos = M.getPos();

        boolean b = true;
        while (true) {
            char c = M.cc();  // Cabezal
            switch (estado) {
                case 0 -> {
                    if (espacio(c)) {
                        M.avanzar();
                        pos = M.getPos();
                    } else if (c == '+') {
                        M.avanzar();
                        ac += c;
                        estado = 1;
                    } else if (c == '-') {
                        M.avanzar();
                        ac += c;
                        estado = 4;
                    } else if (c == '*') {
                        M.avanzar();
                        ac += c;
                        estado = 7;
                    } else if (c == '/') {
                        M.avanzar();
                        ac += c;
                        estado = 8;
                    }else if (c == '%') {
                        M.avanzar();
                        ac += c;
                        estado = 13;
                    } else if (c == '&') {
                        M.avanzar();
                        ac += c;
                        estado = 14;
                    }else if (c == '|') {
                        M.avanzar();
                        ac += c;
                        estado = 15;
                    } else if (c == '!') {
                        M.avanzar();
                        ac += c;
                        estado = 16;
                    } else if (c == ';') {
                        M.avanzar();
                        ac += c;
                        estado = 19;
                    } else if (c == ':') {
                        M.avanzar();
                        ac += c;
                        estado = 20;
                    } else if (c == '(') {
                        M.avanzar();
                        ac += c;
                        estado = 23;
                    } else if (c == ')') {
                        M.avanzar();
                        ac += c;
                        estado = 24;
                    } else if (c == '{') {
                        M.avanzar();
                        ac += c;
                        estado = 25;
                    } else if (c == '}') {
                        M.avanzar();
                        ac += c;
                        estado = 26;
                    } else if (c == ',') { 
                        M.avanzar();
                        ac += c;
                        estado = 27;
                    } else if (c == '=') {
                        M.avanzar();
                        ac += c;
                        estado = 28;
                    } else if (c == '<') {
                        M.avanzar();
                        ac += c;
                        estado = 29;
                    } else if (c == '>') {
                        M.avanzar();
                        ac += c;
                        estado = 32;
                    } else if (esDigito(c)) {
                        M.avanzar();
                        ac += c;
                        estado = 35;
                    } else if (esLetra(c)) {
                        M.avanzar();
                        ac += c;
                        estado = 37;
                    } else if (c == '"') {
                        M.avanzar();
                        ac += c;
                        estado = 40;
                    } else if (c == Cinta.EOF) {
                        pos -= 1;
                        estado = 100;
                    } else { // Error
                        estado = 99;
                    }
                }
                    
                case 1 -> { 
                    if (c == '+') {
                        M.avanzar();
                        ac += c;
                        estado = 3;
                    } else {    // Otro
                        estado = 2;
                    }
                }
                
                case 2 -> {   // Estado final 
                    R.setNom(Token.MAS); // <INC, _>   '++'
                    return;
                }
                
                case 3 -> {   // Estado final 
                    R.setNom(Token.INC); // <INC, _>   '++'
                    return;
                }
                    
                case 4 -> {     
                    if (c == '-') {
                        M.avanzar();
                        ac += c;
                        estado = 6;
                    } else { // Otro
                        estado = 5;
                    }
                }
                
                case 5 -> {  // Estado final 
                    R.setNom(Token.MENOS);  // <MENOS, _>  '-'
                    return;
                }
                
                case 6 -> {  // Estado final 
                    R.setNom(Token.DEC); // <DEC, _>   '--'
                    return;
                }
                
                case 7 -> {   
                    R.setNom(Token.POR);
                    return;
                }
                
                case 8 -> {   
                    switch (c) {
                        case '/' -> {
                            M.avanzar();
                            ac = "";
                            estado = 10;  // Ingresa a comentario de linea
                        }
                        case '*' -> {
                            M.avanzar();
                            ac = "";
                            estado = 11;   // Ingresa a comentario multilinea
                        }
                        default -> estado = 9;
                    }
                }
                
                case 9 -> {   
                    R.setNom(Token.DIV);
                    return;
                }
                
                case 10 -> {
                    if (c == Cinta.EOF || c == Cinta.EOLN) {
                        estado = 0;
                    } else {
                        pos = M.getPos();
                         M.avanzar();
                    }
                }
                
                case 11 -> {
                    switch (c) {
                        case Cinta.EOF -> {
                            estado = 99;
                    }
                        case '*' -> {
                            pos = M.getPos();
                            M.avanzar();
                            estado = 12;
                    }
                        default -> {
                            pos = M.getPos();
                            M.avanzar();
                        }
                    }
                }
                
                case 12 -> {
                    if (c == Cinta.EOF ) {
                         estado = 99;
                    } if (c == '/') {
                        pos = M.getPos();
                        M.avanzar();
                        estado = 0;
                    } else {
                        pos = M.getPos();
                        M.avanzar();
                        estado = 11;
                    } 
                }
                
                case 13 -> {     
                   R.setNom( Token.MOD);
                   return;
                }
                
                case 14 -> {     
                   R.setNom( Token.AND);
                   return;
                }
                
                case 15 -> {     
                   R.setNom( Token.OR);
                   return;
                }
                
                case 16 -> {     
                   if (c == '=') {
                        M.avanzar();
                        ac += c;
                        estado = 18;
                   } else {
                       estado = 17;
                   }
                }
                
                case 17 -> {     
                   R.setNom( Token.NOT);
                   return;
                }
                
                case 18 -> {     
                   R.set( Token.OPREL, Token.DIS);
                   return;
                }
                
                case 19 -> {     
                   R.setNom(Token.PTOCOMA);
                   return;
                }   
                
                case 20 -> {     
                   if (c == '=') {
                       M.avanzar();
                       ac += c;
                       estado = 22;
                   } else {
                       estado = 21;
                   }
                } 
                
                case 21 -> {     
                   R.setNom(Token.DOSPUNTOS);
                   return;
                }   
                
                case 22 -> {     
                   R.setNom(Token.ASSIGN);
                   return;
                }   
                
                case 23 -> {     
                   R.setNom(Token.PA);
                   return;
                }   
                
                case 24 -> {     
                   R.setNom(Token.PC);
                   return;
                }   
                
                case 25 -> {     
                   R.setNom(Token.LA);
                   return;
                }   
                
                case 26 -> {     
                   R.setNom(Token.LC);
                   return;
                }   
                
                case 27 -> {     
                   R.setNom(Token.COMA);
                   return;
                }  
                
                case 28 -> {     
                   R.set(Token.OPREL, Token.IGUAL);
                   return;
                }  
                
                case 29 -> {     
                   if (c == '=') {
                         M.avanzar();
                         ac += c;
                         estado = 31;
                   } else {
                        estado = 30;
                   }
                }  
                
                case 30 -> {     
                   R.set(Token.OPREL, Token.MEN);
                   return;
                }  
                
                case 31 -> {     
                   R.set(Token.OPREL, Token.MEI);
                   return;
                }  
                
                case 32 -> {     
                   if (c == '=') {
                         M.avanzar();
                         ac += c;
                         estado = 34;
                   } else {
                        estado = 33;
                   }
                }  
                
                case 33 -> {     
                   R.set(Token.OPREL, Token.MAY);
                   return;
                }  
                
                case 34 -> {     
                   R.set(Token.OPREL, Token.MAI);
                   return;
                }  
                
                case 35 -> {     
                   if (esDigito(c)) {
                       M.avanzar();
                       ac += c;
                   } else {
                       estado = 36;
                   }
                }  
                
                case 36 -> {     
                   int x = Integer.parseInt(ac);
                   R.set(Token.NUM, x);
                   return;
                }  
                
                case 37 -> {     
                   if (esLetra(c)) {
                       M.avanzar();
                       ac += c;
                   } else if (esDigito(c)) {  // Es si o si ID
                       M.avanzar();
                       ac += c;
                       estado = 39;
                       b = false;  // Cambiar bandera, para no haccer busqueda de keywords
                   } else {
                       estado = 38;
                   }
                }  
                
                case 38 -> {     
                   if (tcp.containsKey(ac)) {
                       Token t =  tcp.get(ac);
                       R.set(t.getNom(), t.getAtr());
                   } else { // Es ID
                       R.set(Token.ID, -1);
                   }
                   return;
                }  
                
                case 39 -> {
                    if (esDigito(c) || esLetra(c)) {
                        M.avanzar();
                        ac += c;
                    } else {
                        estado = 38; 
                    }
                }
                
                case 40 -> {
                switch (c) {
                    case '"' -> {
                        // "
                        M.avanzar();
                        ac += c;
                        estado = 41;
                    }
                    case Cinta.EOF, Cinta.EOLN -> // Error
                        estado = 99;
                    default -> {
                        M.avanzar();
                        ac += c;
                    }
                }
                }
                
                case 41 -> {
                    R.set(Token.STRINGctte, 0);
                    return;
                }
                   
                case 99 -> {     // Otro
                   R.setNom( Token.ERROR);
                   return;
                }
                
                case 100 -> {     // Fin
                   R.setNom( Token.FIN);
                   return;
                }
                default -> throw new AssertionError();
            }
        }    
    }
    
    public void resaltar(){    //Para resaltar el lexema del Preanalisis en el progFuente.
        comunicate(pos, lexema());
    }
    
    public void comunicate(int pos, String lexema){ //Overridable. Para la Interfaz.
        
    }
}
