import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sql2o.*;

public class Store {
  private int mId;
  private String mName;

  public Store(String name) {
    mName = name;
  }

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  @Override
  public boolean equals(Object otherStore) {
    if(!(otherStore instanceof Store)) {
      return false;
    } else {
      Store newStore = (Store) otherStore;
      return this.getId() == newStore.getId() &&
             this.getName().equals(newStore.getName());
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stores (name) VALUES (:name)";
      mId = (int) con.createQuery(sql, true)
        .addParameter("name", mName)
        .executeUpdate()
        .getKey();
    }
  }

  public static Store find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName FROM stores WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Store.class);
    }
  }

  public static List<Store> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName FROM stores";
      return con.createQuery(sql).executeAndFetch(Store.class);
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String storeTableSQL = "DELETE FROM stores WHERE id = :id";
      con.createQuery(storeTableSQL)
        .addParameter("id", mId)
        .executeUpdate();
    }
    try (Connection con = DB.sql2o.open()) {
      String joinTableSQL = "DELETE FROM brands_stores WHERE store_id = :id";
      con.createQuery(joinTableSQL)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public void update(String name) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stores SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }


}
