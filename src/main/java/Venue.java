import java.util.*;
import org.sql2o.*;

public class Venue {
  private int id;
  private String name;
  private String location;

  public Venue(String name, String location) {
    this.name = name;
    this.location = location;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getLocation() {
    return location;
  }

  public static List<Venue> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues";
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object otherVenue) {
    if(!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return newVenue.getName().equals(name) && newVenue.getLocation().equals(location);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues (name, location) VALUES (:name, :location)";
      this.id = (int) con.createQuery(sql, true).addParameter("name", name).addParameter("location", location).executeUpdate().getKey();
    }
  }
}
