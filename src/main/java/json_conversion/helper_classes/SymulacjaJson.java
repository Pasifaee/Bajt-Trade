package json_conversion.helper_classes;

import agenci.Robotnik;
import agenci.Spekulant;

import java.util.List;

public class SymulacjaJson {

    public final Info info;
    public final List<Robotnik> robotnicy;
    public final List<Spekulant> spekulanci;

    public SymulacjaJson(Info info, List<Robotnik> robotnicy, List<Spekulant> spekulanci) {
        this.info = info;
        this.robotnicy = robotnicy;
        this.spekulanci = spekulanci;
    }
}
