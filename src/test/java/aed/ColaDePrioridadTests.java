package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class ColaDePrioridadTests {
    @Test
    void inicializa(){
    TrasladoComparator comparadorT = new TrasladoComparator();
       ColaDePrioridad cola = new ColaDePrioridad<>(comparadorT);

       assertEquals(cola.vacia(), true);
       Traslado t1 = new Traslado(1, 1, 2, 10, 1);
       Traslado t2 = new Traslado(2, 1, 2, 20, 1);
       assertTrue(comparadorT.compare(t1, t2) < 0);
       cola.Encolar(t1);
       cola.Encolar(t2);
       cola.Encolar(new Traslado(3, 1, 2, 130, 1));
       cola.Encolar(new Traslado(4, 1, 2, 5, 1));
       assertEquals(cola.vacia(), false);
       
    }
    
}

//  Traslado[] listaTraslados = new Traslado[] {
//                                             new Traslado(1, 0, 1, 100, 10),
//                                             new Traslado(2, 0, 1, 400, 20),
//                                             new Traslado(3, 3, 4, 500, 50),
//                                             new Traslado(4, 4, 3, 500, 11),
//                                             new Traslado(5, 1, 0, 1000, 40),
//                                             new Traslado(6, 1, 0, 1000, 41),
//                                             new Traslado(7, 6, 3, 2000, 42)
//                                         }
    