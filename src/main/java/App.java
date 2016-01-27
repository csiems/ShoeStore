
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/courses.vtl");
      model.put("courses", Course.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/courses", (request, response) -> {
      Course course = new Course(request.queryParams("course-name"), request.queryParams("course-teacher"));
      course.save();
      response.redirect("/courses");
      return null;
    });

    get("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/students.vtl");
      model.put("students", Student.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/students/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/student.vtl");
      model.put("student", Student.find(Integer.parseInt(request.params("id"))));
      model.put("courses", Course.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/courses/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/course.vtl");
      model.put("course", Course.find(Integer.parseInt(request.params("id"))));
      model.put("students", Student.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/students", (request, response) -> {
      Student student = new Student(request.queryParams("student-name"), request.queryParams("student-enrollment-date"));
      student.save();
      response.redirect("/students");
      return null;
    });

    post("/add_students", (request, response) -> {
      Student student = Student.find(Integer.parseInt(request.queryParams("student_id")));
      Course course = Course.find(Integer.parseInt(request.queryParams("course_id")));
      course.assign(student);
      response.redirect("/courses/" + course.getId());
      return null;
    });

    post("/add_courses", (request, response) -> {
      Student student = Student.find(Integer.parseInt(request.queryParams("student_id")));
      Course course = Course.find(Integer.parseInt(request.queryParams("course_id")));
      student.assign(course);
      response.redirect("/students/" + student.getId());
      return null;
    });

    post("/update_course_name", (request, response) -> {
      Course course = Course.find(Integer.parseInt(request.queryParams("course_id")));
      String newName = request.queryParams("name");
      course.updateName(newName);
      response.redirect("/courses/" + course.getId());
      return null;
    });

    post("/update_course_teacher", (request, response) -> {
      Course course = Course.find(Integer.parseInt(request.queryParams("course_id")));
      String newTeacher = request.queryParams("teacher");
      course.updateTeacher(newTeacher);
      response.redirect("/courses/" + course.getId());
      return null;
    });

    post("/update_student_name", (request, response) -> {
      Student student = Student.find(Integer.parseInt(request.queryParams("student_id")));
      student.updateName(request.queryParams("name"));
      response.redirect("/students/" + student.getId());
      return null;
    });

    post("/update_student_enrollment_date", (request, response) -> {
      Student student = Student.find(Integer.parseInt(request.queryParams("student_id")));
      student.updateEnrollmentDate(request.queryParams("enrollment-date"));
      response.redirect("/students/" + student.getId());
      return null;
    });

    post("/delete_student", (request, response) -> {
      Student student = Student.find(Integer.parseInt(request.queryParams("student_id")));
      student.delete();
      response.redirect("/students");
      return null;
    });

    post("/delete_course", (request, response) -> {
      Course course = Course.find(Integer.parseInt(request.queryParams("course_id")));
      course.delete();
      response.redirect("/courses");
      return null;
    });


    // post("/task/complete/delete", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   for (Task task: Task.all(true)){
    //     if (request.queryParams(Integer.toString(task.getId())) != null) {
    //       task.delete();
    //     }
    //   }
    //   // deletedTask.delete();
    //   response.redirect("/complete");
    //   return null;
    // });
  }
}
