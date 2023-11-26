package strategie.produkcja;

import agenci.Robotnik;
import com.squareup.moshi.Json;
import gielda.Historia;
import stale.Stale;
import zasoby.Produkt;

import java.util.Random;

public class Losowa implements StrategiaProdukcji {

    public final String typ = Stale.losowy;
    @Json(ignore = true)
    private final static StrategiaProdukcji STRATEGIA_LOSOWA = new Losowa();

    public static StrategiaProdukcji stworz() { return STRATEGIA_LOSOWA; }

    @Override
    public Produkt coProdukuje(Robotnik r, Historia h) {
        Random rand = new Random();
        return Produkt.dajProdukt(rand.nextInt(4));
    }
}
