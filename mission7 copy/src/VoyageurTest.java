public class VoyageurTest {
    public static void main(String[] args) {
        VoyageurDAO vdao = new VoyageurDAO();

        Voyageur voyageurtest = new Voyageur("De Bruyne", 23);
        AdressePostale adressetest = new AdressePostale("Lloris", "Paris", 75);

        vdao.insertVoyageurWithAdresse(voyageurtest, adressetest);
    }
}
