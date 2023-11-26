package strategie.nauka;

import agenci.Robotnik;
import gielda.Historia;
import stale.Stale;
import zasoby.Produkt;

public class Konserwatywna implements StrategiaNauki {

    private static final StrategiaNauki STRATEGIA_KONSERWATYWNA = new Konserwatywna();

    public static StrategiaNauki stworz() {
        return STRATEGIA_KONSERWATYWNA;
    }

    @Override
    public Produkt nowaSciezka(Robotnik r, Historia h) {
        return r.kariera();
    }

    @Override
    public String toString() { return Stale.konserwatysta; }
}
