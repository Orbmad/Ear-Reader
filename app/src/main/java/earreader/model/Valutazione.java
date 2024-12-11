package earreader.model;

public class Valutazione {
    private final String emailUtente;
    private final String emailCommento;
    private final String titoloDiscussione;
    private final int codice;
    private final boolean valutazione;

    public Valutazione(final String emailUtente, final String emailCommento, final String titoloDiscussione,
            final int codice, final boolean valutazione) {
        this.emailUtente = emailUtente;
        this.emailCommento = emailCommento;
        this.titoloDiscussione = titoloDiscussione;
        this.codice = codice;
        this.valutazione = valutazione;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public String getEmailCommento() {
        return emailCommento;
    }

    public String getTitoloDiscussione() {
        return titoloDiscussione;
    }

    public int getCodice() {
        return codice;
    }

    public boolean isValutazione() {
        return valutazione;
    }
}
