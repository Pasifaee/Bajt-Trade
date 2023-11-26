package gielda.gielda;

import agenci.Robotnik;
import gielda.Historia;

import java.util.List;

public class Zrownowazona extends Gielda {

    @Override
    public void zrealizujWymiane(List<Robotnik> robotnicy, Historia historia) {
        robotnicy = sortujRobotnikow(robotnicy, historia.dajDzien() % 2 == 1);
        super.zrealizujWymiane(robotnicy, historia);
    }

}
