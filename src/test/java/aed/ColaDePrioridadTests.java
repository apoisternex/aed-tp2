package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class ColaDePrioridadTests {
    ColaDePrioridad<Traslado> cola;
    TrasladoComparatorRedituable comparadorT;
    ArrayList<Traslado> listaTraslados;
    Traslado t1;
    Traslado t2;
    Traslado t3;
    Traslado t4;

    @BeforeEach
    void init() {

        comparadorT = new TrasladoComparatorRedituable();
        cola = new ColaDePrioridad<Traslado>(comparadorT);

        t1 = new Traslado(1, 1, 2, 10, 1);
        t2 = new Traslado(2, 1, 2, 20, 2);
        t3 = new Traslado(3, 1, 2, 130, 3);
        t4 = new Traslado(4, 1, 2, 5, 4);
    }

    @Test
    void inicializa() {
        assertEquals(cola.vacia(), true);
        assertTrue(comparadorT.compare(t1, t2) < 0);
    }

    @Test
    void encolar() {
        cola.Encolar(t1);
        cola.Encolar(t2);
        // t3 es el mas prioritario por su ganancia
        cola.Encolar(t3);
        cola.Encolar(t4);
        assertEquals(cola.vacia(), false);
        Traslado masPrioritario = cola.MasPrioritario();
        assertEquals(masPrioritario.gananciaNeta, t3.gananciaNeta);
    }

    @Test
    void desencolar() {
        // TODO: ver algun caso donde hay algunos con la misma prioridad
        cola.Encolar(t1);
        cola.Encolar(t2);
        // t3 es el mas prioritario por su ganancia
        cola.Encolar(t3);
        cola.Encolar(t4);
        assertEquals(cola.vacia(), false);
        assertEquals(cola.MasPrioritario(), t3);
        cola.DesencolarMax();
        assertEquals(cola.MasPrioritario(), t2);
        cola.DesencolarMax();
        assertEquals(cola.MasPrioritario(), t1);
        cola.DesencolarMax();
        assertEquals(cola.vacia(), false);
        assertEquals(cola.MasPrioritario(), t4);
        cola.DesencolarMax();
        assertEquals(cola.vacia(), true);
    }

    @Test
    void stressTest() {
        int cantElementosATestear = 1000;
        ArrayList<Traslado> bolsaTraslados = new ArrayList<Traslado>();
        int i = 0;

        while (i < cantElementosATestear) {
            // origen y destino nos dan lo mismo para este test
            Traslado newT = new Traslado(i, 1, 2, i, i);
            bolsaTraslados.add(newT);

            i += 1;
        }

        ColaDePrioridad<Traslado> colaAHeapyfiear = new ColaDePrioridad<Traslado>(comparadorT,
                bolsaTraslados.toArray(new Traslado[0]));

        // ----------- Test encolar -----------

        ArrayList<Traslado> restantesAEncolar = (ArrayList<Traslado>) bolsaTraslados.clone();
        assertEquals(restantesAEncolar.size(), cantElementosATestear);

        while (restantesAEncolar.size() > 0) {
            // Encolamos de manera aleatoria
            int indexAEncolar = ThreadLocalRandom.current().nextInt(0, restantesAEncolar.size());

            cola.Encolar(restantesAEncolar.get(indexAEncolar));
            restantesAEncolar.remove(indexAEncolar);
        }
        assertEquals(restantesAEncolar.size(), 0);
        assertEquals(cola.MasPrioritario().id, bolsaTraslados.get(bolsaTraslados.size() - 1).id);

        // ----------- Test desencolar -----------

        restantesAEncolar = (ArrayList<Traslado>) bolsaTraslados.clone();
        assertEquals(restantesAEncolar.size(), cantElementosATestear);

        while (restantesAEncolar.size() > 1) {
            // Desencolamos de manera aleatoria
            int indexADesencolar = ThreadLocalRandom.current().nextInt(0, restantesAEncolar.size());

            cola.DesencolarMax();
            colaAHeapyfiear.DesencolarMax();
            restantesAEncolar.remove(indexADesencolar);
        }
        assertEquals(restantesAEncolar.size(), 1);
        assertEquals(cola.MasPrioritario().id, bolsaTraslados.get(0).id);
        assertEquals(colaAHeapyfiear.MasPrioritario().id, bolsaTraslados.get(0).id);

    }

}
