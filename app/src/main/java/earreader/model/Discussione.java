package earreader.model;

import java.sql.Connection;
import java.sql.Date;
import java.util.HashSet;

public class Discussione {
    private final String emailScrittore;
    private final String titolo;
    private final Date data;
    private final String contenuto;
    private final String argomento;
    private int numeroCommenti;
    private int posizione;

    public int getPosizione() {
        return posizione;
    }

    public Discussione(final String emailScrittore, final String titolo, final Date data, final String contenuto,
            final String argomento, final int numeroCommenti, final int posizione) {
        this.emailScrittore = emailScrittore;
        this.titolo = titolo;
        this.data = data;
        this.contenuto = contenuto;
        this.argomento = argomento;
        this.numeroCommenti = numeroCommenti;
        this.posizione = posizione;
    }

    public Discussione(final String emailScrittore, final String titolo, final Date data, final String contenuto,
            final String argomento, final int numeroCommenti) {
        this.emailScrittore = emailScrittore;
        this.titolo = titolo;
        this.data = data;
        this.contenuto = contenuto;
        this.argomento = argomento;
        this.numeroCommenti = numeroCommenti;
        this.posizione = 0;
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

        public static void newDiscussion(Connection connection, final String emailScrittore, final String titolo,
                final Date data, final String contenuto,
                final String argomento) {
            try (
                    var statement = DAOUtils.prepare(connection, OperationQueries.NEW_TOPIC, emailScrittore, titolo,
                            data, contenuto, argomento, 0);) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static HashSet<Discussione> discussionsRanking(Connection connection) {
            try (
                    var statement = DAOUtils.prepare(connection, OperationQueries.DISCUSSIONS_RANKING);
                    var resultSet = statement.executeQuery();) {
                HashSet<Discussione> discussions = new HashSet<>();
                while (resultSet.next()) {
                    discussions.add(new Discussione(resultSet.getString("Discussioni.Email"),
                            resultSet.getString("Discussioni.Titolo"),
                            resultSet.getDate("Discussioni.Data"),
                            resultSet.getString("Discussioni.Stringa"),
                            resultSet.getString("Discussioni.Argomento"),
                            resultSet.getInt("Discussioni.NumeroCommenti"),
                            resultSet.getInt("Posizione")));
                }
                return discussions;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static HashSet<Commento> getComments(Connection connection, final Discussione discussione) {
            HashSet<Commento> commenti = new HashSet<>();
            String GET_COMMENTS =
            """
                SELECT C*
                FROM Commenti C
                WHERE C.Email = ?
                AND C.Titolo = ?;        
            """;
            try (
                var statement = DAOUtils.prepare(connection, GET_COMMENTS, discussione.getEmailScrittore(), discussione.getTitolo());
                var resultSet = statement.executeQuery();  
            ) {
                while (resultSet.next()) {
                    commenti.add(new Commento(discussione.getEmailScrittore(),
                            discussione.getTitolo(),
                            resultSet.getInt("Commenti.Codice"),
                            resultSet.getDate("Commenti.Data"),
                            resultSet.getString("Commenti.Contenuto"),
                            resultSet.getString("Commenti.EmailUtente")));
                }
                return commenti;
            } catch(Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
