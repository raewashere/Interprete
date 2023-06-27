package mx.ipn.escom.compiladores;

import java.util.ArrayList;
import java.util.List;

public class Arbol {
    private final List<Nodo> raiz;

    public Arbol(Nodo raiz){
        this.raiz = new ArrayList<>();
        this.raiz.add(raiz);
    }

    public Arbol(List<Nodo> raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case PRODUCTO:
                case DIVISION:
                case MODULO:    
                case SUMA:
                case RESTA:               
                    SolverAritmetico solver = new SolverAritmetico(n);
                    Object resAritmetico = solver.resolver();
                    System.out.println(resAritmetico);
                break;
                //Comparadores
                case MENOR_QUE:
                case MAYOR_QUE:
                case MENOR_IGUAL_QUE:
                case MAYOR_IGUAL_QUE:
                case IGUALDAD:
                case DISTINTO:
                    SolverComparador comparador = new SolverComparador(n);
                    Object resComparador = comparador.resolver();
                    System.out.println(resComparador);
                break;
                //Operadores lógicos
                case Y:
                case O:
                case NEGACION: 
                    SolverLogico logico = new SolverLogico(n);
                    Object resLogico = logico.resolver();
                    System.out.println(resLogico);
                break;
            }
        }
    }

}

