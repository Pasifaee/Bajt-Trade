package strategie.rozwoj;

import agenci.Robotnik;
import com.squareup.moshi.Json;
import gielda.Historia;
import stale.Stale;

public class Pracusiowa implements StrategiaRozwoju {

    public final String typ = Stale.pracus;
    @Json(ignore = true)
    private static final StrategiaRozwoju STRATEGIA_PRACUSIOWA = new Pracusiowa();

    public static StrategiaRozwoju stworz() {
        return STRATEGIA_PRACUSIOWA;
    }

    @Override
    public boolean czySieUczy(Robotnik r, Historia h) {
        return false;
    }

}
