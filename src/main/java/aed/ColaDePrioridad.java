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

    public ColaDePrioridad(Comparator<T> c, T[] arrayBase) {    // O(n)  (TODO: FALTA EXPLICAR PQ)

        longitud = arrayBase.length;
        comparador = c;
        elementos = new ArrayList<T>(); 
        for (int i = 0; i < arrayBase.length; i++){
            elementos.add(arrayBase[i]);
        }

        for (int i = longitud - 1; i >= 0; i--) {
            int j = i;
            boolean tieneDosHijos = (2*j + 2) < longitud;                   
            boolean tieneUnHijo = (2*j + 1) < longitud;
            while (tieneUnHijo || tieneDosHijos){
                if (!tieneDosHijos){                
                int hijo = 2*j + 1;
                j = siftDownUnHijo(j,hijo);
            } else {
                int hijoIzq = 2*j + 1;
                int hijoDer = 2*j + 2;
                j = siftDownDosHijos(j,hijoIzq,hijoDer);
            }
            tieneDosHijos = (2*j + 2) < longitud;
            tieneUnHijo = (2*j + 1) < longitud;
            }    
        }
    }

    public boolean vacia() {
        return longitud == 0;
    }

    public void Encolar(T e) {                              // O(log(n))

        elementos.add(e);                                   // 
        longitud += 1;                                      // O(1)
        int i = longitud - 1;                               //
        boolean tienePadre = (i-1)/2 >= 0;                  //     

        while (tienePadre) {                                // (El while se ejecuta en peor caso Log(h) veces, h = altura del heap) 
            int padre = (i-1)/2;                            //
            i = siftUp(i,padre);                            // O(1) (siftUp es O(1)
            tienePadre = (i-1)/2 >= 0;                      //        
        }
    }

    private int siftUp(int i, int padre){                   // O(1)

        T valorI = elementos.get(i);                        // 
        T valorPadre = elementos.get(padre);                //
        if (comparador.compare(valorI,valorPadre) > 0){     // O(1) (comparar y swap son O(1))
            swap(i,padre);                                  //
            return padre;                                   //
        }

        return -1;
    }

    private void swap(int i, int j){        // O(1)

        T valorI = elementos.get(i);        //
        T valorJ = elementos.get(j);        // O(1)
        elementos.set(i,valorJ);            //
        elementos.set(j,valorI);            //
    }

    public T MasPrioritario() {             // O(1)

        return elementos.get(0);
    }

    public T DesencolarMax() {      
        T res = elementos.get(0);                                       // 
        longitud -= 1;                                                  // 
        elementos.set(0,elementos.get(longitud));                       // 
        elementos.remove(longitud);                                     // O(1)
        int i = 0;                                                      // 
        boolean tieneDosHijos = (2*i + 2) < longitud;                   //
        boolean tieneUnHijo = (2*i + 1) < longitud;                     //
        
        while (tieneDosHijos || tieneUnHijo){
            if (!tieneDosHijos){
                int hijo = 2*i + 1;
                i = siftDownUnHijo(i,hijo);
            } else {
                int hijoIzq = 2*i + 1;
                int hijoDer = 2*i + 2;
                i = siftDownDosHijos(i,hijoIzq,hijoDer);
            }
            tieneDosHijos = (2*i + 2) < longitud;
            tieneUnHijo = (2*i + 1) < longitud;
            
        }
        return res;
    }

    private int siftDownUnHijo(int i, int hijo){
        T valorI = elementos.get(i);
        T valorHijo = elementos.get(hijo);

        if(comparador.compare(valorI,valorHijo) < 0){
            swap(i,hijo);
            return hijo;
        }
        return longitud;
    }

    private int siftDownDosHijos(int i, int hijoIzq, int hijoDer) {
        T valorI = elementos.get(i);
        T valorHijoDer = elementos.get(hijoDer);
        T valorHijoIzq = elementos.get(hijoIzq);
        
        if ((comparador.compare(valorI,valorHijoIzq) < 0) || (comparador.compare(valorI,valorHijoDer) < 0)){
            if (comparador.compare(valorHijoIzq,valorHijoDer) >= 0){
                swap(i,hijoIzq);
                return hijoIzq;
            }else {
                swap(i,hijoDer);
                return hijoDer;
            }
        }
           
        return longitud;    
    }

}
