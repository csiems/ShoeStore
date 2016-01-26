import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sql2o.*;

public class Course {
  private String name;
  private String teacher;
  private int id;

  public Course(String name, String teacher) {
    this.name = name;
    this.teacher = teacher;
  }

  public String getName() {
    return name;
  }
  public String getTeacher() {
    return teacher;
  }
  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherCourse) {
    if(!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getName().equals(newCourse.getName()) &&
             this.getTeacher().equals(newCourse.getTeacher()) &&
             this.getId() == newCourse.getId();
    }
  }

  public void save() {
    String sql = "INSERT INTO courses (name, teacher) VALUES (:name, :teacher)";
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("teacher", this.teacher)
        .executeUpdate()
        .getKey();
    }
  }

  public static Course find(int id) {
    String sql = "SELECT * FROM courses WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Course.class);
    }
  }

  public void delete() {
    String sql = "DELETE FROM courses WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void updateName(String name) {
    String sql = "UPDATE courses SET name = :name";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("name", name)
        .executeUpdate();
    }
  }

  public void updateTeacher(String teacher) {
    String sql = "UPDATE courses SET teacher = :teacher";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("teacher", teacher)
        .executeUpdate();
    }
  }

  public static List<Course> all() {
    String sql = "SELECT * FROM courses";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Course.class);
    }
  }

  public void assign(Student student) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses_students (student_id, course_id) VALUES (:student_id, :course_id)";
        con.createQuery(sql)
          .addParameter("student_id", student.getId())
          .addParameter("course_id", this.id)
          .executeUpdate();
    }
  }

  public List<Student> getStudents() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT students.id, students.name, enrollment_date AS enrollmentDate " +
                   "FROM students INNER JOIN courses_students ON (students.id = courses_students.student_id) " +
                   "INNER JOIN courses ON (courses_students.course_id = courses.id) WHERE courses.id = :id";
        return con.createQuery(sql)
          .addParameter("id", this.id)
          .executeAndFetch(Student.class);
    }
  }


}
