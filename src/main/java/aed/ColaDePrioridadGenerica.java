
package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class ColaDePrioridadGenerica<T> {

    /** referencia opaca a un elemento en esta cola de prioridad */
    public class Handle {
        private int pos;
    }

    private class Nodo {
        T v;
        Handle handle;

        private Nodo(T v, ArrayList<Nodo> elementos) {
            this.handle = new Handle();
            this.handle.pos = elementos.size();
            this.v = v;
        }

        public String toString() {
            return "{ " + this.v + " ; " + this.handle.pos + " }";
        }
    }

    private ArrayList<Nodo> elementos;
    private Comparator<T> comparador;

    public ColaDePrioridadGenerica(Comparator<T> c) {
        this.elementos = new ArrayList<Nodo>();
        this.comparador = c;
    }

    /**
     * crea un una cola de prioridad a partir de un array.
     * /* Retorna un array de handles a cada elemento en el orden de arrayBase
     */

    public ColaDePrioridadGenerica(Comparator<T> c, T[] arrayBase) {
        this.comparador = c;
        this.elementos = new ArrayList<Nodo>();
        for (int i = 0; i < arrayBase.length; i++) { // O(n)
            Nodo nuevoNodo = new Nodo(arrayBase[i], this.elementos); // O(1)
            this.elementos.add(nuevoNodo);
        }
        for (int i = this.elementos.size() - 1; i >= 0; i--) { // O(n) algoritmo de Floyd
            heapifearDown(i);
        }

        assert (esMaxHeap());
    }

    public ArrayList<Handle> verHandles() { // O(n)
        ArrayList<Handle> res = new ArrayList<Handle>();
        for (Nodo n : this.elementos) { // O(n)
            res.add(n.handle); // O(1)
        }
        return res;
    }

    private boolean esMaxHeap() {
        for (int padre = 0; padre < this.elementos.size() / 2; padre++) {
            int hijoDer = 2 * padre + 2;
            int hijoIzq = 2 * padre + 1;

            if (hijoDer < this.elementos.size()
                    && this.comparador.compare(this.elementos.get(padre).v, this.elementos.get(hijoDer).v) < 0) {
                return false;
            }
            if (hijoIzq < this.elementos.size()
                    && this.comparador.compare(this.elementos.get(padre).v, this.elementos.get(hijoIzq).v) < 0) {
                return false;
            }
        }
        return true;
    }

    // seteas el elemento en h pero asegurando que no va a cambiar su pos en la cola
    // de Prioridad
    public void setRapido(Handle h, T v) { // O(1)
        this.elementos.get(h.pos).v = v;
    }

    // seteas el elemento en h.
    public void set(Handle h, T v) { // O(log(n))
        this.elementos.get(h.pos).v = v;

        heapifearUno(h.pos);

    }

    private void heapifearUno(int i) { // O(log(n))
        Nodo nuestroNodo = this.elementos.get(i); // O(1)
        boolean tienePadre = i > 0; // O(1)
        if (tienePadre && this.comparador.compare(nuestroNodo.v, this.elementos.get((i - 1) / 2).v) > 0) {
            while (tienePadre) { // O(log(n))-> A lo sumo recorre toda la altura del heap, que es log(n)
                int padre = (i - 1) / 2;
                i = siftUp(i, padre);
                tienePadre = i > 0;
            }
        } else {
            heapifearDown(i); // O(log(n))-> heapifearDown es O(log(n))
        }
    }

    public boolean vacia() { // O(1)
        return this.elementos.size() == 0; // O(1)
    }

    public Handle encolar(T e) {

        Nodo nuevoNodo = new Nodo(e, this.elementos); // O(1)
        this.elementos.add(nuevoNodo); // O(1)

        int i = this.elementos.size() - 1; // O(1)
        boolean tienePadre = i > 0; // O(1)

        while (tienePadre) { // O(log(n))-> El while se ejecuta en peor caso Log(h) veces, h = altura del
                             // heap)
                             // me parece que this comentario esta mal, es lineal con respecto a la altura
            int padre = (i - 1) / 2; //
            i = siftUp(i, padre); // O(1)->siftUp es O(1)
            tienePadre = i > 0; //
        }
        assert (esMaxHeap());
        return nuevoNodo.handle;
    }

    private int siftUp(int i, int padre) { // O(1)

        T valorI = this.elementos.get(i).v; //
        T valorPadre = this.elementos.get(padre).v; //
        if (this.comparador.compare(valorI, valorPadre) > 0) { // O(1) ->comparar y swap son O(1)
            swap(i, padre); //
            return padre; //
        }

        return -1;
    }

    private void swap(int i, int j) { // O(1)
        Nodo nodoI = elementos.get(i); // O(1)
        Nodo nodoJ = elementos.get(j); // O(1)
        elementos.set(i, nodoJ); // O(1)
        elementos.set(j, nodoI); // O(1)
        nodoI.handle.pos = j; // O(1)
        nodoJ.handle.pos = i; // O(1)
    }

    public T MasPrioritario() { // O(1)
        return elementos.get(0).v; // O(1)
    }

    public T desencolarMax() {
        T res = this.elementos.get(0).v; // O(1)

        swap(0, this.elementos.size() - 1); // O(1)
        this.elementos.remove(this.elementos.size() - 1); // O(1)
        heapifearDown(0); // O(log(n))-> heapifearDown es O(1)

        assert (esMaxHeap());
        return res;
    }

    public T eliminar(Handle h) {
        T res = this.elementos.get(h.pos).v; // O(1)

        swap(h.pos, this.elementos.size() - 1); // O(1)
        this.elementos.remove(this.elementos.size() - 1); // O(1)
        heapifearDown(h.pos); // O(log(n))

        assert (esMaxHeap());
        return res;
    }

    private void heapifearDown(int i) { // O(log(n))
        boolean tieneDosHijos = (2 * i + 2) < this.elementos.size(); // O(1)
        boolean tieneUnHijo = (2 * i + 1) < this.elementos.size(); // O(1)

        while (tieneDosHijos || tieneUnHijo) { // O(n)
            if (!tieneDosHijos) { // O(1)
                int hijo = 2 * i + 1; // O(1)
                i = siftDownUnHijo(i, hijo); // O(1)
            } else {
                int hijoIzq = 2 * i + 1; // O(1)
                int hijoDer = 2 * i + 2; // O(1)
                i = siftDownDosHijos(i, hijoIzq, hijoDer); // O(1)
            }
            tieneDosHijos = (2 * i + 2) < this.elementos.size(); // O(1)
            tieneUnHijo = (2 * i + 1) < this.elementos.size(); // O(1)

        }
    }

    private int siftDownUnHijo(int i, int hijo) { // O(1)-> asignaciones, comparaciones y operaciones, sin ciclos, tiene
                                                  // una complejidad constante
        T valorI = this.elementos.get(i).v; // O(1)
        T valorHijo = this.elementos.get(hijo).v; // O(1)

        if (this.comparador.compare(valorI, valorHijo) < 0) { // O(1)
            swap(i, hijo); // O(1)
            return hijo;
        }
        return this.elementos.size(); // O(1)
    }

    private int siftDownDosHijos(int i, int hijoIzq, int hijoDer) { // O(1) -> asignaciones, comparaciones y
                                                                    // operaciones, sin ciclos, tiene una complejidad
                                                                    // constante
        T valorI = this.elementos.get(i).v; // O(1)
        T valorHijoDer = this.elementos.get(hijoDer).v; // O(1)
        T valorHijoIzq = this.elementos.get(hijoIzq).v; // O(1)

        if ((this.comparador.compare(valorI, valorHijoIzq) < 0) // O(1)
                || (this.comparador.compare(valorI, valorHijoDer) < 0)) { // O(1)
            if (this.comparador.compare(valorHijoIzq, valorHijoDer) >= 0) { // O(1)
                swap(i, hijoIzq); // O(1)-> swap es O(1)
                return hijoIzq;
            } else {
                swap(i, hijoDer); // O(1)
                return hijoDer;
            }
        }

        return this.elementos.size(); // O(1)
    }

    public String toString() { // O(1)
        return this.elementos.toString(); // O(1)
    }
}
