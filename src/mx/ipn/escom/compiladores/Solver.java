package mx.ipn.escom.compiladores;

public class Solver {

    private final Nodo nodo;

    public Solver(Nodo nodo) {
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
                    return ((Double)resultadoIzquierdo).doubleValue() == ((Double)resultadoDerecho).doubleValue();
                case DISTINTO:
                    return ((Double)resultadoIzquierdo).doubleValue() != ((Double) resultadoDerecho).doubleValue();
                case Y:
                    return (obtenerValor(resultadoIzquierdo) && obtenerValor(resultadoDerecho));
                case O:
                    return (obtenerValor(resultadoIzquierdo) || obtenerValor(resultadoDerecho));
                case NEGACION:
                    return (!obtenerValor(resultadoIzquierdo));
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
