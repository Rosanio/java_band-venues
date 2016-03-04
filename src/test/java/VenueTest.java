import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_returnsEmptyAtFirst() {
    assertEquals(Venue.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfVenuesHaveSameNameAndMusic() {
    Venue newVenue = new Venue("Epicodus", "Portland");
    Venue newVenue2 = new Venue("Epicodus", "Portland");
    assertTrue(newVenue.equals(newVenue2));
  }

  @Test
  public void save_savesVenueToDatabase() {
    Venue newVenue = new Venue("Epicodus", "Portland");
    newVenue.save();
    assertTrue(Venue.all().get(0).equals(newVenue));
  }

  @Test
  public void save_savesVenueIdToDatabase() {
    Venue newVenue = new Venue("Epicodus", "Portland");
    newVenue.save();
    assertEquals(Venue.all().get(0).getId(),newVenue.getId());
  }

  @Test
  public void find_returnsVenueBasedOnId() {
    Venue newVenue = new Venue("Epicodus", "Portland");
    newVenue.save();
    Venue newVenue2 = new Venue("Madison Square Garden", "New York City");
    newVenue2.save();
    assertTrue(Venue.find(newVenue.getId()).equals(newVenue));
    assertTrue(Venue.find(newVenue2.getId()).equals(newVenue2));
  }

  // @Test
  // public void updateName_updatesVenueName() {
  //   Venue newVenue = new Venue("Epicodus", "Portland");
  //   newVenue.save();
  //   newVenue.updateName("Something Even Stupider");
  //   assertTrue(Venue.find(newVenue.getId()).equals(newVenue));
  // }
  //
  // @Test
  // public void delete_removesVenueFromDatabase() {
  //   Venue newVenue = new Venue("Epicodus", "Portland");
  //   newVenue.save();
  //   newVenue.delete();
  //   assertEquals(Venue.all().size(), 0);
  // }
}
