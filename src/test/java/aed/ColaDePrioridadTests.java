package aed;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class ColaDePrioridadTests {
    ColaDePrioridadGenerica<Traslado> cola;
    TrasladoComparatorRedituable comparadorT;
    ArrayList<Traslado> listaTraslados;
    Traslado t1;
    Traslado t2;
    Traslado t3;
    Traslado t4;

    @BeforeEach
    void init() {

        comparadorT = new TrasladoComparatorRedituable();
        cola = new ColaDePrioridadGenerica<Traslado>(comparadorT);

        t1 = new Traslado(1, 1, 2, 8, 1);
        t2 = new Traslado(2, 1, 2, 72, 2);
        t3 = new Traslado(3, 1, 2, 130, 3);
        t4 = new Traslado(4, 1, 2, 6, 4);
    }

    @Test
    void inicializa() {
        assertEquals(cola.vacia(), true);
        assertTrue(comparadorT.compare(t1, t2) < 0);
    }

    @Test
    void encolar() {
        cola.encolar(t1);
        cola.encolar(t2);
        // t3 es el mas prioritario por su ganancia
        cola.encolar(t3);
        cola.encolar(t4);
        assertEquals(cola.vacia(), false);
        Traslado masPrioritario = cola.MasPrioritario();
        assertEquals(masPrioritario.gananciaNeta, t3.gananciaNeta);
    }

    @Test
    void desencolar() {
        cola.encolar(t1);
        cola.encolar(t2);
        // t3 es el mas prioritario por su ganancia
        cola.encolar(t3);
        cola.encolar(t4);
        assertEquals(cola.vacia(), false);
        assertEquals(cola.MasPrioritario(), t3);
        cola.desencolarMax();
        assertEquals(cola.MasPrioritario(), t2);
        cola.desencolarMax();
        assertEquals(cola.MasPrioritario(), t1);
        cola.desencolarMax();
        assertEquals(cola.vacia(), false);
        assertEquals(cola.MasPrioritario(), t4);
        cola.desencolarMax();
        assertEquals(cola.vacia(), true);
    }

    @Test
    void setTest() {
        ColaDePrioridadGenerica<Integer> colaNumeros = new ColaDePrioridadGenerica<Integer>(Comparator.naturalOrder());

        int max = 10;

        for (int i = 0; i < max; i++) {
            colaNumeros.encolar(i + 1);
        }

        ArrayList<ColaDePrioridadGenerica<Integer>.Handle> handles = colaNumeros.verHandles();

        assertEquals(colaNumeros.MasPrioritario(), 10);

        // Uno de los que tenía menos prioridad, pasa a ser el que más tiene.
        colaNumeros.set(handles.get(1), 30);

        assertEquals(colaNumeros.MasPrioritario(), 30);
    }

    @Test
    void stressTest() {
        int cantElementosATestear = 1000;
        ArrayList<Traslado> bolsaTraslados = new ArrayList<Traslado>();
        int i = 0;

        while (i < cantElementosATestear) {
            // origen y destino podemos ignorarlos para este test
            Traslado newT = new Traslado(i, 1, 2, i, i);
            bolsaTraslados.add(newT);

            i += 1;
        }
        TrasladoComparatorRedituable comparadorT2 = new TrasladoComparatorRedituable();

        ColaDePrioridadGenerica<Traslado> colaAHeapyfiear = new ColaDePrioridadGenerica<Traslado>(comparadorT2,
                bolsaTraslados.toArray(new Traslado[0]));

        // ----------- Test encolar -----------

        ArrayList<Traslado> restantesAEncolar = (ArrayList<Traslado>) bolsaTraslados.clone();
        assertEquals(restantesAEncolar.size(), cantElementosATestear);

        while (restantesAEncolar.size() > 0) {
            // Encolamos de manera aleatoria
            int indexAEncolar = ThreadLocalRandom.current().nextInt(0, restantesAEncolar.size());

            cola.encolar(restantesAEncolar.get(indexAEncolar));
            restantesAEncolar.remove(indexAEncolar);
        }
        assertEquals(restantesAEncolar.size(), 0);
        assertEquals(cola.MasPrioritario().id, bolsaTraslados.get(bolsaTraslados.size() - 1).id);

        // ----------- Test desencolar -----------

        while (bolsaTraslados.size() > 1) {
            Traslado desencoladoColaConstru1 = cola.desencolarMax();
            Traslado desencoladoColaConstru2 = colaAHeapyfiear.desencolarMax();
            assertEquals(desencoladoColaConstru1, bolsaTraslados.get(bolsaTraslados.size() - 1));
            assertEquals(desencoladoColaConstru2,
                    bolsaTraslados.get(bolsaTraslados.size() - 1));

            bolsaTraslados.remove(bolsaTraslados.size() - 1);

        }
        assertEquals(cola.MasPrioritario().id, bolsaTraslados.get(0).id);
    }

}
