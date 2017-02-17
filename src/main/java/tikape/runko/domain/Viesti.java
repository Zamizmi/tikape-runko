package tikape.runko.domain;

import java.sql.Timestamp;

/**
 *
 * @author saklindq
 */
public class Viesti {

    private int id;
    private int vastattu_viesti;
    private int ketju;
    private String nimimerkki;
    private String sisalto;
    private String aikaleima;

    public Viesti(int id, int vastattu_viesti, int ketju, String nimimerkki, String sisalto, String aikaleima) {
        this.id = id;
        this.vastattu_viesti = vastattu_viesti;
        this.ketju = ketju;
        this.nimimerkki = nimimerkki;
        this.sisalto = sisalto;
        this.aikaleima = aikaleima;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int avain) {
        this.id = avain;
    }

    public int getKetju() {
        return ketju;
    }

    public int getVastattu_viesti() {
        return vastattu_viesti;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public String getSisalto() {
        return sisalto;
    }

    public void setKetju(int ketju) {
        this.ketju = ketju;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

    public void setVastattu_viesti(int vastattu_viesti) {
        this.vastattu_viesti = vastattu_viesti;
    }

    public String getAikaleima() {
        return aikaleima;
    }

    public void setAikaleima(String aikaleima) {
        this.aikaleima = aikaleima;
    }

    @Override
    public String toString() {
        return this.sisalto;
    }

}
