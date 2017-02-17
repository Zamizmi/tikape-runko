/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Ketju;
import tikape.runko.domain.Viesti;

/**
 *
 * @author saklindq
 */
public class ViestiDao {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer vastattu_viesti = rs.getInt("vastattu_viesti");
        Integer ketju = rs.getInt("ketju");
        String nimimerkki = rs.getString("nimimerkki");
        String sisalto = rs.getString("sisalto");
        String aikaleima = rs.getString("aikaleima");

        Viesti viesti = new Viesti(id, vastattu_viesti, ketju, nimimerkki, sisalto, aikaleima);

        rs.close();
        stmt.close();
        connection.close();

        return viesti;
    }

    public List<Viesti> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer vastattu_viesti = rs.getInt("vastattu_viesti");
            Integer ketju = rs.getInt("ketju");
            String nimimerkki = rs.getString("nimimerkki");
            String sisalto = rs.getString("sisalto");
            String aikaleima = rs.getString("aikaleima");
           
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    public List<Viesti> findWithKetjunId(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE Viesti.vastattu_viesti = 0 AND Viesti.ketju = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer vastattu_viesti = rs.getInt("vastattu_viesti");
            Integer ketju = rs.getInt("ketju");
            String nimimerkki = rs.getString("nimimerkki");
            String sisalto = rs.getString("sisalto");
            String aikaleima = rs.getString("aikaleima");
            if (vastattu_viesti == 0) {
                viestit.add(new Viesti(id, vastattu_viesti, ketju, nimimerkki, sisalto, aikaleima));
            }
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    public List<Viesti> findWithViestinId(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE Viesti.vastattu_viesti = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer vastattu_viesti = rs.getInt("vastattu_viesti");
            Integer ketju = rs.getInt("ketju");
            String nimimerkki = rs.getString("nimimerkki");
            String sisalto = rs.getString("sisalto");
            String aikaleima = rs.getString("aikaleima");

            if (vastattu_viesti != id && vastattu_viesti == key) {
                viestit.add(new Viesti(id, vastattu_viesti, ketju, nimimerkki, sisalto, aikaleima));
            }

        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

}
