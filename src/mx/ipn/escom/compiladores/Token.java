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

public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;
    final int linea;

    public Token(TipoToken tipo, String lexema, Object literal, int linea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.linea = linea;
    }
    
    //SIN LINEA
    public Token(TipoToken tipo, String lexema, Object literal) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.linea = 0;
    }
    
    //Coonstructor para Parser
    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.linea = 0;
        this.literal = null;
    }
    
    public String toString(){
        return tipo + " " + lexema + " " + literal + " en linea " + linea;
    }
    
    //Identificación tipo de token
    public boolean esOperando(){
        switch (this.tipo){
            case IDENTIFICADOR:
            case NUMERO:
            case LOGICO:
            case VERDADERO:
            case FALSO:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperador(){
        switch (this.tipo){
            case PRODUCTO:
            case DIVISION:
            case MODULO:
            case SUMA:
            case RESTA:
            case MENOR_QUE:
            case MAYOR_QUE:
            case MENOR_IGUAL_QUE:
            case MAYOR_IGUAL_QUE:
            case IGUALDAD:
            case DISTINTO:
            case Y:
            case O:
            case NEGACION:
            case ASIGNACION:               
                return true;
            default:
                return false;
        }
    }
    
     public boolean precedenciaMayorIgual(Token t){
        return this.obtenerPrecedencia() >= t.obtenerPrecedencia();
    }

    private int obtenerPrecedencia(){
        switch (this.tipo){
            case PRODUCTO:
            case DIVISION:
            case MODULO:    
                return 5;
            case SUMA:
            case RESTA:
                return 4;
            case MENOR_QUE:
            case MAYOR_QUE:
            case MENOR_IGUAL_QUE:
            case MAYOR_IGUAL_QUE:
            case IGUALDAD:
            case DISTINTO:
                return 3;
            case Y:
            case O:
                return 2;
            case NEGACION:
                return 1;
            case ASIGNACION:
                return 0;
        }

        return 0;
    }

    public int aridad(){
        switch (this.tipo) {
            case PRODUCTO:
            case DIVISION:
            case MODULO:
            case SUMA:
            case RESTA:            
            case IGUALDAD:
            case DISTINTO:
            case MENOR_QUE:
            case MAYOR_QUE:
            case MENOR_IGUAL_QUE:
            case MAYOR_IGUAL_QUE:
            case Y:
            case O:            
                return 2;
            case NEGACION:
            case ASIGNACION:
                return 1;
        }
        return 0;
    }
    
    //Identificación de palabras reservadas
    public boolean esPalabraReservada(){
        switch (this.tipo){
            case CLASE:
            case ADEMAS:
            case FUN:
            case NULO:
            case IMPRIMIR:
            case RETORNAR:
            case SUPER:
            case ESTE:
            case VAR:
            //case FIN_ORDEN:
                return true;
            default:
                return false;
        }
    }
    
    //Identificación de estructuras de control
    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case PARA:
            case SI:
            case SINO:
            case MIENTRAS:
                return true;
            default:
                return false;
        }
    }
    
}
