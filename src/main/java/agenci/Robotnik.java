package agenci;

import gielda.Historia;
import gielda.oferta.OfertaRobotnika;
import stale.Stale;
import strategie.kupowanie.*;
import strategie.nauka.Konserwatywna;
import strategie.nauka.Rewolucjonistyczna;
import strategie.nauka.StrategiaNauki;
import strategie.produkcja.*;
import strategie.rozwoj.*;
import zasoby.Dobra;
import zasoby.Produkt;
import zasoby.Zasoby;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.min;

public class Robotnik {

    public final int id;
    private final Zasoby zasoby;
    private final int[] produktywnosc;
    private final int[] dzisiejszePremie = new int[5];
    private Produkt kariera;
    private final int[] poziomKariery = new int[5];
    private Produkt dzisiejszyProdukt;
    private boolean dzisiajSieUczyl;

    private StrategiaRozwoju strategiaRozwoju = null;
    private StrategiaNauki strategiaNauki = null;
    private StrategiaProdukcji strategiaProdukcji = null;
    private StrategiaKupowania strategiaKupowania = null;

    private List<OfertaRobotnika> ofertySprzedazy;
    private List<OfertaRobotnika> ofertyKupna;

    private int ileNieJadl = 0;
    private boolean nieMialUbran = false;

    public Robotnik(int id, Zasoby zasoby, int[] produktywnosc, Produkt kariera, int poziom) {
        this.id = id;
        this.zasoby = zasoby;
        assert produktywnosc.length == 5;
        this.produktywnosc = produktywnosc;
        this.kariera = kariera;
        Arrays.fill(poziomKariery, 1);
        poziomKariery[kariera.ordinal()] = poziom;
    }

    public void ustawStrategieRozwoju(String strategia, double... parametry) {
        switch (strategia) {
            case Stale.okresowy:
                strategiaRozwoju = Okresowa.stworz((int) parametry[0]);
                break;
            case Stale.oszczedny:
                strategiaRozwoju = Oszczedna.stworz(parametry[0]);
                break;
            case Stale.pracus:
                strategiaRozwoju = Pracusiowa.stworz();
                break;
            case Stale.rozkladowy:
                strategiaRozwoju = Rozkladowa.stworz();
                break;
            case Stale.student:
                strategiaRozwoju = Studencka.stworz((int) parametry[0], (int) parametry[1]);
                break;
            default:
                assert false;
        }
    }

    public void ustawStrategieNauki(String strategia) {
        switch (strategia) {
            case Stale.konserwatysta:
                strategiaNauki = Konserwatywna.stworz();
                break;
            case Stale.rewolucjonista:
                strategiaNauki = Rewolucjonistyczna.stworz();
                break;
            default:
                assert false;
        }
    }

    public void ustawStrategieProdukcji(String strategia, double... parametry) {
        switch (strategia) {
            case Stale.chciwy:
                strategiaProdukcji = Chciwa.stworz();
                break;
            case Stale.krotkowzroczny:
                strategiaProdukcji = Krotkowzroczna.stworz();
                break;
            case Stale.losowy:
                strategiaProdukcji = Losowa.stworz();
                break;
            case Stale.perspektywiczny:
                strategiaProdukcji = Perspektywiczna.stworz((int) parametry[0]);
                break;
            case Stale.sredniak:
                strategiaProdukcji = Srednia.stworz((int) parametry[0]);
                break;
            default:
                assert false;
        }
    }

    public void ustawStrategieKupowania(String strategia, double... parametry) {
        switch (strategia) {
            case Stale.czyscioszek:
                strategiaKupowania = Czysta.stworz();
                break;
            case Stale.gadzeciarz:
                strategiaKupowania = Gadzetowa.stworz((int) parametry[0]);
                break;
            case Stale.technofob:
                strategiaKupowania = Technofobiczna.stworz();
                break;
            case Stale.zmechanizowany:
                strategiaKupowania = Zmechanizowana.stworz((int) parametry[0]);
                break;
            default:
                assert false;
        }
    }

