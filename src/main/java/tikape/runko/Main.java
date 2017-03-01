package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.database.KetjuDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Ketju;

public class Main {

    public static void main(String[] args) throws Exception {

        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        // käytetään oletuksena paikallista sqlite-tietokantaa
        String jdbcOsoite = "jdbc:sqlite:kanta.db";
        // jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        }

        Database db = new Database(jdbcOsoite);

        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(db);
        KetjuDao ketjuDao = new KetjuDao(db);
        ViestiDao viestiDao = new ViestiDao(db);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "samilla on värikkäät bokserit");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/keskustelualueet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelualueet", keskustelualueDao.findAll());

            return new ModelAndView(map, "keskustelualueet");
        }, new ThymeleafTemplateEngine());

        //Keskustelualueen ketjut -näkymä ketjut.html
        get("/keskustelualueet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            HashMap ketjuMap = new HashMap<>();
            map.put("keskustelualue", keskustelualueDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("ketjut", ketjuDao.findWithKeskustelualueenId(Integer.parseInt(req.params("id"))));

            for (int id : ketjuDao.ketjutAlueessa(Integer.parseInt(req.params("id")))) {
                ketjuMap.put(id, ketjuDao.viestienMaara(id));
            }

            return new ModelAndView(map, "ketjut");
        }, new ThymeleafTemplateEngine());

        //Ketjun viestit -näkymä
        get("/ketjut/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ketjut", ketjuDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("viestit", viestiDao.findWithKetjunId(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "viesti");
        }, new ThymeleafTemplateEngine());

        post("/newviesti", (req, res) -> {
            HashMap map = new HashMap<>();

            String msg = req.params("sisältö");
            String nimi = req.params("nimimerkki");
            String from = req.headers("Referer");
            res.redirect(from);
            return "";
        });

        post("/keskustelualueet/:id", (req, res) -> {

            String keskustelualue = req.params(":id");
            String ketjun_nimi = req.queryParams("ketjun_nimi");
            String luoja = req.queryParams("luoja");

            ketjuDao.luoKetju(keskustelualue, ketjun_nimi, luoja);

            String from = req.headers("Referer");
            res.redirect(from);
            return "";
        });

        //Viestin luominen
        post("/ketjut/:id", (req, res) -> {

            String ketju = req.params(":id");
            String nimimerkki = req.queryParams("nimimerkki");
            String sisalto = req.queryParams("sisalto");
            viestiDao.luoViesti(ketju, nimimerkki, sisalto);

            String from = req.headers("Referer");
            res.redirect(from);
            return "";
        });
    }
}
