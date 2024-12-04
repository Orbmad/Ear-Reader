package earreader.model;

import java.sql.Connection;
import java.sql.Date;

public final class Pagamento {
    private final int codicePagamento;
    private final String emailUtente;
    private final Date data;
    private final int valutaAcquistata;
    private final int codiceMetodoPagamento;
    private final int codiceSconto;

    public Pagamento(final int codicePagamento, final String emailUtente, final Date data, final int valutaAcquistata,
            final int codiceMetodoPagamento, final int codiceSconto) {
        this.codicePagamento = codicePagamento;
        this.emailUtente = emailUtente;
        this.data = data;
        this.valutaAcquistata = valutaAcquistata;
        this.codiceMetodoPagamento = codiceMetodoPagamento;
        this.codiceSconto = codiceSconto;
    }

    public int getCodicePagamento() {
        return codicePagamento;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public Date getData() {
        return data;
    }

    public int getValutaAcquistata() {
        return valutaAcquistata;
    }

    public int getCodiceMetodoPagamento() {
        return codiceMetodoPagamento;
    }

    public int getCodiceSconto() {
        return codiceSconto;
    }

    public final class DAO {

        public static void addPayment(Connection connection, final int codicePagamento, final String emailUtente,
                final Date data, final int valutaAcquistata,
                final int codiceMetodoPagamento, final int codiceSconto) {
            try (
                var statement = DAOUtils.prepare(connection, OperationQueries.BUY_NEW_CURRENCY, codicePagamento, emailUtente, data, valutaAcquistata, codiceMetodoPagamento, codiceSconto);
            ) {
                statement.executeUpdate();
            } catch(Exception e) {
                throw new DAOException(e);
            }
        }

    }

}
