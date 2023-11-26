package agenci;

import gielda.Historia;
import gielda.oferta.OfertaSpekulanta;
import json_conversion.helper_classes.KarieraJson;
import zasoby.*;

import java.util.ArrayList;
import java.util.List;

public class SpekulantSredni extends Spekulant {

    private final int historiaSpekulanta;

    public SpekulantSredni(int id, Zasoby zasoby, int historiaSpekulanta) {
        super(id, zasoby);
        this.historiaSpekulanta = historiaSpekulanta;
    }

    @Override
    public KarieraJson karieraJson() {
        return new KarieraJson("sredni", historiaSpekulanta);
    }

    @Override
    public void wystawOferty(Historia h) {
        List<OfertaSpekulanta> ofertyKupna = new ArrayList<>();
        List<OfertaSpekulanta> ofertySprzedazy = new ArrayList<>();
        Produkt[] produkty = new Produkt[]{Produkt.JEDZENIE, Produkt.UBRANIA, Produkt.NARZEDZIA, Produkt.PROGRAMY};
        for (Produkt p : produkty) {
            double sredniaCena = h.sredniaCena(p, historiaSpekulanta);
            for (int jakosc = 1; jakosc <= h.dajDzien(); jakosc++) {
                Dobra dobra = zasoby.dajProdukt(p).size() >= jakosc ? zasoby.dajProdukt(p).get(jakosc - 1) : null;
                if (dobra != null) {
                    ofertyKupna.add(new OfertaSpekulanta(p, 100, jakosc,
                            sredniaCena * 0.9, this));
                    ofertySprzedazy.add(new OfertaSpekulanta(p, dobra.ile(), jakosc,
                            sredniaCena * 1.1, this));
                } else {
                    ofertyKupna.add(new OfertaSpekulanta(p, 100, jakosc,
                            sredniaCena * 0.95, this));
                }
                if (p.equals(Produkt.JEDZENIE)) {
                    break;
                }
            }
        }
        gielda.dodajOfertyKupnaSpekulanta(ofertyKupna);
        gielda.dodajOfertySprzedazySpekulanta(ofertySprzedazy);
    }
}

