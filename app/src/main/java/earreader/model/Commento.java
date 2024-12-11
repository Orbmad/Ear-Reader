package earreader.model;

import java.sql.Connection;
import java.sql.Date;

public class Commento {
    private final String emailDisussione;
    private final String titoloDiscussione;
    private final int codice;
    private final Date data;
    private final String contenuto;
    private final String emailUtente;

    public Commento(final String emailDisussione, final String titoloDiscussione, final int codice, final Date data,
            final String contenuto, final String emailUtente) {
        this.emailDisussione = emailDisussione;
        this.titoloDiscussione = titoloDiscussione;
        this.codice = codice;
        this.data = data;
        this.contenuto = contenuto;
        this.emailUtente = emailUtente;
    }

    public String getEmailDisussione() {
        return emailDisussione;
    }

    public String getTitoloDiscussione() {
        return titoloDiscussione;
    }

    public int getCodice() {
        return codice;
    }

    public Date getData() {
        return data;
    }

    public String getContenuto() {
        return contenuto;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public final class DAO {

        public static void newComment(Connection connection, final String emailDisussione,
                final String titoloDiscussione, final int codice, final Date data,
                final String contenuto, final String emailUtente) {
            try (
                    var statement = DAOUtils.prepare(connection, OperationQueries.NEW_COMMENT, emailDisussione,
                            titoloDiscussione, codice, data, contenuto, emailUtente);) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static void addLike(Connection connection, final Commento commento, final String emailUtente,
                final boolean like) {
            String ADD_LIKE = """
                        INSERT INTO Valutazioni(EmailUtente, Email, Titolo, Codice, MiPiace)
                        VALUES (?, ?, ?, ?, ?)
                    """;
            try (
                    var statement = DAOUtils.prepare(connection, ADD_LIKE, emailUtente, commento.getEmailDisussione(),
                            commento.getTitoloDiscussione(), commento.getCodice(), like);
                ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
