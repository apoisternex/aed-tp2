package aed;

import java.io.SyncFailedException;
import java.lang.reflect.Array;
import java.text.Format;
import java.util.ArrayList;
import java.util.Comparator;

public class DobleColaDePrioridad<T> {
    private class Nodo {
        int posEnOtra;
        T val;

        public Nodo(T val, int posEnOtra) {
            this.val = val;
            this.posEnOtra = posEnOtra;

        }

        public String toString() {
            return "{ " + this.val + " ; " + this.posEnOtra + " }";
        }
    }

    private class VectorComparable {
        ArrayList<Nodo> elementos;
        Comparator<T> comparador;
    }

    private VectorComparable vA;
    private VectorComparable vB;

    private boolean enSync() {
        for (Nodo n : this.vA.elementos) {
            assert (n.posEnOtra < this.vB.elementos.size())
                    : "el nodo " + n + " en vA tiene una posEnOtra fuera de rango. " + this.toString();
            assert (n.val == this.vB.elementos.get(n.posEnOtra).val)
                    : "el nodo " + n + " en vA tiene un val != al del handle en vB" + this.toString();
        }
        for (Nodo n : this.vB.elementos) {
            assert (n.posEnOtra < this.vA.elementos.size())
                    : "el nodo " + n + " en vB tiene una posEnOtra fuera de rango. " + this.toString();
            assert (n.val == this.vA.elementos.get(n.posEnOtra).val)
                    : "el nodo " + n + " en vB tiene un val != al del handle en vA" + this.toString();
        }
        return true;
    }

    public boolean estaVacia() {
        return this.vA.elementos.size() == 0;
    }

    private boolean esMaxHeap(VectorComparable v) {
        for (int padre = 0; padre < v.elementos.size() / 2; padre++) {
            int hijoDer = 2 * padre + 2;
            int hijoIzq = 2 * padre + 1;

            if (hijoDer < v.elementos.size()
                    && v.comparador.compare(v.elementos.get(padre).val, v.elementos.get(hijoDer).val) < 0) {
                return false;
            }
            if (hijoIzq < v.elementos.size()
                    && v.comparador.compare(v.elementos.get(padre).val, v.elementos.get(hijoIzq).val) < 0) {
                return false;
            }
        }
        return true;
    }

    private void checkInvRep() {
        assert (esMaxHeap(vA)) : "vA no es max Heap";
        assert (esMaxHeap(vB)) : "vB no es max Heap";
        assert (enSync()) : "no estan sincronizados";
    }

    public DobleColaDePrioridad(Comparator<T> cA, Comparator<T> cB, T[] arrayBase) { // O(n) (TODO:FALTA EXPLICAR PQ)

        this.vA = new VectorComparable();
        this.vB = new VectorComparable();
        this.vA.elementos = convertirTrasladosANodos(arrayBase);
        this.vB.elementos = convertirTrasladosANodos(arrayBase);
        this.vA.comparador = cA;
        this.vB.comparador = cB;

        algoritmoFloyd(vA, vB);
        algoritmoFloyd(vB, vA);
    }

    private ArrayList<Nodo> convertirTrasladosANodos(T[] traslados) {
        ArrayList<Nodo> res = new ArrayList<Nodo>();
        for (int i = 0; i < traslados.length; i++) {
            Nodo n = new Nodo(traslados[i], i);
            res.add(n);
        }
        return res;
    }

    private void algoritmoFloyd(VectorComparable este, VectorComparable otro) {

        for (int i = este.elementos.size() - 1; i >= 0; i--) {
            heapifearDownUno(este, otro, i);
        }
    }

    // esto es un problema
    public DobleColaDePrioridad(Comparator<T> cA, Comparator<T> cB) {

        this.vA = new VectorComparable();
        this.vB = new VectorComparable();
        this.vA.elementos = new ArrayList<Nodo>();
        this.vB.elementos = new ArrayList<Nodo>();
        this.vA.comparador = cA;
        this.vB.comparador = cB;
    }

    public boolean vacia(ArrayList<T> elementos) {
        return elementos.size() == 0;
    }

    public void encolar(T e) {
        this.vA.elementos.add(new Nodo(e, this.vA.elementos.size()));
        this.vB.elementos.add(new Nodo(e, this.vB.elementos.size()));
        heapifearUno(vA, vB);
        heapifearUno(vB, vA);
        checkInvRep();
    }

    private int heapifearUno(VectorComparable este, VectorComparable otro) { // O(log(n))

        int i = este.elementos.size() - 1; //
        boolean tienePadre = i > 0; //

        while (tienePadre) { // (El while se ejecuta en peor caso Log(h) veces, h = altura del heap)
                             // me parece que este comentario esta mal, es lineal con respecto a la altura
            int padre = (i - 1) / 2; //
            i = siftUp(este, otro, i, padre); // O(1) (siftUp es O(1)
            tienePadre = i > 0; //
        }
        return i;
    }

    private int siftUp(VectorComparable este, VectorComparable otro, int i, int padre) { // O(1)

        T valorI = este.elementos.get(i).val; //
        T valorPadre = este.elementos.get(padre).val; //
        if (este.comparador.compare(valorI, valorPadre) > 0) { // O(1) (comparar y swap son O(1))
            swap(este, otro, i, padre); //
            return padre; //
        }

        return -1;
    }

