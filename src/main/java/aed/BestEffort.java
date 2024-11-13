package aed;

// import java.awt.Color;
import java.util.ArrayList;
// import java.util.Arrays;

public class BestEffort {
    // Completar atributos privados
    private Ciudad[] ciudades;
    private int maxPerdida;
    private int maxGanancia;

    private ArrayList<Integer> ciudadesMaxGanancia;
    private ArrayList<Integer> ciudadesMaxPerdida;

    private int cantTraslados;
    private int totalTraslados;

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        // Implementar
        this.ciudades = new Ciudad[cantCiudades];
        this.maxPerdida = 0;
        this.maxGanancia = 0;
        this.ciudadesMaxGanancia = new ArrayList<Integer>();
        this.ciudadesMaxPerdida = new ArrayList<Integer>();

        this.cantTraslados = 0;
        this.totalTraslados = 0;
    }

    public void registrarTraslados(Traslado[] traslados) {
        // Implementar
    }

    public int[] despacharMasRedituables(int n) {
        // Implementar
        return null;
    }

    public int[] despacharMasAntiguos(int n) {
        // Implementar
        return null;
    }

    public int ciudadConMayorSuperavit() {
        // Implementar
        return 0;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {
        return this.ciudadesMaxGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {
        return this.ciudadesMaxPerdida;
    }

    public int gananciaPromedioPorTraslado() {
        // Implementar
        return 0;
    }

}
