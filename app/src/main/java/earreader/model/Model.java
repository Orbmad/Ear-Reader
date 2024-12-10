package earreader.model;

import java.util.List;

public interface Model {

    public enum SearchBy {AUTHOR, GENRE, GROUP}
    
    Utente getActualUser();

    void login(final String emailUtente, final String passwordUtente);

    List<Testo> searchBy(final String search, final SearchBy type);

    List<Testo> textRanking();

    List<Testo> getSuggestedTexts();



}
