package earreader.model;

import java.util.List;

public interface Model {

    public enum SearchBy {
        AUTHOR, GENRE, GROUP
    }

    Utente getActualUser();

    boolean isUserLogged();

    void login(final String emailUtente, final String passwordUtente);

    List<Testo> searchBy(final String search, final SearchBy type);

    List<Testo> textRanking();

    List<Testo> getSuggestedTexts();

    void buyChapter(final Capitolo chapter);

    List<Autore> authorsRanking();

    void newComment(final String emailDisussione, final String titoloDiscussione, final int codice,
            final String contenuto);

    void addLike(final Commento commento, final boolean like);

    void newDiscussion(final String titolo, final String contenuto, final String argomento);

    List<Discussione> discussionsRanking();

    List<Commento> getCommentsFromDiscussion(final Discussione discussione);

}
