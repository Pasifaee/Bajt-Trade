package json_conversion.adapters;

import agenci.Spekulant;
import agenci.SpekulantRegulujacy;
import agenci.SpekulantSredni;
import agenci.SpekulantWypukly;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import json_conversion.helper_classes.ProduktyOutJson;
import json_conversion.helper_classes.SpekulantJson;
import json_conversion.helper_classes.SpekulantOutJson;
import zasoby.Dobra;
import zasoby.Produkt;
import zasoby.Zasoby;

import java.util.ArrayList;
import java.util.List;

public class SpekulantAdapter {

    @FromJson
    Spekulant spekulantFromJson(SpekulantJson spekulantJson) {
        double[] zasobyTab = new double[]{spekulantJson.zasoby.jedzenie, spekulantJson.zasoby.ubrania,
                spekulantJson.zasoby.narzedzia, spekulantJson.zasoby.programy, spekulantJson.zasoby.diamenty};
        Zasoby zasoby = new Zasoby(zasobyTab);

        switch (spekulantJson.kariera.typ) {
            case "sredni":
                return new SpekulantSredni(spekulantJson.id, zasoby,
                        spekulantJson.kariera.historia_spekulanta_sredniego);
            case "wypukly":
                return new SpekulantWypukly(spekulantJson.id, zasoby);
            case "regulujacy_rynek":
                return new SpekulantRegulujacy(spekulantJson.id, zasoby);
            default:
                assert false;
                return null;
        }
    }

    @ToJson
    SpekulantOutJson spekulantToJson(Spekulant spekulant) {
        List<Dobra> ubraniaDobra = spekulant.zasoby().dajProdukt(Produkt.UBRANIA);
        List<Integer> ubrania = produkty(ubraniaDobra);
        List<Dobra> narzedziaDobra = spekulant.zasoby().dajProdukt(Produkt.NARZEDZIA);
        List<Integer> narzedzia = produkty(narzedziaDobra);
        List<Dobra> programyDobra = spekulant.zasoby().dajProdukt(Produkt.PROGRAMY);
        List<Integer> programy = produkty(programyDobra);
        ProduktyOutJson zasoby = new ProduktyOutJson(
                spekulant.diamenty(),
                spekulant.zasoby().dajProdukt(Produkt.JEDZENIE).get(0).ile(),
                ubrania, narzedzia, programy);
        return new SpekulantOutJson(spekulant.id, spekulant.karieraJson(), zasoby);
    }

    List<Integer> produkty(List<Dobra> d) {
        List<Integer> wynik = new ArrayList<>();
        for (Dobra dobra : d) {
            assert dobra != null;
            wynik.add(dobra.ile());
        }
        for (int i = d.size() - 1; i >= 0; i--) {
            if (wynik.get(i) != 0)
                break;
            else
                wynik.remove(i);
        }
        return wynik;
    }

}
