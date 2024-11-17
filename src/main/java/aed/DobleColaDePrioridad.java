package aed;

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

    public boolean estaVacia() { // O(1)
        return this.vA.elementos.size() == 0; // O(1)
    }

    public DobleColaDePrioridad(Comparator<T> cA, Comparator<T> cB, T[] arrayBase) { // O(n)

        this.vA = new VectorComparable(); //
        this.vB = new VectorComparable(); //
        this.vA.elementos = convertirTrasladosANodos(arrayBase); // O(n)
        this.vB.elementos = convertirTrasladosANodos(arrayBase); // O(n)
        this.vA.comparador = cA; //
        this.vB.comparador = cB; //

        algoritmoFloyd(vA, vB); // O(n)
        algoritmoFloyd(vB, vA); // O(n)
    }

    private ArrayList<Nodo> convertirTrasladosANodos(T[] traslados) { // O(n)
        ArrayList<Nodo> res = new ArrayList<Nodo>();
        for (int i = 0; i < traslados.length; i++) { // n veces
            Nodo n = new Nodo(traslados[i], i);
            res.add(n); // O(1)
        }
        return res;
    }

    private void algoritmoFloyd(VectorComparable este, VectorComparable otro) { // O(n)
        for (int i = este.elementos.size() - 1; i >= 0; i--) {
            heapifearDownUno(este, otro, i);
        }
    }

    public DobleColaDePrioridad(Comparator<T> cA, Comparator<T> cB) { // Asignaciones básicas, todo O(1)
        this.vA = new VectorComparable();
        this.vB = new VectorComparable();
        this.vA.elementos = new ArrayList<Nodo>();
        this.vB.elementos = new ArrayList<Nodo>();
        this.vA.comparador = cA;
        this.vB.comparador = cB;
    }

    public boolean vacia(ArrayList<T> elementos) { // O(1)
        return elementos.size() == 0; // O(1)
    }

    public void encolar(T e) { // log(n)
        this.vA.elementos.add(new Nodo(e, this.vA.elementos.size())); // O(1)
        this.vB.elementos.add(new Nodo(e, this.vB.elementos.size())); // O(1)
        heapifearUno(vA, vB); // O(log(n))
        heapifearUno(vB, vA); // O(log(n))
        checkInvRep(); // esto se puede comentar, solo lo usamos para testear
    }

    private int heapifearUno(VectorComparable este, VectorComparable otro) { // O(log(n))
        int i = este.elementos.size() - 1; // O(1)
        boolean tienePadre = i > 0; // O(1)
        // (El while se ejecuta en peor caso Log(h) veces, h = altura del heap),
        // por la naturaleza del heap, no vamos a tener una rama gigante con los n
        // elementos
        while (tienePadre) { // O(Log(n))
            int padre = (i - 1) / 2; // O(1)
            i = siftUp(este, otro, i, padre); // O(1) (siftUp es O(1)
            tienePadre = i > 0; // O(1)
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

    private int siftDownUnHijo(VectorComparable este, VectorComparable otro, int i, int hijo) { // O(1)
        T valorI = este.elementos.get(i).val; // O(1)
        T valorHijo = este.elementos.get(hijo).val; // O(1)

        if (este.comparador.compare(valorI, valorHijo) < 0) { // O(1) (comparar y swap son O(1))
            swap(este, otro, i, hijo);
            return hijo;
        }
        return este.elementos.size();
    }

    private int siftDownDosHijos(VectorComparable este, VectorComparable otro, int i, int hijoIzq, int hijoDer) { // O(1)
        T valorI = este.elementos.get(i).val; // O(1)
        T valorHijoDer = este.elementos.get(hijoDer).val; // O(1)
        T valorHijoIzq = este.elementos.get(hijoIzq).val; // O(1)

        if ((este.comparador.compare(valorI, valorHijoIzq) < 0) // O(1) (comparar y swap son O(1))
                || (este.comparador.compare(valorI, valorHijoDer) < 0)) {
            if (este.comparador.compare(valorHijoIzq, valorHijoDer) >= 0) { // O(1)
                swap(este, otro, i, hijoIzq);
                return hijoIzq;
            } else {
                swap(este, otro, i, hijoDer);
                return hijoDer;
            }
        }

        return este.elementos.size();
    }

    private T desencolarMax(VectorComparable este, VectorComparable otro) { // O(log(n))
        T res = este.elementos.get(0).val; // O(1)
        int posEnOtra = este.elementos.get(0).posEnOtra; // O(1)
        swap(este, otro, 0, este.elementos.size() - 1); // O(1)
        swap(otro, este, posEnOtra, otro.elementos.size() - 1); // O(1)
        este.elementos.remove(este.elementos.size() - 1); // O(1)
        otro.elementos.remove(otro.elementos.size() - 1); // (1)

        heapifearDownUno(este, otro, 0); // O(log(n))
        heapifearDownUno(otro, este, posEnOtra); // O(log(n))

        checkInvRep(); // esto se puede comentar, solo lo usamos para testear
        return res;
    }

    private void heapifearDownUno(VectorComparable este, VectorComparable otro, int i) { // O(log(n))
        boolean tieneDosHijos = (2 * i + 2) < este.elementos.size(); // O(1)
        boolean tieneUnHijo = (2 * i + 1) < este.elementos.size(); // O(1)

        while (tieneDosHijos || tieneUnHijo) { // Como mucho la altura del heap. O(log(n)).
            if (!tieneDosHijos) {
                int hijo = 2 * i + 1; // O(1)
                i = siftDownUnHijo(este, otro, i, hijo); // O(1)
            } else {
                int hijoIzq = 2 * i + 1; // O(1)
                int hijoDer = 2 * i + 2; // O(1)
                i = siftDownDosHijos(este, otro, i, hijoIzq, hijoDer); // O(1)
            }
            tieneDosHijos = (2 * i + 2) < este.elementos.size(); // O(1)
            tieneUnHijo = (2 * i + 1) < este.elementos.size(); // O(1)

        }
    }

    public T desencolarA() { // O(log(n))
        return desencolarMax(vA, vB);
    }

    public T desencolarB() { // O(log(n))
        return desencolarMax(vB, vA);
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

    // Advertencia: Usar esto en las funciones obviamente nos cambia la complejidad,
    // pero lo tomamos como parte del testeo,
    // y en caso de implementarse este sistema, sería eliminado de acá y delegado al
    // script de testing
    private void checkInvRep() {
        assert (esMaxHeap(vA)) : "vA no es max Heap";
        assert (esMaxHeap(vB)) : "vB no es max Heap";
        assert (enSync()) : "no estan sincronizados";
    }

}
