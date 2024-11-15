package aed;

import java.util.Comparator;

public class TrasladoComparatorAntiguedad implements Comparator<Traslado> {
    @Override
    public int compare(Traslado traslado1, Traslado traslado2) {
        return Integer.compare(traslado1.timestamp, traslado2.timestamp);
    }
}
