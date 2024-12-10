package earreader.model;

import java.sql.Connection;
import java.util.HashSet;

public class Autore {
    private final int codiceAutore;
    private final String nomeAutore;
    private final String alias;
    private float punteggio;
    private int posizione;

    public Autore(final int codiceAutore, final String nomeAutore, final String alias, final float punteggio,
            final int posizione) {
        this.codiceAutore = codiceAutore;
        this.nomeAutore = nomeAutore;
        this.alias = alias;
        this.punteggio = punteggio;
        this.posizione = posizione;
    }

    public Autore(final int codiceAutore, final String nomeAutore, final String alias, final float punteggio) {
        this.codiceAutore = codiceAutore;
        this.nomeAutore = nomeAutore;
        this.alias = alias;
        this.punteggio = punteggio;
        this.posizione = 0;
    }

    public int getCodiceAutore() {
        return codiceAutore;
    }

    public String getNomeAutore() {
        return nomeAutore;
    }

    public String getAlias() {
        return alias;
    }

    public float getPunteggio() {
        return punteggio;
    }

    public int getPosizione() {
        return posizione;
    }

    public final class DAO {

        public HashSet<Autore> authorsRanking(Connection connection) {
            try (
                    var statement = DAOUtils.prepare(connection, OperationQueries.AUTHORS_RANKING);
                    var resultSet = statement.executeQuery();) {
                HashSet<Autore> authors = new HashSet<>();
                while (resultSet.next()) {
                    authors.add(new Autore(resultSet.getInt("Autori.CodiceAutore"),
                            resultSet.getString("Autori.Nome"),
                            resultSet.getString("Autori.Alias"),
                            resultSet.getFloat("Autori.Punteggio"),
                            resultSet.getInt("Posizione")));
                }
                return authors;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
