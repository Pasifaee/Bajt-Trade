package gielda.gielda;

import agenci.Robotnik;
import gielda.Historia;
import gielda.oferta.OfertaRobotnika;
import gielda.oferta.OfertaSpekulanta;
import zasoby.Dobra;
import zasoby.Produkt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.floor;
import static java.lang.Math.min;

public abstract class Gielda {

    private List<OfertaSpekulanta> ofertySprzedazySpekulantow = new ArrayList<>();
    private List<OfertaSpekulanta> ofertyKupnaSpekulantow = new ArrayList<>();
    private List<OfertaSpekulanta> zrealizowaneOferty = new ArrayList<>();

    public List<OfertaSpekulanta> zrealizowaneOferty() { return zrealizowaneOferty; }

    public void reset() {
        ofertySprzedazySpekulantow = new ArrayList<>();
        ofertyKupnaSpekulantow = new ArrayList<>();
        zrealizowaneOferty = new ArrayList<>();
    }

    public void dodajOfertySprzedazySpekulanta(List<OfertaSpekulanta> o) {
        ofertySprzedazySpekulantow.addAll(o);
    }
    public void dodajOfertyKupnaSpekulanta(List<OfertaSpekulanta> o) {
        ofertyKupnaSpekulantow.addAll(o);
    }

    private List<OfertaSpekulanta> sortujOfertySpekulantow(List<OfertaSpekulanta> oferty, boolean cenaRosnaco) {
        Comparator<OfertaSpekulanta> cmp_jakosc = Comparator
                .comparingInt(OfertaSpekulanta::jakosc);
        Comparator<OfertaSpekulanta> cmp_cena = Comparator
                .comparingDouble(OfertaSpekulanta::cena);
        if (!cenaRosnaco)
            cmp_cena = cmp_cena.reversed();
        Comparator<OfertaSpekulanta> cmp = Comparator
                .comparingInt(OfertaSpekulanta::produktNo)
                .thenComparing(cmp_jakosc.reversed())
                .thenComparing(cmp_cena);

        return oferty.stream()
                .sorted(cmp)
                .collect(Collectors.toList());
    }

    private List<OfertaSpekulanta> sortujOfertySprzedazySpekulantow(List<OfertaSpekulanta> oferty) {
        return sortujOfertySpekulantow(oferty, false);
    }

    private List<OfertaSpekulanta> sortujOfertyKupnaSpekulantow(List<OfertaSpekulanta> oferty) {
        return sortujOfertySpekulantow(oferty, true);
    }

    private List<OfertaSpekulanta> dajOfertyProduktu(Produkt p, List<OfertaSpekulanta> oferty) {
        List<OfertaSpekulanta> wynik = new ArrayList<>();
        for (OfertaSpekulanta o : oferty) {
            if (o.produkt().equals(p)) {
                wynik.add(o);
            }
        }
        return wynik;
    }

    private List<OfertaSpekulanta> dajOfertyProduktuZJakoscia(Produkt p, int jakosc, List<OfertaSpekulanta> oferty) {
        List<OfertaSpekulanta> wynik = new ArrayList<>();
        for (OfertaSpekulanta o : oferty) {
            if (o.produkt().equals(p) && o.jakosc() == jakosc) {
                wynik.add(o);
            }
        }
        return wynik;
    }

    private void usunOferteSprzedazy(OfertaSpekulanta o) {
        boolean removed = ofertySprzedazySpekulantow.remove(o);
        assert removed;
    }

    private void usunOferteKupna(OfertaSpekulanta o) {
        boolean removed = ofertyKupnaSpekulantow.remove(o);
        assert removed;
    }

    public List<Robotnik> sortujRobotnikow(List<Robotnik> robotnicy, boolean rosnaco) {
        Comparator<Robotnik> cmp = Comparator
                .comparingDouble(Robotnik::diamenty)
                .thenComparingInt(Robotnik::id);
        if (!rosnaco)
            cmp = cmp.reversed();
        return robotnicy.stream()
                .sorted(cmp)
                .collect(Collectors.toList());
    }

    public void zrealizujWymiane(List<Robotnik> robotnicy, Historia historia) {
        for (Robotnik r : robotnicy) {
            for (OfertaRobotnika ofertaSprzedazy : r.ofertySprzedazy()) {
                List<OfertaSpekulanta> ofertyKupna = dajOfertyProduktuZJakoscia(
                        ofertaSprzedazy.produkt(), ofertaSprzedazy.jakosc(), ofertyKupnaSpekulantow);
                ofertyKupna = sortujOfertyKupnaSpekulantow(ofertyKupna);
                zrealizujOferteSprzedazy(ofertaSprzedazy, ofertyKupna, historia);
            }
            for (OfertaRobotnika ofertaKupna : r.ofertyKupna()) {
                List<OfertaSpekulanta> ofertySprzedazy = dajOfertyProduktu(
                        ofertaKupna.produkt(), ofertySprzedazySpekulantow);
                ofertySprzedazy = sortujOfertySprzedazySpekulantow(ofertySprzedazy);
                zrealizujOferteKupna(ofertaKupna, ofertySprzedazy);
            }
        }
    }

