import java.util.*;
import org.sql2o.*;

public class Band {
  private int id;
  private String name;
  private String music;

  public Band(String name, String music) {
    this.name = name;
    this.music = music;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getMusic() {
    return music;
  }

  public static List<Band> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands";
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }
}
