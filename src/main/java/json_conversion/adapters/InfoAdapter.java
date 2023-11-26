package json_conversion.adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import gielda.gielda.Gielda;
import gielda.gielda.Kapitalistyczna;
import gielda.gielda.Socjalistyczna;
import gielda.gielda.Zrownowazona;
import json_conversion.helper_classes.Info;
import json_conversion.helper_classes.InfoJson;

public class InfoAdapter {

    @FromJson
    Info infoFromJson(InfoJson infoJson) {
        Gielda gielda = null;
        switch (infoJson.gielda) {
            case "kapitalistyczna":
                gielda = new Kapitalistyczna();
                break;
            case "socjalistyczna":
                gielda = new Socjalistyczna();
                break;
            case "zrownowazona":
                gielda = new Zrownowazona();
                break;
            default:
                assert false;
                break;
        }
        double[] ceny = new double[]{infoJson.ceny.jedzenie, infoJson.ceny.ubrania,
                infoJson.ceny.narzedzia, infoJson.ceny.programy};
        return new Info(infoJson.dlugosc, gielda, infoJson.kara_za_brak_ubran, ceny);

    }

    @ToJson
    InfoJson infoToJson(Info info) {
        return null;
    }

}
