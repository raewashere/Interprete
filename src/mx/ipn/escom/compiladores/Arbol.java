package mx.ipn.escom.compiladores;

import java.util.ArrayList;
import java.util.List;

public class Arbol {
    private Nodo raiz;
    private TablaSimbolos tabla = new TablaSimbolos();
    
    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case PRODUCTO:
                case DIVISION:
                case MODULO:    
                case SUMA:
                case RESTA:     
                case ASIGNACION:
                case MENOR_QUE:
                case MAYOR_QUE:
                case MENOR_IGUAL_QUE:
                case MAYOR_IGUAL_QUE:
                case IGUALDAD:
                case DISTINTO:
                case Y:
                case O:
                case NEGACION:
                case VAR:
                case SI:
                case MIENTRAS:
                case PARA:
                    SolverAritmetico solver = new SolverAritmetico(n,tabla);
                    Object resAritmetico = solver.resolver();
                    System.out.println(resAritmetico);
                break;
                 //Comparadores
                /*case MENOR_QUE:
                case MAYOR_QUE:
                case MENOR_IGUAL_QUE:
                case MAYOR_IGUAL_QUE:
                case IGUALDAD:
                case DISTINTO:
                    SolverComparaciones comparaciones = new SolverComparaciones(n);
                    Object resComparaciones = comparaciones.resolver();
                    System.out.println(resComparaciones);
                break;
                //Operadores lógicos
                case Y:
                case O:
                case NEGACION:
                    SolverLogico logico = new SolverLogico(n);
                    Object resLogico = logico.resolver();
                    System.out.println(resLogico);
                break;*/
                
                
                //Reservadas
                /*case VAR:
                case SI:
                case MIENTRAS:
                case PARA:
                    SolverEstructuras estructuras = new SolverEstructuras(n);
                    Object resEstructuras = estructuras.resolver();
                    System.out.println(resEstructuras);
                break;*/
            }
        }
    }

}

