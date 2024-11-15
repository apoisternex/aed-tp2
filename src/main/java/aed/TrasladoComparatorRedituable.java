package aed;

import java.util.Comparator;

public class TrasladoComparatorRedituable implements Comparator<Traslado> {
    @Override
    public int compare(Traslado traslado1, Traslado traslado2) {
        int resComparacionGanancia = Integer.compare(traslado1.gananciaNeta, traslado2.gananciaNeta);

        if (resComparacionGanancia == 0) {
            return Integer.compare(traslado1.id, traslado2.id);
        }

        return resComparacionGanancia;
    }
}
