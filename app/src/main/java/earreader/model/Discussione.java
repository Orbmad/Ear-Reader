package earreader.model;

import java.sql.Connection;
import java.sql.Date;

public class Discussione {
    private final String emailScrittore;
    private final String titolo;
    private final Date data;
    private final String contenuto;
    private final String argomento;
    private int numeroCommenti;

    public Discussione(final String emailScrittore, final String titolo, final Date data, final String contenuto,
            final String argomento, final int numeroCommenti) {
        this.emailScrittore = emailScrittore;
        this.titolo = titolo;
        this.data = data;
        this.contenuto = contenuto;
        this.argomento = argomento;
        this.numeroCommenti = numeroCommenti;
    }

    public String getEmailScrittore() {
        return emailScrittore;
    }

    public String getTitolo() {
        return titolo;
    }

    public Date getData() {
        return data;
    }

    public String getContenuto() {
        return contenuto;
    }

    public String getArgomento() {
        return argomento;
    }

    public int getNumeroCommenti() {
        return numeroCommenti;
    }

    public final class DAO {

        public static void newTopic(Connection connection, final String emailScrittore, final String titolo,
                final Date data, final String contenuto,
                final String argomento, final int numeroCommenti) {
            try (
                    var statement = DAOUtils.prepare(connection, OperationQueries.NEW_TOPIC, emailScrittore, titolo,
                            data, contenuto, argomento, 0);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
