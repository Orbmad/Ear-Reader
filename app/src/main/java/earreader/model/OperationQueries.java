package earreader.model;

public class OperationQueries {
    
    //OP01
    public static final String NEW_USER =
    """
    INSERT INTO Utenti(Email, Nickname, Password)
    VALUES (?, ?, ?);
    """;

    //OP02
    public static final String BUY_CHAPTER =
    """
    INSERT INTO AcquistiCap(Numero, CodiceTesto, Email)
    VALUES (?, ?, ?);
    """;

    public static final String UPDATE_USER_COINS =
    """
    UPDATE Utenti
    SET EarCoins = EarCoins - (
        SELECT T.Costo
        FROM Testi T
        WHERE T.Codice = ?
    )
    WHERE Email = ?;      
    """;

    //OP03
    public static final String ADD_REVIEW =
    """
    INSERT INTO Valutazioni(Email, CodiceTesto, Voto, Titolo, Stringa)
    VALUES (?, ?, ?, ?);
    """;

    public static final String UPDATE_TEXT_RANK =
    """
    UPDATE Testi
    SET Voto = (
        SELECT AVG(Voto)
        FROM Recensioni
        WHERE CodiceTesto = ?
    )
    WHERE Codice = ?;
    """;

    public static final String UPDATE_AUTHOR_RANK =
    """
    UPDATE Autori A
    SET Punteggio = (
        SELECT AVG(T.Voto)
        FROM Testi T, Scritture S
        WHERE S.CodiceAutore = A.CodiceAutore
        )
    )
    WHERE A.CodiceAutore = (
        SELECT S1.CodiceAutore
        FROM Testi T1, Scritture S1
        WHERE T1.Codice = ?
    );      
    """;

    //OP04
    public static final String NEW_TOPIC =
    """
    INSERT INTO Discussioni(Email, Titolo, Data, Stringa, Argomento)
    VALUES(?, ?, ?, ?, ?);
    """;

    //OP05
    public static final String NEW_COMMENT =
    """
        INSERT INTO Commenti(Email, Titolo, Codice, Data, Stringa, EmailUtente)
        VALUES (?, ?, ?, ?, ?, ?);
        UPDATE Discussioni
        SET NumeroCommenti = NumeroCommenti + 1
        WHERE Email = ?
        AND Titolo = ?;
    """;

    //OP06
    public static final String BUY_NEW_CURRENCY =
    """
        INSERT INTO Pagamenti(Email, Data, EarCoins, CodiceMetodo, CodiceSconto)
        VALUES(?, ?, ?, ?, ?);        
    """;

    public static final String UPDATE_USER_CURRENCY =
    """
        UPDATE Utenti
        SET EarCoins = EarCoins + ?
        WHERE Email = ?;     
    """;

    //OP06.1
    public static final String FIND_DISCOUNT =
    """
        SELECT TOP(1) CodiceSconto, Percentuale
        FROM Sconti
        WHERE QuantitaMinima <= ?
        ORDER BY QuantitaMinima DESC
    """;

    //OP07.1
    public static final String SEARCH_BY_AUTHOR =
    """
        SELECT T.*
        FROM Testi T, Autori A, Scritture S
        WHERE S.CodiceAutore = A.CodiceAutore
        AND S.CodiceTesto = T.Codice;
    """;

    //OP07.2
    public static final String SEARCH_BY_GENRE = 
    """
        SELECT T.*
        FROM Testi T, Generi G
        WHERE T.NomeGenere = G.NomeGenere;        
    """;

    //OP07.3
    public static final String SEARCH_BY_GROUP =
    """
        SELECT T.*
        FROM Testi T, Gruppi G, Appartenenze A
        WHERE G.NomeGruppo = A.NomeGruppo
        AND A.CodiceTesto = T.Codice;         
    """;

    //OP08
    public static final String TEXTS_RANKING =
    """
        SELECT T.* ROW_NUMBER() OVER() AS Posizione
        FROM Testi T
        ORDER BY T.Voto DESC;        
    """;

    //OP09
    public static final String AUTHORS_RANKING =
    """
        SELECT A.* ROW_NUMBER() OVER() AS Posizione
        FROM Autori A
        ORDER BY A.Punteggio DESC;        
    """;

    //OP10
    public static final String DISCUSSIONS_RANKING =
    """
        SELECT D.* ROW_NUMBER() OVER() AS Posizione
        FROM Discussioni D
        ORDER BY D.NumeroCommenti DESC;        
    """;

    //OP11
    public static final String SUGGESTED_TEXTS =
    """
        SELECT DISTINCT T2.Codice, T2.Titolo
        FROM Recensioni R, Contiene C1, Contiene C2, Testi T2
        WHERE R.Email = ?
        AND R.CodiceTesto = C1.CodiceTesto
        AND C1.CodiceTag = C2.CodiceTag
        AND C2.CodiceTesto = T2.Codice
        AND T2.Codice NOT IN (
            SELECT CodiceTesto
            FROM Recensioni
            WHERE Email = ?
        );
    """;

}
