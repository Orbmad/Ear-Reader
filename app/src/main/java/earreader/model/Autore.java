package earreader.model;

import java.sql.Connection;
import java.util.HashSet;

import com.google.common.base.Optional;

public class Autore {
    private final int codiceAutore;
    private final String nomeAutore;
    private final String alias;
    private float punteggio;

    public Autore(final int codiceAutore, final String nomeAutore, final String alias, final float punteggio) {
        this.codiceAutore = codiceAutore;
        this.nomeAutore = nomeAutore;
        this.alias = alias;
        this.punteggio = punteggio;
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
                            resultSet.getFloat("Autori.Punteggio")));
                }
                return authors;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
