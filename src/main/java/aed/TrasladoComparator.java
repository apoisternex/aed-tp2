package aed;

import java.util.Comparator;

public class TrasladoComparator implements Comparator<Traslado>  {
    @Override
    public int compare(Traslado traslado1, Traslado traslado2) {
        int gananciaCompareResult = Integer.compare(traslado1.gananciaNeta, traslado2.gananciaNeta);
        if(gananciaCompareResult == 0) {
         return Integer.compare(traslado1.timestamp, traslado2.timestamp);
        } else {
        return gananciaCompareResult;
        }
    }
}
 