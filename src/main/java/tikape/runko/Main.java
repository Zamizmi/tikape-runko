package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KeskustelualueDao;
import tikape.runko.database.OpiskelijaDao;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        KeskustelualueDao keskustelualueDao = new KeskustelualueDao(database);

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
        
        get("/keskustelualueet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelualue", keskustelualueDao.findOne(Integer.parseInt(req.params("id"))));
            
            return new ModelAndView(map, "keskustelualue");
        }, new ThymeleafTemplateEngine());

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
