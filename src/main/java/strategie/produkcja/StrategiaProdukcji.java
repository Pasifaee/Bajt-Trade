package strategie.produkcja;

import agenci.Robotnik;
import gielda.Historia;
import zasoby.Produkt;

public interface StrategiaProdukcji {

    Produkt coProdukuje(Robotnik r, Historia h);

}
