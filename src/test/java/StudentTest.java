import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Student.all().size(), 0);
  }

  @Test
  public void studentInitiateCorrectly() {
    Student student = new Student("Mark", "1880-12-12");
    student.save();
    Student savedStudent = Student.find(student.getId());
    assertEquals("Mark", student.getName());
    assertEquals("1880-12-12", student.getEnrollmentDate());
    assertEquals(student, savedStudent);
  }

  @Test
  public void delete_studentIsDeleted() {
    Student myStudent = new Student("Mark", "1880-12-12");
    myStudent.save();
    myStudent.delete();
    assertEquals(Student.all().size(), 0);
  }

  @Test
  public void updateName_studentNameIsUpdated() {
    Student myStudent = new Student("Mark", "1880-12-12");
    myStudent.save();
    myStudent.updateName("Jim");
    assertEquals("Jim", Student.find(myStudent.getId()).getName());
  }

  @Test
  public void updateEnrollmentDate_studentEnrollmentDateIsUpdated() {
    Student myStudent = new Student("Mark", "1880-12-12");
    myStudent.save();
    myStudent.updateEnrollmentDate("1780-12-12");
    assertEquals("1780-12-12", myStudent.getEnrollmentDate());
    assertEquals("1780-12-12", Student.find(myStudent.getId()).getEnrollmentDate());
  }

  @Test
  public void courseAssignsToStudentSuccessfully() {
    Course course = new Course("Applied Mathematics", "Twain");
    course.save();
    Student student = new Student("Jimmy", "2016-01-26");
    student.save();
    student.assign(course);
    assertEquals(1, student.getCourses().size());
  }

}
