package gielda;

import gielda.oferta.OfertaSpekulanta;
import zasoby.Produkt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Historia {

    private int dzien;
    private final List<double[]> srednieCeny = new ArrayList<>();
    private final double[] minCenyKupnaWczoraj;
    private double[] minCenyKupna;
    private double[] minCeny, maxCeny;
    private final List<int[]> produkcja = new ArrayList<>();

    public Historia(double[] poczatkoweCeny) {
        dzien = 0;
        assert poczatkoweCeny.length == 4;
        this.srednieCeny.add(poczatkoweCeny);
        this.minCenyKupna = poczatkoweCeny;
        this.minCenyKupnaWczoraj = poczatkoweCeny;
        this.minCeny = poczatkoweCeny;
        this.maxCeny = poczatkoweCeny;
    }

    public int dajDzien() { return dzien; }

    public double dajMinCeneKupnaWczoraj(Produkt produkt) {
        return minCenyKupnaWczoraj[produkt.ordinal()];
    }

    public void kolejnyDzien() {
        dzien++;
        int[] dzisiejszaProdukcja = new int[4];
        Arrays.fill(dzisiejszaProdukcja, 0);
        produkcja.add(dzisiejszaProdukcja);
        minCeny = minCenyKupna;
        minCenyKupna = new double[4];
        Arrays.fill(minCenyKupna, Double.POSITIVE_INFINITY);
    }

    public void zaktualizujMinCenyKupna(Produkt produkt, double minCena) {
        minCenyKupna[produkt.ordinal()] = min(minCenyKupna[produkt.ordinal()], minCena);
    }

    public void aktualizujPoZakonczeniuDnia(List<OfertaSpekulanta> zrealizowaneOferty) {
        double[] sumaCen = new double[4], minCeny = new double[4],
                maxCeny = new double[4], srednieCeny = new double[4];
        Arrays.fill(sumaCen, 0);
        Arrays.fill(minCeny, Double.POSITIVE_INFINITY);
        Arrays.fill(maxCeny, Double.NEGATIVE_INFINITY);
        int[] ileCen = new int[4];
        Arrays.fill(ileCen, 0);
        for (OfertaSpekulanta oferta : zrealizowaneOferty) {
            sumaCen[oferta.produktNo()] += oferta.cena() * oferta.ilosc();
            ileCen[oferta.produktNo()] += oferta.ilosc();
            minCeny[oferta.produktNo()] = min(minCeny[oferta.produktNo()], oferta.cena());
            maxCeny[oferta.produktNo()] = max(maxCeny[oferta.produktNo()], oferta.cena());
        }
        for (int i = 0; i < 4; i++) {
            if (sumaCen[i] == 0) {
                assert minCeny[i] == Double.POSITIVE_INFINITY && maxCeny[i] == Double.NEGATIVE_INFINITY;
                srednieCeny[i] = srednieCenyKiedys(dzien + 1)[i];
                minCeny[i] = srednieCenyKiedys(dzien + 1)[i];
                maxCeny[i] = srednieCenyKiedys(dzien + 1)[i];
            } else {
                srednieCeny[i] = sumaCen[i] / ileCen[i];
            }
        }
        this.srednieCeny.add(srednieCeny);
        this.minCeny = minCeny;
        this.maxCeny = maxCeny;
    }

    public void dodajDoProdukcji(Produkt produkt, int ile) {
        produkcja.get(produkcja.size() - 1)[produkt.ordinal()] += ile;
    }

    public double[] dajCenyMin() { return minCeny; }

    public double[] dajCenyMax() { return maxCeny; }

    public double[] srednieCenyKiedys(int ileDniTemu) {
        assert ileDniTemu >= 0;
        return srednieCeny.get(max(0, dzien - ileDniTemu));
    }

    public int[] produkcjaKiedys(int ileDniTemu) {
        assert ileDniTemu >= 0;
        return produkcja.get(max(0, produkcja.size() - ileDniTemu - 1));
    }

    public double sredniaCena(Produkt produkt, int okres) {
        assert srednieCeny.size() == dzien;
        double wynik = 0;
        for (int i = dzien - 1; i >= max(0, dzien - okres); i--) {
            wynik += srednieCeny.get(i)[produkt.ordinal()];
        }
        return wynik / min(dzien, okres);
    }

    public double maxCena(Produkt produkt, int okres) {
        assert srednieCeny.size() == dzien;
        double maxCena = srednieCeny.get(dzien - 1)[produkt.ordinal()];
        for (int i = dzien - 1; i > max(0, dzien - okres - 1); i--) {
            maxCena = max(maxCena, srednieCeny.get(i)[produkt.ordinal()]);
        }
        return maxCena;
    }

    public static Produkt maxProdukt(double[] dane) {
        assert dane.length == 4;
        double max = dane[0];
        int maxProdukt = 0;
        for (int i = 1; i < 4; i++) {
            if (dane[i] > max) {
                max = dane[i];
                maxProdukt = i;
            }
        }
        return Produkt.dajProdukt(maxProdukt);
    }

    public int najczestszyProdukt(int okres) {
        assert produkcja.size() == dzien;
        int[] sumaProdukcji = new int[]{0, 0, 0, 0};
        for (int i = dzien - 2; i >= max(0, dzien - okres - 1); i--) {
            for (int j = 0; j < 4; j++) {
                sumaProdukcji[j] += produkcja.get(i)[j];
            }
        }
        int max = -1, maxProdukt = -1;
        for (int i = 0; i < 4; i++) {
            if (sumaProdukcji[i] > max) {
                max = sumaProdukcji[i];
                maxProdukt = i;
            }
        }
        assert maxProdukt != -1;
        return maxProdukt;
    }

    public boolean czyCenyWypukle(Produkt p) {
        if (dzien < 3)
            return true;
        return srednieCenyKiedys(2)[p.ordinal()] <
                (srednieCenyKiedys(1)[p.ordinal()] +
                        srednieCenyKiedys(3)[p.ordinal()]) / 2;
    }

    public boolean czyCenyWklesle(Produkt p) {
        if (dzien < 3)
            return true;
        return srednieCenyKiedys(2)[p.ordinal()] >
                (srednieCenyKiedys(1)[p.ordinal()] +
                        srednieCenyKiedys(3)[p.ordinal()]) / 2;
    }

}
