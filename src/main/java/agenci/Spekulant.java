package agenci;

import gielda.Historia;
import gielda.gielda.Gielda;
import json_conversion.helper_classes.KarieraJson;
import zasoby.Dobra;
import zasoby.Produkt;
import zasoby.Zasoby;

public abstract class Spekulant {

    public final int id;
    protected final Zasoby zasoby;
    protected Gielda gielda;

    protected Spekulant(int id, Zasoby zasoby) {
        this.id = id;
        this.zasoby = zasoby;
    }

    public abstract KarieraJson karieraJson();

    public void ustawGielde(Gielda g) { this.gielda = g; }

    public Zasoby zasoby() { return zasoby; }

    public double diamenty() { return zasoby.diamenty(); }

    public void zarob(double diamenty) { zasoby.dodajDiamenty(diamenty); }

    public void zaplac(double diamenty) { zasoby.odejmijDiamenty(diamenty); }

    public void kup(Produkt produkt, Dobra dobra) {
        zasoby.dodajDobra(produkt, dobra, true);
    }

    public void sprzedaj(Produkt produkt, Dobra dobra) { zasoby.odejmijDobraSpekulanta(produkt, dobra); }

    public abstract void wystawOferty(Historia h);

}