    public int id() { return id; }

    public double diamenty() { return zasoby.diamenty(); }

    public Zasoby zasoby() { return zasoby; }

    public void zarob(double diamenty) { zasoby.dodajDiamenty(diamenty); }

    public void zaplac(double diamenty) { zasoby.odejmijDiamenty(diamenty); }

    public void kup(Produkt produkt, Dobra dobra) {
        zasoby.dodajDobra(produkt, dobra, false);
    }

    public int[] produktywnosc() { return produktywnosc; }

    public Produkt kariera() { return kariera; }

    public int poziomKariery() { return poziomKariery[kariera.ordinal()]; }

    public String karieraString() {
        switch (kariera) {
            case JEDZENIE:
                return "rolnik";
            case UBRANIA:
                return "rzemieslnik";
            case NARZEDZIA:
                return "inzynier";
            case DIAMENTY:
                return "gornik";
            case PROGRAMY:
                return "programista";
            default:
                assert false;
                return null;
        }
    }

    public List<OfertaRobotnika> ofertySprzedazy() { return ofertySprzedazy; }

    public List<OfertaRobotnika> ofertyKupna() { return ofertyKupna; }

    public String strategiaNauki() { return strategiaNauki+""; }

    public StrategiaRozwoju strategiaRozwoju() { return strategiaRozwoju; }

    public StrategiaProdukcji strategiaProdukcji() { return strategiaProdukcji; }

    public StrategiaKupowania strategiaKupowania() { return strategiaKupowania; }

    public boolean dzisiajSieUczyl() { return dzisiajSieUczyl; }

    public void resetujOferty() {
        ofertySprzedazy = new ArrayList<>();
        ofertyKupna = new ArrayList<>();
    }

    private void policzPremie(int karaZaBrakUbran) {
        Arrays.fill(dzisiejszePremie, 0);
        int premiaKarierowa;
        switch (poziomKariery[kariera.ordinal()]) {
            case 1 -> premiaKarierowa = 50;
            case 2 -> premiaKarierowa = 150;
            case 3 -> premiaKarierowa = 300;
            default -> {
                assert poziomKariery[kariera.ordinal()] > 3;
                premiaKarierowa = 300 + 25 * (poziomKariery[kariera.ordinal()] - 3);
            }
        }
        dzisiejszePremie[kariera.ordinal()] += premiaKarierowa;
        for (int i = 0; i < 5; i++) {
            if (ileNieJadl == 1)
                dzisiejszePremie[i] -= 100;
            else if (ileNieJadl == 2)
                dzisiejszePremie[i] -= 300;
            if (nieMialUbran)
                dzisiejszePremie[i] -= karaZaBrakUbran;
            for (Dobra narzedzia : zasoby.dajProdukt(Produkt.NARZEDZIA)) {
                dzisiejszePremie[i] += narzedzia.ile() * narzedzia.jakosc;
            }
        }

    }

    public int ileWyprodukujesz(Produkt p) {
        return (int) (produktywnosc[p.ordinal()] * (1 + (double) dzisiejszePremie[p.ordinal()] / 100));
    }

    public int ileUbranKupic() { return zasoby.ileUbranKupic(); }

    public Produkt dzisiejszyProdukt() { return dzisiejszyProdukt; }

    public void wystawOfertySprzedazy(Produkt produkt, List<Dobra> produkcja) {
        for (Dobra d : produkcja) {
            ofertySprzedazy.add(new OfertaRobotnika(produkt, d.ile(), d.jakosc, this));
        }
    }

    public void wystawOfertyKupna(int[] coKupic) {
        assert coKupic.length == 4;
        for (int i = 0; i < 4; i++) {
            if (coKupic[i] > 0) {
                ofertyKupna.add(new OfertaRobotnika(Produkt.dajProdukt(i), coKupic[i],
                        1, this)); // Jakość w ofercie kupna robotnika jest nieistotna.
            }
        }
    }

