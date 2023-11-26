package strategie.kupowanie;

import agenci.Robotnik;
import gielda.Historia;
import stale.Stale;

public class Zmechanizowana implements StrategiaKupowania {

    public final String typ = Stale.zmechanizowany;
    public final int liczba_narzedzi;

    private Zmechanizowana(int liczbaNarzedzi) {
        this.liczba_narzedzi = liczbaNarzedzi;
    }

    public static StrategiaKupowania stworz(int liczbaNarzedzi) { return new Zmechanizowana(liczbaNarzedzi); }

    @Override
    public int[] coKupuje(Robotnik r, Historia h) {
        return new int[]{100, r.ileUbranKupic(), liczba_narzedzi, 0};
    }
}
