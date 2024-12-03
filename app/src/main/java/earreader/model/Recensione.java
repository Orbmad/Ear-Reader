package earreader.model;

import java.sql.Connection;

public class Recensione {
    private final String emailUtente;
    private final int codiceTesto;
    private final int voto;
    private final String titolo;
    private final String contenuto;

    public Recensione(final String emailUtente, final int codiceTesto, final int voto, final String titolo,
            final String contenuto) {
        this.emailUtente = emailUtente;
        this.codiceTesto = codiceTesto;
        this.voto = voto;
        this.titolo = titolo;
        this.contenuto = contenuto;
    }

    public String getUserEmail() {
        return this.emailUtente;
    }

    public int getTextCode() {
        return this.codiceTesto;
    }

    public int getRank() {
        return this.voto;
    }

    public String getTitle() {
        return this.titolo;
    }

    public String getContent() {
        return this.contenuto;
    }

    public final class DAO {

        public static void newReview(Connection connection, final String emailUtente, final int codiceTesto,
                final int voto, final String titolo, final String contenuto) {
            try (
                var statement1 = DAOUtils.prepare(connection, OperationQueries.ADD_REVIEW, emailUtente, codiceTesto, voto, titolo, contenuto);
                var statement2 = DAOUtils.prepare(connection, OperationQueries.UPDATE_TEXT_RANK, codiceTesto);
                var statement3 = DAOUtils.prepare(connection, OperationQueries.UPDATE_AUTHOR_RANK, codiceTesto);
            ) {
                statement1.executeUpdate();
                statement2.executeUpdate();
                statement3.executeUpdate();
            } catch(Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
