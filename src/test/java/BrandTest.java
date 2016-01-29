import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class BrandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Brand.all().size());
  }

  @Test
  public void brandInitiatesCorrectly() {
    Brand brand = new Brand("Waffle Racer");
    brand.save();
    Brand savedBrand = Brand.find(brand.getId());
    assertEquals("Waffle Racer", brand.getName());
    assert(brand.equals(savedBrand));
  }

  @Test
  public void delete_brandIsDeleted() {
    Brand brand = new Brand("Waffle Racer");
    brand.save();
    brand.delete();
    assertEquals(0, Brand.all().size());
  }

  @Test
  public void update_brandNameIsUpdated() {
    Brand brand = new Brand("Waffle Racer");
    brand.save();
    brand.update("Pegasus '83");
    assertEquals("Pegasus '83", Brand.find(brand.getId()).getName());
  }

  @Test
  public void add_storeIsAddedToBrand() {
    Brand brand = new Brand("Waffle Racer");
    brand.save();
    Store store = new Store("Nike Employee Store");
    store.save();
    brand.add(store);
    assertEquals(1, brand.getStores().size());
  }


}
