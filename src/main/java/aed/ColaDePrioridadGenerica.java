
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
    }

    private ArrayList<Nodo> elementos;
    private Comparator<T> comparador;

    public ColaDePrioridadGenerica(Comparator<T> c) {
        throw new UnsupportedOperationException("Feature incomplete. Contact assistance.");
    }

    /**
     * crea un una cola de prioridad a partir de un array.
     * /* Retorna un array de handles a cada elemento en el orden de arrayBase
     */
    public Handle[] ColaDePrioridad(Comparator<T> c, T[] arrayBase) {
        throw new UnsupportedOperationException("Feature incomplete. Contact assistance.");
    }

    // seteas el elemento en h pero asegurando que no va a cambiar su pos en la cola
    // de Prioridad
    public void setRapido(Handle h, T v) { // O(1)
        throw new UnsupportedOperationException("Feature incomplete. Contact assistance.");
    }

    // seteas el elemento en h.
    public void set(Handle h, T v) { // O(log(n))
        throw new UnsupportedOperationException("Feature incomplete. Contact assistance.");
    }

    public boolean vacia() {
        return this.elementos.size() == 0;
    }

    public Handle encolar(T e) {
        throw new UnsupportedOperationException("Feature incomplete. Contact assistance.");
    }

    public Handle eliminar(Handle h) {
        throw new UnsupportedOperationException("Feature incomplete. Contact assistance.");
    }

    private void swap(int i, int j) { // O(1)
        Nodo nodoI = elementos.get(i); //
        Nodo nodoJ = elementos.get(j); // O(1)
        elementos.set(i, nodoJ); //
        elementos.set(j, nodoI); //
        nodoI.handle.pos = j;
        nodoJ.handle.pos = i;
    }

    public T MasPrioritario() { // O(1)
        return elementos.get(0).v;
    }

    public T desencolarMax() {
        throw new UnsupportedOperationException("Feature incomplete. Contact assistance.");
    }

}
