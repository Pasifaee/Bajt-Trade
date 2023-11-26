package strategie.produkcja;

import agenci.Robotnik;
import com.squareup.moshi.Json;
import gielda.Historia;
import stale.Stale;
import zasoby.Produkt;

public class Chciwa implements StrategiaProdukcji {

    public final String typ = Stale.chciwy;
    @Json(ignore = true)
    private final static StrategiaProdukcji STRATEGIA_CHCIWA = new Chciwa();

    public static StrategiaProdukcji stworz() { return STRATEGIA_CHCIWA; }

    @Override
    public Produkt coProdukuje(Robotnik r, Historia h) {
        int[] ileWyprodukuje = new int[4];
        double[] srednieCeny = h.srednieCenyKiedys(1);
        double[] zysk = new double[4];
        for (int i = 0; i < 4; i++) {
            ileWyprodukuje[i] = r.ileWyprodukujesz(Produkt.dajProdukt(i));
            zysk[i] = ileWyprodukuje[i] * srednieCeny[i];
        }
        return Historia.maxProdukt(zysk);
    }

}
