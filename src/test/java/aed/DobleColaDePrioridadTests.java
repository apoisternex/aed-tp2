package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

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

        // t1 = new Traslado(1, 1, 2, 10, 1);
        // t2 = new Traslado(2, 1, 2, 20, 2);
        // t3 = new Traslado(3, 1, 2, 130, 3);
        // t4 = new Traslado(4, 1, 2, 5, 4);
        // g t
        t1 = new Traslado(1, 1, 2, 1, 1);
        t2 = new Traslado(2, 1, 2, 0, 0);
        t3 = new Traslado(3, 1, 2, 3, 3);
        t4 = new Traslado(4, 1, 2, 4, 4);
    }

    @Test
    void encolar() {
        cola.encolar(t2);
        cola.encolar(t1);
        // t3 es el mas prioritario por su ganancia
        cola.encolar(t3);
        cola.encolar(t4);
    }

    @Test
    void desencolar() {
        cola.encolar(t2);
        cola.encolar(t1);
        // // t3 es el mas prioritario por su ganancia
        cola.encolar(t3);
        cola.encolar(t4);

        assertEquals(cola.desencolarA(), t2);
        assertEquals(cola.desencolarB(), t4);
        cola.desencolarA();
        cola.desencolarB();

    }

}
