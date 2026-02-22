package client;

/**
 * RAMO MULTITHREAD: connessioni contemporanee
 * Simula piu' client che si connettono al server CONTEMPORANEAMENTE.
 * Ogni client viene eseguito in un thread separato, quindi le connessioni
 * avvengono in parallelo e il server le serve tutte contemporaneamente.
 */
public class MainClientMultiThread {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("AVVIO MULTICLIENT CONTEMPORANEO\n");

        String[] nomi = {"Alice", "Bob", "Carlo", "AlexG", "Enrico"};
        String[] colori = {"rosso", "blu", "verde", "giallo", "viola"};

        Thread[] threads = new Thread[nomi.length];

        // Crea un thread per ogni client
        for (int i = 0; i < nomi.length; i++) {
            final String nome = nomi[i];
            final String colore = colori[i];

            threads[i] = new Thread(() -> {
                Client client = new Client(nome, colore);

                int esito = client.connetti("localhost", 3000);
                if (esito != 0) {
                    System.err.println("[" + nome + "] connessione fallita.");
                    return;
                }

                client.scrivi("Ciao, sono " + nome + " [colore: " + colore + "]");
                client.leggi();
                client.chiudi();
            });
        }

        // Avvia tutti i thread contemporaneamente
        System.out.println("Avvio di " + nomi.length + " client contemporaneamente\n");
        for (Thread t : threads) {
            t.start();
        }

        // Attende che tutti i client abbiano terminato
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("\nFINE MULTICLIENT CONTEMPORANEO");
    }
}