package tikape.runko.domain;

import java.sql.Date;
import java.sql.Timestamp;

public class Ketju {

    private int id;
    private String nimi;
    private String luoja;
    private String aikaleima;
    private int alue;

    public Ketju(int id, String nimi, String luoja, String aikaleima, int alue) {
        this.id = id;
        this.nimi = nimi;
        this.luoja = luoja;
        this.aikaleima = aikaleima;
        this.alue = alue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getLuoja() {
        return luoja;
    }

    public void setLuoja(String luoja) {
        this.luoja = luoja;
    }

    public String getAikaleima() {
        return aikaleima;
    }

    public void setAikaleima(String aikaleima) {
        this.aikaleima = aikaleima;
    }

    public int getAlue() {
        return alue;
    }

    public void setAlue(int alue) {
        this.alue = alue;
    }    

}
