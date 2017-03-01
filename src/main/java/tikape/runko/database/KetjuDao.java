/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.DriverManager;
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

        Integer vlkm = viestienMaara(id);
            String viimeinenViesti = viimeisinViesti(id);

        Ketju k = new Ketju(id, nimi, luoja, aikaleima, alue, vlkm, viimeinenViesti);

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

            Integer vlkm = viestienMaara(id);
            String viimeinenViesti = viimeisinViesti(id);

            ketjut.add(new Ketju(id, nimi, luoja, aikaleima, alue, vlkm, viimeinenViesti));
        }

        rs.close();
        stmt.close();
        connection.close();

        return ketjut;
    }

    public List<Integer> ketjutAlueessa(int id) throws SQLException {
        List<Integer> palautettava = new ArrayList<>();
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT id FROM Ketju WHERE keskustelualue = ?");

        stmt.setObject(1, id);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            int ketjunId = rs.getInt("id");
            palautettava.add(ketjunId);
        }
        stmt.close();
        connection.close();

        return palautettava;
    }
    
    public int viestienMaara(int id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(id) FROM Viesti WHERE ketju = ?");

        stmt.setObject(1, id);
        ResultSet rs = stmt.executeQuery();

        int lkm = rs.getInt(1);
        stmt.close();
        connection.close();

        return lkm;
    }
    
    public String viimeisinViesti(int id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE ketju = ? ORDER BY id DESC LIMIT 1");

        stmt.setObject(1, id);
        ResultSet rs = stmt.executeQuery();
        
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String aikaleima = rs.getString("aikaleima");
        stmt.close();
        connection.close();

        return aikaleima;
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
            
            Integer vlkm = viestienMaara(id);
            String viimeinenViesti = viimeisinViesti(id);
            
            if (vlkm == 0) {
                viimeinenViesti = "Ei vielä viestejä";
            }

            ketjut.add(new Ketju(id, nimi, luoja, aikaleima, alue, vlkm, viimeinenViesti));
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

    public void luoKetju(String keskustelualue, String ketjun_nimi, String luoja) throws Exception {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Ketju(keskustelualue, ketjun_nimi, luoja) "
                + "VALUES (?,?,?)");
        stmt.setString(1, keskustelualue);
        stmt.setString(2, ketjun_nimi);
        stmt.setString(3, luoja);
        stmt.execute();

        conn.close();

    }

}
