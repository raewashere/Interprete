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
public class SolverEstructuras {

    private final Nodo nodo;

    public SolverEstructuras(Nodo nodo) {
        this.nodo = nodo;
    }

    public Object resolver(){
        return resolver(nodo);
    }
    private Object resolver(Nodo n){
        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            if(n.getValue().tipo == TipoToken.NUMERO || n.getValue().tipo == TipoToken.CADENA){
                return n.getValue().literal;
            }
            else if(n.getValue().tipo == TipoToken.IDENTIFICADOR){
                // Ver la tabla de símbolos
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = resolver(izq);
        Object resultadoDerecho = resolver(der);

        if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double){
            switch (n.getValue().tipo){
                //TODOS LOS OPERADORES
                case VAR:
                    return ((Double)resultadoIzquierdo + (Double) resultadoDerecho);
                case RESTA:
                    return ((Double)resultadoIzquierdo - (Double) resultadoDerecho);
                case PRODUCTO:
                    return ((Double)resultadoIzquierdo * (Double) resultadoDerecho);
                case DIVISION:
                    return ((Double)resultadoIzquierdo / (Double) resultadoDerecho);
                case MODULO:
                    return ((Double)resultadoIzquierdo % (Double) resultadoDerecho);
            }
        }
        else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
            switch (n.getValue().tipo){
                //CONCATENACION
                case SUMA:
                    return ((String)resultadoIzquierdo + (String) resultadoDerecho);
                //CONSIDERAR OPERACIONES BOOLEANAS
            }
        }
        else{
            // Error por diferencia de tipos
        }

        return null;
    }
    
    public boolean obtenerValor(Object valor)
    {
        return valor.equals("verdadero");
    }
}
