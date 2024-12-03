package earreader.model;

import java.sql.Connection;

public class AcquistiCap {
    private final int numero;
    private final int codiceTesto;
    private final String email;

    public AcquistiCap(final int numero, final int codiceTesto, final String email) {
        this.numero = numero;
        this.codiceTesto = codiceTesto;
        this.email = email;
    }

    public int getChapterNumber() {
        return this.numero;
    }

    public int getTextCode() {
        return this.codiceTesto;
    }

    public String getUserEmail() {
        return this.email;
    }

    public final class DAO {

        public static void buyChapter(Connection connection, final int numero, final int codiceTesto, final String email) {
            try (
                var statement1 = DAOUtils.prepare(connection, OperationQueries.BUY_CHAPTER, numero, codiceTesto, email);
                var statement2 = DAOUtils.prepare(connection, OperationQueries.UPDATE_USER_COINS, email);
            ) {
                statement1.executeUpdate();
                statement2.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}

