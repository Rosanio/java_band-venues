import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/band_venues_test", "Matt", "Mro13542");
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteBands = "DELETE FROM bands *;";
      String deleteVenues = "DELETE FROM venues *;";
      String deleteBandsVenues = "DELETE FROM bands_venues *;";
      con.createQuery(deleteBands).executeUpdate();
      con.createQuery(deleteVenues).executeUpdate();
      con.createQuery(deleteBandsVenues).executeUpdate();
    }
  }
}
