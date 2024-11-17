package aed;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class DobleColaDePrioridadTests {
    DobleColaDePrioridad<Traslado> cola;
    TrasladoComparatorAntiguedad comparadorA;
    TrasladoComparatorRedituable comparadorB;
    ArrayList<Traslado> listaTraslados;
    Traslado t1;
    Traslado t2;
    Traslado t3;
    Traslado t4;

    @BeforeEach
    void init() {

        comparadorA = new TrasladoComparatorAntiguedad();
        comparadorB = new TrasladoComparatorRedituable();
        cola = new DobleColaDePrioridad<Traslado>(comparadorA, comparadorB);
        t1 = new Traslado(1, 1, 2, 1, 1);
        t2 = new Traslado(2, 1, 2, 0, 0);
        t3 = new Traslado(3, 1, 2, 3, 3);
        t4 = new Traslado(4, 1, 2, 4, 4);
    }

    @Test
    void encolar() {
        cola.encolar(t2);
        cola.encolar(t1);
        cola.encolar(t3);
        cola.encolar(t4);
    }

    @Test
    void desencolar() {
        cola.encolar(t2);
        cola.encolar(t1);
        cola.encolar(t3);
        cola.encolar(t4);
        // En cada paso chequeamos que se cumpla la invariante de rep (Dentro de la
        // clase)
        cola.desencolarA();
        cola.desencolarB();
        cola.desencolarA();
        cola.desencolarB();

    }

    @Test
    void constructorAlternativo() {
        int cantElementosATestear = 1000;
        ArrayList<Traslado> bolsaTraslados = new ArrayList<Traslado>();
        int i = 0;

        while (i < cantElementosATestear) {
            // origen y destino nos dan lo mismo para este test
            Traslado newT = new Traslado(i, 1, 2, i, cantElementosATestear - i);
            bolsaTraslados.add(newT);

            i += 1;
        }

        DobleColaDePrioridad<Traslado> colaV2 = new DobleColaDePrioridad<Traslado>(comparadorA, comparadorB,
                bolsaTraslados.toArray(new Traslado[0]));

        int j = cantElementosATestear;

        while (j > 0) {
            // Igual que en el otro test, dentro de la clase ya hacemos chequeos sobre el
            // invRep.
            if (j % 2 == 0) {
                colaV2.desencolarA();
            } else {
                colaV2.desencolarB();
            }
            j--;
        }

    }

}
