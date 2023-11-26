package gielda.oferta;

import agenci.Spekulant;
import zasoby.Produkt;

public class OfertaSpekulanta extends Oferta {

    private final double cena;
    private final Spekulant spekulant;

    public OfertaSpekulanta(Produkt produkt, int ilosc, int jakosc, double cena, Spekulant spekulant) {
        super(produkt, ilosc, jakosc);
        this.cena = cena;
        this.spekulant = spekulant;
    }

    public double cena() { return cena; }
    public Spekulant spekulant() { return spekulant; }

}
