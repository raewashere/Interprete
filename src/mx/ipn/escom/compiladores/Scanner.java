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
    private static final Map<String, TipoToken> operadores;
    private static final Map<String, TipoToken> comparadores;
    private static final Map<String, TipoToken> agrupadores;
    private static final Map<String, TipoToken> comentarios;
    private static final Map<String, TipoToken> cadenas;
    static {
        
        //Declaración de palabras reservadas
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
        palabrasReservadas.put(",", TipoToken.COMA);
        palabrasReservadas.put(".", TipoToken.PUNTO);
        palabrasReservadas.put(";", TipoToken.FIN_ORDEN);
        
        //Declaracion de agrupadores
        agrupadores = new HashMap<>();
        agrupadores.put("(", TipoToken.ABRE_PARENTESIS);
        agrupadores.put(")", TipoToken.CIERRA_PARENTESIS);
        agrupadores.put("{", TipoToken.ABRE_LLAVE);
        agrupadores.put("}", TipoToken.CIERRA_LLAVE);
        agrupadores.put("[", TipoToken.ABRE_CORCHETE);
        agrupadores.put("]", TipoToken.CIERRA_CORCHETE);
        
        //Declaración de operadores
        operadores = new HashMap<>();
        operadores.put("-", TipoToken.RESTA);        
        operadores.put("+", TipoToken.SUMA);
        operadores.put("*", TipoToken.PRODUCTO);
        operadores.put("/", TipoToken.DIVISION);
        operadores.put("%", TipoToken.MODULO);
        operadores.put("!", TipoToken.NEGACION);        
        operadores.put("=", TipoToken.ASIGNACION);
        
        //Declaración de comparadores
        comparadores = new HashMap<>();
        comparadores.put("!=", TipoToken.DISTINTO);
        comparadores.put("==", TipoToken.IGUALDAD);        
        comparadores.put("<", TipoToken.MENOR_QUE);
        comparadores.put("<=", TipoToken.MENOR_IGUAL_QUE);
        comparadores.put(">", TipoToken.MAYOR_QUE);
        comparadores.put(">=", TipoToken.MAYOR_IGUAL_QUE);

        //Declaración de comentarios
        comentarios = new HashMap<>();
        comentarios.put("//", TipoToken.COMENTARIO);
        comentarios.put("/*", TipoToken.ABRE_BLOQUE_COMENTARIO);
        comentarios.put("*/", TipoToken.CIERRA_BLOQUE_COMENTARIO);
        
        //Declaracion de cadenas
        cadenas = new HashMap<>();
        cadenas.put("\"", TipoToken.COMENTARIO);
                
    }

    Scanner(String source){
        /*AGREGAR OPCION DE LECTURA DE ARCHIVO
        SI INTRODUCE COMANDO leerArchivo <ruta> buscar archivo y convertirlo en SOURCE*/
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
            
           /*EVALUA NUMEROS ENTEROS*/
            if (Character.isDigit(siguiente)) {
                int v = 0;
                boolean esDecimal = false;
                double factorDecimal = 1.0;

                do {
                    if (siguiente == '.') {
                        if (esDecimal) {
                            // Error: más de un punto decimal encontrado
                            // Maneja el error según tus necesidades
                        }
                        esDecimal = true;
                    } else {
                        if (esDecimal) {
                            factorDecimal *= 0.1;
                        }
                        v = v * 10 + Character.digit(siguiente, 10);
                    }

                    i++;
                    if (i < this.source.length())
                        siguiente = this.source.charAt(i);
                } while ((Character.isDigit(siguiente) || siguiente == '.') && i < this.source.length() && !((siguiente == ' ') || (siguiente == '\t') || (siguiente == '\n')));

                double numero = v * factorDecimal;

                tokens.add(new Numero(TipoToken.NUMERO, String.valueOf(numero), null, linea));
            }

            
            /*EVALUA PALABRAS RESERVADAS, IDENTIFICADORES O CADENAS*/
            if(Character.isLetterOrDigit(siguiente))
            {
                //BANDERA QUE EVALUA SI EL PRIMERO NO ES UN NUMERO
                boolean bandera = false;
                StringBuffer buffer = new StringBuffer();
                do{            
                    if(!bandera && Character.isLetter(siguiente))
                    {
                       buffer.append(siguiente);
                        i++;
                        if (i < this.source.length())
                            siguiente = this.source.charAt(i); 
                        bandera = true;
                    }
                    else if(bandera && Character.isLetterOrDigit(siguiente)){
                        buffer.append(siguiente);
                        i++;
                        if (i < this.source.length())
                            siguiente = this.source.charAt(i); 
                    }                                                    
                }while(bandera && Character.isLetterOrDigit(siguiente)&& i < this.source.length() && !((siguiente == ' ') || (siguiente == '\t') ||(siguiente == '\n') ));
                
                String cadena = buffer.toString();
                if(this.palabrasReservadas.containsKey(cadena))
                {
                    tokens.add(new Token(this.palabrasReservadas.get(cadena), "", null, linea));
                }else
                {
                    tokens.add(new Identificador(TipoToken.IDENTIFICADOR,cadena,null,linea));
                }
            } else if (siguiente == '"') { // Verificar si es una cadena
                StringBuffer buffer = new StringBuffer();
                do {
                    buffer.append(siguiente);
                    i++;
                    if (i < this.source.length())
                        siguiente = this.source.charAt(i);
                } while (siguiente != '"' && i < this.source.length());

                if (siguiente == '"') {
                    buffer.append(siguiente);
                    String cadena = buffer.toString();
                    tokens.add(new Cadena(TipoToken.CADENA, cadena, null, linea));
                }
                
                /* DESCOMENTAR EN CASO DE QUERER VALIDAR SI CIERRA DESDE EL ANALISIS LEXICO
                else{
                    System.out.println("NO CERRO LA CADENA");
                }*/
            }
            
            /*EVALUACION DE BLOQUE DE COMENTARIOS*/
            /*if(siguiente== '/' || siguiente== '*' || siguiente == '/')
            {
                StringBuffer buffer = new StringBuffer();
                while(siguiente== '*' || siguiente == '/' && i < this.source.length()){
                    buffer.append(siguiente);
                    i++;
                    if (i < this.source.length())
                        siguiente = this.source.charAt(i);                    
                }while(Character.isLetter(siguiente)&& i < this.source.length() && !((siguiente == ' ') || (siguiente == '\t') ||(siguiente == '\n') ));
                
                String cadena = buffer.toString();
                if(this.comentarios.containsKey(cadena))
                {
                    tokens.add(new Token(this.comentarios.get(cadena), "", null, linea));
                }
            }*/
            
            if (siguiente == '/' && i + 1 < this.source.length() && this.source.charAt(i + 1) == '/') {

                StringBuffer buffer = new StringBuffer();
                // Ignorar el resto de la línea actual
                while (i < this.source.length() && this.source.charAt(i) != '\n') {
                    buffer.append(siguiente);
                    i++;                 
                    if (i < this.source.length())
                        siguiente = this.source.charAt(i);
                }
                String cadena = buffer.toString();
                if(this.comentarios.containsKey(cadena))
                {
                    tokens.add(new Token(this.comentarios.get(cadena), "", null, linea));
                }
            }

            
            /*EVALUA COMPARADORES U OPERADORES LÓGICOS*/
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
                if(this.comparadores.containsKey(cadena))
                {
                    tokens.add(new Token(this.comparadores.get(cadena), "", null, linea));
                }
            }
            
            /*EVALUA AGRUPADORES*/
            if(siguiente == '(' || siguiente == ')' || siguiente == '[' || siguiente == ']' || siguiente == '{' || siguiente== '}')
            {
                if(this.agrupadores.containsKey(String.valueOf(siguiente)))
                {
                    tokens.add(new Token(this.agrupadores.get(String.valueOf(siguiente)), String.valueOf(siguiente), null, linea));
                }
            }
            
            /*EVALUA OPERADORES MATEMATICOS*/
            if(siguiente == '+' || siguiente == '-' || siguiente == '*' || siguiente == '/' || siguiente == '%' || siguiente == '=' )
            {
                if(this.operadores.containsKey(String.valueOf(siguiente)))
                {
                    tokens.add(new Token(this.operadores.get(String.valueOf(siguiente)), String.valueOf(siguiente), null, linea));
                }                
            }
                                    
            /*EVALUACION DE FIN DE ORDEN*/
            if(siguiente == ';')
            {
                if(this.palabrasReservadas.containsKey(String.valueOf(siguiente)))
                {
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