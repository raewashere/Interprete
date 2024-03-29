/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.ipn.escom.compiladores;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DC
 */
public class TablaSimbolos {
    
    private final Map<String, Object> values = new HashMap<>();

    boolean existeIdentificador(String identificador){
        return values.containsKey(identificador);
    }

    Object obtener(String identificador) {
        if (values.containsKey(identificador)) {
            return values.get(identificador);
        }
        throw new RuntimeException("Variable no definida '" + identificador + "'.");
    }

    void asignar(String identificador, Object valor){
        if(this.existeIdentificador(identificador))
        {
            throw new RuntimeException("Ya está creada la variable '" + identificador + "'.");
        }
        else
        {
            values.put(identificador, valor);
        }        
    }
    
    void cambiaValor(String identificador,Object valor)
    {
        if(this.existeIdentificador(identificador))
        {
            values.replace(identificador, valor) ;
        }
        else
        {
            throw new RuntimeException("Variable no definida '" + identificador + "'.");
        }
    }
}