    public void usunOferteSprzedazy(OfertaRobotnika o) {
        boolean removed = ofertySprzedazy.remove(o);
        assert removed;
    }

    public void usunOferteKupna(OfertaRobotnika o) {
        boolean removed = ofertyKupna.remove(o);
        assert removed;
    }

    private List<Dobra> produkcjaUbranLubNarzedzi(boolean ubrania) {
        Produkt produkt;
        List<Dobra> produkcja = new ArrayList<>();
        if (ubrania)
            produkt = Produkt.UBRANIA;
        else
            produkt = Produkt.NARZEDZIA;
        List<Dobra> programyLista = zasoby.dajProdukt(Produkt.PROGRAMY);
        int doWyprodukowania = ileWyprodukujesz(produkt);
        for (int zaawansowanie = programyLista.size(); zaawansowanie >= 1; zaawansowanie--) {
            Dobra programy = programyLista.get(zaawansowanie - 1);
            if (programy.ile() > 0) {
                int ileProgramowZuzyjemy = min(programy.ile(), doWyprodukowania);
                programy.odejmij(ileProgramowZuzyjemy);
                produkcja.add(new Dobra(ileProgramowZuzyjemy, zaawansowanie));
                doWyprodukowania -= ileProgramowZuzyjemy;
            }
            assert doWyprodukowania >= 0;
            if (doWyprodukowania == 0)
                break;
        }
        return produkcja;
    }

    private List<Dobra> dzisiejszaProdukcja(Produkt dzisiejszyProdukt) {
        List<Dobra> dzisiejszaProdukcja = new ArrayList<>();
        switch (dzisiejszyProdukt) {
            case JEDZENIE:
                dzisiejszaProdukcja.add(new Dobra(ileWyprodukujesz(Produkt.JEDZENIE), 1));
                break;
            case UBRANIA:
                dzisiejszaProdukcja = produkcjaUbranLubNarzedzi(true);
                break;
            case NARZEDZIA:
                dzisiejszaProdukcja = produkcjaUbranLubNarzedzi(false);
                break;
            case PROGRAMY:
                dzisiejszaProdukcja.add(new Dobra(ileWyprodukujesz(Produkt.PROGRAMY),
                        kariera == Produkt.PROGRAMY ? poziomKariery[Produkt.PROGRAMY.ordinal()] : 1));
                break;
            case DIAMENTY:
                zarob(ileWyprodukujesz(Produkt.DIAMENTY));
                break;
            default:
                assert false;
        }
        return dzisiejszaProdukcja;
    }

    public boolean przezyjDzienPracy(Historia historia, int karaZaBrakUbran) {
        assert ileNieJadl <= 3;
        if (ileNieJadl == 3)
            return false;
        dzisiajSieUczyl = false;

        if (strategiaRozwoju.czySieUczy(this, historia)) {
            dzisiajSieUczyl = true;
            ileNieJadl = 0;
            Produkt nowaSciezka = strategiaNauki.nowaSciezka(this, historia);
            if (nowaSciezka == kariera) {
                poziomKariery[kariera.ordinal()]++;
            } else {
                kariera = nowaSciezka;
            }
        } else {
            policzPremie(karaZaBrakUbran);
            dzisiejszyProdukt = strategiaProdukcji.coProdukuje(this, historia);
            historia.dodajDoProdukcji(dzisiejszyProdukt, ileWyprodukujesz(dzisiejszyProdukt));
            List<Dobra> dzisiejszaProdukcja = dzisiejszaProdukcja(dzisiejszyProdukt);

            wystawOfertySprzedazy(dzisiejszyProdukt, dzisiejszaProdukcja);
            wystawOfertyKupna(strategiaKupowania.coKupuje(this, historia));
        }
        return true;
    }

    public void konsumuj() {
        if (!zasoby.zjedz())
            ileNieJadl++;
        zasoby.zuzyjNarzedzia();
        nieMialUbran = !zasoby.zuzyjUbrania();
    }

}
