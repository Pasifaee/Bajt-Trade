package json_conversion.helper_classes;

public class StrategiaJson {

    public final String typ;
    public final double limit_diamentow;
    public final int zapas, okres, okresowosc_nauki;
    public final int historia_sredniej_produkcji, historia_perspektywy;
    public final int liczba_narzedzi;

    public StrategiaJson(String typ, double limit_diamentow, int zapas, int okres, int okresowosc_nauki,
                         int historia_sredniej_produkcji, int historia_perspektywy, int liczba_narzedzi) {
        this.typ = typ;
        this.limit_diamentow = limit_diamentow;
        this.zapas = zapas;
        this.okres = okres;
        this.okresowosc_nauki = okresowosc_nauki;
        this.historia_sredniej_produkcji = historia_sredniej_produkcji;
        this.historia_perspektywy = historia_perspektywy;
        this.liczba_narzedzi = liczba_narzedzi;
    }
}
