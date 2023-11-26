package json_conversion.helper_classes;

public class SpekulantJson {

    public final int id;
    public final KarieraJson kariera;
    public final ProduktyJson zasoby;

    public SpekulantJson(int id, KarieraJson kariera, ProduktyJson zasoby) {
        this.id = id;
        this.kariera = kariera;
        this.zasoby = zasoby;
    }

}
