package mx.ipn.escom.compiladores;

public class SolverLogico {

    private final Nodo nodo;

    public SolverLogico(Nodo nodo) {
        this.nodo = nodo;
    }

    public Object resolver(){
        return resolver(nodo);
    }
    private Object resolver(Nodo n){
        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            if(n.getValue().tipo == TipoToken.VERDADERO || n.getValue().tipo == TipoToken.FALSO){
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

        if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
            switch (n.getValue().tipo){
                //COMPARACION DE CADENAS
                case IGUALDAD:
                    return (resultadoIzquierdo.equals(resultadoDerecho));
                //VALIDACIÓN DE DIFERENCIA
                case DISTINTO:
                    return (resultadoIzquierdo.equals(resultadoDerecho));
                //EVALUACIONES LÖGICAS
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
