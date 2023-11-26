package gielda.gielda;

import agenci.Robotnik;
import gielda.Historia;

import java.util.List;

public class Kapitalistyczna extends Gielda {

    @Override
    public void zrealizujWymiane(List<Robotnik> robotnicy, Historia historia) {
        robotnicy = sortujRobotnikow(robotnicy, false);
        super.zrealizujWymiane(robotnicy, historia);
    }
}
