package client;

public class MainClient {
    public static void main(String[] args) {
        System.out.println("AVVIO CLIENT");

        Client client = new Client("Mario", "rosso");

        int esito = client.connetti("localhost", 3000);
        if (esito != 0) {
            System.err.println("Impossibile connettersi. Terminazione.");
            return;
        }

        client.scrivi("Ciao, sono " + client.getNome() + " [colore: " + client.getColore() + "]");
        client.leggi();
        client.chiudi();

        System.out.println("FINE CLIENT");
    }
}
