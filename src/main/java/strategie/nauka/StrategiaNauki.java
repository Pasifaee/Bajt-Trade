package strategie.nauka;

import agenci.Robotnik;
import gielda.Historia;
import zasoby.Produkt;

public interface StrategiaNauki {

    Produkt nowaSciezka(Robotnik r, Historia h);

}
