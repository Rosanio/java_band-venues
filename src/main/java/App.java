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

    get("/venues", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("venues", Venue.all());
      model.put("template", "templates/venues.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/addVenue", (request, response) -> {
      String venueName = request.queryParams("venueName");
      String venueLocation = request.queryParams("venueLocation");
      if(venueName.trim().length() > 0 && venueLocation.trim().length() > 0) {
        Venue newVenue = new Venue(venueName, venueLocation);
        newVenue.save();
        response.redirect("/venues");
        return null;
      }
      response.redirect("/");
      return null;
    });

    get("/bands/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      model.put("band", band);
      model.put("venues", band.getVenues());
      model.put("allVenues", Venue.all());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params(":id")));
      model.put("venue", venue);
      model.put("venues", venue.getBands());
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:id/updateName", (request, response) -> {
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      String newName = request.queryParams("newName");
      if(newName.trim().length() > 0) {
        band.updateName(newName);
        response.redirect("/bands/" + band.getId());
        return null;
      }
      response.redirect("/bands/" + band.getId());
      return null;
    });

    post("/bands/:id/updateMusic", (request, response) -> {
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      String newMusic = request.queryParams("newMusic");
      if(newMusic.trim().length() > 0) {
        band.updateMusic(newMusic);
        response.redirect("/bands/" + band.getId());
        return null;
      }
      response.redirect("/bands/" + band.getId());
      return null;
    });

    post("/bands/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band deletedBand = Band.find(Integer.parseInt(request.params(":id")));
      deletedBand.delete();
      model.put("bands", Band.all());
      model.put("deletedBand", deletedBand);
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:id/addVenue", (request, response) -> {
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      Venue venue = Venue.find(Integer.parseInt(request.queryParams("venueSelect")));
      band.addVenue(venue);
      response.redirect("/bands/" + band.getId());
      return null;
    });

  }
}
