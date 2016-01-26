import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class CourseTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Course.all().size(), 0);
  }

  @Test
  public void courseInitiateCorrectly() {
    Course course = new Course("Applied Mathematics", "Twain");
    course.save();
    Course savedCourse = Course.find(course.getId());
    assertEquals("Applied Mathematics", course.getName());
    assertEquals(course, savedCourse);
  }

  @Test
  public void delete_courseIsDeleted() {
    Course myCourse = new Course("Applied Mathematics", "Twain");
    myCourse.save();
    myCourse.delete();
    assertEquals(Course.all().size(), 0);
  }

  @Test
  public void updateName_courseNameIsUpdated() {
    Course myCourse = new Course("Applied Mathematics", "Twain");
    myCourse.save();
    myCourse.updateName("Intro to Chemistry");
    assertEquals("Intro to Chemistry", Course.find(myCourse.getId()).getName());
  }

  @Test
  public void updateTeacher_courseTeacherIsUpdated() {
    Course myCourse = new Course("Applied Mathematics", "Twain");
    myCourse.save();
    myCourse.updateTeacher("Mark");
    assertEquals("Mark", Course.find(myCourse.getId()).getTeacher());
  }

  @Test
  public void studentAssignsToCourseSuccessfully() {
    Course course = new Course("Applied Mathematics", "Twain");
    course.save();
    Student student = new Student("Jimmy", "2016-01-26");
    student.save();
    course.assign(student);
    assertEquals(1, course.getStudents().size());
  }


}
