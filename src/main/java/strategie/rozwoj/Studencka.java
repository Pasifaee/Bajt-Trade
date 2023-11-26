package strategie.rozwoj;

import agenci.Robotnik;
import gielda.Historia;
import stale.Stale;

import static zasoby.Produkt.JEDZENIE;

public class Studencka implements StrategiaRozwoju {

    public final String typ = Stale.student;
    public final int zapas;
    public final int okres;

    private Studencka(int zapas, int okres) {
        this.zapas = zapas;
        this.okres = okres;
    }

    public static StrategiaRozwoju stworz(int zapas, int okres) {
        return new Studencka(zapas, okres);
    }

    @Override
    public boolean czySieUczy(Robotnik r, Historia h) {
        return r.diamenty() >= 100 * zapas * h.sredniaCena(JEDZENIE, okres);
    }

}
