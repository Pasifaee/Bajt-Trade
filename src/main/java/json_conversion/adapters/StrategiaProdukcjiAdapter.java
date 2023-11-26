package json_conversion.adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;
import strategie.produkcja.*;

public class StrategiaProdukcjiAdapter {

    private Moshi moshi;

    public void setMoshi(Moshi moshi) {
        this.moshi = moshi;
    }

    @ToJson
    String strategiaJson(StrategiaProdukcji strategiaProdukcji) {
        switch (strategiaProdukcji.getClass().getSimpleName()) {
            case "Chciwa":
                return moshi.adapter(Chciwa.class).toJson((Chciwa) strategiaProdukcji);
            case "Krotkowzroczna":
                return moshi.adapter(Krotkowzroczna.class).toJson((Krotkowzroczna) strategiaProdukcji);
            case "Losowa":
                return moshi.adapter(Losowa.class).toJson((Losowa) strategiaProdukcji);
            case "Perspektywiczna":
                return moshi.adapter(Perspektywiczna.class).toJson((Perspektywiczna) strategiaProdukcji);
            case "Srednia":
                return moshi.adapter(Srednia.class).toJson((Srednia) strategiaProdukcji);
            default:
                assert false;
                return null;
        }
    }

    @FromJson
    StrategiaProdukcji strategiaProdukcji(String strategiaJson) {
        return null;
    }
}
