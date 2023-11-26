package zasoby;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class Zasoby {

    private final Dobra jedzenie;
    // U spekulantów pozycja na liście ubrań ma odpowiadać jakości ubrań.
    private final List<Dobra> ubrania;
    private double diamenty;
    // Pozycja na liście programów/narzędzi ma odpowiadać jakości programów/narzędzi.
    private List<Dobra> narzedzia;
    private final List<Dobra> programy;
    // W powyzszych listach nie ma być nulli

    public Zasoby(double[] zasoby) {
        assert zasoby.length == 5;
        jedzenie = new Dobra((int) zasoby[0]);
        ubrania = new ArrayList<>();
        ubrania.add(new Dobra((int) zasoby[1], 1));
        diamenty = zasoby[2];
        narzedzia = new ArrayList<>();
        narzedzia.add(new Dobra((int) zasoby[3], 1));
        programy = new ArrayList<>();
        programy.add(new Dobra((int) zasoby[4], 1));
    }

    public double diamenty() { return diamenty; }

    public void dodajDiamenty(double diamenty) {
        this.diamenty += diamenty;
    }

    public void odejmijDiamenty(double diamenty) {
        this.diamenty -= diamenty;
        assert this.diamenty >= 0;
    }

    public List<Dobra> dajProdukt(Produkt p) {
        List<Dobra> lista = new ArrayList<>();
        switch (p.ordinal()) {
            case 0:
                lista.add(jedzenie);
                return lista;
            case 1:
                return ubrania;
            case 2:
                return narzedzia;
            case 3:
                return programy;
            default:
                assert false;
                return null;
        }
    }

    public void dodajDobra(Produkt p, Dobra d, boolean czySpekulant) {
        switch (p.ordinal()) {
            case 0:
                jedzenie.dodaj(d.ile());
                break;
            case 1:
                if (czySpekulant) {
                    dodajDobra2(d, ubrania);
                } else {
                    ubrania.add(d);
                }
                break;
            case 2:
                dodajDobra2(d, narzedzia);
                break;
            case 3:
                dodajDobra2(d, programy);
                break;
            default:
                assert false;
        }
    }

    private void dodajDobra2(Dobra d, List<Dobra> narzedzia) {
        if (narzedzia.size() >= d.jakosc) {
            narzedzia.get(d.jakosc - 1).dodaj(d.ile());
        } else {
            for (int i = narzedzia.size(); i < d.jakosc; i++) {
                if (i == d.jakosc - 1)
                    narzedzia.add(d);
                else
                    narzedzia.add(new Dobra(0, i));
            }
        }
    }

    public void odejmijDobraSpekulanta(Produkt p, Dobra d) {
        switch (p.ordinal()) {
            case 0:
                assert jedzenie.ile() >= d.ile();
                jedzenie.odejmij(d.ile());
                break;
            case 1:
                assert ubrania.get(d.jakosc - 1).ile() >= d.ile();
                ubrania.get(d.jakosc - 1).odejmij(d.ile());
                break;
            case 2:
                assert narzedzia.get(d.jakosc - 1).ile() >= d.ile();
                narzedzia.get(d.jakosc - 1).odejmij(d.ile());
                break;
            case 3:
                assert programy.get(d.jakosc - 1).ile() >= d.ile();
                programy.get(d.jakosc - 1).odejmij(d.ile());
                break;
            default:
                assert false;
        }
    }

    public int ileUbranKupic() {
        int doKupienia = 0;
        for (Dobra u : ubrania) {
            if (u.zuzycie() == u.jakosc * u.jakosc - 1) {
                doKupienia += min(u.ile(), 100);
                if (doKupienia == 100)
                    break;
            }
        }
        return doKupienia;
    }

    public boolean zjedz() {
        int doZjedzenia = min(100, jedzenie.ile());
        jedzenie.odejmij(doZjedzenia);
        return doZjedzenia == 100;
    }

    public void zuzyjNarzedzia() {
        narzedzia = new ArrayList<>();
    }

    public boolean zuzyjUbrania() {
        int doZuzycia = 100;
        for (Dobra u : ubrania) {
            int ileZuzyje = min(doZuzycia, u.ile());
            if (ileZuzyje < u.ile()) {
                u.odejmij(u.ile() - ileZuzyje);
                dodajDobra(Produkt.UBRANIA, new Dobra(u.ile() - ileZuzyje, u.jakosc), false);
            }
            if (u.zuzyj())
                ubrania.remove(u);
            doZuzycia -= ileZuzyje;
            assert doZuzycia >= 0;
            if (doZuzycia == 0) {
                return true;
            }
        }
        return false;
    }

}
