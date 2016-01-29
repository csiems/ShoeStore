import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sql2o.*;

public class Brand {
  private int mId;
  private String mName;


  public Brand(String name) {
    mName = name;
  }

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  @Override
  public boolean equals(Object otherBrand) {
    if(!(otherBrand instanceof Brand)) {
      return false;
    } else {
      Brand newBrand = (Brand) otherBrand;
      return this.getId() == newBrand.getId() &&
             this.getName().equals(newBrand.getName());
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO brands (name) VALUES (:name)";
      mId = (int) con.createQuery(sql, true)
        .addParameter("name", mName)
        .executeUpdate()
        .getKey();
    }
  }

  public static Brand find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName FROM brands WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Brand.class);
    }
  }

  public static List<Brand> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName FROM brands";
      return con.createQuery(sql).executeAndFetch(Brand.class);
    }
  }

  public void update(String name) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE brands SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String brandDeleteSQL = "DELETE FROM brands WHERE id = :id";
      con.createQuery(brandDeleteSQL)
        .addParameter("id", mId)
        .executeUpdate();
    }
    try (Connection con = DB.sql2o.open()) {
      String joinDeleteSQL = "DELETE FROM brands_stores WHERE brand_id = :id";
      con.createQuery(joinDeleteSQL)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }
}
