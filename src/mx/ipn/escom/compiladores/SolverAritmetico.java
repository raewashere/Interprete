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
public class SolverAritmetico {

    private final Nodo nodo;
    private TablaSimbolos tabla;
    public SolverAritmetico(Nodo nodo, TablaSimbolos tabla) {
        this.nodo = nodo;
        this.tabla = tabla;
    }

    public Object resolver(){
        return resolver(nodo);
    }
    private Object resolver(Nodo n){
        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            if(n.getValue().tipo == TipoToken.NUMERO || n.getValue().tipo == TipoToken.CADENA || n.getValue().tipo == TipoToken.VERDADERO || n.getValue().tipo == TipoToken.FALSO){
                return n.getValue().literal;
            }
            //REVISAR ESTE CASO
            else if(n.getValue().tipo == TipoToken.IDENTIFICADOR){
                // Ver la tabla de símbolos
                if(this.tabla.existeIdentificador(n.getValue().lexema))
                {
                    return this.tabla.obtener(n.getValue().lexema);
                }
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
                case SUMA:
                    return ((Double)resultadoIzquierdo + (Double) resultadoDerecho);
                case RESTA:
                    return ((Double)resultadoIzquierdo - (Double) resultadoDerecho);
                case PRODUCTO:
                    return ((Double)resultadoIzquierdo * (Double) resultadoDerecho);
                case DIVISION:
                    return ((Double)resultadoIzquierdo / (Double) resultadoDerecho);
                case MODULO:
                    return ((Double)resultadoIzquierdo % (Double) resultadoDerecho);
                case MENOR_QUE:
                    return ((Double)resultadoIzquierdo < (Double) resultadoDerecho);
                case MAYOR_QUE:
                    return ((Double)resultadoIzquierdo > (Double) resultadoDerecho);
                case MENOR_IGUAL_QUE:
                    return ((Double)resultadoIzquierdo <= (Double) resultadoDerecho);
                case MAYOR_IGUAL_QUE:
                    return ((Double)resultadoIzquierdo >= (Double) resultadoDerecho);
                case IGUALDAD:
                    return ((Double)resultadoIzquierdo).doubleValue() == ((Double)resultadoDerecho);
                case DISTINTO:
                    return ((Double)resultadoIzquierdo).doubleValue() != ((Double) resultadoDerecho);
            }
        }
        else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
            switch (n.getValue().tipo){
                //CONCATENACION
                case SUMA:
                    return ((String)resultadoIzquierdo + (String) resultadoDerecho);
                //COMPARACION DE CADENAS
                case IGUALDAD:
                    return (resultadoIzquierdo.equals(resultadoDerecho));
                //VALIDACIÓN DE DIFERENCIA
                case DISTINTO:
                    return (resultadoIzquierdo.equals(resultadoDerecho));
                //CONSIDERAR OPERACIONES BOOLEANAS - cambio de instancia
                 case Y:
                    return (obtenerValor(resultadoIzquierdo) && obtenerValor(resultadoDerecho));
                case O:
                    return (obtenerValor(resultadoIzquierdo) || obtenerValor(resultadoDerecho));
                case NEGACION:
                    return (!obtenerValor(resultadoIzquierdo));
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
