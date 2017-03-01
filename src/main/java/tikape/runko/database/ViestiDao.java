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

        Viesti viesti = new Viesti(id, ketju, nimimerkki, sisalto, aikaleima);

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
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE Viesti.ketju = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer ketju = rs.getInt("ketju");
            String nimimerkki = rs.getString("nimimerkki");
            String sisalto = rs.getString("sisalto");
            String aikaleima = rs.getString("aikaleima");
            viestit.add(new Viesti(id, ketju, nimimerkki, sisalto, aikaleima));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
    
    public void luoViesti(String ketju, String nimimerkki, String sisalto) throws Exception {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Viesti(ketju, nimimerkki, sisalto) "
                + "VALUES (?,?,?)");
        stmt.setString(1, ketju);
        stmt.setString(2, nimimerkki);
        stmt.setString(3, sisalto);
        stmt.execute();

        conn.close();

    }
}
