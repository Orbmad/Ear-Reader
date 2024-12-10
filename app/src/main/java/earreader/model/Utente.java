package earreader.model;

import java.sql.Connection;

public final class Utente {
    private final String email;
    private final String nickname;
    private final String password;
    private int coins;

    public Utente(final String email, final String nickname, final String password, final int coins) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.coins = coins;
    }
    
    public String getEmail() {
        return this.email;
    }

    public String getNickName() {
        return this.nickname;
    }

    public int getCoins() {
        return this.coins;
    }

    public boolean comparePassword(final String pwd) {
        return this.password.equals(pwd);
    }

    public final class DAO {

        public static void newUser(Connection connection, final String email, final String nickname, final String password) {
            try (
                var statement = DAOUtils.prepare(connection, OperationQueries.NEW_USER, email, nickname, password);
            ) {
                statement.executeUpdate();
            } catch(Exception e) {
                throw new DAOException(e);
            }
        }

        public static String login(Connection connection, final String email, final String password) {
            String LOGIN_QUERY = 
            """
                SELECT U.*
                FROM Utenti U
                WHERE U.Email = ?
                AND U.Password = ?        
            """;
            try (
                var statement = DAOUtils.prepare(connection, LOGIN_QUERY, email, password);
                var resultSet = statement.executeQuery();
            ) {
                return resultSet.getString("Email");
            } catch(Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
