package agenci;

import gielda.Historia;
import gielda.oferta.OfertaSpekulanta;
import json_conversion.helper_classes.KarieraJson;
import zasoby.Dobra;
import zasoby.Produkt;
import zasoby.Zasoby;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class SpekulantRegulujacy extends Spekulant {

    public SpekulantRegulujacy(int id, Zasoby zasoby) {
        super(id, zasoby);
    }

    @Override
    public KarieraJson karieraJson() {
        return new KarieraJson("regulujacy_rynek", -1);
    }

    @Override
    public void wystawOferty(Historia h) {
        if (h.dajDzien() == 1)
            return;
        List<OfertaSpekulanta> ofertyKupna = new ArrayList<>();
        List<OfertaSpekulanta> ofertySprzedazy = new ArrayList<>();
        Produkt[] produkty = new Produkt[]{Produkt.JEDZENIE, Produkt.UBRANIA, Produkt.NARZEDZIA, Produkt.PROGRAMY};
        for (Produkt p : produkty) {
            double sredniaCena = h.srednieCenyKiedys(1)[p.ordinal()];
            double cena = sredniaCena * ((double) h.produkcjaKiedys(0)[p.ordinal()] /
                    max(h.produkcjaKiedys(1)[p.ordinal()], 1));

            for (int jakosc = 1; jakosc <= h.dajDzien(); jakosc++) {
                ofertyKupna.add(new OfertaSpekulanta(p, 100, jakosc,
                        cena * 0.9, this));
                Dobra dobra = zasoby.dajProdukt(p).size() >= jakosc ? zasoby.dajProdukt(p).get(jakosc - 1) : null;
                if (dobra != null) {
                    ofertySprzedazy.add(new OfertaSpekulanta(p, dobra.ile(), jakosc,
                            cena * 1.1, this));
                }
            }
        }
        gielda.dodajOfertyKupnaSpekulanta(ofertyKupna);
        gielda.dodajOfertySprzedazySpekulanta(ofertySprzedazy);
    }

}
