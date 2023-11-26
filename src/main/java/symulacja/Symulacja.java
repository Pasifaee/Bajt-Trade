package symulacja;

import agenci.Robotnik;
import agenci.Spekulant;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import gielda.gielda.Gielda;
import gielda.Historia;
import json_conversion.adapters.*;

import java.util.ArrayList;
import java.util.List;

public class Symulacja {

    private final int dlugosc;
    private final List<Robotnik> robotnicy;
    private final List<Robotnik> martwi;
    private final List<Spekulant> spekulanci;
    private final Gielda gielda;
    private final Historia historia;
    private final int karaZaBrakUbran;

    public Symulacja(int dlugosc, List<Robotnik> robotnicy, List<Spekulant> spekulanci, Gielda g, double[] ceny, int kara) {
        assert dlugosc >= 0;
        this.dlugosc = dlugosc;
        this.robotnicy = robotnicy;
        this.spekulanci = spekulanci;
        this.gielda = g;
        this.karaZaBrakUbran = kara;
        assert ceny.length == 4;
        this.historia = new Historia(ceny);
        this.martwi = new ArrayList<>();
    }

    public String symulacjaJson() {
        for (Spekulant s : spekulanci) {
            s.ustawGielde(gielda);
        }
        StringBuilder symulacjaJson = new StringBuilder("{\n");
        for (int i = 0; i < dlugosc; i++) {
            symulacjaJson.append(symulujDzienJson());
            if (i < dlugosc - 1) {
                symulacjaJson.append(",\n");
            }
        }
        symulacjaJson.append("\n}");
        return symulacjaJson.toString();
    }

    private String symulujDzienJson() {
        historia.kolejnyDzien();
        resetujGielde();
        for (Robotnik r : robotnicy) {
            if (!r.przezyjDzienPracy(historia, karaZaBrakUbran)) {
                robotnicy.remove(r);
                dodajMartwego(r);
            }
        }
        for (Spekulant s : spekulanci) {
            s.wystawOferty(historia);
        }
        gielda.zrealizujWymiane(robotnicy, historia);
        gielda.skupNiesprzedane(robotnicy, historia);
        for (Robotnik r : robotnicy) {
            if (!r.dzisiajSieUczyl())
                r.konsumuj();
        }
        historia.aktualizujPoZakonczeniuDnia(gielda.zrealizowaneOferty());

        StrategiaRozwojuAdapter sRA = new StrategiaRozwojuAdapter();
        StrategiaProdukcjiAdapter sPA = new StrategiaProdukcjiAdapter();
        StrategiaKupowaniaAdapter sKA = new StrategiaKupowaniaAdapter();
        Moshi moshi = new Moshi.Builder()
                .add(new InfoAdapter())
                .add(sRA).add(sPA).add(sKA)
                .add(new RobotnikAdapter())
                .add(new SpekulantAdapter())
                .add(new SymulacjaAdapter())
                .build();
        sRA.setMoshi(moshi);
        sPA.setMoshi(moshi);
        sKA.setMoshi(moshi);
        JsonAdapter<Symulacja> jsonAdapter = moshi.adapter(Symulacja.class);
        String json = jsonAdapter.indent("   ").toJson(this);

        return json;
    }

    private void resetujGielde() {
        gielda.reset();
        for (Robotnik r : robotnicy) {
            r.resetujOferty();
        }
    }

    private void dodajMartwego(Robotnik robotnik) {
        martwi.add(robotnik);
        robotnik.zaplac(robotnik.diamenty());
    }

    public Historia historia() { return historia; }

    public List<Robotnik> robotnicy() { return robotnicy; }

    public List<Robotnik> martwi() { return martwi; }

    public List<Spekulant> spekulanci() { return spekulanci; }

}
