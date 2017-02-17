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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Ketju;

/**
 *
 * @author twviiala
 */
public class KetjuDao implements Dao<Ketju, Integer> {

    private Database database;

    public KetjuDao(Database database) {
        this.database = database;
    }

    @Override
    public Ketju findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ketju WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("ketjun_nimi");
        String luoja = rs.getString("luoja");
        String aikaleima = rs.getString("aikaleima");
        Integer alue = rs.getInt("keskustelualue");

        Ketju k = new Ketju(id, nimi, luoja, aikaleima, alue);

        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    @Override
    public List<Ketju> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ketju");

        ResultSet rs = stmt.executeQuery();
        List<Ketju> ketjut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("ketjun_nimi");
            String luoja = rs.getString("luoja");
            String aikaleima = rs.getString("aikaleima");
            Integer alue = rs.getInt("keskustelualue");

            ketjut.add(new Ketju(id, nimi, luoja, aikaleima, alue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return ketjut;
    }
    
        
    public List<Ketju> findWithKeskustelualueenId(Integer key) throws SQLException {
        
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ketju WHERE Ketju.keskustelualue = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        List<Ketju> ketjut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("ketjun_nimi");
            String luoja = rs.getString("luoja");
            String aikaleima = rs.getString("aikaleima");
            Integer alue = rs.getInt("keskustelualue");

            ketjut.add(new Ketju(id, nimi, luoja, aikaleima, alue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return ketjut;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
