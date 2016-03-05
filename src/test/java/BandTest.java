import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_returnsEmptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfBandsHaveSameNameAndMusic() {
    Band newBand = new Band("Matt and the Matties", "Electro Fusion Funk Powersoul");
    Band newBand2 = new Band("Matt and the Matties", "Electro Fusion Funk Powersoul");
    assertTrue(newBand.equals(newBand2));
  }

  @Test
  public void save_savesBandToDatabase() {
    Band newBand = new Band("Matt and the Matties", "Electro Fusion Funk Powersoul");
    newBand.save();
    assertTrue(Band.all().get(0).equals(newBand));
  }

  @Test
  public void save_savesBandIdToDatabase() {
    Band newBand = new Band("Matt and the Matties", "Electro Fusion Funk Powersoul");
    newBand.save();
    assertEquals(Band.all().get(0).getId(),newBand.getId());
  }

  @Test
  public void find_returnsBandBasedOnId() {
    Band newBand = new Band("Matt and the Matties", "Electro Fusion Funk Powersoul");
    newBand.save();
    Band newBand2 = new Band("Rosanio", "Soft Fluffy Indie Music");
    newBand2.save();
    assertTrue(Band.find(newBand.getId()).equals(newBand));
    assertTrue(Band.find(newBand2.getId()).equals(newBand2));
  }

  @Test
  public void updateName_updatesBandName() {
    Band newBand = new Band("Matt and the Matties", "Electro Fusion Funk Powersoul");
    newBand.save();
    newBand.updateName("Something Even Stupider");
    assertTrue(Band.find(newBand.getId()).equals(newBand));
  }

  @Test
  public void updateMusic_updatesBandMusicType() {
    Band newBand = new Band("Matt and the Matties", "Electro Fusion Funk Powersoul");
    newBand.save();
    newBand.updateMusic("Something Even Stupider");
    assertTrue(Band.find(newBand.getId()).equals(newBand));
  }

  @Test
  public void delete_removesBandFromDatabase() {
    Band newBand = new Band("Matt and the Matties", "Electro Fusion Funk Powersoul");
    newBand.save();
    newBand.delete();
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void addVenue_addsVenueToListOfVenuesBandHasPlayedAt() {
    Band newBand = new Band("Matt and the Matties", "Electro Fusion Funk Powersoul");
    newBand.save();
    Venue newVenue = new Venue("Epicodus", "Portland");
    newVenue.save();
    newBand.addVenue(newVenue);
    assertTrue(newBand.getVenues().contains(newVenue));
  }

  @Test
  public void removeVenue_removesVenueFromListOfVenuesBandHasPlayedAt() {
    Band newBand = new Band("Matt and the Matties", "Electro Fusion Funk Powersoul");
    newBand.save();
    Venue newVenue = new Venue("Epicodus", "Portland");
    newVenue.save();
    newBand.addVenue(newVenue);
    newBand.removeVenue(newVenue);
    assertEquals(0, newBand.getVenues().size());
  }

  @Test
  public void similarMusic_returnsListOfBandsWhoPlaySimilarMusic() {
    Band newBand = new Band("Matt and the Matties", "Electro Fusion Funk Powersoul");
    newBand.save();
    Band newBand2 = new Band("Rosanio", "fusion Punk");
    newBand2.save();
    Band newBand3 = new Band("Elliott Smith", "Soulful Indie");
    newBand3.save();
    Band newBand4 = new Band("Little Wayne", "Garbage");
    newBand4.save();
    assertTrue(newBand.similarMusic().contains(newBand2));
    assertEquals(1, newBand.similarMusic().size());
  }

  @Test
  public void sameVenues_returnsListOfBandsWhoHavePlayedAtSameVenues() {
    Band newBand = new Band("Matt and the Matties", "Electro Fusion Funk Powersoul");
    newBand.save();
    Band newBand2 = new Band("Rosanio", "fusion Punk");
    newBand2.save();
    Band newBand3 = new Band("Elliott Smith", "Soulful Indie");
    newBand3.save();
    Band newBand4 = new Band("Little Wayne", "Garbage");
    newBand4.save();
    Venue newVenue = new Venue("Epicodus", "Portland");
    newVenue.save();
    Venue newVenue2 = new Venue("Madison Square Garden", "New York City");
    newVenue2.save();
    newBand.addVenue(newVenue);
    newBand2.addVenue(newVenue2);
    newBand3.addVenue(newVenue);
    newBand4.addVenue(newVenue);
    newBand4.addVenue(newVenue2);
    assertTrue(newBand.sameVenues().contains(newBand3));
    assertTrue(newBand.sameVenues().contains(newBand4));
    assertEquals(newBand.sameVenues().size(), 2);
  }
}
