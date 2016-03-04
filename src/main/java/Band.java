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

  @Override
  public boolean equals(Object otherBand) {
    if(!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return newBand.getName().equals(name) && newBand.getMusic().equals(music);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands (name, music) VALUES (:name, :music)";
      this.id = (int) con.createQuery(sql, true).addParameter("name", name).addParameter("music", music).executeUpdate().getKey();
    }
  }


}
