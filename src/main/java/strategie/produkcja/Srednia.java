package strategie.produkcja;

import agenci.Robotnik;
import gielda.Historia;
import stale.Stale;
import zasoby.Produkt;

public class Srednia implements StrategiaProdukcji {

    public final String typ = Stale.sredniak;
    public final int historia_sredniej_produkcji;

    private Srednia(int historiaSredniejProdukcji) {
        this.historia_sredniej_produkcji = historiaSredniejProdukcji;
    }

    public static StrategiaProdukcji stworz(int historiaSredniejProdukcji) {
        return new Srednia(historiaSredniejProdukcji);
    }

    @Override
    public Produkt coProdukuje(Robotnik r, Historia h) {
        double[] maxCeny = new double[4];
        for (int i = 0; i < 4; i++) {
            maxCeny[i] = h.maxCena(Produkt.dajProdukt(i), historia_sredniej_produkcji);
        }
        return Historia.maxProdukt(maxCeny);
    }

}
