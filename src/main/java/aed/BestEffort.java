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
            if (!this.trasladosHeap.estaVacia()) {
                Traslado t = this.trasladosHeap.desencolarB();

                this.ciudadesPorSuperavit.get(this.handlesCiudades.get(t.origen)).sumarPerdida(t.gananciaNeta);
                this.ciudadesPorSuperavit.get(this.handlesCiudades.get(t.destino)).sumarGanancia(t.gananciaNeta);

                this.cantidadTraslados++;
            }
        }
        return res;
    }

    public int[] despacharMasAntiguos(int n) {
        return new int[n];
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
