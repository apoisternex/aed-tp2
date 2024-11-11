package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class ColaDePrioridad<T> {
    private ArrayList<T> elementos;
    private int longitud;
    private Comparator<T> comparador;
     

    public ColaDePrioridad(Comparator<T> c) {
        elementos = new ArrayList<T>();
        longitud = 0;
        comparador = c;
    }

    public boolean vacia() {
        return longitud == 0;
    }

    public void Encolar(T e) {

        elementos.add(e);
        longitud += 1; 
        
        if(longitud == 1) {

            return;
        }

        int i = longitud - 1;
        
        T padre = elementos.get((i-1)/2);

        while (comparador.compare(e,padre) > 0 && i > 0){
            elementos.set(i,padre); 
            elementos.set((i-1)/2,e);
            i = (i - 1) / 2;
            if (i > 0){
                padre = elementos.get((i-1)/2);
            }
            
        }
    }

    public T MasPrioritario() {
        return elementos.get(0);
    }

    public T DesencolarMax() {      //desencolando 
        T res = elementos.get(0);
        longitud -= 1;
        elementos.set(0,elementos.get(longitud));
        elementos.remove(longitud);
        int i = 0;
        T e = elementos.get(i); 
        if ((2*i+1)<longitud) {
        T  hijoIzq = elementos.get(2*i + 1);
        }    
        if ((2*i+2)<longitud){
        T hijoDer = elementos.get(2*i + 2);
        }

         while ( i < longitud && (comparador.compare(e,hijoDer) < 0 || comparador.compare(e,hijoIzq) < 0) ) {
            if (comparador.compare(hijoDer,hijoIzq) < 0){
                elementos.set(i,hijoDer);
                elementos.set(2*i + 2,e);
                i = 2*i + 2;
            } else {
                elementos.set(i,hijoIzq);
                elementos.set(2*i + 1,e);
                i = 2*i + 1;
            }
            e = elementos.get(i);
            boolean tieneHijoIzq = (2*i + 1) < longitud;
            boolean tieneHijoDer = (2*i + 2) < longitud;
            
            if(tieneHijoIzq) {
            hijoIzq = elementos.get(2*i + 1);
            }
            if(tieneHijoDer) {
            hijoDer = elementos.get(2*i + 2);
            }

            if(!tieneHijoDer && !tieneHijoIzq) {
                i = longitud;
            }
        }
        return res; 
    }

//proc ColaPrioridadVacía()
//proc vacía()
//proc encolar()
//proc desencolarMax()
// proc cambiarPrioridad()



}
