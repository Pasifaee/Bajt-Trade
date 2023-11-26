package json_conversion.helper_classes;

import java.util.List;

public class ProduktyOutJson {

    public final double diamenty;
    public final int jedzenie;
    public final List<Integer> ubrania, narzedzia, programy;

    public ProduktyOutJson(double diamenty, int jedzenie, List<Integer> ubrania, List<Integer> narzedzia, List<Integer> programy) {
        this.diamenty = diamenty;
        this.jedzenie = jedzenie;
        this.ubrania = ubrania;
        this.narzedzia = narzedzia;
        this.programy = programy;
    }

}
