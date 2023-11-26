package json_conversion.helper_classes;

public class InfoOutJson {

    public final int dzien;
    public final CenyJson ceny_srednie, ceny_max, ceny_min;

    public InfoOutJson(int dzien, CenyJson ceny_srednie, CenyJson ceny_max, CenyJson ceny_min) {
        this.dzien = dzien;
        this.ceny_srednie = ceny_srednie;
        this.ceny_max = ceny_max;
        this.ceny_min = ceny_min;
    }

}
