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
            return String.format("{ % ; % }", this.val, this.posEnOtra);
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
            if (n.val != this.vB.elementos.get(n.posEnOtra)) {
                return false;
            }
        }
        for (Nodo n : this.vB.elementos) {
            if (n.val != this.vB.elementos.get(n.posEnOtra)) {
                return false;
            }
        }
        return true;
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
        assert (esMaxHeap(vA));
        heapifearUno(vB, vA);
        assert (esMaxHeap(vB));
        assert (enSync()) : "no esta sincronizado";
    }

    public int heapifearUno(VectorComparable este, VectorComparable otro) { // O(log(n))

        int i = este.elementos.size() - 1; //
        boolean tienePadre = (i - 1) / 2 >= 0; //

        while (tienePadre) { // (El while se ejecuta en peor caso Log(h) veces, h = altura del heap)
                             // me parece que este comentario esta mal, es lineal con respecto a la altura
            int padre = (i - 1) / 2; //
            i = siftUp(este, otro, i, padre); // O(1) (siftUp es O(1)
            tienePadre = (i - 1) / 2 >= 0; //
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

    private void swap(VectorComparable este, VectorComparable otro, int i, int j) { // O(1)
        // es este el unico lugar donde cambiamos cosas de lugar?
        Nodo nodoI = este.elementos.get(i); // O(1)
        Nodo nodoJ = este.elementos.get(j); // O(1)
        este.elementos.set(i, nodoJ); // O(1)
        este.elementos.set(j, nodoI); // O(1)
        otro.elementos.get(nodoI.posEnOtra).posEnOtra = j; // O(1)
        otro.elementos.get(nodoJ.posEnOtra).posEnOtra = i;// O(1)

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

    // public T DesencolarMax(ArrayList<T> elementos) {
    // T res = elementos.get(0); //
    // elementos.set(0, elementos.get(elementos.size() - 1)); //
    // elementos.remove(elementos.size()); // O(1)
    // int i = 0; //
    // boolean tieneDosHijos = (2 * i + 2) < elementos.size(); //
    // boolean tieneUnHijo = (2 * i + 1) < elementos.size(); //
    //
    // while (tieneDosHijos || tieneUnHijo) {
    // if (!tieneDosHijos) {
    // int hijo = 2 * i + 1;
    // i = siftDownUnHijo(elementos, i, hijo);
    // } else {
    // int hijoIzq = 2 * i + 1;
    // int hijoDer = 2 * i + 2;
    // i = siftDownDosHijos(elementos, i, hijoIzq, hijoDer);
    // }
    // tieneDosHijos = (2 * i + 2) < elementos.size();
    // tieneUnHijo = (2 * i + 1) < elementos.size();
    //
    // }
    // return res;
    // }
    //
    // private int siftDownUnHijo(ArrayList<T> elementos, int i, int hijo) {
    // T valorI = elementos.get(i);
    // T valorHijo = elementos.get(hijo);
    //
    // if (comparador.compare(valorI, valorHijo) < 0) {
    // swap(elementos, i, hijo);
    // return hijo;
    // }
    // return elementos.size();
    // }
    //
    // private int siftDownDosHijos(ArrayList<T> elementos, int i, int hijoIzq, int
    // hijoDer) {
    // T valorI = elementos.get(i);
    // T valorHijoDer = elementos.get(hijoDer);
    // T valorHijoIzq = elementos.get(hijoIzq);
    //
    // if ((comparador.compare(valorI, valorHijoIzq) < 0) ||
    // (comparador.compare(valorI, valorHijoDer) < 0)) {
    // if (comparador.compare(valorHijoIzq, valorHijoDer) >= 0) {
    // swap(elementos, i, hijoIzq);
    // return hijoIzq;
    // } else {
    // swap(elementos, i, hijoDer);
    // return hijoDer;
    // }
    // }
    //
    // return elementos.size();
    // }
    //
    //
    // private ArrayList<T> heapify(Comparator<T> c, T[] arrayBase) { // O(n) (TODO:
    // FALTA EXPLICAR PQ)
    // int longitud = arrayBase.length;
    // Comparator<T> comparador = c;
    // ArrayList<T> elementos = new ArrayList<T>(); // podriamos hacerlo en el
    // lugar, quiero cambiar lo menos posible
    // // el codigo
    // for (int i = 0; i < arrayBase.length; i++) {
    // elementos.add(arrayBase[i]);
    // }
    //
    // for (int i = longitud - 1; i >= 0; i--) {
    // int j = i;
    // boolean tieneDosHijos = (2 * j + 2) < longitud;
    // boolean tieneUnHijo = (2 * j + 1) < longitud;
    // while (tieneUnHijo || tieneDosHijos) {
    // if (!tieneDosHijos) {
    // int hijo = 2 * j + 1;
    // j = siftDownUnHijo(elementos, j, hijo);
    // } else {
    // int hijoIzq = 2 * j + 1;
    // int hijoDer = 2 * j + 2;
    // j = siftDownDosHijos(elementos, j, hijoIzq, hijoDer);
    // }
    // tieneDosHijos = (2 * j + 2) < longitud;
    // tieneUnHijo = (2 * j + 1) < longitud;
    // }
    // }
    // return elementos;
    // }

}
