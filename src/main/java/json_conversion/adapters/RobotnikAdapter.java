package json_conversion.adapters;

import agenci.Robotnik;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import json_conversion.helper_classes.ProduktyJson;
import json_conversion.helper_classes.ProduktyOutJson;
import json_conversion.helper_classes.RobotnikJson;
import json_conversion.helper_classes.RobotnikOutJson;
import stale.Stale;
import zasoby.Dobra;
import zasoby.Produkt;
import zasoby.Zasoby;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class RobotnikAdapter {

    @FromJson
    Robotnik robotnikFromJson(RobotnikJson robotnikJson) {
        Produkt kariera = null;
        switch (robotnikJson.kariera) {
            case "rolnik":
                kariera = Produkt.JEDZENIE;
                break;
            case "rzemieslnik":
                kariera = Produkt.UBRANIA;
                break;
            case "inzynier":
                kariera = Produkt.NARZEDZIA;
                break;
            case "gornik":
                kariera = Produkt.DIAMENTY;
                break;
            case "programista":
                kariera = Produkt.PROGRAMY;
                break;
            default:
                assert false;
        }
        int[] produktywnosc = new int[]{(int) robotnikJson.produktywnosc.jedzenie,
                (int) robotnikJson.produktywnosc.ubrania, (int) robotnikJson.produktywnosc.narzedzia,
                (int) robotnikJson.produktywnosc.programy, (int) robotnikJson.produktywnosc.diamenty,};
        double[] zasobyTab = new double[]{robotnikJson.zasoby.jedzenie, robotnikJson.zasoby.ubrania,
                robotnikJson.zasoby.narzedzia, robotnikJson.zasoby.programy, robotnikJson.zasoby.diamenty,};
        Zasoby zasoby = new Zasoby(zasobyTab);

        Robotnik robotnik = new Robotnik(robotnikJson.id, zasoby, produktywnosc, kariera, robotnikJson.poziom);
        skopiujStrategieRozwoju(robotnik, robotnikJson);
        skopiujStrategieNauki(robotnik, robotnikJson);
        skopiujStrategieProdukcji(robotnik, robotnikJson);
        skopiujStrategieKupowania(robotnik, robotnikJson);
        return robotnik;
    }

    private void skopiujStrategieRozwoju(Robotnik r, RobotnikJson rj) {
        switch (rj.uczenie.typ) {
            case Stale.pracus:
                r.ustawStrategieRozwoju(Stale.pracus);
                break;
            case Stale.oszczedny:
                r.ustawStrategieRozwoju(Stale.oszczedny, rj.uczenie.limit_diamentow);
                break;
            case Stale.student:
                r.ustawStrategieRozwoju(Stale.student, rj.uczenie.zapas, rj.uczenie.okres);
                break;
            case Stale.okresowy:
                r.ustawStrategieRozwoju(Stale.okresowy, rj.uczenie.okresowosc_nauki);
                break;
            case Stale.rozkladowy:
                r.ustawStrategieRozwoju(Stale.rozkladowy);
                break;
            default:
                assert false;
        }
    }

    private void skopiujStrategieNauki(Robotnik r, RobotnikJson rj) {
        switch (rj.zmiana) {
            case Stale.konserwatysta:
                r.ustawStrategieNauki(Stale.konserwatysta);
                break;
            case Stale.rewolucjonista:
                r.ustawStrategieNauki(Stale.rewolucjonista);
                break;
            default:
                assert false;
        }
    }

    private void skopiujStrategieProdukcji(Robotnik r, RobotnikJson rj) {
        switch (rj.produkcja.typ) {
            case Stale.krotkowzroczny:
                r.ustawStrategieProdukcji(Stale.krotkowzroczny);
                break;
            case Stale.chciwy:
                r.ustawStrategieProdukcji(Stale.chciwy);
                break;
            case Stale.sredniak:
                r.ustawStrategieProdukcji(Stale.sredniak, rj.produkcja.historia_sredniej_produkcji);
                break;
            case Stale.perspektywiczny:
                r.ustawStrategieProdukcji(Stale.perspektywiczny, rj.produkcja.historia_perspektywy);
                break;
            case Stale.losowy:
                r.ustawStrategieProdukcji(Stale.losowy);
                break;
            default:
                assert false;
        }
    }

    private void skopiujStrategieKupowania(Robotnik r, RobotnikJson rj) {
        switch (rj.kupowanie.typ) {
            case Stale.technofob:
                r.ustawStrategieKupowania(Stale.technofob);
                break;
            case Stale.czyscioszek:
                r.ustawStrategieKupowania(Stale.czyscioszek);
                break;
            case Stale.zmechanizowany:
                r.ustawStrategieKupowania(Stale.zmechanizowany, rj.kupowanie.liczba_narzedzi);
                break;
            case Stale.gadzeciarz:
                r.ustawStrategieKupowania(Stale.gadzeciarz, rj.kupowanie.liczba_narzedzi);
                break;
            default:
                assert false;
        }
    }

    @ToJson
    RobotnikOutJson robotnikToJson(Robotnik robotnik) {
        int[] produktywnosc_ = robotnik.produktywnosc();
        ProduktyJson produktywnosc = new ProduktyJson(produktywnosc_[0], produktywnosc_[1], produktywnosc_[2],
                produktywnosc_[4], produktywnosc_[3]);
        ProduktyOutJson zasoby = new ProduktyOutJson(robotnik.diamenty(),
                robotnik.zasoby().dajProdukt(Produkt.JEDZENIE).get(0).ile(),
                produkty(porzadkujUbrania(robotnik.zasoby().dajProdukt(Produkt.UBRANIA))),
                produkty(robotnik.zasoby().dajProdukt(Produkt.NARZEDZIA)),
                produkty(robotnik.zasoby().dajProdukt(Produkt.PROGRAMY)));
        return new RobotnikOutJson(robotnik.id, robotnik.poziomKariery(), robotnik.karieraString(),
                robotnik.strategiaRozwoju(), robotnik.strategiaProdukcji(), robotnik.strategiaKupowania(),
                robotnik.strategiaNauki(), produktywnosc, zasoby);
    }

    private int maxJakosc(List<Dobra> d) {
        int max = 0;
        for (Dobra dobra : d) {
            max = max(max, dobra.jakosc);
        }
        return max;
    }

    private List<Dobra> porzadkujUbrania(List<Dobra> ubrania) {
        List<Dobra> noweUbrania = new ArrayList<>();
        for (int i = 1; i <= maxJakosc(ubrania); i++) {
            noweUbrania.add(new Dobra(0, i));
        }
        for (Dobra u : ubrania) {
            noweUbrania.get(u.jakosc - 1).dodaj(u.ile());
        }
        return noweUbrania;
    }
    private List<Integer> produkty(List<Dobra> d) {
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
