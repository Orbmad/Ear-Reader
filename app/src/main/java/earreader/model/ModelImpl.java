package earreader.model;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class ModelImpl implements Model {

    private final Connection connection;
    private Optional<Utente> utente;

    public ModelImpl() {
        this.connection = DAOUtils.MySQLConnection("Earreader", "root", "");
        this.utente = Optional.empty();
    }

    @Override
    public Utente getActualUser() {
        return utente.get();
    }

    @Override
    public boolean isUserLogged() {
        return utente.isPresent();
    }

    @Override
    public void login(final String emailUtente, final String passwordUtente) {
        final Utente user;
        user = Utente.DAO.login(connection, emailUtente, passwordUtente);
        this.utente = Optional.of(user);
    }

    @Override
    public void updateLogin() {
        login(utente.get().getEmail(), utente.get().getPassword());
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

    @Override
    public void buyChapter(Capitolo chapter) {
        AcquistiCap.DAO.buyChapter(connection, chapter.getNumeroCapitolo(), chapter.getCodiceTesto(),
                utente.get().getEmail());
    }

    @Override
    public List<Autore> authorsRanking() {
        return Autore.DAO.authorsRanking(connection).stream().toList();
    }

    @Override
    public void newComment(String emailDisussione, String titoloDiscussione, int codice, String contenuto) {

        Commento.DAO.newComment(connection, emailDisussione, titoloDiscussione, codice,
                new Date(System.currentTimeMillis()), contenuto, utente.get().getEmail());
    }

    @Override
    public void addLike(final Commento commento, final boolean like) {
        Commento.DAO.addLike(connection, commento, utente.get().getEmail(), like);
    }

    @Override
    public void newDiscussion(String titolo, String contenuto, String argomento) {
        Discussione.DAO.newDiscussion(connection, utente.get().getEmail(), titolo, new Date(System.currentTimeMillis()),
                contenuto, argomento);
    }

    @Override
    public List<Discussione> discussionsRanking() {
        return Discussione.DAO.discussionsRanking(connection).stream().toList();
    }

    @Override
    public List<Commento> getCommentsFromDiscussion(final Discussione discussione) {
        return Discussione.DAO.getComments(connection, discussione).stream().toList();
    }

    @Override
    public void addPayment(final int valutaAcquistata, final int codiceMetodoPagamento) {
        ValoriSconto valoriSconto = Pagamento.DAO.findDiscount(connection, valutaAcquistata);
        int costoFinale = (valutaAcquistata / 100) * (100 - valoriSconto.percentualeSconto()) / 100;
        Pagamento.DAO.addPayment(connection, utente.get().getEmail(), new Date(System.currentTimeMillis()),
                valutaAcquistata, costoFinale, codiceMetodoPagamento, valoriSconto.codiceSconto());
    }

    @Override
    public List<Recensione> getReviewsOfText(Testo testo) {
        return Testo.DAO.getReviews(connection, testo.getCodiceTesto()).stream().toList();
    }

    @Override
    public void newReview(int codiceTesto, int voto, String titolo, String contenuto) {
        Recensione.DAO.newReview(connection, utente.get().getEmail(), codiceTesto, voto, titolo, contenuto);
    }

}
