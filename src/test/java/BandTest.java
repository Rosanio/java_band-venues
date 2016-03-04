import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

public class BandTest {

  @Test
  public void all_returnsEmptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }
}
