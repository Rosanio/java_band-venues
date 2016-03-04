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

  public static List<Venue> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues";
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }
}
