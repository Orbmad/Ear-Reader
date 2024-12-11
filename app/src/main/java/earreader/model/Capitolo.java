package earreader.model;

import java.sql.Date;

public final class Capitolo {
        public final int codiceTesto;
        public final int numeroCapitolo;
        public final Date data;
        public final String percorsoCapitolo;
        public final String titolo;

        public Capitolo(final int codiceTesto, final int numeroCapitolo, final Date data, final String percorsoCapitolo,
                final String titolo) {
            this.codiceTesto = codiceTesto;
            this.data = data;
            this.percorsoCapitolo = percorsoCapitolo;
            this.titolo = titolo;
            this.numeroCapitolo = numeroCapitolo;
        }

        public int getCodiceTesto() {
            return codiceTesto;
        }

        public int getNumeroCapitolo() {
            return numeroCapitolo;
        }

        public Date getData() {
            return data;
        }

        public String getPercorsoCapitolo() {
            return percorsoCapitolo;
        }

        public String getTitolo() {
            return titolo;
        }
}
