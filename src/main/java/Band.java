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
      return newBand.getName().equals(name) && newBand.getMusic().equals(music) && newBand.getId() == id;
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands (name, music) VALUES (:name, :music)";
      this.id = (int) con.createQuery(sql, true).addParameter("name", name).addParameter("music", music).executeUpdate().getKey();
    }
  }

  public static Band find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands WHERE id = :id";
      return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Band.class);    }
  }

  public void updateName(String newName) {
    this.name = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE bands SET name = :name WHERE id = :id";
      con.createQuery(sql).addParameter("name", newName).addParameter("id", id).executeUpdate();
    }
  }

  public void updateMusic(String newMusic) {
    this.music = newMusic;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE bands SET music = :music WHERE id = :id";
      con.createQuery(sql).addParameter("music", newMusic).addParameter("id", id).executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM bands WHERE id = :id";
      con.createQuery(sql).addParameter("id", id).executeUpdate();
    }
  }

  public void addVenue(Venue venue) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id)";
      con.createQuery(sql).addParameter("band_id", id).addParameter("venue_id", venue.getId()).executeUpdate();
    }
  }

  public List<Venue> getVenues() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT venues.* FROM bands JOIN bands_venues ON (bands.id = bands_venues.band_id) JOIN venues ON (venues.id = bands_venues.venue_id) WHERE bands.id = :id";
      return con.createQuery(sql).addParameter("id", id).executeAndFetch(Venue.class);
    }
  }

  public void removeVenue(Venue venue) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM bands_venues WHERE band_id = :band_id AND venue_id = :venue_id";
      con.createQuery(sql).addParameter("band_id", id).addParameter("venue_id", venue.getId()).executeUpdate();
    }
  }

  public ArrayList<Band> similarMusic() {
    String[] splitMusic = this.getMusic().split("\\s+");
    try(Connection con = DB.sql2o.open()) {
      ArrayList<Band> bands = new ArrayList<Band>();
      for(String musicWord : splitMusic) {
        musicWord = "%" + musicWord + "%";
        String sql = "SELECT * FROM bands WHERE LOWER(music) LIKE LOWER(:music)";
        List<Band> similarBands = con.createQuery(sql).addParameter("music", musicWord).executeAndFetch(Band.class);
        for(Band band : similarBands) {
          if(!band.equals(this)) {
            bands.add(band);
          }
        }
      }
      return bands;
    }
  }

  public ArrayList<Band> sameVenues() {
    List<Venue> venues = this.getVenues();
    try(Connection con = DB.sql2o.open()) {
      ArrayList<Band> bands = new ArrayList<Band>();
      for(Venue venue : venues) {
        String sql = "SELECT bands.* FROM bands_venues JOIN bands ON (bands.id = bands_venues.band_id) WHERE bands_venues.venue_id = :id";
        List<Band> similarBands = con.createQuery(sql).addParameter("id", venue.getId()).executeAndFetch(Band.class);
        for(Band band : similarBands) {
          if(!band.equals(this)) {
            if(!bands.contains(band)) {
              bands.add(band);
            }
          }
        }
      }
      return bands;
    }
  }
}