    private int siftDownUnHijo(VectorComparable este, VectorComparable otro, int i, int hijo) {
        T valorI = este.elementos.get(i).val;
        T valorHijo = este.elementos.get(hijo).val;

        if (este.comparador.compare(valorI, valorHijo) < 0) {
            swap(este, otro, i, hijo);
            return hijo;
        }
        return este.elementos.size();
    }

    private int siftDownDosHijos(VectorComparable este, VectorComparable otro, int i, int hijoIzq, int hijoDer) {
        T valorI = este.elementos.get(i).val;
        T valorHijoDer = este.elementos.get(hijoDer).val;
        T valorHijoIzq = este.elementos.get(hijoIzq).val;

        if ((este.comparador.compare(valorI, valorHijoIzq) < 0)
                || (este.comparador.compare(valorI, valorHijoDer) < 0)) {
            if (este.comparador.compare(valorHijoIzq, valorHijoDer) >= 0) {
                swap(este, otro, i, hijoIzq);
                return hijoIzq;
            } else {
                swap(este, otro, i, hijoDer);
                return hijoDer;
            }
        }

        return este.elementos.size();
    }

    private T desencolarMax(VectorComparable este, VectorComparable otro) {
        T res = este.elementos.get(0).val;
        int posEnOtra = este.elementos.get(0).posEnOtra;
        swap(este, otro, 0, este.elementos.size() - 1);
        swap(otro, este, posEnOtra, otro.elementos.size() - 1);
        este.elementos.remove(este.elementos.size() - 1);
        otro.elementos.remove(otro.elementos.size() - 1);

        heapifearDownUno(este, otro, 0);
        heapifearDownUno(otro, este, posEnOtra);

        checkInvRep();
        return res;
    }

    private void heapifearDownUno(VectorComparable este, VectorComparable otro, int i) {
        boolean tieneDosHijos = (2 * i + 2) < este.elementos.size();
        boolean tieneUnHijo = (2 * i + 1) < este.elementos.size();

        while (tieneDosHijos || tieneUnHijo) {
            if (!tieneDosHijos) {
                int hijo = 2 * i + 1;
                i = siftDownUnHijo(este, otro, i, hijo);
            } else {
                int hijoIzq = 2 * i + 1;
                int hijoDer = 2 * i + 2;
                i = siftDownDosHijos(este, otro, i, hijoIzq, hijoDer);
            }
            tieneDosHijos = (2 * i + 2) < este.elementos.size();
            tieneUnHijo = (2 * i + 1) < este.elementos.size();

        }
    }

    public T desencolarA() {
        return desencolarMax(vA, vB);
    }

    public T desencolarB() {
        return desencolarMax(vB, vA);
    }

    private void eliminar(VectorComparable este, VectorComparable otro, int posAeliminar) {

        swap(este, otro, posAeliminar, este.elementos.size() - 1);
        // este.elementos.set(posAeliminar, este.elementos.get(este.elementos.size() -
        // 1)); //
        este.elementos.remove(este.elementos.size() - 1); // O(1)

        if (posAeliminar == este.elementos.size()) {
            return;
        }

        int i = posAeliminar;

        boolean tieneDosHijos = (2 * i + 2) < este.elementos.size(); //
        boolean tieneUnHijo = (2 * i + 1) < este.elementos.size();
        boolean tienePadre = i > 0;

        while (tieneDosHijos || tieneUnHijo) {
            if (!tieneDosHijos) {
                int hijo = 2 * i + 1;
                i = siftDownUnHijo(este, otro, i, hijo);
            } else {
                int hijoIzq = 2 * i + 1;
                int hijoDer = 2 * i + 2;
                i = siftDownDosHijos(este, otro, i, hijoIzq, hijoDer);
            }
            tieneDosHijos = (2 * i + 2) < este.elementos.size();
            tieneUnHijo = (2 * i + 1) < este.elementos.size();

        }

        i = posAeliminar;
        while (tienePadre) { // (El while se ejecuta en peor caso Log(h) veces, h = altura del heap)
                             // me parece que este comentario esta mal, es lineal con respecto a la altura
            int padre = (i - 1) / 2; //
            i = siftUp(este, otro, i, padre); // O(1) (siftUp es O(1)
            tienePadre = i > 0; //
        }
        checkInvRep();
    }

    private void swap(VectorComparable este, VectorComparable otro, int i, int j) { // O(1)
        // es este el unico lugar donde cambiamos cosas de lugar?
        Nodo nodoI = este.elementos.get(i); // O(1)
        Nodo nodoJ = este.elementos.get(j); // O(1)
        este.elementos.set(i, nodoJ); // O(1)
        este.elementos.set(j, nodoI); // O(1)
        if (nodoI.posEnOtra < otro.elementos.size() && nodoJ.posEnOtra < otro.elementos.size()) {
            otro.elementos.get(nodoI.posEnOtra).posEnOtra = j; // O(1)
            otro.elementos.get(nodoJ.posEnOtra).posEnOtra = i;// O(1)
        }

    }

    public T MasPrioritario(ArrayList<T> elementos) { // O(1)

        return elementos.get(0);
    }

    public String toString() {
        String res = "";
        for (Nodo n : this.vA.elementos) {
            res += ("(" + n.val + ", " + n.posEnOtra + "),");
        }
        res += "  /////  ";
        for (Nodo n : this.vB.elementos) {
            res += ("(" + n.val + ", " + n.posEnOtra + ")");
        }
        return res;
    }
}
