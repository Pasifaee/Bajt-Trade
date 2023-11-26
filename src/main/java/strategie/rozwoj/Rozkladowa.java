package strategie.rozwoj;

import agenci.Robotnik;
import com.squareup.moshi.Json;
import gielda.Historia;
import stale.Stale;

import java.util.Random;

public class Rozkladowa implements StrategiaRozwoju {

    public final String typ = Stale.rozkladowy;
    @Json(ignore = true)
    private static final StrategiaRozwoju STRATEGIA_ROZKLADOWA = new Rozkladowa();

    public static StrategiaRozwoju stworz() {
        return STRATEGIA_ROZKLADOWA;
    }

    @Override
    public boolean czySieUczy(Robotnik r, Historia h) {
        Random rand = new Random();
        return rand.nextInt(1, 101) < 100 / (h.dajDzien() + 3);
    }
}
