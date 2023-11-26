package json_conversion.helper_classes;

public class SpekulantOutJson {

    public final int id;
    public final KarieraJson kariera;
    public final ProduktyOutJson zasoby;

    public SpekulantOutJson(int id, KarieraJson kariera, ProduktyOutJson zasoby) {
        this.id = id;
        this.kariera = kariera;
        this.zasoby = zasoby;
    }

}
