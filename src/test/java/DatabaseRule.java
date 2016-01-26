import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/university_registrar_test", null, null);
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteStudentQuery = "DELETE FROM students *;";
      String deleteCourseQuery = "DELETE FROM courses *;";
      con.createQuery(deleteStudentQuery).executeUpdate();
      con.createQuery(deleteCourseQuery).executeUpdate();
    }
  }
}
