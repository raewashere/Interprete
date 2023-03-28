/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.ipn.escom.compiladores;

/**
 *
 * @author Raymundo Torres Díaz
 */

public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)


    // Palabras clave:
    Y, 
    CLASE,
    ADEMAS,
    FALSO,
    PARA,
    FUN,
    SI,
    NULO,
    O,
    IMPRIMIR,
    RETORNAR,
    SUPER,
    ESTE,
    VERDADERO,
    VAR,
    MIENTRAS,
    ABRE_PARENTESIS,    //(
    CIERRA_PARENTESIS,  //)
    ABRE_LLAVE,         //{
    CIERRA_LLAVE,       //}
    COMA,               //,
    PUNTO,              //.
    PUNTO_COMA,          //;
    RESTA,              //-
    SUMA,               //+
    PRODUCTO,           //*
    DIVISION,           // /
    NEGACION,           //!
    DISTINTO,           //!=
    ASIGNACION,         //=
    IGUALDAD,           //==
    MENOR_QUE,           //<
    MENOR_IGUAL_QUE,      //<=
    MAYOR_QUE,          //>
    MAYOR_IGUAL_QUE,    //>=

    // Final de cadena
    EOF
}