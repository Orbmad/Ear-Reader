package earreader.model;

import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public final class Testo {
    private final int codiceTesto;
    private final Date data;
    private final String titolo;
    private final boolean singolo;
    private final String percorso;
    private final int costo;
    private float voto;
    private final String genere;
    private Optional<HashMap<Integer, Capitolo>> capitoli;
    private int posizione;

    public class Capitolo {
        public final int codiceTesto;
        public final Optional<Date> data;
        public final String percorsoCapitolo;
        public final String titolo;

        public Capitolo(final int codiceTesto, final Optional<Date> data, final String percorsoCapitolo,
                final String titolo) {
            this.codiceTesto = codiceTesto;
            this.data = data;
            this.percorsoCapitolo = percorsoCapitolo;
            this.titolo = titolo;
        }
    }

    public Testo(final int codiceTesto, final Date data, final String titolo, final boolean singolo,
            final String percorso, final int costo, final String genere, final float voto, final int posizione) {
        this.codiceTesto = codiceTesto;
        this.data = data;
        this.titolo = titolo;
        this.singolo = singolo;
        this.percorso = percorso;
        this.costo = costo;
        this.genere = genere;
        this.voto = voto;
        this.posizione = posizione;
    }

    public Testo(final int codiceTesto, final Date data, final String titolo, final boolean singolo,
            final String percorso, final int costo, final String genere, final float voto) {
        this.codiceTesto = codiceTesto;
        this.data = data;
        this.titolo = titolo;
        this.singolo = singolo;
        this.percorso = percorso;
        this.costo = costo;
        this.genere = genere;
        this.voto = voto;
        this.posizione = 0;
    }

    public int getCodiceTesto() {
        return codiceTesto;
    }

    public Date getData() {
        return data;
    }

    public String getTitolo() {
        return titolo;
    }

    public boolean isSingolo() {
        return singolo;
    }

    public String getPercorso() {
        return percorso;
    }

    public int getCosto() {
        return costo;
    }

    public float getVoto() {
        return voto;
    }

    public String getGenere() {
        return genere;
    }

    public Optional<HashMap<Integer, Capitolo>> getCapitoli() {
        return capitoli;
    }

    public int getPosizione() {
        return posizione;
    }

    public final class DAO {

        private HashSet<Testo> searchBy(Connection connection, String searchQuery, String searchString) {
            String upperString = searchString.toLowerCase();
            try (
                    var statement = DAOUtils.prepare(connection, searchQuery, upperString);
                    var resultSet = statement.executeQuery();) {
                HashSet<Testo> testi = new HashSet<>();
                while (resultSet.next()) {
                    testi.add(new Testo(resultSet.getInt("Testi.Codice"),
                            resultSet.getDate("Testi.Data"),
                            resultSet.getString("Testi.Titolo"),
                            resultSet.getBoolean("Testi.Singolo"),
                            resultSet.getString("Testi.Percorso"),
                            resultSet.getInt("Costo"),
                            resultSet.getString("Testi.NomeGenere"),
                            resultSet.getFloat("Voto")));
                }
                return testi;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public HashSet<Testo> searchByAuthor(Connection connection, final String author) {
            return searchBy(connection, OperationQueries.SEARCH_BY_AUTHOR, author);
        }

        public HashSet<Testo> searchByGenre(Connection connection, final String genre) {
            return searchBy(connection, OperationQueries.SEARCH_BY_GENRE, genre);
        }

        public HashSet<Testo> searchByGroup(Connection connection, final String group) {
            return searchBy(connection, OperationQueries.SEARCH_BY_GROUP, group);
        }

        public HashSet<Testo> textRanking(Connection connection) {
            try (
                    var statement = DAOUtils.prepare(connection, OperationQueries.TEXTS_RANKING);
                    var resultSet = statement.executeQuery();) {
                HashSet<Testo> testi = new HashSet<>();
                while (resultSet.next()) {
                    testi.add(new Testo(resultSet.getInt("Testi.Codice"),
                            resultSet.getDate("Testi.Data"),
                            resultSet.getString("Testi.Titolo"),
                            resultSet.getBoolean("Testi.Singolo"),
                            resultSet.getString("Testi.Percorso"),
                            resultSet.getInt("Costo"),
                            resultSet.getString("Testi.NomeGenere"),
                            resultSet.getFloat("Voto"),
                            resultSet.getInt("Posizione")));
                }
                return testi;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public HashSet<Testo> suggested(Connection connection, final String userEmail) {
            try (
                    var statement = DAOUtils.prepare(connection, OperationQueries.SUGGESTED_TEXTS, userEmail);
                    var resultSet = statement.executeQuery();) {
                HashSet<Testo> testi = new HashSet<>();
                while (resultSet.next()) {
                    testi.add(new Testo(resultSet.getInt("Testi.Codice"),
                            resultSet.getDate("Testi.Data"),
                            resultSet.getString("Testi.Titolo"),
                            resultSet.getBoolean("Testi.Singolo"),
                            resultSet.getString("Testi.Percorso"),
                            resultSet.getInt("Costo"),
                            resultSet.getString("Testi.NomeGenere"),
                            resultSet.getFloat("Voto")));
                }
                return testi;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
