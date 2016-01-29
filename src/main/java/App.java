import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      response.redirect("/stores");
      return null;
    });

    get("/stores", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stores", Store.all());
      model.put("template", "templates/stores.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stores/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("store", Store.find(Integer.parseInt(request.params("id"))));
      model.put("brands", Brand.all());
      model.put("template", "templates/store.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stores/addstore", (request, response) -> {
      Store store = new Store(request.queryParams("store-name"));
      store.save();
      response.redirect("/stores/" + store.getId());
      return null;
    });

    post("/stores/:id/addbrand", (request, response) -> {
      Store store = Store.find(Integer.parseInt(request.queryParams("addbrand-store-id")));
      store.add(Integer.parseInt(request.queryParams("addbrand-brand-id")));
      response.redirect("/stores/" + store.getId());
      return null;
    });

    post("/stores/deletestore", (request, response) -> {
      Store store = Store.find(Integer.parseInt(request.queryParams("store-id")));
      store.delete();
      response.redirect("/stores");
      return null;
    });

    get("/brands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/brands.vtl");
      model.put("brands", Brand.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/brands/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("brand", Brand.find(Integer.parseInt(request.params("id"))));
      model.put("stores", Store.all());
      model.put("template", "templates/brand.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/brands/addbrand", (request, response) -> {
      Brand brand = new Brand(request.queryParams("brand-name"));
      brand.save();
      response.redirect("/brands/" + brand.getId());
      return null;
    });

    post("/brands/:id/addstore", (request, response) -> {
      Brand brand = Brand.find(Integer.parseInt(request.queryParams("addstore-brand-id")));
      brand.add(Integer.parseInt(request.queryParams("addstore-store-id")));
      response.redirect("/brands/" + brand.getId());
      return null;
    });

    post("/brands/deletebrand", (request, response) -> {
      Brand brand = Brand.find(Integer.parseInt(request.queryParams("brand-id")));
      brand.delete();
      response.redirect("/brands");
      return null;
    });


  }
}
