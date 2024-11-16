package aed;

import java.util.Comparator;

public class CiudadComparator implements Comparator<Ciudad> {
    @Override
    public int compare(Ciudad c1, Ciudad c2) {
        int superavitC1 = c1.ganancia - c1.perdida;
        int superavitC2 = c2.ganancia - c2.perdida;

        int comparacionSuperavit = Integer.compare(superavitC1, superavitC2);
        if (comparacionSuperavit == 0) {
            return Integer.compare(c2.id, c1.id);
        }

        return comparacionSuperavit;
    }
}
