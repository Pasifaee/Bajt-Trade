package json_conversion.adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;
import strategie.rozwoj.*;

public class StrategiaRozwojuAdapter {

    private Moshi moshi;

    public void setMoshi(Moshi moshi) {
        this.moshi = moshi;
    }

    @ToJson
    String strategiaJson(StrategiaRozwoju strategiaRozwoju) {
        switch (strategiaRozwoju.getClass().getSimpleName()) {
            case "Okresowa":
                return moshi.adapter(Okresowa.class).toJson((Okresowa) strategiaRozwoju);
            case "Oszczedna":
                return moshi.adapter(Oszczedna.class).toJson((Oszczedna) strategiaRozwoju);
            case "Pracusiowa":
                return moshi.adapter(Pracusiowa.class).toJson((Pracusiowa) strategiaRozwoju);
            case "Rozkladowa":
                return moshi.adapter(Rozkladowa.class).toJson((Rozkladowa) strategiaRozwoju);
            case "Studencka":
                return moshi.adapter(Studencka.class).toJson((Studencka) strategiaRozwoju);
            default:
                assert false;
                return null;
        }

    }

    @FromJson
    StrategiaRozwoju strategiaRozwoju(String strategiaJson) {
        return null;
    }
}
