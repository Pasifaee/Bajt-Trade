package strategie.kupowanie;

import agenci.Robotnik;
import gielda.Historia;

public interface StrategiaKupowania {

    int[] coKupuje(Robotnik r, Historia h);

}
