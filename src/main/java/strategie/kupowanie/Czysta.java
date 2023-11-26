package strategie.kupowanie;

import agenci.Robotnik;
import com.squareup.moshi.Json;
import gielda.Historia;
import stale.Stale;

public class Czysta implements StrategiaKupowania {

    public final String typ = Stale.czyscioszek;
    @Json(ignore = true)
    private final static StrategiaKupowania STRATEGIA_CZYSTA = new Czysta();

    public static StrategiaKupowania stworz() { return STRATEGIA_CZYSTA; }

    @Override
    public int[] coKupuje(Robotnik r, Historia h) {
        return new int[]{100, r.ileUbranKupic(), 0, 0};
    }
}
