import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Rule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
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
    assertThat(pageSource()).contains("Shoe Stores");
  }

  @Test
  public void storeIsCreatedTest() {
    goTo("http://localhost:4567/");
    fill("#store-name").with("Nike Outlet");
    submit(".btn");
    assertThat(pageSource()).contains("Nike Outlet");
  }

  @Test
  public void brandIsCreatedTest() {
    goTo("http://localhost:4567/brands");
    fill("#brand-name").with("Waffle Racer");
    submit(".btn");
    assertThat(pageSource()).contains("Waffle Racer");
  }

  @Test
  public void brandIsDeletedTest() {
    Brand brand = new Brand("Waffle Racer");
    brand.save();
    goTo("http://localhost:4567/brands/" + brand.getId());
    submit(".deletebtn");
    assertEquals(0, Brand.all().size());
  }

  @Test
  public void storeIsDeletedTest() {
    Store store = new Store("Nike Outlet");
    store.save();
    goTo("http://localhost:4567/stores/" + store.getId());
    submit(".deletebtn");
    assertEquals(0, Store.all().size());
  }

  @Test
  public void brandIsAddedToStore() {
    Store store = new Store("Nike Outlet");
    store.save();
    Brand brand = new Brand("Waffle Racer");
    brand.save();
    goTo("http://localhost:4567/stores/" + store.getId());
    submit("addbrandbtn");
    assertThat(pageSource()).contains("Waffle Racer");
  }




}
