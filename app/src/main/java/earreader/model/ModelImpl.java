package earreader.model;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ModelImpl implements Model {

    private final Connection connection;
    private Optional<Utente> utente;
    private Optional<List<Discussione>> discussioni;
    private Optional<List<Testo>> testi;
    private Optional<List<Pagamento>> pagamenti;
    private Optional<List<Recensione>> recensioni;
 
    public ModelImpl() {
        this.connection = DAOUtils.MySQLConnection("Earreader", "root", "");
    }

    @Override
    public Utente getActualUser() {
        return utente.get();
    }

    @Override
    public void login(final String emailUtente, final String passwordUtente) {
        final Utente user;
        user = Utente.DAO.login(connection, emailUtente, passwordUtente);
        this.utente = Optional.of(user);
    }


    public void resetUser() {
        this.utente = Optional.empty();
    }

    public void resetDiscussions() {
        this.discussioni = Optional.empty();
    }

    public void resetTexts() {
        this.testi = Optional.empty();
    }

    public void resetPayments() {
        this.testi = Optional.empty();
    }

    public void resetReviews() {
        this.recensioni = Optional.empty();
    }

    @Override
    public List<Testo> searchBy(final String search, final SearchBy type) {
        if (type.equals(SearchBy.AUTHOR)) {
            return Testo.DAO.searchByAuthor(connection, search).stream().toList();
        } else if (type.equals(SearchBy.GENRE)) {
            return Testo.DAO.searchByGenre(connection, search).stream().toList();
        } else {
            return Testo.DAO.searchByGroup(connection, search).stream().toList();
        }
    }

    @Override
    public List<Testo> textRanking() {
        return Testo.DAO.textRanking(connection).stream().toList();
    }

    @Override
    public List<Testo> getSuggestedTexts() {
        return Testo.DAO.suggested(connection, utente.get().getEmail()).stream().toList();
    }

}
