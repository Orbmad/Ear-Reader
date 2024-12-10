package earreader.model;

import java.sql.Connection;

import com.google.common.base.Optional;

public class ModelImpl implements Model {

    private final Connection connection;
    private Optional<String> utente;

    public ModelImpl() {
        this.connection = DAOUtils.MySQLConnection("Earreader", "root", "");
    }

    @Override
    public String getActualUser() {
        return utente.get();
    }

    @Override
    public void login(final String emailUtente, final String passwordUtente) {
        final String email;
        email = Utente.DAO.login(connection, emailUtente, passwordUtente);
        this.utente = Optional.of(email);
    }

}
