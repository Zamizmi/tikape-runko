package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.database.KetjuDao;
import tikape.runko.database.ViestiDao;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);
        KetjuDao ketjuDao = new KetjuDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

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
            map.put("ketjut", ketjuMap);
            

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
        
        post("/ketjut/:id", (req, res) -> {

            String ketju = req.params(":id");
            String nimimerkki = req.queryParams("nimimerkki");
            String sisalto = req.queryParams("sisalto");
            
            viestiDao.luoViesti(ketju, nimimerkki, sisalto);
            
            String from = req.headers("Referer");
            res.redirect(from);
            return "";
        });

//        get("/opiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelijat", opiskelijaDao.findAll());
//
//            return new ModelAndView(map, "opiskelijat");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));
//
//            return new ModelAndView(map, "opiskelija");
//        }, new ThymeleafTemplateEngine());
    }
}
