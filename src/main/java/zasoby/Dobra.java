package zasoby;

public class Dobra {

    private int ile;
    public final int jakosc;
    private int zuzycie;

    public Dobra(int ile) {
        this.ile = ile;
        this.jakosc = 1;
        this.zuzycie = 0;
    }

    public Dobra(int ile, int jakosc) {
        this.ile = ile;
        this.jakosc = jakosc;
        this.zuzycie = 0;
    }

    public int ile() { return ile; }

    public void dodaj(int ile) { this.ile += ile; }

    public void odejmij(int ile) {
        this.ile -= ile;
        assert this.ile >= 0;
    }

    public int zuzycie() { return zuzycie; }

    public boolean zuzyj() {
        zuzycie++;
        return zuzycie == jakosc * jakosc;
    }

}
