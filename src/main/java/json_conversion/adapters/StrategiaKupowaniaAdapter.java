package json_conversion.adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;
import strategie.kupowanie.*;

public class StrategiaKupowaniaAdapter {

    private Moshi moshi;

    public void setMoshi(Moshi moshi) {
        this.moshi = moshi;
    }

    @ToJson
    String strategiaJson(StrategiaKupowania strategiaKupowania) {
        switch (strategiaKupowania.getClass().getSimpleName()) {
            case "Czysta":
                return moshi.adapter(Czysta.class).toJson((Czysta) strategiaKupowania);
            case "Gadzetowa":
                return moshi.adapter(Gadzetowa.class).toJson((Gadzetowa) strategiaKupowania);
            case "Technofobiczna":
                return moshi.adapter(Technofobiczna.class).toJson((Technofobiczna) strategiaKupowania);
            case "Zmechanizowana":
                return moshi.adapter(Zmechanizowana.class).toJson((Zmechanizowana) strategiaKupowania);
            default:
                assert false;
                return null;
        }
    }

    @FromJson
    StrategiaKupowania strategiaKupowania(String strategiaJson) {
        return null;
    }
}
