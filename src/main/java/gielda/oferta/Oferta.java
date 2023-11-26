package gielda.oferta;

import zasoby.Produkt;

public abstract class Oferta {

    private final Produkt produkt;
    private int ilosc;
    private final int jakosc;

    public Oferta(Produkt produkt, int ilosc, int jakosc) {
        this.produkt = produkt;
        this.ilosc = ilosc;
        this.jakosc = jakosc;
    }

    public Produkt produkt() { return produkt; }

    public int produktNo() { return produkt.ordinal(); }

    public int ilosc() { return ilosc; }

    public int jakosc() { return jakosc; }

    public void zmniejszIlosc(int x) {
        ilosc -= x;
        assert ilosc >= 0;
    }

}
