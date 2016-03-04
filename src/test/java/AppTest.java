import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
      goTo("http://localhost:4567/");
      assertThat(pageSource()).contains("Favorite Bands");
  }

  @Test
  public void viewBands() {
    Band newBand = new Band("Matt and the Matties", "Electro Fusion Funk Powersoul");
    newBand.save();
    goTo("http://localhost:4567/bands");
    assertThat(pageSource()).contains("Matt and the Matties");
  }

  @Test
  public void addBand() {
      goTo("http://localhost:4567/");
      fill("#bandName").with("Radiohead");
      fill("#bandMusic").with("Awesome Music");
      submit("#addBand");
      assertThat(pageSource()).contains("Radiohead");
  }

  @Test
  public void viewVenues() {
    Venue newVenue = new Venue("Epicodus", "Portland");
    newVenue.save();
    goTo("http://localhost:4567/venues");
    assertThat(pageSource()).contains("Epicodus");
  }

  @Test
  public void addVenue() {
      goTo("http://localhost:4567/");
      fill("#venueName").with("Epicodus");
      fill("#venueLocation").with("Portland");
      submit("#addVenue");
      assertThat(pageSource()).contains("Epicodus");
  }
}
