package strategie.nauka;

import agenci.Robotnik;
import gielda.Historia;
import stale.Stale;
import zasoby.Produkt;

import static java.lang.Math.max;

public class Rewolucjonistyczna implements StrategiaNauki {

    private static final StrategiaNauki STRATEGIA_REWOLUCJONISTYCZNA = new Rewolucjonistyczna();

    public static StrategiaNauki stworz() {
        return STRATEGIA_REWOLUCJONISTYCZNA;
    }

    @Override
    public Produkt nowaSciezka(Robotnik r, Historia h) {
        int n = max(1, r.id % 17);
        int maxProdukt = h.najczestszyProdukt(n);
        return Produkt.dajProdukt(maxProdukt);
    }

    @Override
    public String toString() { return Stale.rewolucjonista; }
}
