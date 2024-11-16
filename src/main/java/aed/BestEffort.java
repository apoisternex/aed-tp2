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
    private ArrayList<Integer> ciudadesMaxSuperavit;

    private int gananciaTotal;
    private int cantidadTraslados;

    private DobleColaDePrioridad trasladosHeap;
    private ColaDePrioridad ciudadesHeap;

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        // Implementar
        this.ciudades = new Ciudad[cantCiudades];
        this.maxPerdida = 0;
        this.maxGanancia = 0;
        this.ciudadesMaxGanancia = new ArrayList<Integer>();
        this.ciudadesMaxPerdida = new ArrayList<Integer>();

        this.gananciaTotal = 0;
        this.cantidadTraslados = 0;

        // for(int i; i<traslados.length;i++){
        // trasladosHeap.encolar(traslados[i])
        // if (!(traslado[i].destino.pertenece(ciudades))) {
        // ciudades+=new Ciudad(traslados[i].destino, )
        // }
        // if (!(traslado[i].origen.pertenece(ciudades))) {
        // ciudades+=traslados[i].origen
        // }
        //
        // traslados[i].
        // }
        //

    }

    public void registrarTraslados(Traslado[] traslados) {
        // for(n; n>=0; n--){
        // trasladosHeap.encolar(traslados[i])
        // gananciaTotal+=traslados[i].gananciaNeta;
        // cantTraslados+=1
        // }
    }

    public int[] despacharMasRedituables(int n) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) { // checkear complejidad
            this.trasladosHeap.desencolarB();
        }
        return res;
    }

    public int[] despacharMasAntiguos(int n) {
        // Traslados[] trasladosAntiguos=[]
        // for(n; n>=0; n--) {
        // trasladosAntiguos+=trasladosHeap.desencolarB().id
        // }
        // return trasladosRedituables;
        return new int[2];
    }

    public int ciudadConMayorSuperavit() {
        // return this.ciudadConMayorSuperavit;
        return 2;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {
        return this.ciudadesMaxGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {
        return this.ciudadesMaxPerdida;
    }

    public int gananciaPromedioPorTraslado() {
        // return this.gananciaTotal / this.cantTraslados;
        return 2;
    }

}
