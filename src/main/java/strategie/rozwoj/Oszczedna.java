package strategie.rozwoj;

import agenci.Robotnik;
import gielda.Historia;
import stale.Stale;

public class Oszczedna implements StrategiaRozwoju {

    public final String typ = Stale.oszczedny;
    public final double limit_diamentow;

    private Oszczedna(double limitDiamentow) {
        this.limit_diamentow = limitDiamentow;
    }

    public static StrategiaRozwoju stworz(double limitDiamentow) {
        return new Oszczedna(limitDiamentow);
    }

    @Override
    public boolean czySieUczy(Robotnik r, Historia h) { return r.diamenty() > limit_diamentow; }
}
