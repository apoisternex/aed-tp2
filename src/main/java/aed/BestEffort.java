package aed;

// import java.awt.Color;
import java.util.ArrayList;
// import java.util.Arrays;

public class BestEffort {
    // Completar atributos privados
    private int maxPerdida;
    private int maxGanancia;

    private ArrayList<Integer> ciudadesMaxGanancia;
    private ArrayList<Integer> ciudadesMaxPerdida;
    private ArrayList<Integer> ciudadesMaxSuperavit;

    private int gananciaTotal;
    private int cantidadTraslados;

    private DobleColaDePrioridad<Traslado> trasladosHeap;
    private ColaDePrioridadGenerica<Ciudad> ciudadesPorSuperavit;
    private ArrayList<ColaDePrioridadGenerica<Ciudad>.Handle> handlesCiudades;

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        // Implementar
        this.maxPerdida = 0;
        this.maxGanancia = 0;
        this.ciudadesMaxGanancia = new ArrayList<Integer>();
        this.ciudadesMaxPerdida = new ArrayList<Integer>();

        this.gananciaTotal = 0;
        this.cantidadTraslados = 0;

        this.trasladosHeap = new DobleColaDePrioridad<Traslado>(new TrasladoComparatorAntiguedad(),
                new TrasladoComparatorRedituable(), traslados);

        this.ciudadesPorSuperavit = new ColaDePrioridadGenerica<Ciudad>(new CiudadComparator());
        this.handlesCiudades = new ArrayList<ColaDePrioridadGenerica<Ciudad>.Handle>();

        for (int i = 0; i < cantCiudades; i++) { // checkear complejidad
            this.handlesCiudades.add(this.ciudadesPorSuperavit.encolar(new Ciudad(i)));
        }
    }

    public void registrarTraslados(Traslado[] traslados) {
        for (Traslado t : traslados) {
            this.trasladosHeap.encolar(t);
        }
    }

    public int[] despacharMasRedituables(int n) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) { // checkear complejidad
            if (this.trasladosHeap.estaVacia()) {
                return res;
            }
            Traslado t = this.trasladosHeap.desencolarB();

            Ciudad nuevoOrigen = new Ciudad(t.origen);
            Ciudad viejoOrigen = this.ciudadesPorSuperavit.get(this.handlesCiudades.get(t.origen));
            nuevoOrigen.ganancia = viejoOrigen.ganancia + t.gananciaNeta;
            nuevoOrigen.perdida = viejoOrigen.perdida;

            Ciudad nuevoDestino = new Ciudad(t.destino);
            Ciudad viejoDestino = this.ciudadesPorSuperavit.get(this.handlesCiudades.get(t.destino));
            nuevoDestino.perdida = viejoDestino.perdida + t.gananciaNeta;
            nuevoDestino.ganancia = viejoDestino.ganancia;

            ciudadesPorSuperavit.set(this.handlesCiudades.get(t.origen), nuevoOrigen);
            ciudadesPorSuperavit.set(this.handlesCiudades.get(t.destino), nuevoDestino);

            this.cantidadTraslados++;

            if (nuevoOrigen.ganancia > this.maxGanancia) {
                this.maxGanancia = nuevoOrigen.ganancia;
                this.ciudadesMaxGanancia.clear();
                this.ciudadesMaxGanancia.add(t.origen);
            } else if (nuevoOrigen.ganancia == this.maxGanancia) {
                this.ciudadesMaxGanancia.add(t.origen);
            }

            if (nuevoDestino.perdida > this.maxPerdida) {
                this.maxPerdida = nuevoDestino.perdida;
                this.ciudadesMaxPerdida.clear();
                this.ciudadesMaxPerdida.add(t.destino);
            } else if (nuevoDestino.perdida == this.maxPerdida) {
                this.ciudadesMaxPerdida.add(t.destino);
            }
            res[i] = t.id;
        }
        return res;
    }

    public int[] despacharMasAntiguos(int n) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) { // checkear complejidad
            if (trasladosHeap.estaVacia()) {
                return res;
            }
            Traslado t = this.trasladosHeap.desencolarA();

            Ciudad nuevoOrigen = new Ciudad(t.origen);
            Ciudad viejoOrigen = this.ciudadesPorSuperavit.get(this.handlesCiudades.get(t.origen));
            nuevoOrigen.ganancia = viejoOrigen.ganancia + t.gananciaNeta;
            nuevoOrigen.perdida = viejoOrigen.perdida;

            Ciudad nuevoDestino = new Ciudad(t.destino);
            Ciudad viejoDestino = this.ciudadesPorSuperavit.get(this.handlesCiudades.get(t.destino));
            nuevoDestino.perdida = viejoDestino.perdida + t.gananciaNeta;
            nuevoDestino.ganancia = viejoDestino.ganancia;

            this.ciudadesPorSuperavit.set(this.handlesCiudades.get(t.destino), nuevoDestino);
            this.ciudadesPorSuperavit.set(this.handlesCiudades.get(t.origen), nuevoOrigen);

            if (nuevoOrigen.ganancia > this.maxGanancia) {
                this.maxGanancia = nuevoOrigen.ganancia;
                this.ciudadesMaxGanancia.clear();
                this.ciudadesMaxGanancia.add(t.origen);
            } else if (nuevoOrigen.ganancia == this.maxGanancia) {
                this.ciudadesMaxGanancia.add(t.origen);
            }

            if (nuevoDestino.perdida > this.maxPerdida) {
                this.maxPerdida = nuevoDestino.perdida;
                this.ciudadesMaxPerdida.clear();
                this.ciudadesMaxPerdida.add(t.destino);
            } else if (nuevoDestino.perdida == this.maxPerdida) {
                this.ciudadesMaxPerdida.add(t.destino);
            }

            res[i] = t.id;
        }
        return res;
    }

    public int ciudadConMayorSuperavit() {
        return this.ciudadesPorSuperavit.MasPrioritario().id;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {
        return this.ciudadesMaxGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {
        return this.ciudadesMaxPerdida;
    }

    public int gananciaPromedioPorTraslado() {
        return this.gananciaTotal / this.cantidadTraslados;
    }

}
