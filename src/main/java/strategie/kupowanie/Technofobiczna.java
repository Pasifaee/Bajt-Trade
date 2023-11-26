package strategie.kupowanie;

import agenci.Robotnik;
import com.squareup.moshi.Json;
import gielda.Historia;
import stale.Stale;

public class Technofobiczna implements StrategiaKupowania {

    public final String typ = Stale.technofob;
    @Json(ignore = true)
    private final static StrategiaKupowania STRATEGIA_TECHNOFOBICZNA = new Technofobiczna();

    public static StrategiaKupowania stworz() { return STRATEGIA_TECHNOFOBICZNA; }

    @Override
    public int[] coKupuje(Robotnik r, Historia h) {
        return new int[]{100, 0, 0, 0};
    }

}
