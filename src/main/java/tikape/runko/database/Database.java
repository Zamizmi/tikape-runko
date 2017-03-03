package tikape.runko.database;

import java.sql.*;
import java.util.*;
import java.net.*;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws Exception {
        this.databaseAddress = databaseAddress;

        init();
    }

    private void init() {
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }

        return DriverManager.getConnection(databaseAddress);
    }

    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        // heroku käyttää SERIAL-avainsanaa uuden tunnuksen automaattiseen luomiseen
        lista.add("CREATE TABLE Keskustelualue\n"
                + "(\n"
                + "id SERIAL PRIMARY KEY,\n"
                + "alueen_nimi varchar(30) NOT NULL\n"
                + ");");
        lista.add("CREATE TABLE Ketju\n"
                + "(\n"
                + "id SERIAL PRIMARY KEY, \n"
                + "keskustelualue integer NOT NULL,\n"
                + "ketjun_nimi varchar(50) NOT NULL,\n"
                + "luoja varchar(50) NOT NULL,\n"
                + "aikaleima DATE DEFAULT (datetime('now','localtime')) NOT NULL, \n"
                + "FOREIGN KEY (keskustelualue) REFERENCES Keskustelualue(id)\n"
                + ");");
        lista.add("CREATE TABLE Viesti \n"
                + "( \n"
                + "id SERIAL PRIMARY KEY,\n"
                + "ketju integer NOT NULL,\n"
                + "nimimerkki varchar(50) NOT NULL,\n"
                + "sisalto varchar(500) NOT NULL, \n"
                + "aikaleima DATE DEFAULT (datetime('now','localtime')) NOT NULL,\n"
                + "FOREIGN KEY (ketju) REFERENCES Ketju(id)\n"
                + ");");
        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Oskun päiväkirja');");
        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Tommin tupa');");
        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Jokke Japani');");
        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Yleinen keskustelu');");
        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Ohjelmointi');");
        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Autot');");
        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Koulu');");
        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Lemmikit');");
        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Parhaat meemit');");
        
        return lista;
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
//        lista.add("CREATE TABLE Keskustelualue\n"
//                + "(\n"
//                + "id integer PRIMARY KEY,\n"
//                + "alueen_nimi varchar(30) NOT NULL\n"
//                + ");");
//        lista.add("CREATE TABLE Ketju\n"
//                + "(\n"
//                + "id integer PRIMARY KEY, \n"
//                + "keskustelualue integer NOT NULL,\n"
//                + "ketjun_nimi varchar(50) NOT NULL,\n"
//                + "luoja varchar(50) NOT NULL,\n"
//                + "aikaleima DATE DEFAULT (datetime('now','localtime')) NOT NULL, \n"
//                + "FOREIGN KEY (keskustelualue) REFERENCES Keskustelualue(id)\n"
//                + ");");
//        lista.add("CREATE TABLE Viesti \n"
//                + "( \n"
//                + "id integer PRIMARY KEY,\n"
//                + "ketju integer NOT NULL,\n"
//                + "nimimerkki varchar(50) NOT NULL,\n"
//                + "sisalto varchar(500) NOT NULL, \n"
//                + "aikaleima DATE DEFAULT (datetime('now','localtime')) NOT NULL,\n"
//                + "FOREIGN KEY (ketju) REFERENCES Ketju(id)\n"
//                + ");");
//        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Oskun päiväkirja');");
//        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Tommin tupa');");
//        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Jokke Japani');");
//        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Yleinen keskustelu');");
//        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Ohjelmointi');");
//        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Autot');");
//        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Koulu');");
//        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Lemmikit');");
//        lista.add("INSERT INTO Keskustelualue (alueen_nimi) VALUES ('Parhaat meemit');");

        return lista;
    }
}
