package json_conversion.helper_classes;

import gielda.gielda.Gielda;

public class Info {

    public final int dlugosc;
    public final Gielda gielda;
    public final int kara_za_brak_ubran;
    public final double[] ceny;

    public Info(int dlugosc, Gielda gielda, int kara_za_brak_ubran, double[] ceny) {
        this.dlugosc = dlugosc;
        this.gielda = gielda;
        this.kara_za_brak_ubran = kara_za_brak_ubran;
        this.ceny = ceny;
    }

}
