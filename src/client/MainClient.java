package client;

import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) {
        System.out.println("=== AVVIO CLIENT ===");

        Client client = new Client("Mario", "rosso");

        int esito = client.connetti("localhost", 3000);
        if (esito != 0) {
            System.err.println("Impossibile connettersi. Terminazione.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Inserisci un messaggio (oppure 'exit' per uscire): ");
            String messaggio = scanner.nextLine();

            // Controlla se l'utente vuole uscire (tollerando maiuscole e minuscole)
            if (messaggio.equalsIgnoreCase("exit")) {
                client.scrivi("exit");
                client.leggi(); // legge il messaggio di chiusura dal server
                break;
            }

            client.scrivi(messaggio);
            client.leggi();
        }

        scanner.close();
        client.chiudi();

        System.out.println("=== FINE CLIENT ===");
    }
}