package aed;

public class Ciudad {
    private int id;
    private int perdida;
    private int ganancia;

    public Ciudad() {
        this.ganancia = 0;
        this.perdida = 0;
    }

    public void sumarGanancia(int extra) {
        this.ganancia += extra;
    }

    public void sumarPerdida(int extra) {
        this.perdida += extra;
    }

    public int superavit() {
        return this.ganancia - this.perdida;
    }

}
