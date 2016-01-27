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

    @Test
    public void StudentIsCreated() {
      goTo("http://localhost:4567/");
      fill("#student-name").with("Mark");
      fill("#student-enrollment-date").with("1999-11-11");
      submit("#student");
      assertThat(pageSource()).contains("Mark");
      assertThat(pageSource()).contains("1999-11-11");
    }

    @Test
    public void multipleStudentsAreCreated() {
      Student myFirstStudent = new Student("Bob", "2000-01-01");
      myFirstStudent.save();
      Student mySecondStudent = new Student("Jordan", "2001-01-01");
      mySecondStudent.save();
      goTo("http://localhost:4567/students");
      assertThat(pageSource()).contains("Bob");
      assertThat(pageSource()).contains("Jordan");
    }

    @Test
    public void studentIsAssignedToCourse(){
      Course myCourse = new Course("Intro to Chemistry", "Twain");
      myCourse.save();
      Student myStudent = new Student("Bob", "2000-01-01");
      myStudent.save();
      myCourse.assign(myStudent);
      goTo("http://localhost:4567/courses/" + myCourse.getId());
      assertThat(pageSource()).contains("Bob");
    }

    @Test
    public void courseIsAssignedToStudent(){
      Course myCourse = new Course("Intro to Chemistry", "Twain");
      myCourse.save();
      Student myStudent = new Student("Bob", "2000-01-01");
      myStudent.save();
      myStudent.assign(myCourse);
      goTo("http://localhost:4567/students/" + myStudent.getId());
      assertThat(pageSource()).contains("Intro to Chemistry");
    }

    @Test
    public void courseNameIsUpdated(){
      Course myCourse = new Course("Intro to Chemistry", "Twain");
      myCourse.save();
      goTo("http://localhost:4567/courses/" + myCourse.getId());
      fill("#name").with("Applied Mathematics");
      submit("#name-update");
      assertThat(pageSource()).contains("Applied Mathematics");
    }

    @Test
    public void courseTeacherIsUpdated(){
      Course myCourse = new Course("Intro to Chemistry", "Twain");
      myCourse.save();
      goTo("http://localhost:4567/courses/" + myCourse.getId());
      fill("#teacher").with("William Faulkner");
      submit("#teacher-update");
      assertThat(pageSource()).contains("William Faulkner");
    }

    @Test
    public void studentNameIsUpdated(){
      Student myStudent = new Student("Bob", "2000-01-01");
      myStudent.save();
      goTo("http://localhost:4567/students/" + myStudent.getId());
      fill("#name").with("Mark");
      submit("#name-update");
      assertThat(pageSource()).contains("Mark");
    }

    @Test
    public void studentEnrollmentDateIsUpdated(){
      Student myStudent = new Student("Bob", "2000-01-01");
      myStudent.save();
      goTo("http://localhost:4567/students/" + myStudent.getId());
      fill("#enrollment-date").with("2015-04-09");
      submit("#date-update");
      assertThat(pageSource()).contains("2015-04-09");
    }
}
