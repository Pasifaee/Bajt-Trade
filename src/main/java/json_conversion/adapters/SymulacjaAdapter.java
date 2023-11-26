package json_conversion.adapters;

import agenci.Robotnik;
import com.squareup.moshi.*;
import json_conversion.helper_classes.CenyJson;
import json_conversion.helper_classes.InfoOutJson;
import json_conversion.helper_classes.SymulacjaJson;
import json_conversion.helper_classes.SymulacjaOutJson;
import symulacja.Symulacja;

import java.util.List;

public class SymulacjaAdapter {

    @FromJson
    Symulacja symulacjaFromJson(SymulacjaJson symulacjaJson) {
        return new Symulacja(symulacjaJson.info.dlugosc, symulacjaJson.robotnicy, symulacjaJson.spekulanci,
                symulacjaJson.info.gielda, symulacjaJson.info.ceny, symulacjaJson.info.kara_za_brak_ubran);
    }

    @ToJson
    SymulacjaOutJson symulacjaToJson(Symulacja symulacja) {
        double[] cenySrednie = symulacja.historia().srednieCenyKiedys(0);
        double[] cenyMin = symulacja.historia().dajCenyMin();
        double[] cenyMax = symulacja.historia().dajCenyMax();
        InfoOutJson info = new InfoOutJson(symulacja.historia().dajDzien(), ceny(cenySrednie),
                ceny(cenyMax), ceny(cenyMin));
        List<Robotnik> robotnicy = symulacja.robotnicy();
        robotnicy.addAll(symulacja.martwi());
        return new SymulacjaOutJson(info, symulacja.robotnicy(), symulacja.spekulanci());
    }

    private CenyJson ceny(double[] ceny) {
        return new CenyJson(ceny[0], ceny[1], ceny[2], ceny[3]);
    }

}
