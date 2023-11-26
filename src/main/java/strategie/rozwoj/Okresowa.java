package strategie.rozwoj;

import agenci.Robotnik;
import gielda.Historia;
import stale.Stale;

public class Okresowa implements StrategiaRozwoju {

    public final String typ = Stale.okresowy;
    public final int okres;

    private Okresowa(int okres) {
        this.okres = okres;
    }

    public static StrategiaRozwoju stworz(int okres) {
        return new Okresowa(okres);
    }

    @Override
    public boolean czySieUczy(Robotnik r, Historia h) {
        return h.dajDzien() % okres == 0;
    }

}
