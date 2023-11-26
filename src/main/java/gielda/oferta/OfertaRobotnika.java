package gielda.oferta;

import agenci.Robotnik;
import zasoby.Produkt;

public class OfertaRobotnika extends Oferta {

    private final Robotnik robotnik;

    public OfertaRobotnika(Produkt produkt, int ilosc, int jakosc, Robotnik robotnik) {
        super(produkt, ilosc, jakosc);
        this.robotnik = robotnik;
    }

    public Robotnik robotnik() { return robotnik; }

}
