package earreader.model;

public interface Model {
    
    String getActualUser();

    void login(final String emailUtente, final String passwordUtente);

}
