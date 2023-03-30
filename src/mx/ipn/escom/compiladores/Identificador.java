/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.ipn.escom.compiladores;

/**
 *
 * @author raypl
 */
public class Identificador extends Token {
    
    public Identificador(TipoToken tipo, String lexema, Object literal, int linea) {
        super(tipo, lexema, literal, linea);
    }
}
