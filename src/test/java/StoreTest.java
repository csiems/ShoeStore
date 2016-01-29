import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class StoreTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Store.all().size());
  }

  @Test
  public void storeInitiatesCorrectly() {
    Store store = new Store("Nike Outlet");
    store.save();
    Store savedStore = Store.find(store.getId());
    assertEquals("Nike Outlet", store.getName());
    assert(store.equals(savedStore));
  }

  @Test
  public void delete_storeIsDeleted() {
    Store store = new Store("Nike Outlet");
    store.save();
    store.delete();
    assertEquals(0, Store.all().size());
  }

  @Test
  public void update_storeNameIsUpdated() {
    Store store = new Store("Nike Outlet");
    store.save();
    store.update("Nike Employee Store");
    assertEquals("Nike Employee Store", Store.find(store.getId()).getName());
  }

  @Test
  public void add_brandIsAddedToStore() {
    Brand brand = new Brand("Waffle Racer");
    brand.save();
    Store store = new Store("Nike Employee Store");
    store.save();
    store.add(brand.getId());
    assertEquals(1, store.getBrands().size());
  }

}
