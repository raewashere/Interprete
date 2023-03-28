/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.ipn.escom.compiladores;

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
        palabrasReservadas.put(",", TipoToken.COMA);
        palabrasReservadas.put(".", TipoToken.PUNTO);
        palabrasReservadas.put(";", TipoToken.PUNTO_COMA);
        palabrasReservadas.put("-", TipoToken.RESTA);
        
        palabrasReservadas.put("+", TipoToken.SUMA);
        palabrasReservadas.put("*", TipoToken.PRODUCTO);
        palabrasReservadas.put("/", TipoToken.DIVISION);
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

    List<Token> scanTokens(){
        //Aquí va el corazón del scanner.

        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */
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