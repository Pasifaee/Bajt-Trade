package strategie.kupowanie;

import agenci.Robotnik;
import gielda.Historia;
import stale.Stale;

public class Gadzetowa implements StrategiaKupowania {

    public final String typ = Stale.gadzeciarz;
    public final int liczba_narzedzi;

    private Gadzetowa(int liczbaNarzedzi) {
        this.liczba_narzedzi = liczbaNarzedzi;
    }

    public static StrategiaKupowania stworz(int liczbaNarzedzi) { return new Gadzetowa(liczbaNarzedzi); }

    @Override
    public int[] coKupuje(Robotnik r, Historia h) {
        return new int[]{100, r.ileUbranKupic(), liczba_narzedzi, r.ileWyprodukujesz(r.dzisiejszyProdukt())};
    }

}
