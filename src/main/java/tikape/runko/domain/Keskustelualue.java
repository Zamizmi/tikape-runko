
package tikape.runko.domain;

public class Keskustelualue {
    
    private int id;
    private String nimi;

    public Keskustelualue(int id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int avain) {
        this.id = avain;
    }
    
    public String getNimi() {
        return this.nimi;
    }
    
    public void setNimi(String uusiNimi) {
        this.nimi = uusiNimi;
    }

    @Override
    public String toString() {
        return this.nimi;
    }
    
    
    
    
}
