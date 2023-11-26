package json_conversion.helper_classes;

public class InfoJson {

    public final int dlugosc;
    public final String gielda;
    public final int kara_za_brak_ubran;
    public final CenyJson ceny;

    public InfoJson(int dlugosc, String gielda, int kara_za_brak_ubran, CenyJson ceny) {
        this.dlugosc = dlugosc;
        this.gielda = gielda;
        this.kara_za_brak_ubran = kara_za_brak_ubran;
        this.ceny = ceny;
    }

}
