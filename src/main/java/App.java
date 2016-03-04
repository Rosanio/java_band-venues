import java.util.*;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/addBand", (request, response) -> {
      String bandName = request.queryParams("bandName");
      String bandMusic = request.queryParams("bandMusic");
      if(bandName.trim().length() > 0 && bandMusic.trim().length() > 0) {
        Band newBand = new Band(bandName, bandMusic);
        newBand.save();
        response.redirect("/bands");
        return null;
      }
      response.redirect("/");
      return null;
    });

  }
}
