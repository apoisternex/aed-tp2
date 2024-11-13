package aed;

public class Ciudad {
    private int perdida;
    private int ganancia;

    public Ciudad() {
        this.ganancia = 0;
        this.ganancia = 0;
    }

    public void sumarGanancia(int extra) {
        this.ganancia += extra;
    }

    public void sumarPerdida(int extra) {
        this.perdida += extra;
    }
}
