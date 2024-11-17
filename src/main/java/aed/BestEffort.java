package aed;

// import java.awt.Color;
import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Comparator;

public class BestEffort {
    // Completar atributos privados
    private int maxPerdida;
    private int maxGanancia;

    private ArrayList<Integer> ciudadesMaxGanancia;
    private ArrayList<Integer> ciudadesMaxPerdida;

    private int gananciaTotal;
    private int cantidadTraslados;

    private DobleColaDePrioridad<Traslado> trasladosHeap;
    private ColaDePrioridadGenerica<Ciudad> ciudadesPorSuperavit;
    private ArrayList<ColaDePrioridadGenerica<Ciudad>.Handle> handlesCiudades;

    public BestEffort(int cantCiudades, Traslado[] traslados) { // O(|c| + |t|)
        this.maxPerdida = 0; // O(1)
        this.maxGanancia = 0; // O(1)
        this.ciudadesMaxGanancia = new ArrayList<Integer>(); // O(1)
        this.ciudadesMaxPerdida = new ArrayList<Integer>(); // O(1)

        this.gananciaTotal = 0; // O(1)
        this.cantidadTraslados = 0; // O(1)

        this.trasladosHeap = new DobleColaDePrioridad<Traslado>(new TrasladoComparatorAntiguedad(),
                new TrasladoComparatorRedituable(), traslados); // O(|t|) Algoritmo de Floyd

        ArrayList<Ciudad> ciudadesPorAgregar = new ArrayList<Ciudad>(); // O(1)

        for (int i = 0; i < cantCiudades; i++) { // |c| veces
            ciudadesPorAgregar.add(new Ciudad(i)); // O(1)
        }

        this.ciudadesPorSuperavit = new ColaDePrioridadGenerica<Ciudad>(new CiudadComparator(),
                ciudadesPorAgregar.toArray(new Ciudad[0])); // O(|c|)
        this.handlesCiudades = this.ciudadesPorSuperavit.verHandles(); // O(|c|)
    }

    public void registrarTraslados(Traslado[] traslados) { // |traslados|log(|t|)
        for (Traslado t : traslados) { // |traslados| veces
            this.trasladosHeap.encolar(t); // log(|t|)
        }
    }

    private int[] despachar(int n, boolean usarCriterioA) { // O( n( log(|t|) + log(|c|) ) )
        int[] res = new int[n]; // O(1)
        for (int i = 0; i < n; i++) { // n veces
            if (this.trasladosHeap.estaVacia()) { // O(1)
                return res;
            }
            Traslado t = null;

            // si no es criterioA, no queda otra a que sea citerioB, porque solo tenemos 2
            // criterios.
            if (usarCriterioA) {
                t = this.trasladosHeap.desencolarA(); // O( log(|t|) )
            } else {
                t = this.trasladosHeap.desencolarB(); // O( log(|t|) )
            }

            Ciudad nuevoOrigen = new Ciudad(t.origen); // O(1)
            Ciudad viejoOrigen = this.ciudadesPorSuperavit.get(this.handlesCiudades.get(t.origen)); // O(1)
            nuevoOrigen.ganancia = viejoOrigen.ganancia + t.gananciaNeta; // O(1)
            nuevoOrigen.perdida = viejoOrigen.perdida; // O(1)

            Ciudad nuevoDestino = new Ciudad(t.destino); // O(1)
            Ciudad viejoDestino = this.ciudadesPorSuperavit.get(this.handlesCiudades.get(t.destino)); // O(1)
            nuevoDestino.perdida = viejoDestino.perdida + t.gananciaNeta; // O(1)
            nuevoDestino.ganancia = viejoDestino.ganancia; // O(1)

            ciudadesPorSuperavit.set(this.handlesCiudades.get(t.origen), nuevoOrigen); // O( log(|c|) ), por el set
            ciudadesPorSuperavit.set(this.handlesCiudades.get(t.destino), nuevoDestino); // O( log(|c|) ) ''

            this.cantidadTraslados++; // O(1)
            this.gananciaTotal += t.gananciaNeta; // O(1)

            if (nuevoOrigen.ganancia > this.maxGanancia) { // O(1)
                this.maxGanancia = nuevoOrigen.ganancia; // O(1)
                this.ciudadesMaxGanancia.clear(); // O(1)
                this.ciudadesMaxGanancia.add(t.origen); // O(1)
            } else if (nuevoOrigen.ganancia == this.maxGanancia) { // O(1)
                this.ciudadesMaxGanancia.add(t.origen); // O(1)
            }

            if (nuevoDestino.perdida > this.maxPerdida) { // O(1)
                this.maxPerdida = nuevoDestino.perdida; // O(1)
                this.ciudadesMaxPerdida.clear(); // O(1)
                this.ciudadesMaxPerdida.add(t.destino); // O(1)
            } else if (nuevoDestino.perdida == this.maxPerdida) { // O(1)
                this.ciudadesMaxPerdida.add(t.destino); // O(1)
            }
            res[i] = t.id; // O(1)
        }
        return res;
    }

    public int[] despacharMasRedituables(int n) { // O( n( log(|t|) + log(|c|) ) )
        return despachar(n, false);
    }

    public int[] despacharMasAntiguos(int n) { // O( n( log(|t|) + log(|c|) )
        return despachar(n, true);
    }

    public int ciudadConMayorSuperavit() { // O(1)
        return this.ciudadesPorSuperavit.MasPrioritario().id; // O(1)
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() { // O(1)
        return this.ciudadesMaxGanancia; // O(1)
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() { // O(1)
        return this.ciudadesMaxPerdida; // O(1)
    }

    public int gananciaPromedioPorTraslado() { // O(1)
        return this.gananciaTotal / this.cantidadTraslados; // O(1)
    }

}