    // Założenia:
    // Parametr oK to oferty kupna spekulantów które dotyczą tego samego produktu co oferta sprzedaży oS (z jakością)
    // Parametr oK to ODPOWIEDNIO posortowane oferty kupna
    private void zrealizujOferteSprzedazy(OfertaRobotnika ofertaSprzedazy, List<OfertaSpekulanta> ofertyKupna, Historia h) {
        for (OfertaSpekulanta ofertaKupna : ofertyKupna) {
            int naIleStacSpekulanta = (int) floor(ofertaKupna.spekulant().diamenty() / ofertaKupna.cena());
            int ileChceKupicSpekulant = min(naIleStacSpekulanta, ofertaKupna.ilosc());
            int ileKupujeSpekulant = min(ofertaSprzedazy.ilosc(), ileChceKupicSpekulant);
            assert ileKupujeSpekulant >= 0;
            if (ileKupujeSpekulant == 0)
                continue;

            zrealizowaneOferty.add(new OfertaSpekulanta(ofertaKupna.produkt(), ileKupujeSpekulant, ofertaKupna.jakosc(),
                    ofertaKupna.cena(), ofertaKupna.spekulant()));
            h.zaktualizujMinCenyKupna(ofertaKupna.produkt(), ofertaKupna.cena());

            ofertaKupna.zmniejszIlosc(ileKupujeSpekulant);
            if (ofertaKupna.ilosc() == 0)
                usunOferteKupna(ofertaKupna);
            ofertaKupna.spekulant().zaplac(ileKupujeSpekulant * ofertaKupna.cena());
            ofertaKupna.spekulant().kup(ofertaKupna.produkt(), new Dobra(ileKupujeSpekulant, ofertaKupna.jakosc()));

            ofertaSprzedazy.zmniejszIlosc(ileKupujeSpekulant);
            ofertaSprzedazy.robotnik().zarob(ileKupujeSpekulant * ofertaKupna.cena());
            if (ofertaSprzedazy.ilosc() == 0) {
                ofertaSprzedazy.robotnik().usunOferteSprzedazy(ofertaSprzedazy);
                return;
            }
        }
    }

    // Założenia: takie jak wyżej (bez jakości)
    private void zrealizujOferteKupna(OfertaRobotnika ofertaKupna, List<OfertaSpekulanta> ofertySprzedazy) {
        for (OfertaSpekulanta ofertaSprzedazy : ofertySprzedazy) {
            int naIleStacRobotnika = (int) floor(ofertaKupna.robotnik().diamenty() / ofertaSprzedazy.cena());
            int ileChceKupicRobotnik = min(naIleStacRobotnika, ofertaKupna.ilosc());
            int ileKupujeRobotnik = min(ileChceKupicRobotnik, ofertaSprzedazy.ilosc());
            assert ileKupujeRobotnik >= 0;
            if (ileKupujeRobotnik == 0)
                continue;

            zrealizowaneOferty.add(new OfertaSpekulanta(ofertaSprzedazy.produkt(), ileKupujeRobotnik,
                    ofertaSprzedazy.jakosc(), ofertaSprzedazy.cena(), ofertaSprzedazy.spekulant()));

            ofertaSprzedazy.zmniejszIlosc(ileKupujeRobotnik);
            if (ofertaSprzedazy.ilosc() == 0) {
                usunOferteSprzedazy(ofertaSprzedazy);
            }
            ofertaSprzedazy.spekulant().sprzedaj(ofertaSprzedazy.produkt(),
                    new Dobra(ileKupujeRobotnik, ofertaSprzedazy.jakosc()));
            ofertaSprzedazy.spekulant().zarob(ileKupujeRobotnik * ofertaSprzedazy.cena());

            ofertaKupna.zmniejszIlosc(ileKupujeRobotnik);
            ofertaKupna.robotnik().zaplac(ileKupujeRobotnik * ofertaSprzedazy.cena());
            ofertaKupna.robotnik().kup(ofertaSprzedazy.produkt(),
                    new Dobra(ileKupujeRobotnik, ofertaSprzedazy.jakosc()));
            if (ofertaKupna.ilosc() == 0) {
                ofertaKupna.robotnik().usunOferteKupna(ofertaKupna);
                return;
            }

        }
    }

    public void skupNiesprzedane(List<Robotnik> robotnicy, Historia historia) {
        for (Robotnik r : robotnicy) {
            for (OfertaRobotnika oferta : r.ofertySprzedazy()) {
                assert oferta.ilosc() > 0;
                double cena = historia.dajMinCeneKupnaWczoraj(oferta.produkt());
                if (cena == Double.POSITIVE_INFINITY)
                    cena = historia.srednieCenyKiedys(historia.dajDzien())[oferta.produktNo()];
                oferta.robotnik().zarob(cena * oferta.ilosc());
            }
        }
    }

}
