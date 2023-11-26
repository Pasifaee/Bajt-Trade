package strategie.produkcja;

import agenci.Robotnik;
import com.squareup.moshi.Json;
import gielda.Historia;
import stale.Stale;
import zasoby.Produkt;

public class Krotkowzroczna implements StrategiaProdukcji {

    public final String typ = Stale.krotkowzroczny;
    @Json(ignore = true)
    private final static StrategiaProdukcji STRATEGIA_KROTKOWZROCZNA = new Krotkowzroczna();

    public static StrategiaProdukcji stworz() { return STRATEGIA_KROTKOWZROCZNA; }

    @Override
    public Produkt coProdukuje(Robotnik r, Historia h) {
        return Historia.maxProdukt(h.srednieCenyKiedys(1));
    }

}
