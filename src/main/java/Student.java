import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sql2o.*;

public class Student {
  private String name;
  private String enrollmentDate;
  private int id;

  public Student(String name, String enrollmentDate) {
    this.name = name;
    this.enrollmentDate = enrollmentDate;
  }

  public String getName() {
    return name;
  }

  public String getEnrollmentDate() {
    return enrollmentDate;
  }

  public int getId() {
    return id;
  }

  public static List<Student> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, name, enrollment_date AS enrollmentDate FROM students";
      return con.createQuery(sql)
        .executeAndFetch(Student.class);
    }
  }

  public static Student find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, name, enrollment_date AS enrollmentDate FROM students WHERE id=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Student.class);
    }
  }

  @Override
  public boolean equals(Object otherStudent) {
    if(!(otherStudent instanceof Student)) {
      return false;
    } else {
      Student newStudent = (Student) otherStudent;
      return this.getName().equals(newStudent.getName()) &&
             this.getEnrollmentDate().equals(newStudent.getEnrollmentDate()) &&
             this.getId() == newStudent.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students (name, enrollment_date) VALUES (:name, TO_DATE (:enrollment_date, 'yyyy-mm-dd'))";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("enrollment_date", enrollmentDate)
        .executeUpdate()
        .getKey();
    }
  }

  public void delete() {
    String sql = "DELETE FROM students WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
    sql = "DELETE FROM courses_students WHERE student_id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void updateName(String name) {
    String sql = "UPDATE students SET name = :name WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", this.id)
        .executeUpdate();
        this.name = name;
    }
  }

  public void updateEnrollmentDate(String date) {
    String sql = "UPDATE students SET enrollment_date = TO_DATE (:date, 'yyyy-mm-dd') WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("date", date)
        .addParameter("id", this.id)
        .executeUpdate();
        this.enrollmentDate = date;
    }
  }

  public void assign(Course newCourse) {
    boolean isDuplicate = false;
    for (Course course : this.getCourses()) {
      if (course.equals(newCourse)) {
        isDuplicate = true;
      }
    }
    if (!isDuplicate) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO courses_students (course_id, student_id) VALUES (:course, :student)";
        con.createQuery(sql)
        .addParameter("course", newCourse.getId())
        .addParameter("student", this.id)
        .executeUpdate();
      }
    }
  }

  public List<Course> getCourses() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT courses.id, courses.name, courses.teacher FROM courses " +
                   "INNER JOIN courses_students AS c_s ON courses.id = c_s.course_id " +
                   "INNER JOIN students ON students.id = c_s.student_id WHERE c_s.student_id = :id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Course.class);
    }
  }

  public String getCourseNames() {
    List<Course> courses = this.getCourses();
    String results = "|";
    for ( Course course : courses ) {
      String newCourse = course.getName();
      results += (" " + newCourse + " |");
    }
    return results;
  }

}
