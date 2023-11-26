package strategie.produkcja;

import agenci.Robotnik;
import gielda.Historia;
import stale.Stale;
import zasoby.Produkt;

public class Perspektywiczna implements StrategiaProdukcji {

    public final String typ = Stale.perspektywiczny;
    public final int historia_perspektywy;

    private Perspektywiczna(int historiaPerspektywy) {
        this.historia_perspektywy = historiaPerspektywy;
    }

    public static StrategiaProdukcji stworz(int historiaPerspektywy) {
        return new Perspektywiczna(historiaPerspektywy);
    }

    @Override
    public Produkt coProdukuje(Robotnik r, Historia h) {
        double[] wzrost = new double[4];
        for (int i = 0; i < 4; i++) {
            wzrost[i] = h.srednieCenyKiedys(1)[i]
                    - h.srednieCenyKiedys(historia_perspektywy + 1)[i];
        }
        return Historia.maxProdukt(wzrost);
    }
}
