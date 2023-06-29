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
            if(n.getValue().tipo == TipoToken.VERDADERO || n.getValue().tipo == TipoToken.FALSO || n.getValue().tipo == TipoToken.LOGICO){
                return n.getValue().literal;
            }
            else if(n.getValue().tipo == TipoToken.IDENTIFICADOR){
                // Ver la tabla de símbolos
            }
        }
        Nodo izq = null;
        Nodo der = null;
        Object resultadoIzquierdo = null;
        Object resultadoDerecho = null;
        if(n.getHijos().size() == 1)
        {
            izq = n.getHijos().get(0);
            resultadoIzquierdo = resolver(izq);
            if(resultadoIzquierdo instanceof String){
                switch (n.getValue().tipo){
                    case NEGACION:
                        return !obtenerValor(resultadoIzquierdo);
                }
            }
            else{
                // Error por diferencia de tipos
            }
        }else{
            izq = n.getHijos().get(0);
            der = n.getHijos().get(1);
            resultadoIzquierdo = resolver(izq);
            resultadoDerecho = resolver(der);                 
            
            if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
                switch (n.getValue().tipo){
                    case Y:
                        return (obtenerValor(resultadoIzquierdo) && obtenerValor(resultadoDerecho));
                    case O:
                        return (obtenerValor(resultadoIzquierdo) || obtenerValor(resultadoDerecho));
                }
            }
            else{
                // Error por diferencia de tipos
            }
        }
        return null;
    }
    
    public boolean obtenerValor(Object valor)
    {
        return valor.equals("verdadero");
    }
}
