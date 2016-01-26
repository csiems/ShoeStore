import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
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
        assertThat(pageSource()).contains("University Registrar");
      }

    @Test
    public void courseIsCreatedTest() {
      goTo("http://localhost:4567/");
      fill("#course-name").with("Intro to Chemistry", "Twain");
      submit("#course");
      assertThat(pageSource()).contains("Intro to Chemistry");
    }

    @Test
    public void multipleCoursesAreCreated() {
      Course myFirstCourse = new Course("Intro to Chemistry", "Twain");
      myFirstCourse.save();
      Course mySecondCourse = new Course("Applied Mathematics", "Faulkner");
      mySecondCourse.save();
      goTo("http://localhost:4567/courses");
      assertThat(pageSource()).contains("Intro to Chemistry");
      assertThat(pageSource()).contains("Applied Mathematics");
    }


}
