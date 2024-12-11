package earreader.model;

import java.util.List;

public interface Model {

    public enum SearchBy {
        AUTHOR, GENRE, GROUP
    }

    Utente getActualUser();

    boolean isUserLogged();

    void login(final String emailUtente, final String passwordUtente);

    void updateLogin();

    void newUser(final String email, final String nickname, final String password);

    List<Testo> searchBy(final String search, final SearchBy type);

    List<Testo> textRanking();

    List<Testo> getSuggestedTexts();

    List<Recensione> getReviewsOfText(final Testo testo);

    void buyChapter(final Capitolo chapter);

    List<Autore> authorsRanking();

    void newComment(final String emailDisussione, final String titoloDiscussione, final int codice,
            final String contenuto);

    void addLike(final Commento commento, final boolean like);

    void newDiscussion(final String titolo, final String contenuto, final String argomento);

    List<Discussione> discussionsRanking();

    List<Commento> getCommentsFromDiscussion(final Discussione discussione);

    void addPayment(final int valutaAcquistata, final int codiceMetodoPagamento);

    void newReview(final int codiceTesto,
            final int voto, final String titolo, final String contenuto);

}
