package zasoby;

public enum Produkt {
    JEDZENIE, UBRANIA, NARZEDZIA, PROGRAMY, DIAMENTY;

    public static Produkt dajProdukt(int nrProduktu) {
        assert nrProduktu >= 0 && nrProduktu <= 4;
        switch (nrProduktu) {
            case 0:
                return JEDZENIE;
            case 1:
                return UBRANIA;
            case 2:
                return NARZEDZIA;
            case 3:
                return PROGRAMY;
            case 4:
                return DIAMENTY;
            default:
                assert false;
                return null;
        }
    }
}
