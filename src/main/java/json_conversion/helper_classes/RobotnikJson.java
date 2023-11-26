package json_conversion.helper_classes;

public class RobotnikJson {

    public final int id;
    public final int poziom;
    public final String kariera;
    public final StrategiaJson uczenie, produkcja, kupowanie;
    public final String zmiana;
    public final ProduktyJson produktywnosc, zasoby;

    public RobotnikJson(int id, int poziom, String kariera, StrategiaJson uczenie, StrategiaJson produkcja,
                        StrategiaJson kupowanie, String zmiana, ProduktyJson produktywnosc, ProduktyJson zasoby) {
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
