/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.ipn.escom.compiladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Raymundo Torres Díaz
 */

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;
    
    private char siguiente = ' ';

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("y", TipoToken.Y);
        palabrasReservadas.put("clase", TipoToken.CLASE);
        palabrasReservadas.put("ademas", TipoToken.ADEMAS);
        palabrasReservadas.put("falso", TipoToken.FALSO);
        palabrasReservadas.put("para", TipoToken.PARA);
        palabrasReservadas.put("fun", TipoToken.FUN); //definir funciones
        palabrasReservadas.put("si", TipoToken.SI);
        palabrasReservadas.put("nulo", TipoToken.NULO);
        palabrasReservadas.put("o", TipoToken.O);
        palabrasReservadas.put("imprimir", TipoToken.IMPRIMIR);
        palabrasReservadas.put("retornar", TipoToken.RETORNAR);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("este", TipoToken.ESTE);
        palabrasReservadas.put("verdadero", TipoToken.VERDADERO);
        palabrasReservadas.put("var", TipoToken.VAR); //definir variables
        palabrasReservadas.put("mientras", TipoToken.MIENTRAS);
        
        palabrasReservadas.put("(", TipoToken.ABRE_PARENTESIS);
        palabrasReservadas.put(")", TipoToken.CIERRA_PARENTESIS);
        palabrasReservadas.put("{", TipoToken.ABRE_LLAVE);
        palabrasReservadas.put("}", TipoToken.CIERRA_LLAVE);
        palabrasReservadas.put("[", TipoToken.ABRE_CORCHETE);
        palabrasReservadas.put("]", TipoToken.CIERRA_CORCHETE);
        palabrasReservadas.put(",", TipoToken.COMA);
        palabrasReservadas.put(".", TipoToken.PUNTO);
        palabrasReservadas.put(";", TipoToken.PUNTO_COMA);
        
        palabrasReservadas.put("-", TipoToken.RESTA);        
        palabrasReservadas.put("+", TipoToken.SUMA);
        palabrasReservadas.put("*", TipoToken.PRODUCTO);
        palabrasReservadas.put("/", TipoToken.DIVISION);
        palabrasReservadas.put("%", TipoToken.MODULO);
        palabrasReservadas.put("!", TipoToken.NEGACION);
        palabrasReservadas.put("!=", TipoToken.DISTINTO);
        palabrasReservadas.put("=", TipoToken.ASIGNACION);
        palabrasReservadas.put("==", TipoToken.IGUALDAD);
        
        palabrasReservadas.put("<", TipoToken.MENOR_QUE);
        palabrasReservadas.put("<=", TipoToken.MENOR_IGUAL_QUE);
        palabrasReservadas.put(">", TipoToken.MAYOR_QUE);
        palabrasReservadas.put(">=", TipoToken.MAYOR_IGUAL_QUE);        
        
    }

    Scanner(String source){
        this.source = source;
    }

    List<Token> scanTokens() throws IOException{
        //Aquí va el corazón del scanner.

        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */
        int i = 0;
        
        for(i = 0; i<this.source.length(); i++){
            
            siguiente = source.charAt(i);
            /*OMITIR ESPACIOS Y TABULADORES, CUENTA LINEAS*/
            if((siguiente == ' ') || (siguiente == '\t'))
            {
                    continue;
            }
            else
            {
                if(siguiente == '\n')
                {
                    linea = linea + 1;
                    break;
                }
            }
            /*VALIDAR QUE NO SEA UN NUMERO SOLO*/
           
            if(Character.isDigit(siguiente))
            {
                int v = 0;
                do{
                    v = v * 10 + Character.digit(siguiente, 10);
                    i++;
                    if (i < this.source.length())
                        siguiente = this.source.charAt(i);
                }while(Character.isDigit(siguiente) && i < this.source.length() && !((siguiente == ' ') || (siguiente == '\t') ||(siguiente == '\n') ));
                tokens.add(new Numero(TipoToken.NUMERO,String.valueOf(v),null,linea));                                       
            }
            
            /*EVALUA PALABRAS RESERVADAS o IDENTIFICADORES*/
            if(Character.isLetter(siguiente))
            {
                StringBuffer buffer = new StringBuffer();
                do{
                    buffer.append(siguiente);
                    i++;
                    if (i < this.source.length())
                        siguiente = this.source.charAt(i);                    
                }while(Character.isLetter(siguiente)&& i < this.source.length() && !((siguiente == ' ') || (siguiente == '\t') ||(siguiente == '\n') ));
                
                String cadena = buffer.toString();
                if(this.palabrasReservadas.containsKey(cadena))
                {
                    tokens.add(new Token(this.palabrasReservadas.get(cadena), "", null, linea));
                }else
                {
                    tokens.add(new Identificador(TipoToken.IDENTIFICADOR,cadena,null,linea));
                }
            }
            
            if(siguiente== '!' || siguiente == '=' || siguiente == '<' || siguiente == '>')
            {
                StringBuffer buffer = new StringBuffer();
                while(siguiente== '!' || siguiente == '=' || siguiente == '<' || siguiente == '>' && i < this.source.length()){
                    buffer.append(siguiente);
                    i++;
                    if (i < this.source.length())
                        siguiente = this.source.charAt(i);                    
                }while(Character.isLetter(siguiente)&& i < this.source.length() && !((siguiente == ' ') || (siguiente == '\t') ||(siguiente == '\n') ));
                
                String cadena = buffer.toString();
                if(this.palabrasReservadas.containsKey(cadena))
                {
                    tokens.add(new Token(this.palabrasReservadas.get(cadena), "", null, linea));
                }
            }
            
            if(siguiente == '(' || siguiente == ')' || siguiente == '[' || siguiente == ']' || siguiente == '{' || siguiente== '}'
                    || siguiente == '+' || siguiente == '-' || siguiente == '*' || siguiente == '/'
                    || siguiente == '%' )
            {
                if(this.palabrasReservadas.containsKey(String.valueOf(siguiente)))
                {
                    //System.out.println("LLAVE " + siguiente);
                    tokens.add(new Token(this.palabrasReservadas.get(String.valueOf(siguiente)), String.valueOf(siguiente), null, linea));
                }
            }

            
        
        }                    
        tokens.add(new Token(TipoToken.EOF, "", null, linea));
        
        return tokens;
    }
}

/*
Signos o símbolos del lenguaje:
(
)
{
}
,
.
;
-
+
*
/
!
!=
=
==
<
<=
>
>=
// -> comentarios (no se genera token)
/* ... * / -> comentarios (no se genera token)
Identificador,
Cadena
Numero
Cada palabra reservada tiene su nombre de token

 */