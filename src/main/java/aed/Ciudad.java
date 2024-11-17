package aed;

public class Ciudad {
    int id;
    int perdida;
    int ganancia;

    public Ciudad(int id) {
        this.id = id;
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
