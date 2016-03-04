import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

public class BandTest {

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
}
