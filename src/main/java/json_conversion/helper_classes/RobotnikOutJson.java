package json_conversion.helper_classes;

import strategie.kupowanie.StrategiaKupowania;
import strategie.produkcja.StrategiaProdukcji;
import strategie.rozwoj.StrategiaRozwoju;

public class RobotnikOutJson {

    public final int id;
    public final int poziom;
    public final String kariera;
    public final StrategiaRozwoju uczenie;
    public final StrategiaProdukcji produkcja;
    public final StrategiaKupowania kupowanie;
    public final String zmiana;
    public final ProduktyJson produktywnosc;
    public final ProduktyOutJson zasoby;

    public RobotnikOutJson(int id, int poziom, String kariera, StrategiaRozwoju uczenie, StrategiaProdukcji produkcja,
                           StrategiaKupowania kupowanie, String zmiana, ProduktyJson produktywnosc, ProduktyOutJson zasoby) {
        this.id = id;
        this.poziom = poziom;
        this.kariera = kariera;
        this.uczenie = uczenie;
        this.produkcja = produkcja;
        this.kupowanie = kupowanie;
        this.zmiana = zmiana;
        this.produktywnosc = produktywnosc;
        this.zasoby = zasoby;
    }
}
